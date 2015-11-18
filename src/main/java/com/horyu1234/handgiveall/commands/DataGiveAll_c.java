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
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DataGiveAll_c implements CommandExecutor {
	public HandGiveAll plugin;
	DecimalFormat fmt = new DecimalFormat("#,###");
	public String itemsDir = "plugins/HandGiveAll/items/";
	public List<String> itemslist = new ArrayList<String>();

	public DataGiveAll_c(HandGiveAll pl) {
		this.plugin = pl;
	}

	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		try {
			if (l.equalsIgnoreCase("dga")) {
				if (!s.isOp()) {
					s.sendMessage(plugin.prefix+"§c권한이 없습니다.");
					return false;
				}
				if (a.length == 0) {
					plugin.getHelp().sendHelp(s, l, 3, false);
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
						plugin.getHelp().sendHelp(s, l, 3, false);
				} else if (a[0].equalsIgnoreCase("remove")) {
					reloadItemList();
					if (Pattern.matches("^[A-Za-z0-9가-힣]{1,20}$", a[1])) {
						if (contain(a[1])) {
							File file = new File(itemsDir+a[1]+".yml");
							try {
								file.delete();
								s.sendMessage(plugin.prefix+"§e"+a[1]+"§a을(를) 성공적으로 삭제했습니다.");
								return true;
							} catch (Exception e) {
								s.sendMessage(plugin.prefix+"§e"+a[1]+"§a을(를) 삭제하는데 문제가 발생했습니다.");
							}
						} else s.sendMessage(plugin.prefix+"§c존재하지 않는 이름입니다.");
					} else {
						s.sendMessage(plugin.prefix+"§c이름에 들어갈 수 없는 문자가 포함되어 있습니다.");
						s.sendMessage(plugin.prefix+"§c이름에는 영어, 한글, 숫자만 들어갈 수 있으며, 최소1글자~최대20글자입니다.");
					}
				} else if (a[0].equalsIgnoreCase("give")) {
					reloadItemList();
					Player p = plugin.getPlayerUtils().getPlayer(a[1]);
					if (p != null) {
						if (contain(a[2])) {
							int amount = 0;
							try { amount = Integer.parseInt(a[3]); } catch (Exception e) {
								s.sendMessage(plugin.prefix+"§c숫자만 입력해주세요.");
								return false;
							}
							if (amount <= 0) {
								s.sendMessage(plugin.prefix + "§c수량은 0보다 커야 합니다.");
								return false;
							}
							File file = new File(itemsDir+a[2]+".yml");
							FileConfiguration items = YamlConfiguration.loadConfiguration(file);
							ItemStack item = items.getItemStack("item");
							item.setAmount(amount);
							p.getInventory().addItem(item);
							s.sendMessage(plugin.bcprefix+"§a해당 플레이어에게 아이템을 지급했습니다. §6플러그인제작자: horyu1234 [https://horyu1234.com]");
							p.sendMessage(plugin.bcprefix+"§e"+s.getName()+"§a님이 당신에게 아이템을 지급하셨습니다. §6플러그인제작자: horyu1234 [https://horyu1234.com]");
							return true;
						} else s.sendMessage(plugin.prefix+"§c존재하지 않는 이름입니다.");
					} else s.sendMessage(plugin.prefix+"§c온라인플레이어가 아닙니다.");
				} else if (a[0].equalsIgnoreCase("giveall")) {
					reloadItemList();
					if (contain(a[1])) {
						int amount = 0;
						try { amount = Integer.parseInt(a[2]); } catch (Exception e) {
							s.sendMessage(plugin.prefix+"§c숫자만 입력해주세요.");
							return false;
						}
						if (amount <= 0) {
							s.sendMessage(plugin.prefix + "§c수량은 0보다 커야 합니다.");
							return false;
						}
						File file = new File(itemsDir+a[1]+".yml");
						FileConfiguration items = YamlConfiguration.loadConfiguration(file);
						ItemStack item = items.getItemStack("item");
						plugin.getItemUtils().giveItemAll(item, amount, plugin.getConfig().getBoolean("invfullmsg"));
						plugin.getFireworkUtils().launchFireworkToAll();
						PlayerUtils.sendMsg("");
						PlayerUtils.sendMsg("");
						PlayerUtils.sendMsg("");
						PlayerUtils.sendMsg(plugin.bcprefix+"§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
						PlayerUtils.sendMsg(plugin.bcprefix+String.format("§e%s§a님이 모든 온라인 플레이어에게 §e%s§a을(를) §e%s개 §a지급하였습니다.", plugin.getConfig().getBoolean("usenickname") ? (s instanceof Player) ? ((Player) s).getDisplayName() : s.getName() : s.getName(), plugin.getItemUtils().getItemName(item), fmt.format(amount)));
						PlayerUtils.sendMsg(plugin.bcprefix+"§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
						PlayerUtils.sendMsg(plugin.bcprefix+"§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
						PlayerUtils.sendMsg("");
						return true;
					} else s.sendMessage(plugin.prefix+"§c존재하지 않는 이름입니다.");
				} else if (a[0].equalsIgnoreCase("rgive")) {
					reloadItemList();
					Player p = plugin.getPlayerUtils().getPlayer(a[1]);
					if (p != null) {
						if (contain(a[2])) {
							int min = 0, max = 0;
							try {
								min = Integer.parseInt(a[3]);
								max = Integer.parseInt(a[4]);
							} catch (Exception e) {
								s.sendMessage(plugin.prefix+"§c숫자만 입력해주세요.");
								return false;
							}
							if (min >= max) {
								s.sendMessage(plugin.prefix+"§c최대값은 최솟값보다 커야합니다.");
								return false;
							}
							int random = plugin.getNumberUtil().randomInRange(min, max);
							File file = new File(itemsDir+a[2]+".yml");
							FileConfiguration items = YamlConfiguration.loadConfiguration(file);
							ItemStack item = items.getItemStack("item");
							item.setAmount(random);
							p.getInventory().addItem(item);
							s.sendMessage(plugin.bcprefix+"§a해당 플레이어에게 아이템을 지급했습니다. §6플러그인제작자: horyu1234 [https://horyu1234.com]");
							p.sendMessage(plugin.bcprefix+"§e"+s.getName()+"§a님이 당신에게 (랜덤수량의) 아이템을 지급하셨습니다. §6플러그인제작자: horyu1234 [https://horyu1234.com]");
							return true;
						} else s.sendMessage(plugin.prefix+"§c존재하지 않는 이름입니다.");
					} else s.sendMessage(plugin.prefix+"§c온라인플레이어가 아닙니다.");
				} else if (a[0].equalsIgnoreCase("rgiveall")) {
					reloadItemList();
					if (contain(a[1]))
					{
						int min = 0, max = 0;
						try {
							min = Integer.parseInt(a[2]);
							max = Integer.parseInt(a[3]);
						} catch (Exception e) {
							s.sendMessage(plugin.prefix+"§c숫자만 입력해주세요.");
							return false;
						}
						if (min >= max) {
							s.sendMessage(plugin.prefix+"§c최대값은 최솟값보다 커야합니다.");
							return false;
						}
						int random = plugin.getNumberUtil().randomInRange(min, max);
						File file = new File(itemsDir+a[1]+".yml");
						FileConfiguration items = YamlConfiguration.loadConfiguration(file);
						ItemStack item = items.getItemStack("item");
						plugin.getItemUtils().giveItemAll(item, random, plugin.getConfig().getBoolean("invfullmsg"));
						plugin.getFireworkUtils().launchFireworkToAll();
						PlayerUtils.sendMsg("");
						PlayerUtils.sendMsg("");
						PlayerUtils.sendMsg("");
						PlayerUtils.sendMsg(plugin.bcprefix+"§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
						PlayerUtils.sendMsg(plugin.bcprefix+String.format("§e%s§a님이 모든 온라인 플레이어에게 §e%s§a을(를) §e(랜덤추첨된)%s개 §a지급하였습니다.", plugin.getConfig().getBoolean("usenickname") ? (s instanceof Player) ? ((Player) s).getDisplayName() : s.getName() : s.getName(), plugin.getItemUtils().getItemName(item), fmt.format(random)));
						PlayerUtils.sendMsg(plugin.bcprefix+String.format("§3최솟값: §b%s§f, §3최댓값: §b%s", fmt.format(min), fmt.format(max)));
						PlayerUtils.sendMsg(plugin.bcprefix+"§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
						PlayerUtils.sendMsg(plugin.bcprefix+"§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
						PlayerUtils.sendMsg("");
						return true;
					} else s.sendMessage(plugin.prefix+"§c존재하지 않는 이름입니다.");
				} else if (a[0].equalsIgnoreCase("add")) {
					if (s instanceof Player) {
						Player p = (Player) s;
						ItemStack hand = p.getItemInHand().clone();
						if (!hand.getType().equals(Material.AIR)) {
							if (Pattern.matches("^[A-Za-z0-9가-힣ㄱ-ㅎ]{1,20}$", a[1])) {
								reloadItemList();
								if (!contain(a[1])) {
									File file = new File(itemsDir+a[1]+".yml");
									FileConfiguration items = YamlConfiguration.loadConfiguration(file);
									items.set("item", hand);
									try { items.save(file); } catch (IOException e) {
										p.sendMessage(plugin.prefix+"§c데이터를 저장하는데 문제가 발생했습니다.");
									}
									p.sendMessage(plugin.prefix+"§e"+a[1]+"§a의 이름에 손에 들고있는 아이템을 저장했습니다.");
									return true;
								} else p.sendMessage(plugin.prefix+"§c이미 존재하는 이름입니다.");
							} else {
								p.sendMessage(plugin.prefix+"§c이름에 들어갈 수 없는 문자가 포함되어 있습니다.");
								p.sendMessage(plugin.prefix+"§c이름에는 영어, 한글, 숫자만 들어갈 수 있으며, 최소1글자~최대20글자입니다.");
							}
						} else p.sendMessage(plugin.prefix+"§c손에 아이템을 들고 있어야 사용가능합니다.");
					} else s.sendMessage(plugin.prefix + "§c게임 내에서만 사용 가능한 명령어입니다.");
				} else if (a[0].equalsIgnoreCase("list")) {
					if (s instanceof Player) {
						Player p = (Player) s;
						reloadItemList();
						ItemStack book = new ItemStack(Material.WRITTEN_BOOK);

						BookMeta bm = (BookMeta) book.getItemMeta();
						bm.setAuthor("HandGiveAll");
						bm.setTitle("저장된 아이템 목록");
						bm.setDisplayName("§a저장된 아이템 목록");
						List<String> lore = new ArrayList<String>();
						lore.add("§e플러그인제작자: horyu1234");
						String[] page = {"", ""};
						page[0] = "§2§l저장된 아이템목록을 출력합니다.\n§9§l[플러그인제작자: horyu1234]\n§9§l=-=-=-=-=-=-=-=-=-=-=-=-=";
						int i = 2, ii = 0;
						for (String s2 : itemslist) {
							if (page[ii].equals("")) {
								page[ii] += "§9§l[플러그인제작자: horyu1234]";
								page[ii] += "§9§l=-=-=-=-=-=-=-=-=-=-=-=-=";
							}
							page[ii] += "\n§2§l"+s2.replaceAll(".yml", "");
							i++;
							if (i % 13 == 0)
								ii++;
						}
						for (int iii = 0 ; iii <= ii ; iii++)
							bm.addPage(page[ii]);
						bm.setLore(lore);
						book.setItemMeta(bm);
						p.getInventory().addItem(book);
						p.sendMessage(plugin.prefix+"§a저장된 아이템 목록을 책으로 생성하였습니다.");
						return true;
					} else s.sendMessage(plugin.prefix + "§c게임 내에서만 사용 가능한 명령어입니다.");
				}
			}
		} catch (Exception e) {
			plugin.getHelp().sendHelp(s, l, 3, false);
			plugin.error_notice(e);
		}
		return false;
	}

	public void reloadItemList() {
		itemslist.clear();
		File folder = new File(itemsDir);
		for (File items : folder.listFiles())
			itemslist.add(items.getName());
	}
	public boolean contain(String in) {
		reloadItemList();
		for (String s : itemslist)
			if (s.contains(in))
				return true;
		return false;
	}
}