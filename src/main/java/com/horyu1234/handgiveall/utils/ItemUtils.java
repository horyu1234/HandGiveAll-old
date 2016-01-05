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

package com.horyu1234.handgiveall.utils;

import com.horyu1234.handgiveall.HandGiveAll;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {
	private HandGiveAll plugin;
	public ItemUtils(HandGiveAll pl) {
		this.plugin = pl;
	}

	public void giveItemAll(ItemStack item, int amount, boolean fullInvMsg) {
		item.setAmount(amount);
		for (Player all : PlayerUtils.getOnlinePlayers()) {
			if (all.getInventory().firstEmpty() != -1) {
				all.getInventory().addItem(item.clone());
			} else if (fullInvMsg) {
				PlayerUtils.sendMsg(plugin.bcprefix + "§e" + all.getName() + " §c님은 인벤토리가 꽉차여 아이템을 받지 못하셨습니다.");
			}
		}
	}

	public String getItemName(ItemStack item) {
		ItemMeta itemM = item.getItemMeta();
		if (itemM.hasDisplayName())
			return itemM.getDisplayName();
		return item.getType().name();
	}
}