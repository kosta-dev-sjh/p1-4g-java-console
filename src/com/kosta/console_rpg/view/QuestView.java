package com.kosta.console_rpg.view;

import com.kosta.console_rpg.controller.QuestController;

/**
 * 게임 메인화면 뷰
 *
 * 작성자     : 김재민
 * 생성일     : 2026.03.16
 * 최종 수정자 : 이진주
 * 최종 수정일 : 2026.03.16
 */
public class QuestView {
    public static void showQuestLists(int heroId){
        QuestController questController = new QuestController();
        questController.selectQuestsByHeroId(heroId);
    }
}
