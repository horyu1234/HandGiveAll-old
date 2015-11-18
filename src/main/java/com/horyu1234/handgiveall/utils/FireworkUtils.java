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

import org.bukkit.entity.Player;

import com.horyu1234.handgiveall.HandGiveAll;

public class FireworkUtils {
	private HandGiveAll plugin;
	public FireworkUtils(HandGiveAll pl) {
		this.plugin = pl;
	}
	
	public void launchFireworkToAll() {
		if (plugin.getConfig().getBoolean("firework"))
			for (Player all : PlayerUtils.getOnlinePlayers())
				RandomFireworks.getManager().launchRandomFirework(all.getLocation());
	}
}