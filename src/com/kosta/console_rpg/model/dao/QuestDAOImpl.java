package com.kosta.console_rpg.model.dao;

import com.kosta.console_rpg.model.dto.QuestDTO;
import com.kosta.console_rpg.util.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *  업적 db 서버에서 받아오는 DAO 구현
 *
 * 작성자      : 김재민
 * 생성일      : 2026.03.13
 * 최종 수정자 :
 * 최종 수정일 : 2026.03.15
 */
public class QuestDAOImpl implements QuestDAO {
    @Override
    public List<QuestDTO> selectQuestsByHeroId(int heroId) throws SQLException {
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;


        List<QuestDTO> questDTOList= new ArrayList<QuestDTO>();
        try {
            con = DBManager.getConnection();
            ps= con.prepareStatement("select *  from quest join quest_ing on quest_id = fk_quest_id where fk_hero_id = ?;");

            ps.setInt(1, heroId);
            rs = ps.executeQuery();

            while (rs.next()) {
                QuestDTO questDTO= new QuestDTO();
                questDTO.setQuestId(rs.getInt("quest_id"));
                questDTO.setQuestName(rs.getString("quest_name"));
                questDTO.setQuestInfo(rs.getString("quest_info"));
                questDTO.setQuestType(rs.getString("quest_type"));
                questDTO.setQuestTarget(rs.getInt("quest_target"));
                questDTO.setQuestId(rs.getInt("quest_id"));
                questDTO.setHeroId(rs.getInt("fk_hero_id"));
                questDTO.setQuestIngProgress(rs.getInt("quest_ing_progress"));
                if(rs.getInt("quest_ing_complete") == 1 ){
                    questDTO.setQuestIngComplete(true);
                }
                else {
                    questDTO.setQuestIngComplete(false);
                }
                System.out.println(questDTO);
                questDTOList.add(questDTO);
            }
            return questDTOList;
        }finally {
            DBManager.close(con, ps, rs);
        }


    }

    @Override
    public QuestDTO selectQuestById(int questId) throws SQLException {
        return null;
    }

    @Override
    public QuestDTO selectQuestIng(int heroId, int questId) throws SQLException {
        return null;
    }

    @Override
    public void updateQuestProgress(QuestDTO questIng, int heroId, int questId) throws SQLException {

    }
}
