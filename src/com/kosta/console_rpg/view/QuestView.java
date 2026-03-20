package com.kosta.console_rpg.view;



import com.kosta.console_rpg.controller.QuestController;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.QuestDTO;
import com.kosta.console_rpg.session.LoginSession;
import com.kosta.console_rpg.util.ConsoleUtils;
import com.kosta.console_rpg.util.InputUtil;

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
    public static final int WIDTH = 70;
    
    public static void printQuest(){
        ConsoleUtils.printTitleBar("Quest");
    }

    public static void showQuestLists(int heroId){
        QuestController questController = new QuestController();
        questController.selectQuestsByHeroId(heroId);
    }
    public static void selectQuestById( QuestDTO quest){
        System.out.println(quest.getQuestId()+"/"+quest.getQuestName()+"/"+quest.getQuestType()+"/"+quest.getQuestInfo()+"/"+quest.getQuestIngProgress()+"/"+quest.getQuestTarget());
    }
    public static void selectQuestIng(List<QuestDTO> questList){
        System.out.println("\n──────────────── IN PROGRESS QUEST ─────────────────\n");
        for (QuestDTO q : questList) {
            System.out.println(" ▸ " + q.getQuestName() + "(" + q.getQuestIngProgress() + "/" + q.getQuestTarget() + ")");
        }
    }
    public static void selectQuestEnd(List<QuestDTO> questList){
        System.out.println("──────────────── COMPLETE QUEST ──────────────────\n");
        //업적 이름만 출력
            questList.stream()
                    .map(quest -> " □ " + quest.getQuestName())
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

    public static void printBackToMenu(){
        System.out.println("\n────────────────── MENU ──────────────────\n");
        System.out.println(" [0] 뒤로가기\n");
        System.out.print("선택 ▶ ");
        InputUtil.backToMenu();
    }
}
