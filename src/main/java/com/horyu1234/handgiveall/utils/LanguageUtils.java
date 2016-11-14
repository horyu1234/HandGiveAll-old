/*******************************************************************************
 * Copyright (c) 2014~2016 HoryuSystems Ltd. All rights reserved.
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
 * 라이센스: Copyright (c) 2014~2016 HoryuSystems Ltd. All rights reserved.
 * ============================================
 *
 * 자세한 내용은 https://horyu1234.com/EULA 를 확인해주세요.
 ******************************************************************************/

package com.horyu1234.handgiveall.utils;

import com.horyu1234.handgiveall.HandGiveAll;

import java.util.HashMap;

/**
 * Created by horyu on 2016-07-21.
 */
public class LanguageUtils {
    private HandGiveAll plugin;
    private static HashMap<String, String> language = new HashMap<String, String>();

    public LanguageUtils(HandGiveAll pl) {
        this.plugin = pl;
    }

    public static String getString(String key) {
        return language.get(key);
    }

    public static void setKorean() {
        // Key: 상태.옵션
        /* 플러그인 활성화 */
        language.put("enable.installing", "§f플러그인 설치 중입니다. 잠시만 기다려주세요...");
        language.put("enable.notices.header", "§e=====§b§l[ §aHandGiveAll 공지 §b§l]§e=====");
        language.put("enable.notices.footer", "§e===========================");
        language.put("enable.done.1", "§a플러그인이 활성화 되었습니다.");
        language.put("enable.done.2", "§a플러그인제작자: horyu1234");
        language.put("enable.done.3", "§aCopyright 2014 ~ @currentYear@ Horyu Systems Ltd, All Rights Reserved.");

        /* 플러그인 비활성화 */
        language.put("disable.done.1", "§a플러그인이 비활성화 되었습니다.");
        language.put("disable.done.2", "§a플러그인제작자: horyu1234");
        language.put("disable.done.3", "§aCopyright 2014 ~ @currentYear@ Horyu Systems Ltd, All Rights Reserved.");

        /* 전체 예외 처리 */
        language.put("error.date.format", "yyyy-MM-dd HH.mm.ss");
        language.put("error.file.header", "아래 오류를 개발자에게 전송해주시면 큰 도움이 됩니다.");
        language.put("error.notice.1", "§c예상치 못한 오류가 §eHandGiveAll/errors/ §c폴더에 저장되었습니다.");
        language.put("error.notice.2", "§c개발자에게 해당 오류를 전송해주시면 큰 도움이 됩니다.");
        language.put("error.error", "§c플러그인 오류 로그를 저장하는데 문제가 발생했습니다.");

        /* hn 명령어 */
        language.put("command.hn.notices.header", "§e=====§b§l[ §aHandGiveAll 공지 §b§l]§e=====");
        language.put("command.hn.notices.footer", "§e===========================");
        language.put("command.hn.notices.empty", "§f공지 사항이 없습니다.");

        /* 플레이어 입장 */
        language.put("event.join.notices.no_date", "§f등록된 공지 사항이 있습니다. 확인하시려면 §a/hn §f를 입력해주세요.");
        language.put("event.join.notices.with_date", "§e@date@§f에 등록된 공지 사항이 있습니다. 확인하시려면 §a/hn §f를 입력해주세요.");

        /* 업데이트 확인 */
        language.put("check.update.web.Content-Language", "ko-KR");
        language.put("check.update.new_version.header", "§b#==============================#");
        language.put("check.update.new_version.1", "§f플러그인의 새로운 업데이트가 발견되었습니다!");
        language.put("check.update.new_version.2", "§c현재버전: ");
        language.put("check.update.new_version.3", "§a새로운버전: ");
        language.put("check.update.new_version.4", "§e플러그인 다운로드 링크: https://horyu1234.com/HandGiveAll");
        language.put("check.update.new_version.footer", "§b#==============================#");
        language.put("check.update.new_version.message_box", "플러그인의 새로운 업데이트가 발견되었습니다!");
        language.put("check.update.no_new_version.1", "§f새로운 버전이 없습니다.");
        language.put("check.update.error_version.header", "§f#==============================#");
        language.put("check.update.error_version.1", "§c플러그인의 버전을 확인하는데 문제가 발생했습니다.");
        language.put("check.update.error_version.footer", "§f#==============================#");
        language.put("check.update.error.1", "§c업데이트를 확인하는 중 문제가 발생했습니다.");
        language.put("check.update.error.2", "§c메시지: ");

        /* 버전에 대한 정보 가져오기 */
        language.put("version_info.error.1", "§c버전에 대한 정보를 가져오는 중 문제가 발생했습니다.");
        language.put("version_info.error.2", "§c메시지: ");
        language.put("version_info.default.disable_message", "서버와 통신에 실패했습니다.");

        /* ItemUtils */
        language.put("item_utils.inv_full", "§e@player@ §c님은 인벤토리가 꽉차여 아이템을 받지 못하셨습니다.");

        /* 비활성화 확인 */
        language.put("check.disable.header", "§c#==============================#");
        language.put("check.disable.1", "본 플러그인의 제작자가 플러그인의 구동을 비활성화하여");
        language.put("check.disable.2", "플러그인 구동이 제한됩니다.");
        language.put("check.disable.3", "");
        language.put("check.disable.4", "§4사유: ");
        language.put("check.disable.footer", "§c#==============================#");
        language.put("check.disable.message_box", "본 플러그인의 제작자가 플러그인의 구동을 비활성화하여\n플러그인의 구동이 제한됩니다.");

        /* 데이터 확인 */
        language.put("check.data.create.config", "§c설정 파일을 찾을 수 없습니다. 새로 생성을 시작합니다...");
        language.put("check.data.create_done.config", "§a완료");
        language.put("check.data.loaded_config", "§a콘피그를 불러왔습니다.");
        language.put("check.data.error.load_config.1", "§c콘피그를 불러오는 중 오류가 발생했습니다.");
        language.put("check.data.error.load_config.2", "§c메시지: ");
        language.put("check.data.error.load_config.message_box", "콘피그를 불러오는 중 오류가 발생했습니다.");
        language.put("check.data.create.folder", "§c데이터 폴더를 찾을 수 없습니다. 새로 생성을 시작합니다...");
        language.put("check.data.create_done.folder", "§a완료");

        /* EULA 확인 */
        language.put("check.eula.header", "§f#==============================#");
        language.put("check.eula.1", "§cConfig 에서 플러그인의 EULA에 동의해주세요!!");
        language.put("check.eula.2", "§cEULA를 동의하지 않으시면 플러그인의 사용이 불가능합니다.");
        language.put("check.eula.footer", "§f#==============================#");
        language.put("check.eula.message_box", "Config 에서 플러그인의 EULA 에 동의해주시기 바랍니다.");

        /* Config 버전 확인 */
        language.put("check.config_version.header", "§f#==============================#");
        language.put("check.config_version.1", "§cConfig 의 버전이 맞지 않습니다!");
        language.put("check.config_version.2", "§cHandGiveAll 폴더 안의 config.yml 을 삭제하신 후 플러그인을 다시 실행해주시기 바랍니다.");
        language.put("check.config_version.footer", "§f#==============================#");
        language.put("check.config_version.message_box", "Config 의 버전이 맞지 않습니다.");

        /* Vault 확인 */
        language.put("check.hook_vault.version_fail.header", "§f#==============================#");
        language.put("check.hook_vault.version_fail.1", "§cVault 가 존재하지만, 호환되지 않는 버전입니다.");
        language.put("check.hook_vault.version_fail.2", "§cVault 버전을 1.4.1 버전 이상으로 업데이트 바랍니다.");
        language.put("check.hook_vault.version_fail.footer", "§f#==============================#");
        language.put("check.hook_vault.fail.header", "§f#==============================#");
        language.put("check.hook_vault.fail.1", "§cVault 와 연결하는데 실패했습니다.");
        language.put("check.hook_vault.fail.2", "§c돈 관련 명령어가 비활성화됩니다.");
        language.put("check.hook_vault.fail.footer", "§f#==============================#");
        language.put("check.hook_vault.success", "§aVault 와 성공적으로 연결되었습니다.");
        language.put("check.hook_vault.not_exist.header", "§f#==============================#");
        language.put("check.hook_vault.not_exist.1", "§cVault 가 존재하지 않습니다.");
        language.put("check.hook_vault.not_exist.2", "§c돈 관련 명령어가 비활성화됩니다.");
        language.put("check.hook_vault.not_exist.footer", "§f#==============================#");

        /* 파일 이름 확인 */
        language.put("check.file_name.header", "§f#==============================#");
        language.put("check.file_name.1", "§c플러그인 파일 이름 변경이 감지되었습니다.");
        language.put("check.file_name.2", "§c정상 이름으로 변경해주시기 바랍니다.");
        language.put("check.file_name.3", "");
        language.put("check.file_name.4", "§f현재 이름: §c");
        language.put("check.file_name.5", "§f정상 이름: §a");
        language.put("check.file_name.footer", "§f#==============================#");
        language.put("check.file_name.message_box", "플러그인 파일 이름 변경이 감지되었습니다.");

        /* 파일 변조 확인 */
        language.put("check.file_edit.header", "§f#==============================#");
        language.put("check.file_edit.1", "§c플러그인 파일 변조가 감지되었습니다.");
        language.put("check.file_edit.2", "§c정상 파일로 변경해주시기 바랍니다.");
        language.put("check.file_edit.footer", "§f#==============================#");
        language.put("check.file_edit.message_box", "플러그인 파일 변조가 감지되었습니다.");

        /* pga 명령어 */
        language.put("command.pga.error.access_denied", "§c권한이 없습니다.");
        language.put("command.pga.give.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.pga.give.1", "§e@player@§a님이 모든 온라인 플레이어에게 §e@effect@§a효과를 지급하였습니다.");
        language.put("command.pga.give.2", "§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
        language.put("command.pga.give.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.pga.error.data_empty", "§c포션 데이터가 없어, 지급이 불가능합니다.");
        language.put("command.pga.take.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.pga.take.1", "§e@player@§a님이 모든 온라인 플레이어에게서 모든 포션 효과를 제거하셨습니다.");
        language.put("command.pga.take.2", "§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
        language.put("command.pga.take.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.pga.error.hand_empty", "§c손에 포션 또는 우유를 들고 있어야 합니다.");
        language.put("command.pga.error.only_player", "§c게임 내에서만 사용 가능한 명령어입니다.");
        language.put("command.pga.error.only_number", "§c숫자만 입력해주세요!");
        language.put("command.pga.potion_format", "(이름:@name@, 레벨:@level@, 시간:@second@초)");

        /* mga 명령어 */
        language.put("command.mga.error.access_denied", "§c권한이 없습니다.");
        language.put("command.mga.error.not_hooked_vault", "§c현재 사용할 수 없는 기능입니다. Vault 와 연동 여부를 확인해주세요.");
        language.put("command.mga.error.only_number", "§c숫자만 입력해주세요!");
        language.put("command.mga.error.amount", "§c양은 0보다 커야합니다.");
        language.put("command.mga.give.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.mga.give.1", "§e@player@§a님이 모든 온라인 플레이어에게 §e@money@§a을(를) §a지급하였습니다.");
        language.put("command.mga.give.2", "§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
        language.put("command.mga.give.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.mga.take.error", "§e@player@ §c님은 잔액이 부족하여, 돈을 뺏지 못했습니다. 현재잔액: §e@money@");
        language.put("command.mga.take.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.mga.take.1", "§e@player@§c님이 모든 온라인 플레이어에게서 §e@money@§c을(를) §c뺏었습니다.");
        language.put("command.mga.take.2", "§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
        language.put("command.mga.take.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.mga.error.minmax", "§c최댓값은 최솟값보다 커야합니다.");
        language.put("command.mga.rgive.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.mga.rgive.1", "§e@player@§a님이 모든 온라인 플레이어에게 §e(랜덤추첨된)@money@§a을(를) §a지급하였습니다.");
        language.put("command.mga.rgive.2", "§3최솟값: §b@min@§f, §3최댓값: §b@max@");
        language.put("command.mga.rgive.3", "§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
        language.put("command.mga.rgive.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.mga.rtake.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.mga.rtake.1", "§e@player@§c님이 모든 온라인 플레이어에게서 §e(랜덤추첨된)@money@§c을(를) §c뺏었습니다.");
        language.put("command.mga.rtake.2", "§3최솟값: §b@min@§f, §3최댓값: §b@max@");
        language.put("command.mga.rtake.3", "§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
        language.put("command.mga.rtake.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

        /* hga 명령어 */
        language.put("command.hga.error.access_denied", "§c권한이 없습니다.");
        language.put("command.hga.error.only_number", "§c숫자만 입력해주세요!");
        language.put("command.hga.give.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.hga.give.1", "§e@player@§a님이 모두에게 §e@item@§a을(를) §e@amount@개 §a지급하였습니다.");
        language.put("command.hga.give.2", "§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
        language.put("command.hga.give.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.hga.error.amount", "§c수량은 0보다 커야 합니다.");
        language.put("command.hga.error.hand_empty", "§c손에 아이템을 들고 있어야 사용 가능합니다.");
        language.put("command.hga.error.only_player", "§c게임 내에서만 사용 가능한 명령어입니다.");

        language.put("command.hgar.error.access_denied", "§c권한이 없습니다.");
        language.put("command.hgar.error.only_number", "§c숫자만 입력해주세요!");
        language.put("command.hgar.give.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.hgar.give.1", "§e@player@§a님이 모두에게 §e@item@§a을(를) §e(랜덤추첨된)@amount@개 §a지급하였습니다.");
        language.put("command.hgar.give.2", "§3최솟값: §b@min@§f, §3최댓값: §b@max@");
        language.put("command.hgar.give.3", "§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
        language.put("command.hgar.give.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.hgar.error.amount", "§c값은 0보다 커야 합니다.");
        language.put("command.hgar.error.min_max", "§c최댓값은 최솟값보다 커야합니다.");
        language.put("command.hgar.error.hand_empty", "§c손에 아이템을 들고 있어야 사용 가능합니다.");
        language.put("command.hgar.error.only_player", "§c게임 내에서만 사용 가능한 명령어입니다.");

        language.put("command.hgac.error.access_denied", "§c권한이 없습니다.");
        language.put("command.hgac.error.only_number", "§c숫자만 입력해주세요!");
        language.put("command.hgac.give.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.hgac.give.1", "§e@player@§a님이 모든 온라인 플레이어에게 §e@item@§a을(를) §e@amount@개 §a지급하였습니다.");
        language.put("command.hgac.give.2", "§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
        language.put("command.hgac.give.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.hgac.error.amount", "§c수량은 0보다 커야합니다.");

        language.put("command.hgacr.error.access_denied", "§c권한이 없습니다.");
        language.put("command.hgacr.error.only_number", "§c숫자만 입력해주세요!");
        language.put("command.hgacr.error.not_exist_item_code", "§c존재하지 않는 아이템 코드입니다.");
        language.put("command.hgacr.give.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.hgacr.give.1", "§e@player@§a님이 모든 온라인 플레이어에게 §e@item@§a을(를) §e(랜덤추첨된)@amount@개 §a지급하였습니다.");
        language.put("command.hgacr.give.2", "§3최솟값: §b@min@§f, §3최댓값: §b@max@");
        language.put("command.hgacr.give.3", "§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
        language.put("command.hgacr.give.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.hgacr.error.amount", "§c값은 0보다 커야합니다.");
        language.put("command.hgacr.error.min_max", "§c최댓값은 최솟값보다 커야합니다.");

        /* hca 명령어 */
        language.put("command.hca.error.access_denied", "§c권한이 없습니다.");
        language.put("command.hca.error.only_number", "§c숫자만 입력해주세요!");
        language.put("command.hca.error.not_exist_item_code", "§c존재하지 않는 아이템 코드입니다.");
        language.put("command.hca.error.inventory_full", "§c책을 생성할 공간이 없습니다. 인벤토리를 비운 후 다시 시도해주세요.");
        language.put("command.hca.book.title", "해당아이템을 가지고있는 플레이어 목록");
        language.put("command.hca.book.display_name", "§a해당아이템을 가지고있는 플레이어 목록");
        language.put("command.hca.book.lore", "§e플러그인제작자: horyu1234");
        language.put("command.hca.book.max_line", "\n §c§l최대 줄제한입니다.");
        language.put("command.hca.book.none", "§c§l없음");
        language.put("command.hca.book.not_set", "§c§l설정안됨");
        language.put("command.hca.book.checked_item_info.1", "§9§l확인한 아이템정보");
        language.put("command.hca.book.checked_item_info.2", "\n§a§l아이템종류: §6§l@type@");
        language.put("command.hca.book.checked_item_info.3", "\n§a§l아이템이름: §6§l@name@");
        language.put("command.hca.book.checked_item_info.4", "\n§a§l아이템설명: @lore@");
        language.put("command.hca.book.checked_item_info.5", "\n§a§l인첸트: @enchant@");
        language.put("command.hca.book.page1.1", "§9§l[비교한정보]\n");
        language.put("command.hca.book.page1.2", "§9§l@data@\n");
        language.put("command.hca.book.page1.3", "§9§l[플러그인제작자: horyu1234]\n");
        language.put("command.hca.book.page1.4", "§9§l=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.hca.book.enchant_empty", "§c§l없음");
        language.put("command.hca.book.enchant_not_empty", "§a§l있음");
        language.put("command.hca.book.next_page", "\n§9§l해당아이템을 가지고있는 사람목록은 다음장에 있습니다.");
        language.put("command.hca.book.done", "§a해당아이템을 가지고있는 사람리스트를 책으로 생성하였습니다.");
        language.put("command.hca.error.hand_empty", "§c손에 아이템을 들고 있어야 사용 가능합니다.");
        language.put("command.hca.error.only_player", "§c게임 내에서만 사용 가능한 명령어입니다.");

        /* dga 명령어 */
        language.put("command.dga.error.access_denied", "§c권한이 없습니다.");
        language.put("command.dga.error.only_number", "§c숫자만 입력해주세요!");

        language.put("command.dga.delete", "§e@data@§a을(를) 성공적으로 삭제했습니다.");
        language.put("command.dga.delete.error.delete", "§e@data@§a을(를) 삭제하는데 문제가 발생했습니다.");
        language.put("command.dga.delete.error.data_not_exist", "§c존재하지 않는 이름입니다.");
        language.put("command.dga.delete.error.name_regex.1", "§c이름에 들어갈 수 없는 문자가 포함되어 있습니다.");
        language.put("command.dga.delete.error.name_regex.2", "§c이름에는 영어, 한글, 숫자만 들어갈 수 있으며, 최소1글자~최대20글자입니다.");

        language.put("command.dga.give.error.only_number", "§c숫자만 입력해주세요.");
        language.put("command.dga.give.error.amount", "§c수량은 0보다 커야 합니다.");
        language.put("command.dga.give.sender", "§a해당 플레이어에게 아이템을 지급했습니다. §6플러그인제작자: horyu1234 [https://horyu1234.com]");
        language.put("command.dga.give.player", "§e@player@§a님이 당신에게 아이템을 지급하셨습니다. §6플러그인제작자: horyu1234 [https://horyu1234.com]");
        language.put("command.dga.give.error.data_not_exist", "§c존재하지 않는 이름입니다.");
        language.put("command.dga.give.error.not_online_player", "§c온라인플레이어가 아닙니다.");

        language.put("command.dga.giveall.error.only_number", "§c숫자만 입력해주세요.");
        language.put("command.dga.giveall.error.number", "§c수량은 0보다 커야 합니다.");
        language.put("command.dga.giveall.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.dga.giveall.1", "§e@player@§a님이 모든 온라인 플레이어에게 §e@item@§a을(를) §e@amount@개 §a지급하였습니다.");
        language.put("command.dga.giveall.2", "§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
        language.put("command.dga.giveall.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.dga.giveall.error.data_not_exist", "§c존재하지 않는 이름입니다.");

        language.put("command.dga.rgive.error.only_number", "§c숫자만 입력해주세요.");
        language.put("command.dga.rgive.error.min_max", "§c최댓값은 최솟값보다 커야합니다.");
        language.put("command.dga.rgive.sender", "§a해당 플레이어에게 아이템을 지급했습니다. §6플러그인제작자: horyu1234 [https://horyu1234.com]");
        language.put("command.dga.rgive.player", "§e@player@§a님이 당신에게 (랜덤수량의) 아이템을 지급하셨습니다. §6플러그인제작자: horyu1234 [https://horyu1234.com]");
        language.put("command.dga.rgive.error.data_not_exist", "§c존재하지 않는 이름입니다.");
        language.put("command.dga.rgive.error.not_online_player", "§c온라인플레이어가 아닙니다.");

        language.put("command.dga.rgiveall.error.only_number", "§c숫자만 입력해주세요.");
        language.put("command.dga.rgiveall.error.min_max", "§c최댓값은 최솟값보다 커야합니다.");
        language.put("command.dga.rgiveall.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.dga.rgiveall.1", "§e@player@§a님이 모든 온라인 플레이어에게 §e@item@§a을(를) §e(랜덤추첨된)@amount@개 §a지급하였습니다.");
        language.put("command.dga.rgiveall.2", "§3최솟값: §b@min@§f, §3최댓값: §b@max@");
        language.put("command.dga.rgiveall.3", "§6플러그인 제작자: horyu1234 [https://horyu1234.com]");
        language.put("command.dga.rgiveall.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.dga.rgiveall.error.data_not_exist", "§c존재하지 않는 이름입니다.");

        language.put("command.dga.add.error.save", "§c데이터를 저장하는데 문제가 발생했습니다.");
        language.put("command.dga.add.save", "§e@name@§a의 이름에 손에 들고있는 아이템을 저장했습니다.");
        language.put("command.dga.add.error.exist_name", "§c이미 존재하는 이름입니다.");
        language.put("command.dga.add.error.name_regex.1", "§c이름에 들어갈 수 없는 문자가 포함되어 있습니다.");
        language.put("command.dga.add.error.name_regex.2", "§c이름에는 영어, 한글, 숫자만 들어갈 수 있으며, 최소1글자~최대20글자입니다.");
        language.put("command.dga.add.error.hand_empty", "§c손에 아이템을 들고 있어야 사용가능합니다.");
        language.put("command.dga.add.error.only_player", "§c게임 내에서만 사용 가능한 명령어입니다.");

        language.put("command.dga.error.inventory_full", "§c책을 생성할 공간이 없습니다. 인벤토리를 비운 후 다시 시도해주세요.");
        language.put("command.dga.list.book.title", "저장된 아이템 목록");
        language.put("command.dga.list.book.display_name", "§a저장된 아이템 목록");
        language.put("command.dga.list.book.lore", "§e플러그인제작자: horyu1234");
        language.put("command.dga.list.book.top", "§2§l저장된 아이템목록을 출력합니다.\n§9§l[플러그인제작자: horyu1234]\n§9§l=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.dga.list.book.page1.1", "§9§l[플러그인제작자: horyu1234]");
        language.put("command.dga.list.book.page1.2", "§9§l=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.dga.list.done", "§a저장된 아이템 목록을 책으로 생성하였습니다.");
        language.put("command.dga.list.error.only_player", "§c게임 내에서만 사용 가능한 명령어입니다.");
    }

    public static void setEnglish() {
        /* 플러그인 활성화 */
        language.put("enable.installing", "§fInstalling plugin. Please wait...");
        language.put("enable.notices.header", "§e=====§b§l[ §aHandGiveAll Message §b§l]§e=====");
        language.put("enable.notices.footer", "§e===========================");
        language.put("enable.done.1", "§aPlugin has been enabled.");
        language.put("enable.done.2", "§aDeveloper: horyu1234");
        language.put("enable.done.3", "§aCopyright 2014 ~ @currentYear@ Horyu Systems Ltd, All Rights Reserved.");

        /* 플러그인 비활성화 */
        language.put("disable.done.1", "§aPlugin has been disabled.");
        language.put("disable.done.2", "§aDeveloper: horyu1234");
        language.put("disable.done.3", "§aCopyright 2014 ~ @currentYear@ Horyu Systems Ltd, All Rights Reserved.");

        /* 전체 예외 처리 */
        language.put("error.date.format", "yyyy-MM-dd HH.mm.ss");
        language.put("error.file.header", "Please send the error below.");
        language.put("error.notice.1", "§cAn unexpected error has been logged at §eHandGiveAll/errors/");
        language.put("error.notice.2", "§cIt helps a lot if you report this error.");
        language.put("error.error", "§cError occured while saving error message to file.");

        /* hn 명령어 */
        language.put("command.hn.notices.header", "§e=====§b§l[ §aHandGiveAll Message §b§l]§e=====");
        language.put("command.hn.notices.footer", "§e===========================");
        language.put("command.hn.notices.empty", "§fThere are no announcements to be made.");

        /* 플레이어 입장 */
        language.put("event.join.notices.no_date", "§fTo check existing announcements please type §a/hn");
        language.put("event.join.notices.with_date", "§eThere was an announcement on @date@§f. Type §a/hn §fto check.");

        /* 업데이트 확인 */
        language.put("check.update.web.Content-Language", "en_US");
        language.put("check.update.new_version.header", "§b#==============================#");
        language.put("check.update.new_version.1", "§fA new update has been found!");
        language.put("check.update.new_version.2", "§cCurrent Version: ");
        language.put("check.update.new_version.3", "§aLatest Version: ");
        language.put("check.update.new_version.4", "§eDownload Link: https://horyu1234.com/HandGiveAll");
        language.put("check.update.new_version.footer", "§b#==============================#");
        language.put("check.update.new_version.message_box", "A new update has been found!");
        language.put("check.update.no_new_version.1", "§fYou are using the latest version.");
        language.put("check.update.error_version.header", "§f#==============================#");
        language.put("check.update.error_version.1", "§cAn error occured while checking for update.");
        language.put("check.update.error_version.footer", "§f#==============================#");
        language.put("check.update.error.1", "§cAn error occured while checking for update");
        language.put("check.update.error.2", "§cMessage: ");

        /* 버전에 대한 정보 가져오기 */
        language.put("version_info.error.1", "§cError occured while getting version info.");
        language.put("version_info.error.2", "§cMessage: ");
        language.put("version_info.default.disable_message", "Failed to communicate with the server.");

        /* ItemUtils */
        language.put("item_utils.inv_full", "§e@player@ §cdidn't receive because his/her inventory was full.");

        /* 비활성화 확인 */
        language.put("check.disable.header", "§c#==============================#");
        language.put("check.disable.1", "The developer of this plugin has disabled");
        language.put("check.disable.2", "the plugin.");
        language.put("check.disable.3", "");
        language.put("check.disable.4", "§4Reason: ");
        language.put("check.disable.footer", "§c#==============================#");
        language.put("check.disable.message_box", "The developer of this plugin has disabled\nthe plugin.");

        /* 데이터 확인 */
        language.put("check.data.create.config", "§cCouldn't find a config file. Creating one...");
        language.put("check.data.create_done.config", "§aDone");
        language.put("check.data.loaded_config", "§aSuccessfully loaded config.");
        language.put("check.data.error.load_config.1", "§cAn error occured while loading config.");
        language.put("check.data.error.load_config.2", "§cMessage: ");
        language.put("check.data.error.load_config.message_box", "An error occured while loading config.");
        language.put("check.data.create.folder", "§cCouldn't find plugin folder. Creating one...");
        language.put("check.data.create_done.folder", "§aDone");

        /* EULA 확인 */
        language.put("check.eula.header", "§f#==============================#");
        language.put("check.eula.1", "§cPlease agree to EULA of this plugin in config!!");
        language.put("check.eula.2", "§cYou must agree with the EULA to use this plugin.");
        language.put("check.eula.footer", "§f#==============================#");
        language.put("check.eula.message_box", "Please agree to EULA of this plugin in config.");

        /* Config 버전 확인 */
        language.put("check.config_version.header", "§f#==============================#");
        language.put("check.config_version.1", "§cConfig version doesn't match!");
        language.put("check.config_version.2", "§cPlease delete the config file.");
        language.put("check.config_version.footer", "§f#==============================#");
        language.put("check.config_version.message_box", "Config version doesn't match!");

        /* Vault 확인 */
        language.put("check.hook_vault.version_fail.header", "§f#==============================#");
        language.put("check.hook_vault.version_fail.1", "§cVault exists but its version isn't supported.");
        language.put("check.hook_vault.version_fail.2", "§cPlease update Vault version to be above 1.4.1");
        language.put("check.hook_vault.version_fail.footer", "§f#==============================#");
        language.put("check.hook_vault.fail.header", "§f#==============================#");
        language.put("check.hook_vault.fail.1", "§cCould not connect to Vault");
        language.put("check.hook_vault.fail.2", "§cDisabling commands related to money.");
        language.put("check.hook_vault.fail.footer", "§f#==============================#");
        language.put("check.hook_vault.success", "§aSuccessfully connect to Vault.");
        language.put("check.hook_vault.not_exist.header", "§f#==============================#");
        language.put("check.hook_vault.not_exist.1", "§cVault does not exist.");
        language.put("check.hook_vault.not_exist.2", "§cDisabling commands related to money.");
        language.put("check.hook_vault.not_exist.footer", "§f#==============================#");

        /* 파일 이름 확인 */
        language.put("check.file_name.header", "§f#==============================#");
        language.put("check.file_name.1", "§cThe plugin jar seems to have been renamed.");
        language.put("check.file_name.2", "§cPlease change it back");
        language.put("check.file_name.3", "");
        language.put("check.file_name.4", "§fCurrent Name: §c");
        language.put("check.file_name.5", "§fOriginal Name: §a");
        language.put("check.file_name.footer", "§f#==============================#");
        language.put("check.file_name.message_box", "The plugin jar seems to have been renamed.");

        /* 파일 변조 확인 */
        language.put("check.file_edit.header", "§f#==============================#");
        language.put("check.file_edit.1", "§cPlugin seems to have been altered.");
        language.put("check.file_edit.2", "§cPlease use the original version.");
        language.put("check.file_edit.footer", "§f#==============================#");
        language.put("check.file_edit.message_box", "Plugin seems to have been altered.");

        /* pga 명령어 */
        language.put("command.pga.error.access_denied", "§cYou don't have permission.");
        language.put("command.pga.give.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.pga.give.1", "§e@player@§ahas applied §e@effect@§a to everyone.");
        language.put("command.pga.give.2", "§6Developer: horyu1234 [https://horyu1234.com]");
        language.put("command.pga.give.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.pga.error.data_empty", "§cThere is no potion data. Potion effect will not been given.");
        language.put("command.pga.take.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.pga.take.1", "§e@player@§ahas removed all potion effects from everyone.");
        language.put("command.pga.take.2", "§6Developer: horyu1234 [https://horyu1234.com]");
        language.put("command.pga.take.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.pga.error.hand_empty", "§cYou must be holding either a potion or a bucket of milk.");
        language.put("command.pga.error.only_player", "§cThis feature is only available via in-game command.");
        language.put("command.pga.error.only_number", "§cPlease entere a numeric value!");
        language.put("command.pga.potion_format", "(Name:@name@, Level:@level@, Duration:@second@sec(s))");

        /* mga 명령어 */
        language.put("command.mga.error.access_denied", "§cYou don't have permission.");
        language.put("command.mga.error.not_hooked_vault", "§cYou can't use this feature without Vault integration.");
        language.put("command.mga.error.only_number", "§clease entere a numeric value!");
        language.put("command.mga.error.amount", "§cMonetary value must be higher than 0.");
        language.put("command.mga.give.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.mga.give.1", "§e@player@§agave everyone §e@money@§a.");
        language.put("command.mga.give.2", "§6Developer: horyu1234 [https://horyu1234.com]");
        language.put("command.mga.give.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.mga.take.error", "§cWasn't able to withdraw money from §e@player@ §cdue to lack of balance. Curret balance: §e@money@");
        language.put("command.mga.take.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.mga.take.1", "§e@player@§ctook §e@money@§c from everyone.");
        language.put("command.mga.take.2", "§6Developer: horyu1234 [https://horyu1234.com]");
        language.put("command.mga.take.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.mga.error.minmax", "§cMax must be greater than min.");
        language.put("command.mga.rgive.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.mga.rgive.1", "§e@player@§agave everyone a §erandom amount of @money@§a");
        language.put("command.mga.rgive.2", "§3Min: §b@min@§f, §3Max: §b@max@");
        language.put("command.mga.rgive.3", "§6Developer: horyu1234 [https://horyu1234.com]");
        language.put("command.mga.rgive.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.mga.rtake.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.mga.rtake.1", "§e@player@§ctook §ea random amount of @money@§cfrom everyone.");
        language.put("command.mga.rtake.2", "§3Min: §b@min@§f, §3Max: §b@max@");
        language.put("command.mga.rtake.3", "§6Developer: horyu1234 [https://horyu1234.com]");
        language.put("command.mga.rtake.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

        /* hga 명령어 */
        language.put("command.hga.error.access_denied", "§cYou have no permission");
        language.put("command.hga.error.only_number", "§cPlease enter a number");
        language.put("command.hga.give.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.hga.give.1", "§e@player@§a gave everyone §e@amount@§a amount of §e@item@§a.");
        language.put("command.hga.give.2", "§6Developer: horyu1234 [https://horyu1234.com]");
        language.put("command.hga.give.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.hga.error.amount", "§cAmount must be greater than 0");
        language.put("command.hga.error.hand_empty", "§cYou must be holding an item in hand to use this command.");
        language.put("command.hga.error.only_player", "§cYou can only use this command in game.");
        language.put("command.hga.error.amount", "§cAmount must be greater than 0");

        language.put("command.hgar.error.access_denied", "§cYou have no permission");
        language.put("command.hgar.give.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.hgar.give.1", "§e@player@§a gave everyone a random amount §e(@amount@)§a of §e@item@.");
        language.put("command.hgar.give.2", "§3Min: §b@min@§f, §3Max: §b@max@");
        language.put("command.hgar.give.3", "§6Developer: horyu1234 [https://horyu1234.com]");
        language.put("command.hgar.give.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.hgar.error.amount", "§cValue must be greater than 0.");
        language.put("command.hgar.error.min_max", "§cMax must be greater than Min.");
        language.put("command.hgar.error.hand_empty", "§cYou must be holding an item in hand to use this command.");
        language.put("command.hgar.error.only_player", "§cYou can only use this command in game.");

        language.put("command.hgac.error.access_denied", "§cYou have no permission");
        language.put("command.hgac.error.only_number", "§cPlease enter a number");
        language.put("command.hgac.give.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.hgac.give.1", "§e@player@§a gave everyone §e@amount@§a amount of §e@item@.");
        language.put("command.hgac.give.2", "§6Developer: horyu1234 [https://horyu1234.com]");
        language.put("command.hgac.give.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.hgac.error.amount", "§cAmount must be greater than 0.");

        language.put("command.hgacr.error.access_denied", "§cYou have no permission");
        language.put("command.hgacr.error.only_number", "§cPlease enter a number");
        language.put("command.hgacr.give.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.hgacr.give.1", "§e@player@§a gave everyone a random amount §e(@amount@)§a of §e@item@.");
        language.put("command.hgacr.give.2", "§3Min: §b@min@§f, §3Max: §b@max@");
        language.put("command.hgacr.give.3", "§6Developer: horyu1234 [https://horyu1234.com]");
        language.put("command.hgacr.give.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.hgacr.error.amount", "§cAmount must be greater than 0.");
        language.put("command.hgacr.error.min_max", "§cMax must be greater than Min.");

        /* hca 명령어 */
        language.put("command.hca.error.access_denied", "§cYou have no permission");
        language.put("command.hca.error.only_number", "§cPlease enter a number");
        language.put("command.hca.error.not_exist_item_code", "§cSuch item id doesn't exist.");
        language.put("command.hca.error.inventory_full", "§cYour inventory is full. Please make space for a book in your inventory.");
        language.put("command.hca.book.title", "Everyone with that item");
        language.put("command.hca.book.display_name", "§aEveryone with that item");
        language.put("command.hca.book.lore", "§eDeveloper: horyu1234");
        language.put("command.hca.book.max_line", "\n §c§lMaximum line limit.");
        language.put("command.hca.book.none", "§c§lnone");
        language.put("command.hca.book.not_set", "§c§lNot configured");
        language.put("command.hca.book.checked_item_info.1", "§9§lItem Search Query");
        language.put("command.hca.book.checked_item_info.2", "\n§a§lItem Type: §6§l@type@");
        language.put("command.hca.book.checked_item_info.3", "\n§a§lItem Name: §6§l@name@");
        language.put("command.hca.book.checked_item_info.4", "\n§a§lItem Lore: @lore@");
        language.put("command.hca.book.checked_item_info.5", "\n§a§lEnchant: @enchant@");
        language.put("command.hca.book.page1.1", "§9§l[Compared value]]\n");
        language.put("command.hca.book.page1.2", "§9§l@data@\n");
        language.put("command.hca.book.page1.3", "§9§l[Developer: horyu1234]\n");
        language.put("command.hca.book.page1.4", "§9§l=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.hca.book.enchant_empty", "§c§lNot Enchanted");
        language.put("command.hca.book.enchant_not_empty", "§a§lEnchanted");
        language.put("command.hca.book.next_page", "\n§9§lA list of players with that item is in the next page.");

        language.put("command.hca.book.done", "§aA book with a list of players with that item has been generated.");
        language.put("command.hca.error.hand_empty", "§cYou must be holding an item in hand to use this command.");
        language.put("command.hca.error.only_player", "§cYou can only use this command in game.");

        /* dga 명령어 */
        language.put("command.dga.error.access_denied", "§cYou have no permission");
        language.put("command.dga.error.only_number", "§cPlease enter a number");

        language.put("command.dga.delete", "§e@data@§a was successfully deleted.");
        language.put("command.dga.delete.error.delete", "§aAn error occured while deleting §e@data@§a.");
        language.put("command.dga.delete.error.data_not_exist", "§cCouldn't find the saved item with that name.");
        language.put("command.dga.delete.error.name_regex.1", "§cName contains unpermitted characters.");
        language.put("command.dga.delete.error.name_regex.2", "§cYou can only use English and Korean letters and numbers. The maximum length of the name is 20 letters.");

        language.put("command.dga.give.error.only_number", "§cPlease enter a number.");
        language.put("command.dga.give.error.amount", "§cAmount must be greater than 0");
        language.put("command.dga.give.sender", "§aThe item was given to that player. §6Developer: horyu1234 [https://horyu1234.com]");
        language.put("command.dga.give.player", "§e@player@§a gave you an item. §6Developer: horyu1234 [https://horyu1234.com]");
        language.put("command.dga.give.error.data_not_exist", "§cCouldn't find the saved item with that name.");
        language.put("command.dga.give.error.not_online_player", "§cPlayer is not online.");

        language.put("command.dga.giveall.error.only_number", "§cPlease enter a number.");
        language.put("command.dga.giveall.error.number", "§cAmount must be greater than 0");
        language.put("command.dga.giveall.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.dga.giveall.1", "§e@player@§a gave everyone §e@amount@§a amount of §e@item@.");
        language.put("command.dga.giveall.2", "§6Developer: horyu1234 [https://horyu1234.com]");
        language.put("command.dga.giveall.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.dga.giveall.error.data_not_exist", "§cCouldn't find the saved item with that name.");

        language.put("command.dga.rgive.error.only_number", "§cPlease enter a number.");
        language.put("command.dga.rgive.error.min_max", "§cMax must be greater than Min.");
        language.put("command.dga.rgive.sender", "§aThe item was given to that player. §6Developer: horyu1234 [https://horyu1234.com]");
        language.put("command.dga.rgive.player", "§e@player@§a gave you an random amount of item. §6Developer: horyu1234 [https://horyu1234.com]");
        language.put("command.dga.rgive.error.data_not_exist", "§cCouldn't find the saved item with that name.");
        language.put("command.dga.rgive.error.not_online_player", "§cPlayer is not online.");

        language.put("command.dga.rgiveall.error.only_number", "§cPlease enter a number.");
        language.put("command.dga.rgiveall.error.min_max", "§cMax must be greater than Min.");
        language.put("command.dga.rgiveall.header", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.dga.rgiveall.1", "§e@player@§a gave everyone a random amount §e(@amount@)§a of §e@item@.");
        language.put("command.dga.rgiveall.2", "§3Min: §b@min@§f, §3Max: §b@max@");
        language.put("command.dga.rgiveall.3", "§6Developer: horyu1234 [https://horyu1234.com]");
        language.put("command.dga.rgiveall.footer", "§3=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.dga.rgiveall.error.data_not_exist", "§cCouldn't find the saved item with that name.");

        language.put("command.dga.add.error.save", "§cAn error occured while saving item.");
        language.put("command.dga.add.save", "§aItem in hand was saved as §e@name@§a.");
        language.put("command.dga.add.error.exist_name", "§cThere is a saved item with the same name.");
        language.put("command.dga.add.error.name_regex.1", "§cName contains unpermitted characters.");
        language.put("command.dga.add.error.name_regex.2", "§cYou can only use English and Korean letters and numbers. The maximum length of the name is 20 letters.");
        language.put("command.dga.add.error.hand_empty", "§cYou must be holding an item.");
        language.put("command.dga.add.error.only_player", "§cYou can only use this command in game.");

        language.put("command.dga.error.inventory_full", "§cThere is no space in your inventory to create a book. Try again after emptying a slot.");
        language.put("command.dga.list.book.title", "List of Saved Items");
        language.put("command.dga.list.book.display_name", "§aList of Saved Items");
        language.put("command.dga.list.book.lore", "§eDeveloper: horyu1234");
        language.put("command.dga.list.book.top", "§2§lDisplaying a list of saved items.\n§9§l[Developer: horyu1234]\n§9§l=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.dga.list.book.page1.1", "§9§l[Developer: horyu1234]");
        language.put("command.dga.list.book.page1.2", "§9§l=-=-=-=-=-=-=-=-=-=-=-=-=");
        language.put("command.dga.list.done", "§aList of saved items was exported on to a book.");
        language.put("command.dga.list.error.only_player", "§cYou can only use this command in game.");
    }
}