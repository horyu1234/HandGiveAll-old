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

package com.horyu1234.handgiveall.commands;

import com.horyu1234.handgiveall.HandGiveAll;
import com.horyu1234.handgiveall.utils.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;

public class HandGiveAll_c implements CommandExecutor {
	public HandGiveAll plugin;
	DecimalFormat fmt = new DecimalFormat("#,###");

	public HandGiveAll_c(HandGiveAll pl) {
		this.plugin = pl;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		try {
			if (l.equalsIgnoreCase("hga")) {
				if (!s.isOp()) {
					s.sendMessage(plugin.prefix+"§c권한이 없습니다.");
					return false;
				}
				if (a.length == 0) {
					plugin.getHelp().sendHelp(s, l, 1, false);
					return false;
				}
				if (a[0].equalsIgnoreCase("?")) {
					if (a.length == 2) {
						int page = plugin.getNumberUtil().parseInt(a[1]);
						if (page == -999) {
							s.sendMessage(plugin.prefix + "§c숫자만 입력해주세요!");
							return false;
						}
						plugin.getHelp().sendHelp(s, l, page, false);
					} else
						plugin.getHelp().sendHelp(s, l, 1, false);
				} else {
					if (s instanceof Player) {
						Player p = (Player) s;
						if (!p.getItemInHand().clone().getType().equals(Material.AIR)) {
							int amount = plugin.getNumberUtil().parseInt(a[0]);
							if (amount == -999) {
								p.sendMessage(plugin.prefix + "§c숫자만 입력해주세요.");
								return false;
							}
							if (amount > 0) {
								plugin.getItemUtils().giveItemAll(p.getItemInHand().clone(), amount, plugin.config_show_inv_full_msg);
								plugin.getFireworkUtils().launchFireworkToAll();
								PlayerUtils.sendMsg("");
								PlayerUtils.sendMsg("");
								PlayerUtils.sendMsg("");
								PlayerUtils.sendMsg(plugin.bcprefix + "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
								PlayerUtils.sendMsg(plugin.bcprefix + String.format(
										"§e%s§a님이 모두에게 §e%s§a을(를) §e%s개 §a지급하였습니다.",
										plugin.config_use_nickname ? p
												.getDisplayName() : p
												.getName(),
										plugin.getItemUtils()
												.getItemName(
														p.getItemInHand()
																.clone()), fmt.format(amount)));
								PlayerUtils.sendMsg(plugin.bcprefix + "§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
								PlayerUtils.sendMsg(plugin.bcprefix + "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
								PlayerUtils.sendMsg("");
								return true;
							} else p.sendMessage(plugin.prefix + "§c수량은 0보다 커야 합니다.");
						} else p.sendMessage(plugin.prefix + "§c손에 아이템을 들고 있어야 사용 가능합니다.");
					} else s.sendMessage(plugin.prefix + "§c게임 내에서만 사용 가능한 명령어입니다.");
				}
			} else if (l.equalsIgnoreCase("hgar")) {
				if (!s.isOp()) {
					s.sendMessage(plugin.prefix+"§c권한이 없습니다.");
					return false;
				}

				if (s instanceof Player) {
					if (a.length != 2) {
						plugin.getHelp().sendHelp(s, l, 1, false);
						return false;
					}
					Player p = (Player) s;
					ItemStack hand = p.getItemInHand().clone();
					if (!hand.getType().equals(Material.AIR)) {
						int min = plugin.getNumberUtil().parseInt(a[0]);
						int max = plugin.getNumberUtil().parseInt(a[1]);
						if (min == -999 || max == -999) {
							p.sendMessage(plugin.prefix + "§c숫자만 입력해주세요.");
							return false;
						}
						if (max >= min) {
							if (max > 0 && min > 0) {
								int random = plugin.getNumberUtil().randomInRange(min, max);
								hand.setAmount(random);
								plugin.getItemUtils().giveItemAll(hand, random, plugin.config_show_inv_full_msg);
								plugin.getFireworkUtils().launchFireworkToAll();
								PlayerUtils.sendMsg("");
								PlayerUtils.sendMsg("");
								PlayerUtils.sendMsg("");
								PlayerUtils.sendMsg(plugin.bcprefix + "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
								PlayerUtils.sendMsg(plugin.bcprefix
										+ String.format(
										"§e%s§a님이 모두에게 §e%s§a을(를) §e(랜덤추첨된)%s개 §a지급하였습니다.",
										plugin.config_use_nickname ? p
												.getDisplayName() : p
												.getName(), plugin
												.getItemUtils()
												.getItemName(hand),
										fmt.format(random)));
								PlayerUtils.sendMsg(plugin.bcprefix+String.format("§3최솟값: §b%s§f, §3최댓값: §b%s", fmt.format(min), fmt.format(max)));
								PlayerUtils.sendMsg(plugin.bcprefix + "§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
								PlayerUtils.sendMsg(plugin.bcprefix + "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
								PlayerUtils.sendMsg("");
								return true;
							} else p.sendMessage(plugin.prefix + "§c값은 0보다 커야 합니다.");
						} else p.sendMessage(plugin.prefix + "§c최댓값은 최솟값보다 커야합니다.");
					} else p.sendMessage(plugin.prefix + "§c손에 아이템을 들고 있어야 사용가능합니다.");
				} else s.sendMessage(plugin.prefix + "§c게임 내에서만 사용 가능한 명령어입니다.");
			} else if (l.equalsIgnoreCase("hgac")) {
				if (!s.isOp()) {
					s.sendMessage(plugin.prefix+"§c권한이 없습니다.");
					return false;
				}

				if (a.length != 2) {
					plugin.getHelp().sendHelp(s, l, 1, false);
					return false;
				}
				int id = plugin.getNumberUtil().parseInt(a[0]);
				int amount = plugin.getNumberUtil().parseInt(a[1]);
				if (id == -999 || amount == -999) {
					s.sendMessage(plugin.prefix+"§c숫자만 입력해주세요.");
					return false;
				}

				if (amount > 0) {
					ItemStack item = new ItemStack(Material.AIR);
					item.setType(Material.getMaterial(id));
					item.setAmount(amount);
					plugin.getItemUtils().giveItemAll(item.clone(), amount, plugin.config_show_inv_full_msg);
					plugin.getFireworkUtils().launchFireworkToAll();
					PlayerUtils.sendMsg("");
					PlayerUtils.sendMsg("");
					PlayerUtils.sendMsg("");
					PlayerUtils.sendMsg(plugin.bcprefix+"§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
					PlayerUtils.sendMsg(plugin.bcprefix+String.format("§e%s§a님이 모든 온라인 플레이어에게 §e%s§a을(를) §e%s개 §a지급하였습니다.", plugin.config_use_nickname ? (s instanceof Player ? ((Player) s).getDisplayName() : s.getName()) : s.getName(), item.getType().name(), fmt.format(amount)));
					PlayerUtils.sendMsg(plugin.bcprefix+"§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
					PlayerUtils.sendMsg(plugin.bcprefix+"§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
					PlayerUtils.sendMsg("");
					return true;
				} else s.sendMessage(plugin.prefix+"§c수량은 0보다 커야합니다.");
			} else if (l.equalsIgnoreCase("hgacr")) {
				if (!s.isOp()) {
					s.sendMessage(plugin.prefix+"§c권한이 없습니다.");
					return false;
				}

				if (a.length != 3) {
					plugin.getHelp().sendHelp(s, l, 1, false);
					return false;
				}
				int id = plugin.getNumberUtil().parseInt(a[0]);
				int min = plugin.getNumberUtil().parseInt(a[1]);
				int max = plugin.getNumberUtil().parseInt(a[2]);
				if (id == -999 || min == -999 || max == -999) {
					s.sendMessage(plugin.prefix+"§c숫자만 입력해주세요.");
					return false;
				}

				if (max >= min) {
					if (max > 0 && min > 0) {
						int random = plugin.getNumberUtil().randomInRange(min, max);
						ItemStack item = new ItemStack(Material.AIR);
						item.setType(Material.getMaterial(id));
						item.setAmount(random);
						plugin.getItemUtils().giveItemAll(item.clone(), random, plugin.config_show_inv_full_msg);
						plugin.getFireworkUtils().launchFireworkToAll();
						PlayerUtils.sendMsg("");
						PlayerUtils.sendMsg("");
						PlayerUtils.sendMsg("");
						PlayerUtils.sendMsg(plugin.bcprefix+"§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
						PlayerUtils.sendMsg(plugin.bcprefix+String.format("§e%s§a님이 모든 온라인 플레이어에게 §e%s§a을(를) §e(랜덤추첨된)%s개 §a지급하였습니다.", plugin.config_use_nickname ? (s instanceof Player ? ((Player) s).getDisplayName() : s.getName()) : s.getName(), item.getType().name(), fmt.format(random)));
						PlayerUtils.sendMsg(plugin.bcprefix+String.format("§3최솟값: §b%s§f, §3최댓값: §b%s", fmt.format(min), fmt.format(max)));
						PlayerUtils.sendMsg(plugin.bcprefix+"§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
						PlayerUtils.sendMsg(plugin.bcprefix+"§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
						PlayerUtils.sendMsg("");
						return true;
					} else s.sendMessage(plugin.prefix+"§c값은 0보다 커야합니다.");
				} else s.sendMessage(plugin.prefix+"§c최대값은 최솟값보다 커야합니다.");
			}
		} catch (Exception e) {
			plugin.getHelp().sendHelp(s, l, 1, false);
			plugin.error_notice(e);
		}
		return false;
	}
}