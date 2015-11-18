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

package com.horyu1234.handgiveall;

import com.horyu1234.handgiveall.commands.*;
import com.horyu1234.handgiveall.utils.*;
import com.horyu1234.handgiveall.web.Notice;
import com.horyu1234.handgiveall.web.Stats;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class HandGiveAll extends JavaPlugin implements Listener {
	public String prefix = "§b§l[§f§lHandGiveAll v10.5§b§l] §r";
	public String bcprefix = "§b§l[§f§lHGA v10.5§b§l] §r";
	public double pluginversion;
	public boolean hookedVault = false;
	public Economy economy = null;
	//=================
	public NumberUtils numberutil;
	public PlayerUtils playerutil;
	public ItemUtils itemutil;
	public FireworkUtils fireworkutil;
	public EnableUtils enableutils;
	public ReflectionUtils reflectionutils;
	//=================
	public Help command_help;
	public HandGiveAll_c command_HandGiveAll_c;
	public HandCheckAll_c command_HandCheckAll_c;
	public DataGiveAll_c command_DataGiveAll_c;
	public MoneyGiveAll_c command_MoneyGiveAll_c;
	public PotionGiveAll_c command_PotionGiveAll_c;
	//=================
	public double version;
	public static List<String> Notices;

	public void onDisable() {
		sendConsole("§a플러그인이 비활성화 되었습니다.");
		sendConsole("§a플러그인제작자: horyu1234");
		sendConsole("§aⓒ "+Calendar.getInstance().get(Calendar.YEAR)+" Horyu Systems Ltd, All Rights Reserved.");
	}

	public void onEnable() {
		initClasses();
		sendConsole("§f플러그인 설치 중입니다. 잠시만 기다려주세요...");
		Stats stats = new Stats(this);
		stats.sendStatsData();
		if (enableutils.checkFileName()) return;
		PluginDescriptionFile pdf = getDescription();
		pluginversion = Double.parseDouble(pdf.getVersion());
		getServer().getPluginManager().registerEvents(new HandGiveAllListener(this), this);
		enableutils.checkData();
		new Thread(new Runnable() {
			public void run() {
				if (enableutils.checkDisable()) return;
				enableutils.checkUpdate();

				if (!enableutils.checkEULA()) return;
				if (enableutils.checkConfigVersion()) return;
				enableutils.hookVault();
				initCommands();
				sendConsole("§a플러그인이 활성화 되었습니다.");
				sendConsole("§a플러그인제작자: horyu1234");
				sendConsole("§aCopyright 2014 ~ "+Calendar.getInstance().get(Calendar.YEAR)+" Horyu Systems Ltd, All Rights Reserved.");

				Notice.getNotices();
				if (!Notices.get(0).equalsIgnoreCase("none"))
				{
					sendConsole("§e=====§b§l[ §aHandGiveAll 공지 §b§l]§e=====");
					for (String not : Notices)
						sendConsole(not);
					sendConsole("§e===========================");
				}
			}
		}).start();
	}

	public void sendConsole(String msg) {
		getServer().getConsoleSender().sendMessage(prefix + msg);
	}

	public boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null)
			economy = economyProvider.getProvider();
		return (economy != null);
	}

	public void initClasses() {
		enableutils = new EnableUtils(this);
		numberutil = new NumberUtils(this);
		playerutil = new PlayerUtils(this);
		itemutil = new ItemUtils(this);
		fireworkutil = new FireworkUtils(this);
		reflectionutils = new ReflectionUtils(this);
		RandomFireworks.getManager().addColors();
		RandomFireworks.getManager().addTypes();
	}

	public void initCommands() {
		command_HandGiveAll_c = new HandGiveAll_c(this);
		command_HandCheckAll_c = new HandCheckAll_c(this);
		command_DataGiveAll_c = new DataGiveAll_c(this);
		command_MoneyGiveAll_c = new MoneyGiveAll_c(this);
		command_PotionGiveAll_c = new PotionGiveAll_c(this);
		command_help = new Help(this);
		getCommand("hh").setExecutor(command_help);
		getCommand("hga").setExecutor(command_HandGiveAll_c);
		getCommand("hgar").setExecutor(command_HandGiveAll_c);
		getCommand("hgac").setExecutor(command_HandGiveAll_c);
		getCommand("hgacr").setExecutor(command_HandGiveAll_c);
		getCommand("hca").setExecutor(command_HandCheckAll_c);
		getCommand("dga").setExecutor(command_DataGiveAll_c);
		getCommand("mga").setExecutor(command_MoneyGiveAll_c);
		getCommand("pga").setExecutor(command_PotionGiveAll_c);
	}

	public NumberUtils getNumberUtil() { return this.numberutil; }
	public Help getHelp() { return this.command_help; }
	public PlayerUtils getPlayerUtils() { return this.playerutil; }
	public ItemUtils getItemUtils() { return this.itemutil; }
	public FireworkUtils getFireworkUtils() { return this.fireworkutil; }
	public ReflectionUtils getReflectionUtils() { return this.reflectionutils; }

	public void error_notice(Exception e) {
		File a = new File("plugins/HandGiveAll/errors");
		if (!a.exists()) {
			a.mkdirs();
		}

		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));

		try {
			File f = new File("plugins/HandGiveAll/errors/"+getTimestamp()+".txt");
			if (f.exists()) f.delete();
			BufferedWriter out = new BufferedWriter(new FileWriter(f));
			out.write("아래 오류를 개발자에게 전송해주시면 큰 도움이 됩니다.\n\n"+errors.toString());
			out.close();
			PlayerUtils.sendMsg(prefix+"§c플러그인의 오류가 §eHandGiveAll/errors/ §c폴더에 저장되었습니다.");
			PlayerUtils.sendMsg(prefix+"§c개발자에게 해당 오류를 전송해주시면 큰 도움이 됩니다.");
		} catch (Exception e2) {
			PlayerUtils.sendMsg(prefix+"§c플러그인 오류 로그를 저장하는데 문제가 발생했습니다.");
		}
	}

	public String getTimestamp() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월dd일_HH시mm분ss초");
		return sdf.format(cal.getTime());
	}
}