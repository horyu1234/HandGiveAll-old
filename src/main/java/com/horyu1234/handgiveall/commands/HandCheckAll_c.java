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
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.List;

public class HandCheckAll_c implements CommandExecutor {
	public HandGiveAll plugin;

	public HandCheckAll_c(HandGiveAll pl) {
		this.plugin = pl;
	}

	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		try {
			if (l.equalsIgnoreCase("hca")) {
				if (!s.isOp()) {
					s.sendMessage(plugin.prefix+"§c권한이 없습니다.");
					return false;
				}
				if (a.length == 0) {
					plugin.getHelp().sendHelp(s, l, 2, false);
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
						plugin.getHelp().sendHelp(s, l, 2, false);
				} else {
					if (s instanceof Player) {
						Player p = (Player) s;
						if (p.getInventory().firstEmpty() == -1) {
							s.sendMessage(plugin.prefix + "§c인벤토리를 비운 후 다시 시도해주세요.");
							return false;
						}


						ItemStack hand = p.getItemInHand().clone();

						if (!hand.getType().equals(Material.AIR)) {
							ItemStack book = new ItemStack(Material.WRITTEN_BOOK);

							BookMeta bm = (BookMeta) book.getItemMeta();
							bm.setAuthor("HandGiveAll");
							bm.setTitle("해당아이템을 가지고있는 플레이어 목록");
							bm.setDisplayName("§a해당아이템을 가지고있는 플레이어 목록");
							List<String> lore = new ArrayList<String>();
							lore.add("§e플러그인제작자: horyu1234");
							String llore = "";
							String Dname = "";
							String[] nicknames = {"", ""};
							if (hand.getItemMeta().hasLore()) {
								int iii = 0;
								for (String ss : hand.getItemMeta().getLore()) {
									if (llore == "") {llore = "\n §0§l- §e§l" + ss;}
									else {
										iii++;
										llore += "\n §0§l- §e§l"+ss;
										if (iii > 5) {
											llore += "\n §c§l최대 줄제한입니다.";
											break;
										}
									}
								}
							}
							else llore = "§c§l없음";
							if (hand.getItemMeta().hasDisplayName())
								Dname = hand.getItemMeta().getDisplayName();
							else Dname = "§c§l설정안됨";
							int i = 0;
							int n = 3;
							for (Player all : PlayerUtils.getOnlinePlayers()) {
								if (containsItem(hand.clone(), all, a)) {
									if (nicknames[i] == "") {
										nicknames[i] += "§9§l[비교한정보]\n";
										nicknames[i] += "§9§l"+a[0]+"\n";
										nicknames[i] += "§9§l[플러그인제작자: horyu1234]\n";
										nicknames[i] += "§9§l=-=-=-=-=-=-=-=-=-=-=-=-=";
									}
									n++;
									nicknames[i] += "\n§a§l" + all.getName();
									if (n % 12 == 0) i++;
								}
							}
							String en = "";
							if (hand.getItemMeta().hasEnchants()) {
								en = "§a§l있음";
								en += "\n§9§l해당아이템을 가지고있는 사람목록은 다음장에 있습니다.";
							}
							else {
								en = "§c§l없음";
								en += "\n§9§l해당아이템을 가지고있는 사람목록은 다음장에 있습니다.";
							}
							bm.addPage("§9§l확인한 아이템정보\n§a§l아이템종류: §6§l" + hand.getType() + "\n§a§l아이템이름: §6§l" + Dname + "\n§a§l아이템설명: " + llore + "\n§a§l인첸트: " + en);
							if (nicknames[0] == "") {
								nicknames[0] = "§9§l[플러그인제작자: horyu1234]\n";
								nicknames[0] += "§9§l=-=-=-=-=-=-=-=-=-=-=-=-=";
								bm.addPage(nicknames[0]);
							}
							else
								for (int ii = 0 ; ii <= i ; ii++)
									bm.addPage(nicknames[ii]);
							bm.setLore(lore);
							book.setItemMeta(bm);
							p.getInventory().addItem(book);
							p.sendMessage(plugin.prefix+"§a해당아이템을 가지고있는 사람리스트를 책으로 생성하였습니다.");
							return true;
						} else p.sendMessage(plugin.prefix+"§c손에 아이템을 들고있어야 사용가능합니다.");
					} else s.sendMessage(plugin.prefix + "§c게임 내에서만 사용 가능한 명령어입니다.");
				}
			}
		} catch (Exception e) {
			plugin.getHelp().sendHelp(s, l, 2, false);
			plugin.error_notice(e);
		}
		return false;
	}

	public boolean containsItem(ItemStack item, Player p, String[] a) {
		String option = a[0];
		ItemMeta itemM = item.getItemMeta();
		for (ItemStack i : p.getInventory().getContents()) {
			if (i!=null) {
				if ((!i.getType().equals(Material.AIR))) {
					ItemMeta iM = i.getItemMeta();
					if (option.contains("name") || option.contains("이름"))
						if (itemM.hasDisplayName()&&iM.hasDisplayName())
							if (itemM.getDisplayName().equals(iM.getDisplayName()))
								return true;
					if (option.contains("lore") || option.contains("설명"))
						if (itemM.hasLore()&&iM.hasLore())
							if (itemM.getLore().equals(iM.getLore()))
								return true;
					if (option.contains("type") || option.contains("타입"))
						if (item.getType().equals(i.getType()))
							return true;
					if (option.contains("enchant") || option.contains("인첸트"))
						if (itemM.hasEnchants()&&iM.hasEnchants())
							if (itemM.getEnchants().equals(iM.getEnchants()))
								return true;
					if (option.contains("amount") || option.contains("수량"))
						if (item.getAmount()==i.getAmount())
							return true;
					if (option.contains("potion") || option.contains("포션"))
						if (item.getType().equals(Material.POTION)&&i.getType().equals(Material.POTION)) {
							PotionMeta itemPotionM = (PotionMeta) item.getItemMeta();
							PotionMeta iPotionM = (PotionMeta) i.getItemMeta();
							if (itemPotionM.hasCustomEffects()&&iPotionM.hasCustomEffects())
								if (itemPotionM.getCustomEffects().equals(iPotionM.getCustomEffects()))
									return true;
						}
				}
			}
		}
		return false;
	}
}