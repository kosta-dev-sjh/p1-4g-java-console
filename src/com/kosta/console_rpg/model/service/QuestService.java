package com.kosta.console_rpg.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dao.QuestDAO;
import com.kosta.console_rpg.model.dao.QuestDAOImpl;
import com.kosta.console_rpg.model.dto.QuestDTO;

/**
 *  업적 관련 기능 구현 service
 *
 * 작성자      : 김재민
 * 생성일      : 2026.03.13
 * 최종 수정자 : 송정현
 * 최종 수정일 : 2026.03.18
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

        	throw new GameException("진행중인 퀘스트가 없습니다.");
        }

        return questList;

    }
    //완료된 업적
    public List<QuestDTO> selectQuestEnd (int heroId)throws SQLException, GameException {

        List<QuestDTO> questList = questDAO.selectQuestEnd(heroId);
        //list의 값이 비어있으면 excpetion출력
        if(questList.isEmpty()) {
            System.out.println("──────────────── COMPLETE QUEST ──────────────────");
        	throw new GameException("완료한 퀘스트가 없습니다.");
        	
        	
        }
        return questList;

    }
    //업적 정보 수정
    public void updateQuestProgress(QuestDTO questIng) throws SQLException {
    	
    	questDAO.updateQuestProgress(questIng);
    }

    // 트랜잭션용 업적 정보 수정
    public void updateQuestProgress(Connection con, QuestDTO questIng) throws SQLException {
    	
    	questDAO.updateQuestProgress(con, questIng);
    }
    
    //캐릭터 추가시 업적 init
    public void insertQuestInit(int heroId) throws SQLException {

        questDAO.insertQuestInit(heroId);
    }
    public void deleteQuest(int heroId) throws SQLException {

        questDAO.deleteQuest(heroId);
    }


}
