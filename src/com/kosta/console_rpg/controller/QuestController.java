package com.kosta.console_rpg.controller;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.QuestDTO;
import com.kosta.console_rpg.model.service.QuestService;
import com.kosta.console_rpg.view.FailView;

import java.sql.SQLException;
import java.util.List;
/**
 *  업적 관련 기능 구현 controller
 *
 * 작성자      : 김재민
 * 생성일      : 2026.03.13
 * 최종 수정자 :
 * 최종 수정일 : 2026.03.16
 */
public class QuestController {
    private QuestService questService = new QuestService();


    public void selectQuestsByHeroId(int heroId) {
        try{
            List<QuestDTO> questList = questService.selectQuestsByHeroId(heroId);
            System.out.println(questList);
        } catch (SQLException e) {           
            FailView.errorMessage(e.getMessage());
        }
    }
    public void selectQuestById(int heroId,int questId) {
        try{
            QuestDTO quest = questService.selectQuestById(heroId, questId);
            System.out.println(quest);
        } catch (SQLException e) {           
            FailView.errorMessage(e.getMessage());
        }
    }
    public void selectQuestIng(int heroId) {
        System.out.println("-------in progress--------");
        try{
            List<QuestDTO> questList = questService.selectQuestIng(heroId);
            
            //업적 전부 출력
            //System.out.println(questList);



            for (QuestDTO q: questList){
                System.out.println(">"+q.getQuestName()+"("+q.getQuestIngProgress()+"/"+q.getQuestTarget()+")");

            }

            
        } catch (SQLException | GameException e) {           
            FailView.errorMessage(e.getMessage());
        }
    }
    
    //완료된 업적
    public void selectQuestEnd(int heroId) {
        System.out.println("-------complete--------");
        try{
            List<QuestDTO> questList = questService.selectQuestEnd(heroId);

            //업적 이름만 출력
//            questList.stream()
//                    .map(quest -> "> " + quest.getQuestName())
//                    .forEach(System.out::println);

            for (QuestDTO q: questList){
                System.out.println(">"+q.getQuestName()+"("+q.getQuestIngProgress()+"/"+q.getQuestTarget()+")");

            }

        } catch (SQLException | GameException e) {           
            FailView.errorMessage(e.getMessage());
        }
    }
    //업적 정보 수정
    public void updateQuestProgress(QuestDTO questIng) {
    	try {
    	questService.updateQuestProgress(questIng);
    	}
    	catch (SQLException e) {           
            FailView.errorMessage(e.getMessage());
        }
    }
    //캐릭터 추가시 업적 init
    public void insertQuestInit(int heroId) {
        try {
            questService.insertQuestInit(heroId);
        }
        catch (SQLException e) {
            FailView.errorMessage(e.getMessage());
        }
    }
}
