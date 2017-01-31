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

package com.horyu1234.handgiveall.web;

import com.horyu1234.handgiveall.HandGiveAll;
import com.horyu1234.handgiveall.utils.LanguageUtils;
import org.bukkit.command.CommandSender;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker implements Runnable {
    private HandGiveAll plugin;
    private CommandSender sender;
    private boolean showMessageBox;
    private String url_str;

    public UpdateChecker(final HandGiveAll plugin, final CommandSender sender, boolean showMessageBox) {
        this.plugin = plugin;
        this.sender = sender;
        this.showMessageBox = showMessageBox;
        this.url_str = "http://minecraft.horyu.me/minecraft/" + plugin.getName() + "/version";
        new Thread(this).start();
    }

    public void run() {
        try {
            URL url = new URL(url_str);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; ko) HandGiveAll/" + plugin.getDescription().getVersion() + " (Made By horyu1234)");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Content-Language", LanguageUtils.getString("check.update.web.Content-Language"));
            httpURLConnection.setDoOutput(true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF8"));

            String msg;
            while ((msg = bufferedReader.readLine()) != null) {
                double plugin_version = Double.parseDouble(msg);
                bufferedReader.close();

                if (plugin_version > plugin.plugin_version) {
                    sender.sendMessage(plugin.prefix + LanguageUtils.getString("check.update.new_version.header"));
                    sender.sendMessage(plugin.prefix + LanguageUtils.getString("check.update.new_version.1"));
                    sender.sendMessage(plugin.prefix + LanguageUtils.getString("check.update.new_version.2") + plugin.plugin_version);
                    sender.sendMessage(plugin.prefix + LanguageUtils.getString("check.update.new_version.3") + plugin_version);
                    sender.sendMessage(plugin.prefix + LanguageUtils.getString("check.update.new_version.4"));
                    sender.sendMessage(plugin.prefix + LanguageUtils.getString("check.update.new_version.footer"));
                    if (plugin.config_show_message_box && showMessageBox) {
                        new Thread(new Runnable() {
                            public void run() {
                                JOptionPane.showMessageDialog(null, LanguageUtils.getString("check.update.new_version.message_box"), "HandGiveAll v" + plugin.plugin_version, JOptionPane.INFORMATION_MESSAGE);
                            }
                        }).start();
                    }
                } else if (plugin_version == plugin.plugin_version) {
                    sender.sendMessage(plugin.prefix + LanguageUtils.getString("check.update.no_new_version.1"));
                } else {
                    sender.sendMessage(plugin.prefix + LanguageUtils.getString("check.update.error_version.header"));
                    sender.sendMessage(plugin.prefix + LanguageUtils.getString("check.update.error_version.1"));
                    sender.sendMessage(plugin.prefix + LanguageUtils.getString("check.update.error_version.footer"));
                }
                return;
            }
            bufferedReader.close();
        } catch (Exception exception) {
            plugin.sendConsole(LanguageUtils.getString("check.update.error.1"));
            plugin.sendConsole(LanguageUtils.getString("check.update.error.2") + exception.getMessage());
        }
    }
}