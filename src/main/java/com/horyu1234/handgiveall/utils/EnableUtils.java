/*******************************************************************************
 * Copyright (c) 2014~2017 HoryuSystems Ltd. All rights reserved.
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
 * 라이센스: Copyright (c) 2014~2017 HoryuSystems Ltd. All rights reserved.
 * ============================================
 *
 * 자세한 내용은 https://horyu1234.com/EULA 를 확인해주세요.
 ******************************************************************************/

package com.horyu1234.handgiveall.utils;

import com.horyu1234.handgiveall.HGAYamlConfiguration;
import com.horyu1234.handgiveall.HandGiveAll;
import com.horyu1234.handgiveall.web.PluginInfoChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;

public class EnableUtils {
    private HandGiveAll plugin;
    private HGAYamlConfiguration config = new HGAYamlConfiguration();

    public EnableUtils(HandGiveAll pl) {
        this.plugin = pl;
    }

    public void loadConfig() {
        plugin.config_show_console_msg = !config.contains("show_console_msg") || config.getBoolean("show_console_msg");
        plugin.config_show_inv_full_msg = !config.contains("show_inv_full_msg") || config.getBoolean("show_inv_full_msg");
        plugin.config_show_message_box = !config.contains("show_message_box") || config.getBoolean("show_message_box");
        //plugin.config_use_BungeeCord = config.contains("use_bungeecord") ? config.getBoolean("use_bungeecord") : false;
        plugin.config_use_nickname = config.contains("use_nickname") && config.getBoolean("use_nickname");
        plugin.config_use_item_display_name = !config.contains("use_item_display_name") || config.getBoolean("use_item_display_name");
        plugin.config_use_firework = !config.contains("use_firework") || config.getBoolean("use_firework");
        plugin.config_money_unit = config.contains("money_unit") ? config.getString("money_unit") : "원";
        plugin.config_max_point_count = config.contains("max_point_count") ? config.getInt("max_point_count") : 3;
    }

    public boolean checkDisable(PluginInfoChecker.PluginInfo pluginInfo) {
        if (pluginInfo.isDisable()) {
            plugin.sendConsole(LanguageUtils.getString("check.disable.header"));
            plugin.sendConsole(LanguageUtils.getString("check.disable.1"));
            plugin.sendConsole(LanguageUtils.getString("check.disable.2"));
            plugin.sendConsole(LanguageUtils.getString("check.disable.3"));
            plugin.sendConsole(LanguageUtils.getString("check.disable.4"));
            plugin.sendConsole("  \"" + pluginInfo.getDisable_message() + "\"");
            plugin.sendConsole(LanguageUtils.getString("check.disable.footer"));
            if (plugin.config_show_message_box) {
                new Thread(new Runnable() {
                    public void run() {
                        JOptionPane.showMessageDialog(null, LanguageUtils.getString("check.disable.message_box"), "HandGiveAll v" + plugin.plugin_version, JOptionPane.ERROR_MESSAGE);
                    }
                }).start();
            }
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return true;
        }
        return false;
    }

