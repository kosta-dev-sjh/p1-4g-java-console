package com.kosta.console_rpg.view;

import com.kosta.console_rpg.controller.QuestController;

public class QuestView {
    public static void showQuestLists(int heroId){
        QuestController questController = new QuestController();
        questController.selectQuestsByHeroId(heroId);
    }
}
