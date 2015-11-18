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

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.Random;

public class RandomFireworks {
	private static RandomFireworks fireWorks = new RandomFireworks();
	public static RandomFireworks getManager() {
		return fireWorks;
	}
	ArrayList<Color> colors = new ArrayList<Color>();
	ArrayList<FireworkEffect.Type> types = new ArrayList<FireworkEffect.Type>();
	public void addColors() {
		colors.add(Color.WHITE);
		colors.add(Color.PURPLE);
		colors.add(Color.RED);
		colors.add(Color.GREEN);
		colors.add(Color.AQUA);
		colors.add(Color.BLUE);
		colors.add(Color.FUCHSIA);
		colors.add(Color.GRAY);
		colors.add(Color.LIME);
		colors.add(Color.MAROON);
		colors.add(Color.YELLOW);
		colors.add(Color.SILVER);
		colors.add(Color.TEAL);
		colors.add(Color.ORANGE);
		colors.add(Color.OLIVE);
		colors.add(Color.NAVY);
		colors.add(Color.BLACK);
	}
	public void addTypes() {
		types.add(FireworkEffect.Type.BURST);
		types.add(FireworkEffect.Type.BALL);
		types.add(FireworkEffect.Type.BALL_LARGE);
		types.add(FireworkEffect.Type.CREEPER);
		types.add(FireworkEffect.Type.STAR);
	}
	public FireworkEffect.Type getRandomType() {
		int size = types.size();
		Random ran = new Random();
		FireworkEffect.Type theType = types.get(ran.nextInt(size));
		return theType;
	}
	public Color getRandomColor() {
		int size = colors.size();
		Random ran = new Random();
		Color color = colors.get(ran.nextInt(size));
		return color;
	}
	public void launchRandomFirework(Location loc) {
		Firework fw = loc.getWorld().spawn(loc, Firework.class);
		FireworkMeta fm = fw.getFireworkMeta();
		fm.setPower(1);
		fm.addEffects(FireworkEffect.builder().with(getRandomType())
				.withColor(getRandomColor()).build());
		fw.setFireworkMeta(fm);
	}
}