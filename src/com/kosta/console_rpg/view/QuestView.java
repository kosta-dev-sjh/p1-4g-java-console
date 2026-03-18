package com.kosta.console_rpg.view;



import com.kosta.console_rpg.controller.QuestController;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.QuestDTO;
import com.kosta.console_rpg.model.service.QuestService;
import com.kosta.console_rpg.session.LoginSession;

import java.util.List;

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


    public static void showQuestLists(int heroId){
        QuestController questController = new QuestController();
        questController.selectQuestsByHeroId(heroId);
    }
    public static void selectQuestById( QuestDTO quest){
        System.out.println(quest.getQuestId()+"/"+quest.getQuestName()+"/"+quest.getQuestType()+"/"+quest.getQuestInfo()+"/"+quest.getQuestIngProgress()+"/"+quest.getQuestTarget());
    }
    public static void selectQuestIng(List<QuestDTO> questList){
        System.out.println("-------in progress--------");
        for (QuestDTO q : questList) {
            System.out.println(">" + q.getQuestName() + "(" + q.getQuestIngProgress() + "/" + q.getQuestTarget() + ")");
        }
    }
    public static void selectQuestEnd(List<QuestDTO> questList){
        System.out.println("-------complete--------");
        //업적 이름만 출력
            questList.stream()
                    .map(quest -> "> " + quest.getQuestName())
                    .forEach(System.out::println);
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
        //selectQuestEnd(hero.getHeroId());
        //selectQuestIng(hero.getHeroId());


    }
}
