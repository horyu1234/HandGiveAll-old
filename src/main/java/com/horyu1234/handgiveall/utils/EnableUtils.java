/*******************************************************************************
 * Copyright (c) 2014~2016 HoryuSystems All rights reserved.
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
 * 라이센스: Copyright (c) 2014~2016 HoryuSystems All rights reserved.
 * ============================================
 *
 * 소스에 대한 피드백등은 언제나 환영합니다! 아래는 개발자 연락처입니다.
 *
 * Skype: horyu1234
 * KakaoTalk: horyu1234
 * Telegram: @horyu1234
 ******************************************************************************/

package com.horyu1234.handgiveall.utils;

import com.horyu1234.handgiveall.HandGiveAll;
import com.horyu1234.handgiveall.web.PluginInfoChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class EnableUtils {
	private HandGiveAll plugin;
	public EnableUtils(HandGiveAll pl) {
		this.plugin = pl;
	}

	public void loadConfig() {
		plugin.config_show_console_msg = plugin.getConfig().contains("show_console_msg") ? plugin.getConfig().getBoolean("show_console_msg") : true;
		plugin.config_show_inv_full_msg = plugin.getConfig().contains("show_inv_full_msg") ? plugin.getConfig().getBoolean("show_inv_full_msg") : true;
		//plugin.config_use_BungeeCord = plugin.getConfig().contains("use_bungeecord") ? plugin.getConfig().getBoolean("use_bungeecord") : false;
		plugin.config_use_nickname = plugin.getConfig().contains("use_nickname") ? plugin.getConfig().getBoolean("use_nickname") : false;
		plugin.config_use_item_display_name = plugin.getConfig().contains("use_item_display_name") ? plugin.getConfig().getBoolean("use_item_display_name") : true;
		plugin.config_use_firework = plugin.getConfig().contains("use_firework") ? plugin.getConfig().getBoolean("use_firework") : true;
		plugin.config_money_unit = plugin.getConfig().contains("money_unit") ? plugin.getConfig().getString("money_unit") : "원";
		plugin.config_max_point_count = plugin.getConfig().contains("max_point_count") ? plugin.getConfig().getInt("max_point_count") : 3;
	}

	public boolean checkDisable(PluginInfoChecker.PluginInfo pluginInfo) {
		if (pluginInfo.isDisable()) {
			plugin.sendConsole("§c#==============================#");
			plugin.sendConsole("본 플러그인의 제작자가 플러그인의 구동을 비활성화하여");
			plugin.sendConsole("플러그인 구동이 제한됩니다.");
			plugin.sendConsole("");
			plugin.sendConsole("§4사유: ");
			plugin.sendConsole("  \"" + pluginInfo.getDisable_message() + "\"");
			plugin.sendConsole("§c#==============================#");
			new Thread(new Runnable() {
				public void run() {
					JOptionPane.showMessageDialog(null, "본 플러그인의 제작자가 플러그인의 구동을 비활성화하여\n플러그인의 구동이 제한됩니다.", "HandGiveAll v" + plugin.plugin_version, JOptionPane.ERROR_MESSAGE);
				}
			}).start();
			plugin.getServer().getPluginManager().disablePlugin(plugin);
			return true;
		}
		return false;
	}

	public void checkData() {
		if (!new File(plugin.getDataFolder(), "config.yml").exists()) {
			plugin.sendConsole("§c설정 파일을 찾을 수 없습니다. 새로 생성을 시작합니다...");
			plugin.saveDefaultConfig();
			plugin.sendConsole("§a완료");
			plugin.reloadConfig();
		}
		File a = new File("plugins/HandGiveAll/items");
		if (!a.exists()) {
			plugin.sendConsole("§c데이터 폴더를 찾을 수 없습니다. 새로 생성을 시작합니다...");
			a.mkdirs();
			plugin.sendConsole("§a완료");
		}
	}

	public boolean checkEULA() {
		if (!plugin.getConfig().getBoolean("EULA")) {
			plugin.sendConsole("§f#==============================#");
			plugin.sendConsole("§cConfig 에서 플러그인의 EULA에 동의해주세요!!");
			plugin.sendConsole("§cEULA를 동의하지 않으시면 플러그인의 사용이 불가능합니다.");
			plugin.sendConsole("§f#==============================#");
			new Thread(new Runnable() {
				public void run() {
					JOptionPane.showMessageDialog(null, "Config 에서 플러그인의 EULA 에 동의해주시기 바랍니다.", "HandGiveAll v" + plugin.plugin_version, JOptionPane.ERROR_MESSAGE);
				}
			}).start();
			plugin.getServer().getPluginManager().disablePlugin(plugin);
			return false;
		}
		return true;
	}

	public boolean checkConfigVersion() {
		if (plugin.getConfig().getDouble("config") != 1.5) {
			plugin.sendConsole("§f#==============================#");
			plugin.sendConsole("§cConfig 의 버전이 맞지 않습니다!");
			plugin.sendConsole("§cHandGiveAll 폴더 안의 config.yml 을 삭제하신 후 플러그인을 다시 실행해주시기 바랍니다.");
			plugin.sendConsole("§f#==============================#");
			new Thread(new Runnable() {
				public void run() {
					JOptionPane.showMessageDialog(null, "Config 의 버전이 맞지 않습니다.", "HandGiveAll v" + plugin.plugin_version, JOptionPane.ERROR_MESSAGE);
				}
			}).start();
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
				plugin.sendConsole("§f#==============================#");
				plugin.sendConsole("§cVault 가 존재하지만, 호환되지 않는 버전입니다.");
				plugin.sendConsole("§cVault 버전을 1.4.1 버전 이상으로 업데이트 바랍니다.");
				plugin.sendConsole("§f#==============================#");
			} else if (!plugin.setupEconomy()) {
				plugin.hookedVault = false;
				plugin.sendConsole("§f#==============================#");
				plugin.sendConsole("§cVault 와 연결하는데 실패했습니다.");
				plugin.sendConsole("§c돈 관련 명령어가 비활성화됩니다.");
				plugin.sendConsole("§f#==============================#");
			} else {
				plugin.hookedVault = true;
				plugin.sendConsole("§aVault 와 성공적으로 연결되었습니다.");
			}
		} else {
			plugin.hookedVault = false;
			plugin.sendConsole("§f#==============================#");
			plugin.sendConsole("§cVault 가 존재하지 않습니다.");
			plugin.sendConsole("§c돈 관련 명령어가 비활성화됩니다.");
			plugin.sendConsole("§f#==============================#");
		}
	}

	public boolean checkFileName() {
		try {
			File jar = new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
			if (!jar.getName().equalsIgnoreCase("HandGiveAll v" + plugin.plugin_version + ".jar")) {
				plugin.sendConsole("§f#==============================#");
				plugin.sendConsole("§c플러그인 파일 이름 변경이 감지되었습니다.");
				plugin.sendConsole("§c정상 이름으로 변경해주시기 바랍니다.");
				plugin.sendConsole("");
				plugin.sendConsole("§f현재 이름: §c"+jar.getName());
				plugin.sendConsole("§f정상 이름: §aHandGiveAll v" + plugin.plugin_version + ".jar");
				plugin.sendConsole("§f#==============================#");
				new Thread(new Runnable() {
					public void run() {
						JOptionPane.showMessageDialog(null, "플러그인 파일 이름 변경이 감지되었습니다.", "HandGiveAll v" + plugin.plugin_version, JOptionPane.ERROR_MESSAGE);
					}
				}).start();
				plugin.getServer().getPluginManager().disablePlugin(plugin);
				return true;
			}
		} catch (Exception e) { }
		return false;
	}

	public boolean checkMD5(PluginInfoChecker.PluginInfo pluginInfo) {
		if (pluginInfo.getMd5().equals("없음")) return false;
		if (!plugin.checkSumApacheCommons("HandGiveAll").equals(pluginInfo.getMd5())) {
			notice_edited();
			plugin.sendConsole("§f#==============================#");
			plugin.sendConsole("§c플러그인 파일 변조가 감지되었습니다.");
			plugin.sendConsole("§c정상 파일로 변경해주시기 바랍니다.");
			plugin.sendConsole("§f#==============================#");
			new Thread(new Runnable() {
				public void run() {
					JOptionPane.showMessageDialog(null, "플러그인 파일 변조가 감지되었습니다.", "HandGiveAll v" + plugin.plugin_version, JOptionPane.ERROR_MESSAGE);
				}
			}).start();
			plugin.getServer().getPluginManager().disablePlugin(plugin);
			return true;
		}
		return false;
	}

	public void notice_edited() {
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
					conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
					conn.setRequestProperty("Content-Length",String.valueOf(postDataBytes.length));
					conn.setRequestProperty("Referer", "HGA-MD5-LOG-PL-00001");
					conn.setDoOutput(true);
					conn.getOutputStream().write(postDataBytes);
					new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				} catch (Exception e) { }
			}
		}).start();
	}
}