/*******************************************************************************
 * Copyright (c) 2014~2017 HoryuSystems Ltd. All rights reserved.
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
 * 라이센스: Copyright (c) 2014~2017 HoryuSystems Ltd. All rights reserved.
 * ============================================
 *
 * 자세한 내용은 https://horyu1234.com/EULA 를 확인해주세요.
 ******************************************************************************/

package com.horyu1234.handgiveall.commands;

import com.horyu1234.handgiveall.HandGiveAll;
import com.horyu1234.handgiveall.utils.LanguageUtils;
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

public class HandCheckAll implements CommandExecutor {
    public HandGiveAll plugin;

    public HandCheckAll(HandGiveAll pl) {
        this.plugin = pl;
    }

    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        try {
            if (l.equalsIgnoreCase("hca")) {
                if (!s.isOp()) {
                    s.sendMessage(plugin.prefix + LanguageUtils.getString("command.hca.error.access_denied"));
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
                            s.sendMessage(plugin.prefix + LanguageUtils.getString("command.hca.error.only_number"));
                            return false;
                        }
                        plugin.getHelp().sendHelp(s, l, page, false);
                    } else
                        plugin.getHelp().sendHelp(s, l, 2, false);
                } else {
                    if (s instanceof Player) {
                        Player p = (Player) s;
                        if (p.getInventory().firstEmpty() == -1) {
                            s.sendMessage(plugin.prefix + LanguageUtils.getString("command.hca.error.inventory_full"));
                            return false;
                        }


                        ItemStack hand = p.getItemInHand().clone();

                        if (!hand.getType().equals(Material.AIR)) {
                            ItemStack book = new ItemStack(Material.WRITTEN_BOOK);

                            BookMeta bm = (BookMeta) book.getItemMeta();
                            bm.setAuthor("HandGiveAll");
                            bm.setTitle(LanguageUtils.getString("command.hca.book.title"));
                            bm.setDisplayName(LanguageUtils.getString("command.hca.book.display_name"));
                            List<String> lore = new ArrayList<String>();
                            lore.add(LanguageUtils.getString("command.hca.book.lore"));
                            String llore = "";
                            String Dname;
                            String[] nicknames = {"", ""};
                            if (hand.getItemMeta().hasLore()) {
                                int iii = 0;
                                for (String ss : hand.getItemMeta().getLore()) {
                                    if (llore.equals("")) {
                                        llore = "\n §0§l- §e§l" + ss;
                                    } else {
                                        iii++;
                                        llore += "\n §0§l- §e§l" + ss;
                                        if (iii > 5) {
                                            llore += LanguageUtils.getString("command.hca.book.max_line");
                                            break;
                                        }
                                    }
                                }
                            } else llore = LanguageUtils.getString("command.hca.book.none");
                            if (hand.getItemMeta().hasDisplayName())
                                Dname = hand.getItemMeta().getDisplayName();
                            else Dname = LanguageUtils.getString("command.hca.book.not_set");
                            int i = 0;
                            int n = 3;
                            for (Player all : PlayerUtils.getOnlinePlayers()) {
                                if (containsItem(hand.clone(), all, a)) {
                                    if (nicknames[i].equals("")) {
                                        nicknames[i] += LanguageUtils.getString("command.hca.book.page1.1");
                                        nicknames[i] += LanguageUtils.getString("command.hca.book.page1.2").replace("@data@", a[0]);
                                        nicknames[i] += LanguageUtils.getString("command.hca.book.page1.3");
                                        nicknames[i] += LanguageUtils.getString("command.hca.book.page1.4");
                                    }
                                    n++;
                                    nicknames[i] += "\n§a§l" + all.getName();
                                    if (n % 12 == 0) i++;
                                }
                            }
                            String en;
                            if (hand.getItemMeta().hasEnchants()) {
                                en = LanguageUtils.getString("command.hca.book.enchant_not_empty");
                            } else {
                                en = LanguageUtils.getString("command.hca.book.enchant_empty");
                            }
                            en += LanguageUtils.getString("command.hca.book.next_page");

                            String checkedItemInfoTest = LanguageUtils.getString("command.hca.book.checked_item_info.1");
                            checkedItemInfoTest += LanguageUtils.getString("command.hca.book.checked_item_info.2").replace("@type@", hand.getType().name());
                            checkedItemInfoTest += LanguageUtils.getString("command.hca.book.checked_item_info.3").replace("@name@", Dname);
                            checkedItemInfoTest += LanguageUtils.getString("command.hca.book.checked_item_info.4").replace("@lore@", llore);
                            checkedItemInfoTest += LanguageUtils.getString("command.hca.book.checked_item_info.5").replace("@enchant@", en);

                            bm.addPage(checkedItemInfoTest);
                            if (nicknames[0].equals("")) {
                                nicknames[0] = LanguageUtils.getString("command.hca.book.page1.3");
                                nicknames[0] += LanguageUtils.getString("command.hca.book.page1.4");
                                bm.addPage(nicknames[0]);
                            } else
                                for (int ii = 0; ii <= i; ii++)
                                    bm.addPage(nicknames[ii]);
                            bm.setLore(lore);
                            book.setItemMeta(bm);
                            p.getInventory().addItem(book);
                            p.sendMessage(plugin.prefix + LanguageUtils.getString("command.hca.book.done"));
                            return true;
                        } else p.sendMessage(plugin.prefix + LanguageUtils.getString("command.hca.error.hand_empty"));
                    } else s.sendMessage(plugin.prefix + LanguageUtils.getString("command.hca.error.only_player"));
                }
            }
        } catch (Exception e) {
            plugin.getHelp().sendHelp(s, l, 2, false);
            plugin.error_notice(e);
        }
        return false;
    }

    private boolean containsItem(ItemStack item, Player p, String[] a) {
        String option = a[0];
        ItemMeta itemM = item.getItemMeta();
        for (ItemStack i : p.getInventory().getContents()) {
            if (i != null) {
                if ((!i.getType().equals(Material.AIR))) {
                    ItemMeta iM = i.getItemMeta();
                    if (option.contains("name") || option.contains("이름"))
                        if (itemM.hasDisplayName() && iM.hasDisplayName())
                            if (itemM.getDisplayName().equals(iM.getDisplayName()))
                                return true;
                    if (option.contains("lore") || option.contains("설명"))
                        if (itemM.hasLore() && iM.hasLore())
                            if (itemM.getLore().equals(iM.getLore()))
                                return true;
                    if (option.contains("type") || option.contains("타입"))
                        if (item.getType().equals(i.getType()))
                            return true;
                    if (option.contains("enchant") || option.contains("인첸트"))
                        if (itemM.hasEnchants() && iM.hasEnchants())
                            if (itemM.getEnchants().equals(iM.getEnchants()))
                                return true;
                    if (option.contains("amount") || option.contains("수량"))
                        if (item.getAmount() == i.getAmount())
                            return true;
                    if (option.contains("potion") || option.contains("포션"))
                        if (item.getType().equals(Material.POTION) && i.getType().equals(Material.POTION)) {
                            PotionMeta itemPotionM = (PotionMeta) item.getItemMeta();
                            PotionMeta iPotionM = (PotionMeta) i.getItemMeta();
                            if (itemPotionM.hasCustomEffects() && iPotionM.hasCustomEffects())
                                if (itemPotionM.getCustomEffects().equals(iPotionM.getCustomEffects()))
                                    return true;
                        }
                }
            }
        }
        return false;
    }
}