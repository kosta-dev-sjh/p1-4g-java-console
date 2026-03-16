package com.kosta.console_rpg.model.dao;

import com.kosta.console_rpg.model.dto.QuestDTO;


import java.sql.SQLException;
import java.util.List;

/**
 *  업적 정보 db 서버에서 받아오는 DAO
 *
 * 작성자      : 김재민
 * 생성일      : 2026.03.13
 * 최종 수정자 :
 * 최종 수정일 :
 */
public interface QuestDAO {

    //heroId를 바탕으로 현재 보유중인 업적 내용 전체 반환
    List<QuestDTO> selectQuestsByHeroId(int heroId) throws SQLException;

    //업적 Id를 바탕으로 해당 업적에대한 상세 내용 반환
    QuestDTO selectQuestById(int questId) throws SQLException;

    //heroId와 questId를 바탕으로 진행 중인 퀘스트의 상세 내용 반환
    QuestDTO selectQuestIng(int heroId, int questId) throws SQLException;

    //진행 중인 퀘스트의 상태 변경
    void updateQuestProgress(QuestDTO questIng, int heroId, int questId) throws SQLException;

}