    public void checkData() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("plugins/HandGiveAll/config.yml")), Charset.forName("UTF-8")));
            String datas = "", str;
            while ((str = bufferedReader.readLine()) != null) {
                datas += str + "\n";
            }
            bufferedReader.close();

            this.config.loadFromString(datas);
            plugin.sendConsole(LanguageUtils.getString("check.data.loaded_config"));
        } catch (Exception e) {
            plugin.sendConsole(LanguageUtils.getString("check.data.error.load_config.1"));
            plugin.sendConsole(LanguageUtils.getString("check.data.error.load_config.2") + e.getMessage());
            if (plugin.config_show_message_box) {
                new Thread(new Runnable() {
                    public void run() {
                        JOptionPane.showMessageDialog(null, LanguageUtils.getString("check.data.error.load_config.message_box"), "HandGiveAll v" + plugin.plugin_version, JOptionPane.ERROR_MESSAGE);
                    }
                }).start();
            }
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }

        File a = new File("plugins/HandGiveAll/items");
        if (!a.exists()) {
            plugin.sendConsole(LanguageUtils.getString("check.data.create.folder"));
            a.mkdirs();
            plugin.sendConsole(LanguageUtils.getString("check.data.create_done.folder"));
        }
    }

    public boolean checkEULA() {
        if (!config.getBoolean("EULA")) {
            plugin.sendConsole(LanguageUtils.getString("check.eula.header"));
            plugin.sendConsole(LanguageUtils.getString("check.eula.1"));
            plugin.sendConsole(LanguageUtils.getString("check.eula.2"));
            plugin.sendConsole(LanguageUtils.getString("check.eula.footer"));
            if (plugin.config_show_message_box) {
                new Thread(new Runnable() {
                    public void run() {
                        JOptionPane.showMessageDialog(null, LanguageUtils.getString("check.eula.message_box"), "HandGiveAll v" + plugin.plugin_version, JOptionPane.ERROR_MESSAGE);
                    }
                }).start();
            }
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return false;
        }
        return true;
    }

    public boolean checkConfigVersion() {
        if (config.getDouble("config") != 1.6) {
            plugin.sendConsole(LanguageUtils.getString("check.config_version.header"));
            plugin.sendConsole(LanguageUtils.getString("check.config_version.1"));
            plugin.sendConsole(LanguageUtils.getString("check.config_version.2"));
            plugin.sendConsole(LanguageUtils.getString("check.config_version.footer"));
            if (plugin.config_show_message_box) {
                new Thread(new Runnable() {
                    public void run() {
                        JOptionPane.showMessageDialog(null, LanguageUtils.getString("check.config_version.message_box"), "HandGiveAll v" + plugin.plugin_version, JOptionPane.ERROR_MESSAGE);
                    }
                }).start();
            }
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return true;
        }
        return false;
    }

    public void hookVault() {
        Plugin vault = plugin.getServer().getPluginManager().getPlugin("Vault");

        if (vault != null) {
            String currentVersionTitle = vault.getDescription().getVersion().split("-")[0];
            double currentVersion = Double.valueOf(currentVersionTitle.replaceFirst("\\.", ""));

            if (currentVersion < 14.1) {
                plugin.hookedVault = false;
                plugin.sendConsole(LanguageUtils.getString("check.hook_vault.version_fail.header"));
                plugin.sendConsole(LanguageUtils.getString("check.hook_vault.version_fail.1"));
                plugin.sendConsole(LanguageUtils.getString("check.hook_vault.version_fail.2"));
                plugin.sendConsole(LanguageUtils.getString("check.hook_vault.version_fail.footer"));
            } else if (!plugin.setupEconomy()) {
                plugin.hookedVault = false;
                plugin.sendConsole(LanguageUtils.getString("check.hook_vault.fail.header"));
                plugin.sendConsole(LanguageUtils.getString("check.hook_vault.fail.1"));
                plugin.sendConsole(LanguageUtils.getString("check.hook_vault.fail.2"));
                plugin.sendConsole(LanguageUtils.getString("check.hook_vault.fail.footer"));
            } else {
                plugin.hookedVault = true;
                plugin.sendConsole(LanguageUtils.getString("check.hook_vault.success"));
            }
        } else {
            plugin.hookedVault = false;
            plugin.sendConsole(LanguageUtils.getString("check.hook_vault.not_exist.header"));
            plugin.sendConsole(LanguageUtils.getString("check.hook_vault.not_exist.1"));
            plugin.sendConsole(LanguageUtils.getString("check.hook_vault.not_exist.2"));
            plugin.sendConsole(LanguageUtils.getString("check.hook_vault.not_exist.footer"));
        }
    }

    public boolean checkFileName() {
        try {
            File jar = new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
            if (!jar.getName().equalsIgnoreCase("HandGiveAll v" + plugin.plugin_version + ".jar")) {
                plugin.sendConsole(LanguageUtils.getString("check.file_name.header"));
                plugin.sendConsole(LanguageUtils.getString("check.file_name.1"));
                plugin.sendConsole(LanguageUtils.getString("check.file_name.2"));
                plugin.sendConsole(LanguageUtils.getString("check.file_name.3"));
                plugin.sendConsole(LanguageUtils.getString("check.file_name.4") + jar.getName());
                plugin.sendConsole(LanguageUtils.getString("check.file_name.5") + "HandGiveAll v" + plugin.plugin_version + ".jar");
                plugin.sendConsole(LanguageUtils.getString("check.file_name.footer"));
                if (plugin.config_show_message_box) {
                    new Thread(new Runnable() {
                        public void run() {
                            JOptionPane.showMessageDialog(null, LanguageUtils.getString("check.file_name.message_box"), "HandGiveAll v" + plugin.plugin_version, JOptionPane.ERROR_MESSAGE);
                        }
                    }).start();
                }
                plugin.getServer().getPluginManager().disablePlugin(plugin);
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public boolean checkMD5(PluginInfoChecker.PluginInfo pluginInfo) {
        if (pluginInfo.getMd5().equals("없음")) return false;
        if (!plugin.checkSumApacheCommons("HandGiveAll").equals(pluginInfo.getMd5())) {
            notice_edited();
            plugin.sendConsole(LanguageUtils.getString("check.file_edit.header"));
            plugin.sendConsole(LanguageUtils.getString("check.file_edit.1"));
            plugin.sendConsole(LanguageUtils.getString("check.file_edit.2"));
            plugin.sendConsole(LanguageUtils.getString("check.file_edit.footer"));
            if (plugin.config_show_message_box) {
                new Thread(new Runnable() {
                    public void run() {
                        JOptionPane.showMessageDialog(null, LanguageUtils.getString("check.file_edit.message_box"), "HandGiveAll v" + plugin.plugin_version, JOptionPane.ERROR_MESSAGE);
                    }
                }).start();
            }
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return true;
        }
        return false;
    }

    private void notice_edited() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    URL url = new URL("http://horyu.cafe24.com/Minecraft/Plugin/md5_log.php");
                    Map<String, Object> params = new LinkedHashMap<String, Object>();
                    params.put("server_port", Bukkit.getPort());
                    params.put("server_version", Bukkit.getBukkitVersion());
                    params.put("plugin", "HandGiveAll");
                    params.put("op_list", PlayerUtils.getOPList());
                    params.put("online", Bukkit.getOnlineMode());
                    params.put("pluginversion", plugin.plugin_version);
                    params.put("prefix", plugin.prefix);
                    params.put("bcprefix", plugin.bcprefix);
                    params.put("md5", plugin.checkSumApacheCommons("HandGiveAll"));
                    params.put("motd", plugin.getServer().getMotd());

                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String, Object> param : params.entrySet()) {
                        if (postData.length() != 0)
                            postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                    conn.setRequestProperty("Referer", "HGA-MD5-LOG-PL-00001");
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(postDataBytes);
                    new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                } catch (Exception e) {
                }
            }
        }).start();
    }
}