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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PotionGiveAll_c implements CommandExecutor {
	public HandGiveAll plugin;
	//private HashMap<Integer, PotionEffect> potioneffectlist;

	public PotionGiveAll_c(HandGiveAll pl) {
		this.plugin = pl;
	}

	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		try {
			if (l.equalsIgnoreCase("pga")) {
				if (!s.isOp()) {
					s.sendMessage(plugin.prefix+"§c권한이 없습니다.");
					return false;
				}
				if (a.length == 0) {
					if (s instanceof Player) {
						Player p = (Player) s;
						ItemStack hand = p.getItemInHand().clone();
						if (hand.getType().equals(Material.POTION))
						{
							PotionMeta potionmeta = (PotionMeta) hand.getItemMeta();
							List<PotionEffect> potionList = new ArrayList<PotionEffect>();
							if (potionmeta.hasCustomEffects())
								potionList.addAll(potionmeta.getCustomEffects());
							else if (getDefaultPotionEffectByItemDurability(hand.getDurability()) != null) potionList.add(getDefaultPotionEffectByItemDurability(hand.getDurability()));
							if (potionList.size() > 0) {
								for (Player all : PlayerUtils.getOnlinePlayers()) {
									for (PotionEffect pe : potionList)
										all.removePotionEffect(pe.getType());
									all.addPotionEffects(potionList);
								}
								plugin.getFireworkUtils().launchFireworkToAll();
								PlayerUtils.sendMsg("");
								PlayerUtils.sendMsg("");
								PlayerUtils.sendMsg("");
								PlayerUtils.sendMsg(plugin.bcprefix+"§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
								PlayerUtils.sendMsg(plugin.bcprefix+String.format("§e%s§a님이 모든 온라인 플레이어에게 §e%s§a효과를 지급하였습니다.", plugin.getConfig().getBoolean("usenickname") ? (s instanceof Player) ? ((Player) s).getDisplayName() : s.getName() : s.getName(), convertPotionEffectListToString(potionList)));
								PlayerUtils.sendMsg(plugin.bcprefix+"§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
								PlayerUtils.sendMsg(plugin.bcprefix+"§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
								PlayerUtils.sendMsg("");
							} else {
								p.sendMessage(plugin.prefix+"§c포션 데이터가 없어, 지급이 불가능합니다.");
								return false;
							}
						} else if (hand.getType().equals(Material.MILK_BUCKET)) {
							for (Player all : PlayerUtils.getOnlinePlayers()) {
								for (PotionEffect pe : all.getActivePotionEffects())
									all.removePotionEffect(pe.getType());
								plugin.getFireworkUtils().launchFireworkToAll();
							}
							PlayerUtils.sendMsg("");
							PlayerUtils.sendMsg("");
							PlayerUtils.sendMsg("");
							PlayerUtils.sendMsg(plugin.bcprefix+"§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
							PlayerUtils.sendMsg(plugin.bcprefix+String.format("§e%s§a님이 모든 온라인 플레이어에게서 모든 포션 효과를 제거하셨습니다.", plugin.getConfig().getBoolean("usenickname") ? (s instanceof Player) ? ((Player) s).getDisplayName() : s.getName() : s.getName()));
							PlayerUtils.sendMsg(plugin.bcprefix+"§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
							PlayerUtils.sendMsg(plugin.bcprefix+"§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
							PlayerUtils.sendMsg("");
						} else {
							p.sendMessage(plugin.prefix+"§c손에 포션을 들고 있어야 합니다.");
							return false;
						}
						return true;
					} else s.sendMessage(plugin.prefix + "§c게임 내에서만 사용 가능한 명령어입니다.");
				} else if (a[0].equalsIgnoreCase("?")) {
					if (a.length == 2) {
						int page = plugin.getNumberUtil().parseInt(a[1]);
						if (page == -999) {
							s.sendMessage(plugin.prefix + "§c숫자만 입력해주세요!");
							return false;
						}
						plugin.getHelp().sendHelp(s, l, page, false);
					} else
						plugin.getHelp().sendHelp(s, l, 5, false);
				}
			}
		} catch (Exception e) {
			plugin.getHelp().sendHelp(s, l, 5, false);
			plugin.error_notice(e);
		}
		return false;
	}

	public String convertPotionEffectListToString(List<PotionEffect> potionList) {
		String temp = "";
		for (PotionEffect pe : potionList) {
			if (temp.equals("")) temp = String.format("(이름:%s, 레벨:%s, 시간:%s초)", pe.getType().getName(), pe.getAmplifier()+1, pe.getDuration()/20);
			else temp += ", " + String.format("(이름:%s, 레벨:%s, 시간:%s초)", pe.getType().getName(), pe.getAmplifier()+1, pe.getDuration()/20);
		}
		return temp;
	}

	public PotionEffect getDefaultPotionEffectByItemDurability(int durability) {
		try {
			Class<?> c = Class.forName("org.bukkit.craftbukkit." + plugin.getReflectionUtils().getPackageName() + ".potion.CraftPotionBrewer");
			Method m = c.getMethod("getEffectsFromDamage", int.class);
			@SuppressWarnings("unchecked")
			Collection<PotionEffect> potions = (Collection<PotionEffect>) m.invoke(c.newInstance(), durability);
			for (PotionEffect effect : potions) {
				//Bukkit.broadcastMessage("§f");
				//Bukkit.broadcastMessage("§b[DEBUG] Type: " + effects.getType().getName());
				//Bukkit.broadcastMessage("§b[DEBUG] Amplifier: " + effects.getAmplifier());
				//Bukkit.broadcastMessage("§b[DEBUG] Duration: " + effects.getDuration());
				return effect;
			}
		} catch (Exception e) {
			plugin.error_notice(e);
		}
		return null;
	}
	
	/*
	public PotionEffect getDefaultPotionEffect(ItemStack item) {
		potioneffectlist = new HashMap<Integer, PotionEffect>();
		potioneffectlist.put(8193, new PotionEffect(PotionEffectType.REGENERATION, 20*45, 0));
		potioneffectlist.put(8194, new PotionEffect(PotionEffectType.SPEED, 20*60*3, 0));
		potioneffectlist.put(8195, new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20*60*3, 0));
		potioneffectlist.put(8196, new PotionEffect(PotionEffectType.POISON, 20*45, 0));
		potioneffectlist.put(8197, new PotionEffect(PotionEffectType.HEAL, 20, 0));
		potioneffectlist.put(8198, new PotionEffect(PotionEffectType.NIGHT_VISION, 20*60*3, 0));
		potioneffectlist.put(8200, new PotionEffect(PotionEffectType.WEAKNESS, 20*90, 0));
		potioneffectlist.put(8201, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*60*3, 0));
		potioneffectlist.put(8202, new PotionEffect(PotionEffectType.SLOW, 20*90, 0));
		potioneffectlist.put(8204, new PotionEffect(PotionEffectType.HARM, 20, 0));
		potioneffectlist.put(8205, new PotionEffect(PotionEffectType.WATER_BREATHING, 20*60*3, 0));
		potioneffectlist.put(8206, new PotionEffect(PotionEffectType.INVISIBILITY, 20*60*3, 0));
		
		
		potioneffectlist.put(8225, new PotionEffect(PotionEffectType.REGENERATION, 20*22, 1));
		potioneffectlist.put(8226, new PotionEffect(PotionEffectType.SPEED, 20*90, 1));
		potioneffectlist.put(8228, new PotionEffect(PotionEffectType.POISON, 20*22, 1));
		potioneffectlist.put(8229, new PotionEffect(PotionEffectType.HEAL, 20, 1));
		potioneffectlist.put(8233, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*90, 1));
		//potioneffectlist.put(8235, new PotionEffect(PotionEffectType.LEAPING, 20*90, 1));
		potioneffectlist.put(8236, new PotionEffect(PotionEffectType.HARM, 20, 1));
		
		potioneffectlist.put(8257, new PotionEffect(PotionEffectType.REGENERATION, 20*60*2, 0));
		potioneffectlist.put(8258, new PotionEffect(PotionEffectType.SPEED, 20*60*8, 0));
		potioneffectlist.put(8259, new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20*60*8, 0));
		potioneffectlist.put(8260, new PotionEffect(PotionEffectType.POISON, 20*60*2, 0));
		potioneffectlist.put(8262, new PotionEffect(PotionEffectType.NIGHT_VISION, 20*60*8, 0));
		potioneffectlist.put(8264, new PotionEffect(PotionEffectType.WEAKNESS, 20*60*4, 0));
		potioneffectlist.put(8265, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*60*8, 0));
		potioneffectlist.put(8266, new PotionEffect(PotionEffectType.SLOW, 20*60*4, 0));
		//potioneffectlist.put(8267, new PotionEffect(PotionEffectType.REGENERATION, 20*60*3, 0));
		potioneffectlist.put(8269, new PotionEffect(PotionEffectType.WATER_BREATHING, 20*60*8, 0));
		potioneffectlist.put(8270, new PotionEffect(PotionEffectType.INVISIBILITY, 20*60*8, 0));
		
		potioneffectlist.put(8289, new PotionEffect(PotionEffectType.REGENERATION, 20*60, 1));
		potioneffectlist.put(8290, new PotionEffect(PotionEffectType.SPEED, 20*60*4, 1));
		potioneffectlist.put(8292, new PotionEffect(PotionEffectType.POISON, 20*60, 1));
		potioneffectlist.put(8297, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*60*4, 1));
		
		potioneffectlist.put(16385, new PotionEffect(PotionEffectType.REGENERATION, 20*33, 0));
		potioneffectlist.put(16386, new PotionEffect(PotionEffectType.SPEED, 20*135, 0));
		potioneffectlist.put(16387, new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20*135, 0));
		potioneffectlist.put(16388, new PotionEffect(PotionEffectType.POISON, 20*33, 0));
		potioneffectlist.put(16389, new PotionEffect(PotionEffectType.HEAL, 20, 0));
		potioneffectlist.put(16390, new PotionEffect(PotionEffectType.NIGHT_VISION, 20*135, 0));
		potioneffectlist.put(16392, new PotionEffect(PotionEffectType.WEAKNESS, 20*67, 0));
		potioneffectlist.put(16393, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*135, 0));
		potioneffectlist.put(16394, new PotionEffect(PotionEffectType.SLOW, 20*67, 0));
		potioneffectlist.put(16396, new PotionEffect(PotionEffectType.HARM, 20, 0));
		potioneffectlist.put(16397, new PotionEffect(PotionEffectType.WATER_BREATHING, 20*135, 0));
		potioneffectlist.put(16398, new PotionEffect(PotionEffectType.INVISIBILITY, 20*135, 0));
		
		potioneffectlist.put(16417, new PotionEffect(PotionEffectType.REGENERATION, 20*16, 1));
		potioneffectlist.put(16418, new PotionEffect(PotionEffectType.SPEED, 20*67, 1));
		potioneffectlist.put(16420, new PotionEffect(PotionEffectType.POISON, 20*16, 1));
		potioneffectlist.put(16421, new PotionEffect(PotionEffectType.HEAL, 20, 1));
		potioneffectlist.put(16425, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*67, 1));
		//potioneffectlist.put(16427, new PotionEffect(PotionEffectType.LEAPING, 20*67, 1));
		potioneffectlist.put(16428, new PotionEffect(PotionEffectType.HARM, 20, 1));
		
		potioneffectlist.put(16449, new PotionEffect(PotionEffectType.REGENERATION, 20*90, 0));
		potioneffectlist.put(16450, new PotionEffect(PotionEffectType.SPEED, 20*60*6, 0));
		potioneffectlist.put(16451, new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20*60*6, 0));
		potioneffectlist.put(16452, new PotionEffect(PotionEffectType.POISON, 20*90, 0));
		potioneffectlist.put(16454, new PotionEffect(PotionEffectType.NIGHT_VISION, 20*60*6, 0));
		potioneffectlist.put(16456, new PotionEffect(PotionEffectType.WEAKNESS, 20*60*3, 0));
		potioneffectlist.put(16457, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*60*6, 0));
		potioneffectlist.put(16458, new PotionEffect(PotionEffectType.SLOW, 20*60*3, 0));
		//potioneffectlist.put(16459, new PotionEffect(PotionEffectType.LEAPING, 20*135, 0));
		potioneffectlist.put(16461, new PotionEffect(PotionEffectType.WATER_BREATHING, 20*60*6, 0));
		potioneffectlist.put(16462, new PotionEffect(PotionEffectType.INVISIBILITY, 20*60*6, 0));
		
		potioneffectlist.put(16481, new PotionEffect(PotionEffectType.REGENERATION, 20*45, 1));
		potioneffectlist.put(16482, new PotionEffect(PotionEffectType.SPEED, 20*60*3, 1));
		potioneffectlist.put(16484, new PotionEffect(PotionEffectType.POISON, 20*45, 1));
		potioneffectlist.put(16489, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*60*3, 1));
		
		int data = item.getDurability();
		if (potioneffectlist.containsKey(data)) return potioneffectlist.get(data); else return null;
	}
	*/
}