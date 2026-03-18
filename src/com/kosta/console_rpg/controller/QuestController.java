package com.kosta.console_rpg.controller;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.QuestDTO;
import com.kosta.console_rpg.model.service.QuestService;
import com.kosta.console_rpg.view.FailView;
import com.kosta.console_rpg.view.QuestView;

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
            //System.out.println(questList);
        } catch (SQLException e) {           
            FailView.errorMessage(e.getMessage());
        }
    }
    public void selectQuestById(int heroId,int questId) {
        try{
            QuestDTO quest = questService.selectQuestById(heroId, questId);
            QuestView.selectQuestById(quest);
        } catch (SQLException e) {           
            FailView.errorMessage(e.getMessage());
        }
    }
    public void selectQuestIng(int heroId) {
        System.out.println("-------in progress--------");
        try{
            List<QuestDTO> questList = questService.selectQuestIng(heroId);

            //view
            QuestView.selectQuestIng(questList);

            
        } catch (SQLException | GameException e) {           
            FailView.errorMessage(e.getMessage());
        }
    }
    
    //완료된 업적
    public void selectQuestEnd(int heroId) {

        try{
            List<QuestDTO> questList = questService.selectQuestEnd(heroId);

            //view
            QuestView.selectQuestEnd(questList);

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
