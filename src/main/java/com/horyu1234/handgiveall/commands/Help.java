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

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.horyu1234.handgiveall.HandGiveAll;

public class Help implements CommandExecutor {
    public HandGiveAll plugin;

    public Help(HandGiveAll pl) {
        this.plugin = pl;
    }

    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        try {
            if (l.equalsIgnoreCase("hh")) {
                int page = plugin.getNumberUtil().parseInt(a[0]);
                if (page == -999) {
                    s.sendMessage(plugin.prefix + "§c숫자만 입력해주세요!");
                    return false;
                }
                sendHelp(s, l, page, true);
            }
        } catch (Exception e) {
            sendHelp(s, l, 0, true);
        }
        return true;
    }

    public void sendHelp(CommandSender s, String l, int page, boolean basic) {
        if (plugin.use_korean) {
            sendHelpKR(s, l, page, basic);
        } else {
            sendHelpEN(s, l, page, basic);
        }
    }

    private void sendHelpKR(CommandSender s, String l, int page, boolean basic) {
        String main = "/" + l + (basic ? " " : " ? ");
        String name = "HandGiveAll v" + plugin.plugin_version;
        if (page == 0) {
            s.sendMessage("§6■==== §e" + name + " §6====■");
            s.sendMessage("§a" + main + "1 §f손에 있는 아이템 주기 명령어 관련");
            s.sendMessage("§a" + main + "2 §f아이템 조사 관련");
            s.sendMessage("§a" + main + "3 §f데이터 저장 관련");
            s.sendMessage("§a" + main + "4 §f돈 관련");
            s.sendMessage("§a" + main + "5 §f포션 관련");
            s.sendMessage("§a/hn §fHandGiveAll 공지 관련");
            s.sendMessage("§bⓒ Horyu Systems Ltd, All Rights Reserved.");
            s.sendMessage("§6■==== §eMade By horyu1234 §6====■");
        } else if (page == 1) {
            s.sendMessage("§6■==== §e" + name + " §6====■");
            s.sendMessage("§a1. 손에 있는 아이템 주기 명령어 관련");
            s.sendMessage("§b/hga <수량> §r손에 들고 있는 아이템을 모두에게 지급합니다.");
            s.sendMessage("§b/hgar <최솟값> <최댓값> §r<최솟값> 과 <최댓값> 의 사이 랜덤 수량으로 손에 들고 있는 아이템을 모두에게 지급합니다.");
            s.sendMessage("§b/hgac <아이템코드> <수량> §r해당 아이템을 모두에게 지급합니다.");
            s.sendMessage("§b/hgacr <아이템코드> <최솟값> <최댓값> §r<최솟값> 과 <최댓값> 의 사이 랜덤 수량으로 해당 아이템을 모두에게 지급합니다.");
            s.sendMessage("§bⓒ Horyu Systems Ltd, All Rights Reserved.");
            s.sendMessage("§6■==== §eMade By horyu1234 §6====■");
        } else if (page == 2) {
            s.sendMessage("§6■==== §e" + name + " §6====■");
            s.sendMessage("§a2. 아이템 조사 관련");
            s.sendMessage("§b/hca [비교할정보] §r손에 들고 있는 아이템과 [비교할정보]가 같은 아이템을 가지고 있는 플레이어가 적힌 책을 생성합니다.");
            s.sendMessage("§f비교할정보: name|lore|type|enchant|amount|potion (한국어설명: 이름|설명|타입|인첸트|수량|포션)");
            s.sendMessage("§f사용 예: §a(손에 들고 있는 아이템의 타입과 이름, 인첸트가 같은 아이템을 들고 있는 사람을 찾을 때)");
            s.sendMessage("§a/hca name,type,enchant 또는 /hca name|type|enchant");
            s.sendMessage("§bⓒ Horyu Systems Ltd, All Rights Reserved.");
            s.sendMessage("§6■==== §eMade By horyu1234 §6====■");
        } else if (page == 3) {
            s.sendMessage("§6■==== §e" + name + " §6====■");
            s.sendMessage("§a3. 데이터 저장 관련");
            s.sendMessage("§b/dga add <이름> §r손에 들고 있는 아이템을 파일에 저장합니다.");
            s.sendMessage("§b/dga remove <이름> §r저장된 아이템을 삭제합니다.");
            s.sendMessage("§b/dga list §r저장된 아이템들을 책으로 출력합니다.");
            s.sendMessage("§b/dga give <닉네임> <이름> <수량> §r<닉네임> 에게 해당 아이템을 지급합니다.");
            s.sendMessage("§b/dga giveall <이름> <수량> §r모두에게 해당 아이템을 지급합니다.");
            s.sendMessage("§b/dga rgive <닉네임> <이름> <최솟값> <최댓값> §r<최솟값> 과 <최댓값> 의 사이 랜덤 수량으로 해당 아이템을 <닉네임> 에게 지급합니다.");
            s.sendMessage("§b/dga rgiveall <이름> <최솟값> <최댓값> §r<최솟값> 과 <최댓값> 의 사이 랜덤 수량으로 해당 아이템을 모두에게 지급합니다.");
            s.sendMessage("§bⓒ Horyu Systems Ltd, All Rights Reserved.");
            s.sendMessage("§6■==== §eMade By horyu1234 §6====■");
        } else if (page == 4) {
            s.sendMessage("§6■==== §e" + name + " §6====■");
            s.sendMessage("§a4. 돈 관련");
            s.sendMessage("§b/mga give <금액> §r돈을 모두에게 지급합니다.");
            s.sendMessage("§b/mga take <금액> §r돈을 모두에게서 뺏어옵니다.");
            s.sendMessage("§b/mga rgive <최솟값> <최댓값> §r<최솟값> 과 <최댓값> 의 사이 랜덤 금액을 모두에게 지급합니다.");
            s.sendMessage("§b/mga rtake <최솟값> <최댓값> §r<최솟값> 과 <최댓값> 의 사이 랜덤 금액을 모두에게서 뺏어옵니다.");
            s.sendMessage("§bⓒ Horyu Systems Ltd, All Rights Reserved.");
            s.sendMessage("§6■==== §eMade By horyu1234 §6====■");
        } else if (page == 5) {
            s.sendMessage("§6■==== §e" + name + " §6====■");
            s.sendMessage("§a5. 포션 관련");
            s.sendMessage("§b/pga §r손에 들고 있는 포션의 효과를 모두에게 지급합니다.");
            s.sendMessage("§f우유를 들고 명령어를 치면, 모두에게서 모든 포션 효과를 제거합니다. (우유 효과와 동일)");
            s.sendMessage("§bⓒ Horyu Systems Ltd, All Rights Reserved.");
            s.sendMessage("§6■==== §eMade By horyu1234 §6====■");
        } else {
            s.sendMessage(plugin.prefix + "§c존재하지 않는 페이지입니다.");
        }
    }

    private void sendHelpEN(CommandSender s, String l, int page, boolean basic) {
        String main = "/" + l + (basic ? " " : " ? ");
        String name = "HandGiveAll v" + plugin.plugin_version;
        if (page == 0) {
            s.sendMessage("§6■==== §e" + name + " §6====■");
            s.sendMessage("§a" + main + "1 §fItem In Hand Related Commands");
            s.sendMessage("§a" + main + "2 §fItem Info Related Commands");
            s.sendMessage("§a" + main + "3 §fItem-to-File Related Commands");
            s.sendMessage("§a" + main + "4 §fMoney Related Commands");
            s.sendMessage("§a" + main + "5 §fPotion Related Commands");
            s.sendMessage("§a/hn §fHandGiveAll Announcer Related Commands");
            s.sendMessage("§bⓒ Horyu Systems Ltd, All Rights Reserved.");
            s.sendMessage("§6■==== §eMade By horyu1234 §6====■");
        } else if (page == 1) {
            s.sendMessage("§6■==== §e" + name + " §6====■");
            s.sendMessage("§a1. Item In Hand Related Commands");
            s.sendMessage("§b/hga <amount> §rGive everyone the item in hand.");
            s.sendMessage("§b/hgar <min> <max> §rGive everyone a random amount of item in hand within the range of <min> and <max>.");
            s.sendMessage("§b/hgac <item> <amount> §rGive everyone <amount> amount of <item>.");
            s.sendMessage("§b/hgacr <item> <min> <max> §rGive everyone a random amount of <item> within the range of <min> and <max>.");
            s.sendMessage("§bⓒ Horyu Systems Ltd, All Rights Reserved.");
            s.sendMessage("§6■==== §eMade By horyu1234 §6====■");
        } else if (page == 2) {
            s.sendMessage("§6■==== §e" + name + " §6====■");
            s.sendMessage("§a2. Item Info Related Commands");
            s.sendMessage("§b/hca [type] §rCreate a book with a list of players who has the same item with [type] value as the item in hand.");
            s.sendMessage("§fTypes: name|lore|type|enchant|amount|potion");
            s.sendMessage("§fUsage: §a(When looking for players who has the same item with [type] value)");
            s.sendMessage("§a/hca name,type,enchant or /hca name|type|enchant");
            s.sendMessage("§bⓒ Horyu Systems Ltd, All Rights Reserved.");
            s.sendMessage("§6■==== §eMade By horyu1234 §6====■");
        } else if (page == 3) {
            s.sendMessage("§6■==== §e" + name + " §6====■");
            s.sendMessage("§a3. Item-to-File Related Commands");
            s.sendMessage("§b/dga add <name> §rSave the item in hand to a file.");
            s.sendMessage("§b/dga remove <name> §rDelete the saved item");
            s.sendMessage("§b/dga list §rCreate a book with list of saved items.");
            s.sendMessage("§b/dga give <player> <name> <amount> §rGive <player> <name>.");
            s.sendMessage("§b/dga giveall <name> <amount> §rGive everyone <name.");
            s.sendMessage("§b/dga rgive <player> <name> <min> <max> §rGive <player> a random (with min and max) amount of <name>.");
            s.sendMessage("§b/dga rgiveall <name> <min> <max> §r Give everyone a random (with min and max) amount of <name>.");
            s.sendMessage("§bⓒ Horyu Systems Ltd, All Rights Reserved.");
            s.sendMessage("§6■==== §eMade By horyu1234 §6====■");
        } else if (page == 4) {
            s.sendMessage("§6■==== §e" + name + " §6====■");
            s.sendMessage("§a4. Money Related Commands");
            s.sendMessage("§b/mga give <amount> §rGive everyone <amount> of money.");
            s.sendMessage("§b/mga take <amount> §rTake <amount> of money from everyone.");
            s.sendMessage("§b/mga rgive <min> <max> §rGive everyone a random amount of money within the range of <min> and <max>.");
            s.sendMessage("§b/mga rtake <min> <max> §rTake a random amount of money from everyone within the range of <min> and <max>.");
            s.sendMessage("§bⓒ Horyu Systems Ltd, All Rights Reserved.");
            s.sendMessage("§6■==== §eMade By horyu1234 §6====■");
        } else if (page == 5) {
            s.sendMessage("§6■==== §e" + name + " §6====■");
            s.sendMessage("§a5. Potion Related Commands");
            s.sendMessage("§b/pga §rApply everyone the potion effect of the potion you're holding.");
            s.sendMessage("§fIf you're holding a bucket of milk, /pga will remove all potion effects from everyone.");
            s.sendMessage("§bⓒ Horyu Systems Ltd, All Rights Reserved.");
            s.sendMessage("§6■==== §eMade By horyu1234 §6====■");
        } else {
            s.sendMessage(plugin.prefix + "§cPage not exists.");
        }
    }
}