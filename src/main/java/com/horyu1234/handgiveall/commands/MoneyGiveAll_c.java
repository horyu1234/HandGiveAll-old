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

package com.horyu1234.handgiveall.commands;

import com.horyu1234.handgiveall.HandGiveAll;
import com.horyu1234.handgiveall.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class MoneyGiveAll_c implements CommandExecutor {
	public HandGiveAll plugin;
	DecimalFormat fmt;

	public MoneyGiveAll_c(HandGiveAll pl) {
		this.plugin = pl;
		int pointamount = plugin.getConfig().getInt("pointamount");
		String regex = "#,###.#";
		if (pointamount > 1)
			for (int i = 0 ; i < pointamount-1 ; i++)
				regex += "#";
		fmt = new DecimalFormat(regex);
	}

	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		try {
			if (l.equalsIgnoreCase("mga")) {
				if (!s.isOp()) {
					s.sendMessage(plugin.prefix+"§c권한이 없습니다.");
					return false;
				}
				if (!plugin.hookedVault) {
					s.sendMessage(plugin.prefix+"§c현재 사용할 수 없는 기능입니다. Vault 와 연동 여부를 확인해주세요.");
					return false;
				}
				if (a.length == 0) {
					plugin.getHelp().sendHelp(s, l, 4, false);
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
						plugin.getHelp().sendHelp(s, l, 4, false);
				} else if (a[0].equalsIgnoreCase("give")) {
					double amount;
					try {
						amount = Double.parseDouble(a[1]);
					} catch (Exception e) {
						s.sendMessage("§c숫자만 입력해주세요.");
						return false;
					}
					if (amount <= 0) {
						s.sendMessage("§c양은 0보다 커야합니다.");
						return false;
					}
					for (Player all : PlayerUtils.getOnlinePlayers())
						plugin.economy.depositPlayer(all, amount);
					plugin.getFireworkUtils().launchFireworkToAll();
					PlayerUtils.sendMsg("");
					PlayerUtils.sendMsg("");
					PlayerUtils.sendMsg("");
					PlayerUtils.sendMsg(plugin.bcprefix+"§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
					PlayerUtils.sendMsg(plugin.bcprefix+String.format("§e%s§a님이 모든 온라인 플레이어에게 §e%s§a을(를) §a지급하였습니다.", plugin.getConfig().getBoolean("usenickname") ? (s instanceof Player) ? ((Player) s).getDisplayName() : s.getName() : s.getName(), fmt.format(amount)+plugin.getConfig().getString("money")));
					PlayerUtils.sendMsg(plugin.bcprefix+"§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
					PlayerUtils.sendMsg(plugin.bcprefix+"§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
					PlayerUtils.sendMsg("");
					return true;
				} else if (a[0].equalsIgnoreCase("take")) {
					double amount;
					try {
						amount = Double.parseDouble(a[1]);
					} catch (Exception e) {
						s.sendMessage("§c숫자만 입력해주세요.");
						return false;
					}
					if (amount <= 0) {
						s.sendMessage("§c양은 0보다 커야합니다.");
						return false;
					}
					for (Player all : PlayerUtils.getOnlinePlayers())
						if (plugin.economy.getBalance(all)>=amount)
							plugin.economy.withdrawPlayer(all, amount);
						else
							PlayerUtils.sendMsg(plugin.bcprefix+"§e"+all.getName()+" §c님은 잔액이 부족하여, 돈을 뺏지 못했습니다. 현재잔액: §e"+fmt.format(plugin.economy.getBalance(all))+plugin.getConfig().getString("money"));
					PlayerUtils.sendMsg("");
					PlayerUtils.sendMsg("");
					PlayerUtils.sendMsg("");
					PlayerUtils.sendMsg(plugin.bcprefix+"§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
					PlayerUtils.sendMsg(plugin.bcprefix+String.format("§e%s§c님이 모든 온라인 플레이어에게서 §e%s§c을(를) §c뺏었습니다.", plugin.getConfig().getBoolean("usenickname") ? (s instanceof Player) ? ((Player) s).getDisplayName() : s.getName() : s.getName(), fmt.format(amount)+plugin.getConfig().getString("money")));
					PlayerUtils.sendMsg(plugin.bcprefix+"§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
					PlayerUtils.sendMsg(plugin.bcprefix+"§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
					PlayerUtils.sendMsg("");
					return true;
				} else if (a[0].equalsIgnoreCase("rgive")) {
					double min, max;
					try {
						min = Double.parseDouble(a[1]);
						max = Double.parseDouble(a[2]);
					} catch (Exception e) {
						s.sendMessage("§c숫자만 입력해주세요.");
						return false;
					}
					if (min >= max) {
						s.sendMessage(plugin.prefix+"§c최댓값은 최솟값보다 커야합니다.");
						return false;
					}
					double random = plugin.getNumberUtil().randomDoubleInRange(min, max);
					for (Player all : PlayerUtils.getOnlinePlayers())
						plugin.economy.depositPlayer(all, random);
					plugin.getFireworkUtils().launchFireworkToAll();
					PlayerUtils.sendMsg("");
					PlayerUtils.sendMsg("");
					PlayerUtils.sendMsg("");
					PlayerUtils.sendMsg(plugin.bcprefix+"§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
					PlayerUtils.sendMsg(plugin.bcprefix+String.format("§e%s§a님이 모든 온라인 플레이어에게 §e(랜덤추첨된)%s§a을(를) §a지급하였습니다.", plugin.getConfig().getBoolean("usenickname") ? (s instanceof Player) ? ((Player) s).getDisplayName() : s.getName() : s.getName(), fmt.format(random)+plugin.getConfig().getString("money")));
					PlayerUtils.sendMsg(plugin.bcprefix+String.format("§3최솟값: §b%s§f, §3최댓값: §b%s", fmt.format(min)+plugin.getConfig().getString("money"), fmt.format(max)+plugin.getConfig().getString("money")));
					PlayerUtils.sendMsg(plugin.bcprefix+"§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
					PlayerUtils.sendMsg(plugin.bcprefix+"§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
					PlayerUtils.sendMsg("");
					return true;
				} else if (a[0].equalsIgnoreCase("rtake")) {
					double min, max;
					try {
						min = Double.parseDouble(a[1]);
						max = Double.parseDouble(a[2]);
					} catch (Exception e) {
						s.sendMessage("§c숫자만 입력해주세요.");
						return false;
					}
					if (min >= max) {
						s.sendMessage(plugin.prefix+"§c최댓값은 최솟값보다 커야합니다.");
						return false;
					}
					double random = plugin.getNumberUtil().randomDoubleInRange(min, max);
					for (Player all : Bukkit.getOnlinePlayers())
						if (plugin.economy.getBalance(all)>=random)
							plugin.economy.withdrawPlayer(all, random);
						else
							PlayerUtils.sendMsg(plugin.bcprefix+"§e"+all.getName()+" §c님은 잔액이 부족하여, 돈을 뺏지 못했습니다. 현재잔액: §e"+fmt.format(plugin.economy.getBalance(all))+plugin.getConfig().getString("money"));
					PlayerUtils.sendMsg("");
					PlayerUtils.sendMsg("");
					PlayerUtils.sendMsg("");
					PlayerUtils.sendMsg(plugin.bcprefix+"§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
					PlayerUtils.sendMsg(plugin.bcprefix+String.format("§e%s§c님이 모든 온라인 플레이어에게서 §e(랜덤추첨된)%s§c을(를) §c뺏었습니다.", plugin.getConfig().getBoolean("usenickname") ? (s instanceof Player) ? ((Player) s).getDisplayName() : s.getName() : s.getName(), fmt.format(random)+plugin.getConfig().getString("money")));
					PlayerUtils.sendMsg(plugin.bcprefix+String.format("§3최솟값: §b%s§f, §3최댓값: §b%s", fmt.format(min)+plugin.getConfig().getString("money"), fmt.format(max)+plugin.getConfig().getString("money")));
					PlayerUtils.sendMsg(plugin.bcprefix+"§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
					PlayerUtils.sendMsg(plugin.bcprefix+"§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
					PlayerUtils.sendMsg("");
					return true;
				}
			}
		} catch (Exception e) {
			plugin.getHelp().sendHelp(s, l, 4, false);
			plugin.error_notice(e);
		}
		return false;
	}
}
