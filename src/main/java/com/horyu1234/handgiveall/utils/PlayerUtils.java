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

package com.horyu1234.handgiveall.utils;

import com.horyu1234.handgiveall.HandGiveAll;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

public class PlayerUtils {
	@SuppressWarnings("unused")
	private HandGiveAll plugin;
	public PlayerUtils(HandGiveAll pl) {
		this.plugin = pl;
	}

	public static Player[] getOnlinePlayers() {
		Class<?>[] parameterTypes = {};
		Object obj = null;
		try{
			obj = Bukkit.class.getMethod("getOnlinePlayers", parameterTypes).invoke(null, (Object[])null);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		if(obj instanceof Player[]) {
			Player[] onlinePlayers = (Player[]) obj;
			return onlinePlayers;
		} else if(obj instanceof Collection) {
			@SuppressWarnings("unchecked")
			Collection<? extends Player> onlinePlayer = (Collection<? extends Player>) obj;
			Player[] onlinePlayers = new Player[onlinePlayer.size()];
			int i = 0;
			for(Player player: onlinePlayer) {
				onlinePlayers[i] = player;
				i++;
			}
			return onlinePlayers;
		}
		return null;
	}

	public Player getPlayer(String name)
	{
		for (Player all : getOnlinePlayers())
			if (all.getName().equalsIgnoreCase(name))
				return all;
		return null;
	}

	public static void sendMsg(String msg) {
		Bukkit.getConsoleSender().sendMessage(msg);
		for (Player all : getOnlinePlayers()) {
			all.sendMessage(msg);
		}
	}
}