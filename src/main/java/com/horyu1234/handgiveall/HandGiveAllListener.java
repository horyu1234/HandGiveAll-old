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

import com.horyu1234.handgiveall.utils.LanguageUtils;
import com.horyu1234.handgiveall.web.MCBlacklist;
import com.horyu1234.handgiveall.web.PluginInfoChecker;
import com.horyu1234.handgiveall.web.UpdateChecker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class HandGiveAllListener implements Listener {
    private HandGiveAll plugin;
    private PluginInfoChecker.PluginInfo pluginInfo;

    public HandGiveAllListener(HandGiveAll pl, PluginInfoChecker.PluginInfo pluginInfo) {
        this.plugin = pl;
        this.pluginInfo = pluginInfo;
    }

    @EventHandler
    private void onPlayerJoin(final PlayerJoinEvent e) {
        new UpdateChecker(plugin, e.getPlayer());

        if (pluginInfo.getNotices().size() > 0) {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    if (e.getPlayer().isOnline()) {
                        if (pluginInfo.getNotice_date().equals("없음")) {
                            e.getPlayer().sendMessage(plugin.prefix + LanguageUtils.getString("event.join.notices.no_date"));
                        } else {
                            e.getPlayer().sendMessage(plugin.prefix + LanguageUtils.getString("event.join.notices.with_date").replace("@date", pluginInfo.getNotice_date()));
                        }
                    }
                }
            }, 200L);
        }

        MCBlacklist.check(e.getPlayer());
    }
}
/*
final class Version_Thread extends Thread {
	private HandGiveAll plugin;
	private Player p;

	public Version_Thread(HandGiveAll pl, Player player) {
		this.plugin = pl;
		this.p = player;
	}

	public void run() {
		if (p.isOp()) {
			if (plugin.version == plugin.pluginversion)
				p.sendMessage(plugin.prefix+"§f새로운 버전이 없습니다.");
			else {
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						if (plugin.version > plugin.pluginversion) {
							p.sendMessage(plugin.prefix+"§b#==============================#");
							p.sendMessage(plugin.prefix+"§f플러그인의 새로운 업데이트가 발견되었습니다!");
							p.sendMessage(plugin.prefix+"§c현재버전: "+plugin.pluginversion);
							p.sendMessage(plugin.prefix+"§a현재버전: "+plugin.version);
							p.sendMessage(plugin.prefix+"§e플러그인 다운로드 링크: https://horyu1234.com/HandGiveAll");
							p.sendMessage(plugin.prefix+"§b#==============================#");
						} else if (plugin.version < plugin.pluginversion) {
							p.sendMessage(plugin.prefix+"§f#==============================#");
							p.sendMessage(plugin.prefix+"§c서버에 올려진 플러그인의 버전보다 현재 플러그인의 버전이 높습니다.");
							p.sendMessage(plugin.prefix+"§f#==============================#");
						} else {
							p.sendMessage(plugin.prefix+"§f#==============================#");
							p.sendMessage(plugin.prefix+"§c플러그인의 버전을 확인하는데 문제가 발생했습니다.");
							p.sendMessage(plugin.prefix+"§f#==============================#");
						}
					}
				}, 20L * 5);
			}
		}
	}
}
*/
/*
final class Notice_Thread extends Thread {
	private HandGiveAll plugin;
	private Player p;

	public Notice_Thread(HandGiveAll pl, Player player) {
		this.plugin = pl;
		this.p = player;
	}

	public void run() {
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				p.sendMessage("§e=====§b§l[ §aHandGiveAll 공지 §b§l]§e=====");
				for (String not : HandGiveAll.Notices)
					p.sendMessage(not);
				p.sendMessage("§e===========================");
			}
		}, 20L * 10);
	}
}
*/