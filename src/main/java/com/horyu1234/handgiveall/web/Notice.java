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

package com.horyu1234.handgiveall.web;

import com.horyu1234.handgiveall.HandGiveAll;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Notice
{
	public static void getNotices()
	{
		HandGiveAll.Notices = new ArrayList<String>();
		try
		{
			URL url = new URL("https://raw.githubusercontent.com/horyu1234/Checker/master/Notice/HandGiveAll_v10.5");
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), Charset.forName("UTF-8")));
			String str;
			while ((str = br.readLine()) != null)
			{
				if (str.contains("@notice@"))
				{
					HandGiveAll.Notices.add(str.replaceAll("@notice@", ""));
				}
			}
			br.close();
		}
		catch (Exception e)
		{
			System.out.println("[HandGiveAll] Failed to get notices list!");
		}
	}
}