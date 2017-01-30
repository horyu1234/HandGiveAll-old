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

package com.horyu1234.handgiveall.commands;

import com.horyu1234.handgiveall.Values;
import com.horyu1234.handgiveall.utils.LanguageUtils;
import com.horyu1234.handgiveall.utils.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HandGiveAll implements CommandExecutor {
    public com.horyu1234.handgiveall.HandGiveAll plugin;

    public HandGiveAll(com.horyu1234.handgiveall.HandGiveAll pl) {
        this.plugin = pl;
    }

    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        try {
            if (l.equalsIgnoreCase("hga")) {
                if (!s.isOp()) {
                    s.sendMessage(plugin.prefix + LanguageUtils.getString("command.hga.error.access_denied"));
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
                            s.sendMessage(plugin.prefix + LanguageUtils.getString("command.hga.error.only_number"));
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
                                p.sendMessage(plugin.prefix + LanguageUtils.getString("command.hga.error.only_number"));
                                return false;
                            }
                            if (amount > 0) {
                                plugin.getItemUtils().giveItemAll(p.getItemInHand().clone(), amount, plugin.config_show_inv_full_msg);
                                plugin.getFireworkUtils().launchFireworkToAll();
                                PlayerUtils.sendMsg("");
                                PlayerUtils.sendMsg("");
                                PlayerUtils.sendMsg("");
                                PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.hga.give.header"));
                                PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.hga.give.1").replace("@player@", plugin.config_use_nickname ? p.getDisplayName() : p.getName()).replace("@item@", plugin.getItemUtils().getItemName(p.getItemInHand().clone())).replace("@amount@", Values.FORMAT_THOUSANDS.format(amount)));
                                PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.hga.give.2"));
                                PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.hga.give.footer"));
                                PlayerUtils.sendMsg("");
                                return true;
                            } else p.sendMessage(plugin.prefix + LanguageUtils.getString("command.hga.error.amount"));
                        } else p.sendMessage(plugin.prefix + LanguageUtils.getString("command.hga.error.hand_empty"));
                    } else s.sendMessage(plugin.prefix + LanguageUtils.getString("command.hga.error.only_player"));
                }
            } else if (l.equalsIgnoreCase("hgar")) {
                if (!s.isOp()) {
                    s.sendMessage(plugin.prefix + LanguageUtils.getString("command.hgar.error.access_denied"));
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
                            p.sendMessage(plugin.prefix + LanguageUtils.getString("command.hgar.error.only_number"));
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
                                PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.hgar.give.header"));
                                PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.hgar.give.1").replace("@player@", plugin.config_use_nickname ? p.getDisplayName() : p.getName()).replace("@item@", plugin.getItemUtils().getItemName(hand)).replace("@amount@", Values.FORMAT_THOUSANDS.format(random)));
                                PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.hgar.give.2").replace("@min@", Values.FORMAT_THOUSANDS.format(min)).replace("@max@", Values.FORMAT_THOUSANDS.format(max)));
                                PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.hgar.give.3"));
                                PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.hgar.give.footer"));
                                PlayerUtils.sendMsg("");
                                return true;
                            } else p.sendMessage(plugin.prefix + LanguageUtils.getString("command.hgar.error.amount"));
                        } else p.sendMessage(plugin.prefix + LanguageUtils.getString("command.hgar.error.min_max"));
                    } else p.sendMessage(plugin.prefix + LanguageUtils.getString("command.hgar.error.hand_empty"));
                } else s.sendMessage(plugin.prefix + LanguageUtils.getString("command.hgar.error.only_player"));
            } else if (l.equalsIgnoreCase("hgac")) {
                if (!s.isOp()) {
                    s.sendMessage(plugin.prefix + LanguageUtils.getString("command.hgac.error.access_denied"));
                    return false;
                }

                if (a.length != 2) {
                    plugin.getHelp().sendHelp(s, l, 1, false);
                    return false;
                }
                int id = plugin.getNumberUtil().parseInt(a[0]);
                int amount = plugin.getNumberUtil().parseInt(a[1]);
                if (id == -999 || amount == -999) {
                    s.sendMessage(plugin.prefix + LanguageUtils.getString("command.hgac.error.only_number"));
                    return false;
                }

                if (amount > 0) {
                    ItemStack item = new ItemStack(Material.AIR);
                    try {
                        item.setType(Material.getMaterial(id));
                    } catch (Exception e) {
                        s.sendMessage(plugin.prefix + LanguageUtils.getString("command.hgacr.error.not_exist_item_code"));
                        return false;
                    }
                    item.setAmount(amount);
                    plugin.getItemUtils().giveItemAll(item.clone(), amount, plugin.config_show_inv_full_msg);
                    plugin.getFireworkUtils().launchFireworkToAll();
                    PlayerUtils.sendMsg("");
                    PlayerUtils.sendMsg("");
                    PlayerUtils.sendMsg("");
                    PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.hgac.give.header"));
                    PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.hgac.give.1").replace("@player@", plugin.config_use_nickname ? (s instanceof Player ? ((Player) s).getDisplayName() : s.getName()) : s.getName()).replace("@item@", item.getType().name()).replace("@amount@", Values.FORMAT_THOUSANDS.format(amount)));
                    PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.hgac.give.2"));
                    PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.hgac.give.footer"));
                    PlayerUtils.sendMsg("");
                    return true;
                } else s.sendMessage(plugin.prefix + LanguageUtils.getString("command.hgac.error.amount"));
            } else if (l.equalsIgnoreCase("hgacr")) {
                if (!s.isOp()) {
                    s.sendMessage(plugin.prefix + LanguageUtils.getString("command.hgacr.error.access_denied"));
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
                    s.sendMessage(plugin.prefix + LanguageUtils.getString("command.hgacr.error.only_number"));
                    return false;
                }

                if (max >= min) {
                    if (max > 0 && min > 0) {
                        int random = plugin.getNumberUtil().randomInRange(min, max);
                        ItemStack item = new ItemStack(Material.AIR);
                        try {
                            item.setType(Material.getMaterial(id));
                        } catch (Exception e) {
                            s.sendMessage(plugin.prefix + LanguageUtils.getString("command.hca.error.not_exist_item_code"));
                            return false;
                        }
                        item.setAmount(random);
                        plugin.getItemUtils().giveItemAll(item.clone(), random, plugin.config_show_inv_full_msg);
                        plugin.getFireworkUtils().launchFireworkToAll();
                        PlayerUtils.sendMsg("");
                        PlayerUtils.sendMsg("");
                        PlayerUtils.sendMsg("");
                        PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.hgacr.give.header"));
                        PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.hgacr.give.1").replace("@player@", plugin.config_use_nickname ? (s instanceof Player ? ((Player) s).getDisplayName() : s.getName()) : s.getName()).replace("@item@", item.getType().name()).replace("@amount@", Values.FORMAT_THOUSANDS.format(random)));
                        PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.hgacr.give.2").replace("@min@", Values.FORMAT_THOUSANDS.format(min)).replace("@max@", Values.FORMAT_THOUSANDS.format(max)));
                        PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.hgacr.give.3"));
                        PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.hgacr.give.footer"));
                        PlayerUtils.sendMsg("");
                        return true;
                    } else s.sendMessage(plugin.prefix + LanguageUtils.getString("command.hgacr.error.amount"));
                } else s.sendMessage(plugin.prefix + LanguageUtils.getString("command.hgacr.error.min_max"));
            }
        } catch (Exception e) {
            plugin.getHelp().sendHelp(s, l, 1, false);
            plugin.error_notice(e);
        }
        return false;
    }
}