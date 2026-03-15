package com.kosta.console_rpg.model.service;

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
 * 최종 수정일 : 2026.03.15
 */
public class QuestService {
    QuestDAO questDAO = new QuestDAOImpl();

    public List<QuestDTO> selectQuestsByHeroId (int heroId)throws SQLException {

        List<QuestDTO> questList = questDAO.selectQuestsByHeroId(heroId);

        return questList;

    }

}
