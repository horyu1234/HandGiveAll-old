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

package com.horyu1234.handgiveall;

import com.horyu1234.handgiveall.commands.*;
import com.horyu1234.handgiveall.utils.*;
import com.horyu1234.handgiveall.web.MCBlacklist;
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
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HandGiveAll extends JavaPlugin {
    public String prefix, bcprefix;
    public double plugin_version;
    public boolean hookedVault = false;
    public Economy economy = null;
    //=================
    public boolean use_korean = false;
    public boolean config_show_console_msg = false;
    public boolean config_show_inv_full_msg = true;
    public boolean config_show_message_box = true;
    //public boolean config_use_BungeeCord = false;
    public boolean config_use_nickname = false;
    public boolean config_use_item_display_name = true;
    public boolean config_use_firework = false;
    public String config_money_unit = "원";
    public int config_max_point_count = 3;
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

    public void onDisable() {
        sendConsole("§aPlugin has been disabled.");
        sendConsole("§aDeveloper: horyu1234");
        sendConsole("§aCopyright 2014 ~ " + Calendar.getInstance().get(Calendar.YEAR) + " Horyu Systems Ltd, All Rights Reserved.");
    }

    public void onEnable() {
        PluginDescriptionFile pdf = getDescription();
        plugin_version = Double.parseDouble(pdf.getVersion());

        prefix = "§b§l[§f§lHandGiveAll v" + plugin_version + "§b§l] §r";
        bcprefix = "§b§l[§f§lHGA v" + plugin_version + "§b§l] §r";

        if (!new File(getDataFolder(), "config.yml").exists()) {
            sendConsole("§cCouldn't find a config file. Creating one...");
            saveResource("config.yml", false);
            sendConsole("§aDone");
        }

        HGAYamlConfiguration config = new HGAYamlConfiguration();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("plugins/HandGiveAll/config.yml")), Charset.forName("UTF-8")));
            String datas = "", str;
            while ((str = bufferedReader.readLine()) != null) {
                datas += str + "\n";
            }
            bufferedReader.close();

            config.loadFromString(datas);
        } catch (Exception e) {
            sendConsole("§c[English]");
            sendConsole("§cAn error occurred while loading config.yml");
            sendConsole("§cPlease assure that config.yml is encoded with UTF-8");
            sendConsole("§cDon't edit config.yml using Windows Notepad! This could cause conflicts with UTF-8 encoding.");
            sendConsole("§c[Korean]");
            sendConsole("§cconfig.yml 을 로딩하는 동안 오류가 발생했습니다.");
            sendConsole("§cconfig.yml 파일의 인코딩이 UTF-8 인지 확인해주세요");
            sendConsole("§cconfig.yml 파일을 윈도우 기본 메모장으로 수정하지 마세요! 이것은 인코딩이 깨지는 현상의 원인이 될 수 있습니다.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (!config.contains("use_english") || config.getBoolean("use_english")) {
            // use english
            use_korean = false;
            LanguageUtils.setEnglish();
            sendConsole("§dLanguage was set to English");
            sendConsole("§dSpecial thanks to Peulia for his English translation.");
        } else {
            // use korean
            use_korean = true;
            LanguageUtils.setKorean();
            sendConsole("§d사용 언어가 한국어로 설정되었습니다.");
        }

        sendConsole(LanguageUtils.getString("enable.installing"));

        initClasses();

        if (enableutils.checkFileName()) return;

        enableutils.checkData();
        if (!enableutils.checkEULA()) return;

        if (enableutils.checkConfigVersion()) return;
        enableutils.loadConfig();

        enableutils.hookVault();
        initCommands();

        new UpdateChecker(this, Bukkit.getConsoleSender(), true);
        PluginInfoChecker pluginInfoChecker = new PluginInfoChecker();
        pluginInfo = pluginInfoChecker.getInfo(this);

        if (enableutils.checkDisable(pluginInfo)) return;
        if (enableutils.checkMD5(pluginInfo)) return;
        if (pluginInfo.getNotices().size() > 0) {
            sendConsole(LanguageUtils.getString("enable.notices.header"));
            for (String notice : pluginInfo.getNotices())
                sendConsole(notice);
            sendConsole(LanguageUtils.getString("enable.notices.footer"));
        }

        MCBlacklist.init(this);
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
        sendConsole(LanguageUtils.getString("enable.done.1"));
        sendConsole(LanguageUtils.getString("enable.done.2"));
        sendConsole(LanguageUtils.getString("enable.done.3").replace("@currentYear@", Calendar.getInstance().get(Calendar.YEAR) + ""));
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

    private void initClasses() {
        enableutils = new EnableUtils(this);
        numberutil = new NumberUtils(this);
        playerutil = new PlayerUtils(this);
        itemutil = new ItemUtils(this);
        fireworkutil = new FireworkUtils(this);
        reflectionutils = new ReflectionUtils(this);
        RandomFireworks.getManager().addColors();
        RandomFireworks.getManager().addTypes();
    }

    private void initCommands() {
        com.horyu1234.handgiveall.commands.HandGiveAll handGiveAll = new com.horyu1234.handgiveall.commands.HandGiveAll(this);
        HandCheckAll handCheckAll = new HandCheckAll(this);
        DataGiveAll dataGiveAll = new DataGiveAll(this);
        MoneyGiveAll moneyGiveAll = new MoneyGiveAll(this);
        PotionGiveAll potionGiveAll = new PotionGiveAll(this);
        command_help = new Help(this);

        getCommand("hh").setExecutor(command_help);

        getCommand("hga").setExecutor(handGiveAll);
        getCommand("hgar").setExecutor(handGiveAll);
        getCommand("hgac").setExecutor(handGiveAll);
        getCommand("hgacr").setExecutor(handGiveAll);

        getCommand("hca").setExecutor(handCheckAll);

        getCommand("dga").setExecutor(dataGiveAll);

        getCommand("mga").setExecutor(moneyGiveAll);

        getCommand("pga").setExecutor(potionGiveAll);

        Stats stats = new Stats(this);
        stats.sendStatsData();
    }

    public NumberUtils getNumberUtil() {
        return this.numberutil;
    }

    public Help getHelp() {
        return this.command_help;
    }

    public PlayerUtils getPlayerUtils() {
        return this.playerutil;
    }

    public ItemUtils getItemUtils() {
        return this.itemutil;
    }

    public FireworkUtils getFireworkUtils() {
        return this.fireworkutil;
    }

    public ReflectionUtils getReflectionUtils() {
        return this.reflectionutils;
    }

    public void error_notice(Exception e) {
        File a = new File("plugins/HandGiveAll/errors");
        if (!a.exists()) {
            a.mkdirs();
        }

        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));

        try {
            File f = new File("plugins/HandGiveAll/errors/" + getTimestamp() + ".txt");
            if (f.exists()) f.delete();

            BufferedWriter out = new BufferedWriter(new FileWriter(f));
            out.write(LanguageUtils.getString("error.file.header") + "\n\n" + errors.toString());
            out.close();

            PlayerUtils.sendMsg(prefix + LanguageUtils.getString("error.notice.1"));
            PlayerUtils.sendMsg(prefix + LanguageUtils.getString("error.notice.2"));
        } catch (Exception e2) {
            PlayerUtils.sendMsg(prefix + LanguageUtils.getString("error.error"));
        }
    }

    private String getTimestamp() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(LanguageUtils.getString("error.date.format"));
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
        } catch (Exception e) {
            try {
                fis.close();
            } catch (Exception e2) {
            }
        }
        return null;
    }

    private String replaceColors(String str) {
        for (int i = 0; i < 10; ++i) {
            str = str.replaceAll("§" + i, "&" + i);
        }
        final char[] c = {'a', 'b', 'c', 'd', 'e', 'f', 'l', 'm', 'n', 'o', 'r'};
        for (int j = 0; j < c.length; ++j) {
            str = str.replaceAll("§" + c[j], "&" + c[j]);
        }
        return str;
    }

    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        if (l.equalsIgnoreCase("hn")) {
            if (pluginInfo.getNotices().size() > 0) {
                s.sendMessage(prefix + LanguageUtils.getString("command.hn.notices.header"));
                for (String notice : pluginInfo.getNotices())
                    s.sendMessage(prefix + notice);
                s.sendMessage(prefix + LanguageUtils.getString("command.hn.notices.footer"));
            } else s.sendMessage(prefix + LanguageUtils.getString("command.hn.notices.empty"));
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