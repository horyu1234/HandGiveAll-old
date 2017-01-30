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

import com.horyu1234.handgiveall.HandGiveAll;
import com.horyu1234.handgiveall.utils.LanguageUtils;
import com.horyu1234.handgiveall.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class MoneyGiveAll implements CommandExecutor {
    public HandGiveAll plugin;
    private DecimalFormat fmt;

    public MoneyGiveAll(HandGiveAll pl) {
        this.plugin = pl;
        int pointAmount = plugin.config_max_point_count;
        String regex = "#,###.#";
        if (pointAmount > 1)
            for (int i = 0; i < pointAmount - 1; i++)
                regex += "#";
        fmt = new DecimalFormat(regex);
    }

    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        try {
            if (l.equalsIgnoreCase("mga")) {
                if (!s.isOp()) {
                    s.sendMessage(plugin.prefix + LanguageUtils.getString("command.mga.error.access_denied"));
                    return false;
                }
                if (!plugin.hookedVault) {
                    s.sendMessage(plugin.prefix + LanguageUtils.getString("command.mga.error.not_hooked_vault"));
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
                            s.sendMessage(plugin.prefix + LanguageUtils.getString("command.mga.error.only_number"));
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
                        s.sendMessage(plugin.prefix + LanguageUtils.getString("command.mga.error.only_number"));
                        return false;
                    }
                    if (amount <= 0) {
                        s.sendMessage(plugin.prefix + LanguageUtils.getString("command.mga.error.amount"));
                        return false;
                    }
                    for (Player all : PlayerUtils.getOnlinePlayers())
                        plugin.economy.depositPlayer(all, amount);
                    plugin.getFireworkUtils().launchFireworkToAll();
                    PlayerUtils.sendMsg("");
                    PlayerUtils.sendMsg("");
                    PlayerUtils.sendMsg("");
                    PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.mga.give.header"));
                    PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.mga.give.1").replace("@player@", plugin.config_use_nickname ? (s instanceof Player) ? ((Player) s).getDisplayName() : s.getName() : s.getName()).replace("@money@", fmt.format(amount) + plugin.config_money_unit));
                    PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.mga.give.2"));
                    PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.mga.give.footer"));
                    PlayerUtils.sendMsg("");
                    return true;
                } else if (a[0].equalsIgnoreCase("take")) {
                    double amount;
                    try {
                        amount = Double.parseDouble(a[1]);
                    } catch (Exception e) {
                        s.sendMessage(plugin.prefix + LanguageUtils.getString("command.mga.error.only_number"));
                        return false;
                    }
                    if (amount <= 0) {
                        s.sendMessage(plugin.prefix + LanguageUtils.getString("command.mga.error.amount"));
                        return false;
                    }
                    for (Player all : PlayerUtils.getOnlinePlayers())
                        if (plugin.economy.getBalance(all) >= amount)
                            plugin.economy.withdrawPlayer(all, amount);
                        else
                            PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.mga.take.error").replace("@player@", all.getDisplayName().equals(all.getName()) ? all.getName() : all.getDisplayName() + "(" + all.getName() + ")").replace("@money@", fmt.format(plugin.economy.getBalance(all)) + plugin.config_money_unit));
                    PlayerUtils.sendMsg("");
                    PlayerUtils.sendMsg("");
                    PlayerUtils.sendMsg("");
                    PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.mga.take.header"));
                    PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.mga.take.1").replace("@player@", plugin.config_use_nickname ? (s instanceof Player) ? ((Player) s).getDisplayName() : s.getName() : s.getName()).replace("@money@", fmt.format(amount) + plugin.config_money_unit));
                    PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.mga.take.2"));
                    PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.mga.take.footer"));
                    PlayerUtils.sendMsg("");
                    return true;
                } else if (a[0].equalsIgnoreCase("rgive")) {
                    double min, max;
                    try {
                        min = Double.parseDouble(a[1]);
                        max = Double.parseDouble(a[2]);
                    } catch (Exception e) {
                        s.sendMessage(plugin.prefix + LanguageUtils.getString("command.mga.error.only_number"));
                        return false;
                    }
                    if (min >= max) {
                        s.sendMessage(plugin.prefix + LanguageUtils.getString("command.mga.error.minmax"));
                        return false;
                    }
                    double random = plugin.getNumberUtil().randomDoubleInRange(min, max);
                    for (Player all : PlayerUtils.getOnlinePlayers())
                        plugin.economy.depositPlayer(all, random);
                    plugin.getFireworkUtils().launchFireworkToAll();
                    PlayerUtils.sendMsg("");
                    PlayerUtils.sendMsg("");
                    PlayerUtils.sendMsg("");
                    PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.mga.rgive.header"));
                    PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.mga.rgive.1").replace("@player@", plugin.config_use_nickname ? (s instanceof Player) ? ((Player) s).getDisplayName() : s.getName() : s.getName()).replace("@money@", fmt.format(random) + plugin.config_money_unit));
                    PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.mga.rgive.2").replace("@min@", fmt.format(min) + plugin.config_money_unit).replace("@max@", fmt.format(max) + plugin.config_money_unit));
                    PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.mga.rgive.3"));
                    PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.mga.rgive.footer"));
                    PlayerUtils.sendMsg("");
                    return true;
                } else if (a[0].equalsIgnoreCase("rtake")) {
                    double min, max;
                    try {
                        min = Double.parseDouble(a[1]);
                        max = Double.parseDouble(a[2]);
                    } catch (Exception e) {
                        s.sendMessage(plugin.prefix + LanguageUtils.getString("command.mga.error.only_number"));
                        return false;
                    }
                    if (min >= max) {
                        s.sendMessage(plugin.prefix + LanguageUtils.getString("command.mga.error.minmax"));
                        return false;
                    }
                    double random = plugin.getNumberUtil().randomDoubleInRange(min, max);
                    for (Player all : Bukkit.getOnlinePlayers())
                        if (plugin.economy.getBalance(all) >= random)
                            plugin.economy.withdrawPlayer(all, random);
                        else
                            PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.mga.take.error").replace("@player@", all.getName()).replace("@money@", fmt.format(plugin.economy.getBalance(all)) + plugin.config_money_unit));
                    PlayerUtils.sendMsg("");
                    PlayerUtils.sendMsg("");
                    PlayerUtils.sendMsg("");
                    PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.mga.rtake.header"));
                    PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.mga.rtake.1").replace("@player@", plugin.config_use_nickname ? (s instanceof Player) ? ((Player) s).getDisplayName() : s.getName() : s.getName()).replace("@money@", fmt.format(random) + plugin.config_money_unit));
                    PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.mga.rtake.2").replace("@min@", fmt.format(min) + plugin.config_money_unit).replace("@max@", fmt.format(max) + plugin.config_money_unit));
                    PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.mga.rtake.3"));
                    PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.mga.rtake.footer"));
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
