package com.kosta.console_rpg.view;

import java.sql.SQLException;

import com.kosta.console_rpg.controller.QuestController;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.QuestDTO;
import com.kosta.console_rpg.model.service.QuestService;
import com.kosta.console_rpg.session.LoginSession;

/**
 *  업적 관련 기능 view
 *
 * 작성자      : 김재민
 * 생성일      : 2026.03.13
 * 최종 수정자 :
 * 최종 수정일 : 2026.03.16
 */
public class QuestView {
    static HeroDTO hero = LoginSession.getInstance().getCurrentHero();
    static QuestService service = new QuestService();

    public static void showQuestLists(int heroId){
        QuestController questController = new QuestController();
        questController.selectQuestsByHeroId(heroId);
    }
    public static void selectQuestById(int heroId, int questId){
        QuestController questController = new QuestController();
        questController.selectQuestById(heroId, questId);
    }
    public static void selectQuestIng(int heroId){
        QuestController questController = new QuestController();
        questController.selectQuestIng(heroId);
    }
    public static void selectQuestEnd(int heroId){
        QuestController questController = new QuestController();
        questController.selectQuestEnd(heroId);
    }
    public static void updateQuestProgress(QuestDTO quest) {
        QuestController questController = new QuestController();
        questController.updateQuestProgress(quest);
    }
    public static void insertQuestInit(int heroId) {
        QuestController questController = new QuestController();
        questController.insertQuestInit(heroId);
    }

    public static void printQuest(){
        System.out.println(hero.getHeroId());
        selectQuestEnd(hero.getHeroId());
        selectQuestIng(hero.getHeroId());


    }
}
