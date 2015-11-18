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

import java.util.Random;

public class NumberUtils {
	@SuppressWarnings("unused")
	private HandGiveAll plugin;

	public NumberUtils(HandGiveAll pl) {
		this.plugin = pl;
	}

	// success -> return number
	// fail -> return -999
	public int parseInt(String number) {
		int num;
		try {
			num = Integer.parseInt(number);
		} catch (Exception e) {
			return -999;
		}
		return num;
	}

	// return random in min & max
	public int randomInRange(int min, int max) {
		Random rand = new Random();
		return rand.nextInt(max - min + 1) + min;
	}

	// return random in min & max
	public double randomDoubleInRange(double max, double min) {
		double r = Math.random();
		if (r < 0.5) {
			return ((1 - Math.random()) * (max - min) + min);
		}
		return (Math.random() * (max - min) + min);
	}
}