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

package com.horyu1234.handgiveall;

import com.horyu1234.handgiveall.commands.*;
import com.horyu1234.handgiveall.utils.*;
import com.horyu1234.handgiveall.web.PluginInfoChecker;
import com.horyu1234.handgiveall.web.Stats;
import com.horyu1234.handgiveall.web.UpdateChecker;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HandGiveAll extends JavaPlugin {
	public String prefix, bcprefix;
	public double plugin_version;
	public boolean hookedVault = false;
	public Economy economy = null;
	private PluginInfoChecker.PluginInfo pluginInfo;
	//=================
	private NumberUtils numberutil;
	private PlayerUtils playerutil;
	private ItemUtils itemutil;
	private FireworkUtils fireworkutil;
	private EnableUtils enableutils;
	private ReflectionUtils reflectionutils;
	//=================
	private Help command_help;
	private HandGiveAll_c command_HandGiveAll_c;
	private HandCheckAll_c command_HandCheckAll_c;
	private DataGiveAll_c command_DataGiveAll_c;
	private MoneyGiveAll_c command_MoneyGiveAll_c;
	private PotionGiveAll_c command_PotionGiveAll_c;
	//=================
	public boolean config_show_console_msg = false;
	public boolean config_show_inv_full_msg = true;
    public boolean config_show_message_box = true;
	//public boolean config_use_BungeeCord = false;
	public boolean config_use_nickname = false;
	public boolean config_use_item_display_name = true;
	public boolean config_use_firework = false;
	public String config_money_unit = "원";
	public int config_max_point_count = 3;


	public void onDisable() {
		sendConsole("§a플러그인이 비활성화 되었습니다.");
		sendConsole("§a플러그인제작자: horyu1234");
		sendConsole("§aCopyright 2014 ~ "+Calendar.getInstance().get(Calendar.YEAR)+" Horyu Systems Ltd, All Rights Reserved.");
	}

	public void onEnable() {
		PluginDescriptionFile pdf = getDescription();
		plugin_version = Double.parseDouble(pdf.getVersion());

		prefix = "§b§l[§f§lHandGiveAll v" + plugin_version + "§b§l] §r";
		bcprefix = "§b§l[§f§lHGA v" + plugin_version + "§b§l] §r";

        sendConsole("§f플러그인 설치 중입니다. 잠시만 기다려주세요...");

		initClasses();

		if (enableutils.checkFileName()) return;

		enableutils.checkData();
		if (!enableutils.checkEULA()) return;

		if (enableutils.checkConfigVersion()) return;
		enableutils.loadConfig();

		enableutils.hookVault();
		initCommands();

		new UpdateChecker(this, Bukkit.getConsoleSender());
		PluginInfoChecker pluginInfoChecker = new PluginInfoChecker();
		pluginInfo = pluginInfoChecker.getInfo(this);

		if (enableutils.checkDisable(pluginInfo)) return;
		if (enableutils.checkMD5(pluginInfo)) return;
		if (pluginInfo.getNotices().size() > 0)
		{
			sendConsole("§e=====§b§l[ §aHandGiveAll 공지 §b§l]§e=====");
			for (String notice : pluginInfo.getNotices())
				sendConsole(notice);
			sendConsole("§e===========================");
		}

		getServer().getPluginManager().registerEvents(new HandGiveAllListener(this, pluginInfo), this);
		/*
		if (config_use_BungeeCord) {
			this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
			this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("GetServers");

			this.getServer().sendPluginMessage(this, "BungeeCord", out.toByteArray());
			sendConsole("§fBungeeCord 와 연결되어 있는지 확인 중입니다.");
		}
		*/
		sendConsole("§a플러그인이 활성화 되었습니다.");
		sendConsole("§a플러그인제작자: horyu1234");
		sendConsole("§aCopyright 2014 ~ "+Calendar.getInstance().get(Calendar.YEAR)+" Horyu Systems Ltd, All Rights Reserved.");
	}

	public void sendConsole(String msg) {
		if (config_show_console_msg) {
			PlayerUtils.sendMsg(prefix + msg);
		} else {
			getServer().getConsoleSender().sendMessage(prefix + msg);
		}
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
		Stats stats = new Stats(this);
		stats.sendStatsData();
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

			PlayerUtils.sendMsg(prefix+"§c예상치 못한 오류가 §eHandGiveAll/errors/ §c폴더에 저장되었습니다.");
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

	public String checkSumApacheCommons(String plugin) {
		File jar = null;
		try {
			jar = new File(Bukkit.getPluginManager().getPlugin(plugin)
					.getClass().getProtectionDomain().getCodeSource()
					.getLocation().toURI());
		} catch (URISyntaxException e) {
			return null;
		}

		FileInputStream fis = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			fis = new FileInputStream(jar);
			byte[] dataBytes = new byte[1024];

			int nread = 0;

			while ((nread = fis.read(dataBytes)) != -1) {
				md.update(dataBytes, 0, nread);
			}

			byte[] mdbytes = md.digest();

			// convert the byte to hex format
			StringBuffer sb = new StringBuffer("");
			for (int i = 0; i < mdbytes.length; i++) {
				sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			fis.close();
			return sb.toString().toUpperCase();
		} catch (Exception e) { try { fis.close(); } catch (Exception e2) { }}
		return null;
	}

	private String replaceColors(String str) {
		for (int i = 0; i < 10; ++i) {
			str = str.replaceAll("§" + i, "&" + i);
		}
		final char[] c = { 'a', 'b', 'c', 'd', 'e', 'f', 'l', 'm', 'n', 'o', 'r' };
		for (int j = 0; j < c.length; ++j) {
			str = str.replaceAll("§" + c[j], "&" + c[j]);
		}
		return str;
	}

	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		if (l.equalsIgnoreCase("hn")) {
			if (pluginInfo.getNotices().size() > 0) {
				s.sendMessage(prefix + "§e=====§b§l[ §aHandGiveAll 공지 §b§l]§e=====");
				for (String notice : pluginInfo.getNotices())
					s.sendMessage(prefix + notice);
				s.sendMessage(prefix + "§e===========================");
			} else s.sendMessage(prefix + "§f공지 사항이 없습니다.");
		}
		return false;
	}

	/*
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("BungeeCord")) return;

		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();
		sendConsole("subchannel : " + subchannel);
		if (subchannel.equals("SomeSubChannel")) {
			// Use the code sample in the 'Response' sections below to read
			// the data.
		}
	}
	*/
}