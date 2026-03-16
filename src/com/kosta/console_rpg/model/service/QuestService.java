package com.kosta.console_rpg.model.service;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dao.QuestDAO;
import com.kosta.console_rpg.model.dao.QuestDAOImpl;
import com.kosta.console_rpg.model.dto.QuestDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *  업적 관련 기능 구현 service
 *
 * 작성자      : 김재민
 * 생성일      : 2026.03.13
 * 최종 수정자 :
 * 최종 수정일 : 2026.03.16
 */
public class QuestService {
    QuestDAO questDAO = new QuestDAOImpl();
    
    public List<QuestDTO> selectQuestsByHeroId (int heroId)throws SQLException {

        List<QuestDTO> questList = questDAO.selectQuestsByHeroId(heroId);

        return questList;

    }
    //특정(questId, heroId)으로 상세 출력
    public QuestDTO selectQuestById (int heroId,int questId)throws SQLException {

        QuestDTO questList = questDAO.selectQuestById(heroId, questId);

        return questList;

    }
    //진행중인 업적
    public List<QuestDTO> selectQuestIng (int heroId)throws SQLException, GameException  {

        List<QuestDTO> questList = questDAO.selectQuestIng(heroId);
        //list의 값이 비어있으면 excpetion출력
        if(questList.isEmpty()) {
        	
        	throw new GameException("완료한 퀘스트가 없습니다.");
        }

        return questList;

    }
    //완료된 업적
    public List<QuestDTO> selectQuestEnd (int heroId)throws SQLException, GameException {

        List<QuestDTO> questList = questDAO.selectQuestEnd(heroId);
        //list의 값이 비어있으면 excpetion출력
        if(questList.isEmpty()) {
        	
        	throw new GameException("완료한 퀘스트가 없습니다.");
        	
        	
        }
        return questList;

    }
    //업적 정보 수정
    public void updateQuestProgress(QuestDTO questIng) throws SQLException {
    	
    	questDAO.updateQuestProgress(questIng);
    }


}
