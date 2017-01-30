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
import com.horyu1234.handgiveall.Values;
import com.horyu1234.handgiveall.utils.LanguageUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DataGiveAll implements CommandExecutor {
    public HandGiveAll plugin;
    private String itemsDir = "plugins/HandGiveAll/items/";
    private List<String> itemList = new ArrayList<String>();

    public DataGiveAll(HandGiveAll pl) {
        this.plugin = pl;
    }

    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        try {
            if (l.equalsIgnoreCase("dga")) {
                if (!s.isOp()) {
                    s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.error.access_denied"));
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
                            s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.error.only_number"));
                            return false;
                        }
                        plugin.getHelp().sendHelp(s, l, page, false);
                    } else
                        plugin.getHelp().sendHelp(s, l, 3, false);
                } else if (a[0].equalsIgnoreCase("remove")) {
                    if (a.length != 2) {
                        plugin.getHelp().sendHelp(s, l, 3, false);
                        return false;
                    }

                    reloadItemList();

                    if (Pattern.matches("^[A-Za-z0-9가-힣]{1,20}$", a[1])) {
                        if (contain(a[1])) {
                            File file = new File(itemsDir + a[1] + ".yml");
                            try {
                                file.delete();
                                s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.delete").replace("@data@", a[1]));
                                return true;
                            } catch (Exception e) {
                                s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.delete.error.delete").replace("@data@", a[1]));
                            }
                        } else
                            s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.delete.error.data_not_exist"));
                    } else {
                        s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.delete.error.name_regex.1"));
                        s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.delete.error.name_regex.2"));
                    }
                } else if (a[0].equalsIgnoreCase("give")) {
                    if (a.length != 4) {
                        plugin.getHelp().sendHelp(s, l, 3, false);
                        return false;
                    }

                    reloadItemList();
                    Player p = plugin.getPlayerUtils().getPlayer(a[1]);
                    if (p != null) {
                        if (contain(a[2])) {
                            int amount;
                            try {
                                amount = Integer.parseInt(a[3]);
                            } catch (Exception e) {
                                s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.give.error.only_number"));
                                return false;
                            }
                            if (amount <= 0) {
                                s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.give.error.amount"));
                                return false;
                            }
                            File file = new File(itemsDir + a[2] + ".yml");
                            FileConfiguration items = YamlConfiguration.loadConfiguration(file);
                            ItemStack item = items.getItemStack("item");
                            item.setAmount(amount);
                            p.getInventory().addItem(item);
                            s.sendMessage(plugin.bcprefix + LanguageUtils.getString("command.dga.give.sender"));
                            p.sendMessage(plugin.bcprefix + LanguageUtils.getString("command.dga.give.player").replace("@player@", s.getName()));
                            return true;
                        } else
                            s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.give.error.data_not_exist"));
                    } else
                        s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.give.error.not_online_player"));
                } else if (a[0].equalsIgnoreCase("giveall")) {
                    if (a.length != 3) {
                        plugin.getHelp().sendHelp(s, l, 3, false);
                        return false;
                    }

                    reloadItemList();
                    if (contain(a[1])) {
                        int amount;
                        try {
                            amount = Integer.parseInt(a[2]);
                        } catch (Exception e) {
                            s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.giveall.error.only_number"));
                            return false;
                        }
                        if (amount <= 0) {
                            s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.giveall.error.number"));
                            return false;
                        }
                        File file = new File(itemsDir + a[1] + ".yml");
                        FileConfiguration items = YamlConfiguration.loadConfiguration(file);
                        ItemStack item = items.getItemStack("item");
                        plugin.getItemUtils().giveItemAll(item, amount, plugin.config_show_inv_full_msg);
                        plugin.getFireworkUtils().launchFireworkToAll();
                        PlayerUtils.sendMsg("");
                        PlayerUtils.sendMsg("");
                        PlayerUtils.sendMsg("");
                        PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.dga.giveall.header"));
                        PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.dga.giveall.1").replace("@player@", plugin.config_use_nickname ? (s instanceof Player) ? ((Player) s).getDisplayName() : s.getName() : s.getName()).replace("@item@", plugin.getItemUtils().getItemName(item)).replace("@amount@", Values.FORMAT_THOUSANDS.format(amount)));
                        PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.dga.giveall.2"));
                        PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.dga.giveall.footer"));
                        PlayerUtils.sendMsg("");
                        return true;
                    } else
                        s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.giveall.error.data_not_exist"));
                } else if (a[0].equalsIgnoreCase("rgive")) {
                    if (a.length != 5) {
                        plugin.getHelp().sendHelp(s, l, 3, false);
                        return false;
                    }

                    reloadItemList();
                    Player p = plugin.getPlayerUtils().getPlayer(a[1]);
                    if (p != null) {
                        if (contain(a[2])) {
                            int min, max;
                            try {
                                min = Integer.parseInt(a[3]);
                                max = Integer.parseInt(a[4]);
                            } catch (Exception e) {
                                s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.rgive.error.only_number"));
                                return false;
                            }
                            if (min >= max) {
                                s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.rgive.error.min_max"));
                                return false;
                            }
                            int random = plugin.getNumberUtil().randomInRange(min, max);
                            File file = new File(itemsDir + a[2] + ".yml");
                            FileConfiguration items = YamlConfiguration.loadConfiguration(file);
                            ItemStack item = items.getItemStack("item");
                            item.setAmount(random);
                            p.getInventory().addItem(item);
                            s.sendMessage(plugin.bcprefix + LanguageUtils.getString("command.dga.rgive.sender"));
                            p.sendMessage(plugin.bcprefix + LanguageUtils.getString("command.dga.rgive.player").replace("@player@", s.getName()));
                            return true;
                        } else
                            s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.rgive.error.data_not_exist"));
                    } else
                        s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.rgive.error.not_online_player"));
                } else if (a[0].equalsIgnoreCase("rgiveall")) {
                    if (a.length != 4) {
                        plugin.getHelp().sendHelp(s, l, 3, false);
                        return false;
                    }

                    reloadItemList();
                    if (contain(a[1])) {
                        int min, max;
                        try {
                            min = Integer.parseInt(a[2]);
                            max = Integer.parseInt(a[3]);
                        } catch (Exception e) {
                            s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.rgiveall.error.only_number"));
                            return false;
                        }
                        if (min >= max) {
                            s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.rgiveall.error.min_max"));
                            return false;
                        }
                        int random = plugin.getNumberUtil().randomInRange(min, max);
                        File file = new File(itemsDir + a[1] + ".yml");
                        FileConfiguration items = YamlConfiguration.loadConfiguration(file);
                        ItemStack item = items.getItemStack("item");
                        plugin.getItemUtils().giveItemAll(item, random, plugin.config_show_inv_full_msg);
                        plugin.getFireworkUtils().launchFireworkToAll();
                        PlayerUtils.sendMsg("");
                        PlayerUtils.sendMsg("");
                        PlayerUtils.sendMsg("");
                        PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.dga.rgiveall.header"));
                        PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.dga.rgiveall.1").replace("@player@", plugin.config_use_nickname ? (s instanceof Player) ? ((Player) s).getDisplayName() : s.getName() : s.getName()).replace("@item@", plugin.getItemUtils().getItemName(item)).replace("@amount@", Values.FORMAT_THOUSANDS.format(random)));
                        PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.dga.rgiveall.2").replace("@min@", Values.FORMAT_THOUSANDS.format(min)).replace("@max@", Values.FORMAT_THOUSANDS.format(max)));
                        PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.dga.rgiveall.3"));
                        PlayerUtils.sendMsg(plugin.bcprefix + LanguageUtils.getString("command.dga.rgiveall.footer"));
                        PlayerUtils.sendMsg("");
                        return true;
                    } else
                        s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.rgiveall.error.data_not_exist"));
                } else if (a[0].equalsIgnoreCase("add")) {
                    if (a.length != 2) {
                        plugin.getHelp().sendHelp(s, l, 3, false);
                        return false;
                    }

                    if (s instanceof Player) {
                        Player p = (Player) s;
                        ItemStack hand = p.getItemInHand().clone();
                        if (!hand.getType().equals(Material.AIR)) {
                            if (Pattern.matches("^[A-Za-z0-9가-힣ㄱ-ㅎ]{1,20}$", a[1])) {
                                reloadItemList();
                                if (!contain(a[1])) {
                                    File file = new File(itemsDir + a[1] + ".yml");
                                    FileConfiguration items = YamlConfiguration.loadConfiguration(file);
                                    items.set("item", hand);
                                    try {
                                        items.save(file);
                                    } catch (IOException e) {
                                        p.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.add.error.save"));
                                    }
                                    p.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.add.save").replace("@name@", a[1]));
                                    return true;
                                } else
                                    p.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.add.error.exist_name"));
                            } else {
                                p.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.add.error.name_regex.1"));
                                p.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.add.error.name_regex.2"));
                            }
                        } else
                            p.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.add.error.hand_empty"));
                    } else s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.add.error.only_player"));
                } else if (a[0].equalsIgnoreCase("list")) {
                    if (s instanceof Player) {
                        Player p = (Player) s;

                        if (p.getInventory().firstEmpty() == -1) {
                            s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.error.inventory_full"));
                            return false;
                        }

                        reloadItemList();
                        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);

                        BookMeta bm = (BookMeta) book.getItemMeta();
                        bm.setAuthor("HandGiveAll");
                        bm.setTitle(LanguageUtils.getString("command.dga.list.book.title"));
                        bm.setDisplayName(LanguageUtils.getString("command.dga.list.book.display_name"));
                        List<String> lore = new ArrayList<String>();
                        lore.add(LanguageUtils.getString("command.dga.list.book.lore"));
                        String[] page = {"", ""};
                        page[0] = LanguageUtils.getString("command.dga.list.book.top");
                        int i = 2, ii = 0;
                        for (String s2 : itemList) {
                            if (page[ii].equals("")) {
                                page[ii] += LanguageUtils.getString("command.dga.list.book.page1.1");
                                page[ii] += LanguageUtils.getString("command.dga.list.book.page1.2");
                            }
                            page[ii] += "\n§2§l" + s2.replaceAll(".yml", "");
                            i++;
                            if (i % 13 == 0)
                                ii++;
                        }
                        for (int iii = 0; iii <= ii; iii++)
                            bm.addPage(page[ii]);
                        bm.setLore(lore);
                        book.setItemMeta(bm);
                        p.getInventory().addItem(book);
                        p.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.list.done"));
                        return true;
                    } else s.sendMessage(plugin.prefix + LanguageUtils.getString("command.dga.list.error.only_player"));
                }
            }
        } catch (Exception e) {
            plugin.getHelp().sendHelp(s, l, 3, false);
            plugin.error_notice(e);
        }
        return false;
    }

    private void reloadItemList() {
        itemList.clear();

        File folder = new File(itemsDir);
        for (File items : folder.listFiles()) {
            itemList.add(items.getName());
        }
    }

    private boolean contain(String in) {
        reloadItemList();

        for (String s : itemList)
            if (s.replace(".yml", "").equals(in))
                return true;
        return false;
    }
}