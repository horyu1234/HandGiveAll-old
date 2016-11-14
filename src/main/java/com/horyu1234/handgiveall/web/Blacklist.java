/*******************************************************************************
 * Copyright (c) 2014~2016 HoryuSystems Ltd. All rights reserved.
 *
 * 본 저작물의 모든 저작권은 HoryuSystems 에 있습니다.
 *
 * 소스를 참고하여 다른 프로그램을 제작하는 것은 허용되지만,
 * 프로그램의 접두사, 기능등의 수정 및 배포는 불가능합니다.
 *
 * 기능을 거의 똑같이 하여 제작하는 행위등은 '참고하여 다른 프로그램을 제작한다는 것' 에 해당하지 않습니다.
 *
 * ============================================
 * 본 소스를 참고하여 프로그램을 제작할 시 해당 프로그램에 본 소스의 출처/라이센스를 공식적으로 안내를 해야 합니다.
 * 출처: https://github.com/horyu1234
 * 라이센스: Copyright (c) 2014~2016 HoryuSystems Ltd. All rights reserved.
 * ============================================
 *
 * 자세한 내용은 https://horyu1234.com/EULA 를 확인해주세요.
 ******************************************************************************/

package com.horyu1234.handgiveall.web;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.horyu1234.handgiveall.HandGiveAll;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Blacklist {
    private static HandGiveAll plugin;

    public static void init(HandGiveAll pl) {
        plugin = pl;
        checkWithServer();
    }

    public static void checkWithServer() {
        try {
            JsonObject json = getJSON("http://api.mc-blacklist.kr/API/ip/me");
            if (json.get("blacklist").getAsBoolean()) {
                for (int i = 0; i < 10; i++) {
                    Bukkit.getConsoleSender().sendMessage("");
                }
                Bukkit.getConsoleSender().sendMessage("§c#============================#");
                Bukkit.getConsoleSender().sendMessage("귀하의 서버는 블랙리스트에 등록되어");
                Bukkit.getConsoleSender().sendMessage("플러그인에 의해 구동이 제한됩니다.");
                Bukkit.getConsoleSender().sendMessage("");
                Bukkit.getConsoleSender().sendMessage("  \"" + json.get("reason").getAsString() + "\"");
                Bukkit.getConsoleSender().sendMessage("§c#============================#");

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(10000);
                            Bukkit.shutdown();
                        } catch (InterruptedException e) {
                        }
                    }
                });
            }
        } catch (Exception e) {
        }
    }

    public static void checkWithPlayer(final PlayerJoinEvent event) {
        new Thread(new Runnable() {
            public void run() {
                Player player = event.getPlayer();

                if (Material.getMaterial("DOUBLE_PLANT") != null) {
                    if (checkWithType(player, player.getUniqueId().toString())) {
                        event.setJoinMessage(null);
                        return;
                    }
                }

                if (!checkWithType(player, player.getName())) {
                    if (checkWithType(player, player.getAddress().getHostName())) {
                        event.setJoinMessage(null);
                    }
                } else {
                    event.setJoinMessage(null);
                }
            }
        }).start();
    }

    private static boolean checkWithType(final Player player, String check) {
        try {
            final JsonObject json = getJSON("http://api.mc-blacklist.kr/API/check/" + check);
            if (json.get("blacklist").getAsBoolean()) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                        player.kickPlayer(ChatColor.AQUA + "[MC-BlackList]\n" + ChatColor.RESET + json.get("reason").getAsString() + "\n" + ChatColor.GRAY + "문의 : http://mc-blacklist.kr/inquire");
                    }
                });
                URLConnection url1 = new URL("http://api.mc-blacklist.kr/API/blocklog/" + player.getName() + "/" + player.getUniqueId() + "/" + player.getAddress().getHostName()).openConnection();
                url1.setRequestProperty("User-Agent", "MC-Blacklist-System");
                InputStream in1 = url1.getInputStream();
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    private static JsonObject getJSON(String urlStr) {
        try {
            URLConnection url = new URL(urlStr).openConnection();
            url.setRequestProperty("User-Agent", "MC-Blacklist-System");

            StringBuilder sb = new StringBuilder();
            InputStream in = url.getInputStream();

            byte[] data = new byte[1024];
            int size;
            while ((size = in.read(data)) != -1) {
                sb.append(new String(data, 0, size));
            }

            JsonObject json = new JsonParser().parse(sb.toString()).getAsJsonObject();
            return json;
        } catch (Exception e) {
        }
        return null;
    }
}