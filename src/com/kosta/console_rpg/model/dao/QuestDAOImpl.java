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
	
	//heroID에 해당하는 업적 목록 전부 반환
    @Override
    public List<QuestDTO> selectQuestsByHeroId(int heroId) throws SQLException {
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;


        List<QuestDTO> questDTOList= new ArrayList<QuestDTO>();
        try {
            con = DBManager.getConnection();
            //quest+quest_ing(join) hero id가 같은 값 전체 선택
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
                
                //db상 1/0으로 저장 >> boolean으로 변경
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
    
    //questID값을 바탕으로 업적이름, 업적 정보, 업적 분류, 업적 진행상황, 완료 여부반환
    @Override
    public QuestDTO selectQuestById(int heroId,int questId) throws SQLException {
    	  Connection con=null;
          PreparedStatement ps=null;
          ResultSet rs=null;
          QuestDTO questDTO= new QuestDTO();
          
          try {
              con = DBManager.getConnection();
              //quest+quest_ing(join) hero id + quest id가 같은 값 선택
              ps= con.prepareStatement("select * from quest join quest_ing on quest_id = fk_quest_id where fk_quest_id = ?");

              ps.setInt(1, questId);
              rs = ps.executeQuery();

              if (rs.next()) {                 
                  
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
              }
              return questDTO;
          }finally {
              DBManager.close(con, ps, rs);
          }

    }
    //heroId와 questId를 바탕으로 진행중인 퀘스트의 리스트 반환
    @Override
    public List<QuestDTO> selectQuestIng(int heroId) throws SQLException {
    	 Connection con=null;
         PreparedStatement ps=null;
         ResultSet rs=null;


         List<QuestDTO> questDTOList= new ArrayList<QuestDTO>();
         try {
             con = DBManager.getConnection();
             //quest+quest_ing(join) quest_ing_complete = 0 > 진행중  / hero id가 같은 값 전체 선택
             ps= con.prepareStatement("select * from quest join quest_ing on quest_id = fk_quest_id where quest_ing_complete = 0 and fk_hero_id = ?");
             	
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
                 
                 questDTOList.add(questDTO);
             }
             return questDTOList;
         }finally {
             DBManager.close(con, ps, rs);
         }
    }
    //heroId와 questId를 바탕으로 완료한 퀘스트의 리스트 반환
	@Override
	public List<QuestDTO> selectQuestEnd(int heroId) throws SQLException {
		 Connection con=null;
	        PreparedStatement ps=null;
	        ResultSet rs=null;


	        List<QuestDTO> questDTOList= new ArrayList<QuestDTO>();
	        try {
	            con = DBManager.getConnection();
	             //quest+quest_ing(join) quest_ing_complete = 1 > 완료  / hero id가 같은 값 전체 선택
	            ps= con.prepareStatement("select * from quest join quest_ing on quest_id = fk_quest_id where quest_ing_complete = 1 and fk_hero_id = ?");
             	
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
	                
	                questDTOList.add(questDTO);
	            }
	            return questDTOList;
	        }finally {
	            DBManager.close(con, ps, rs);
	        }
	}

    @Override
    public void updateQuestProgress(QuestDTO questIng) throws SQLException {
    	  Connection con=null;
          PreparedStatement ps=null;        
                    
          try {
              con = DBManager.getConnection();
              //quest+quest_ing(join)  quest progress (진행도) 수정/ hero id + quest id가 같을때 
              ps= con.prepareStatement("UPDATE quest_ing JOIN quest ON quest_id = fk_quest_id SET quest_ing_progress = ? where fk_hero_id = ? and fk_quest_id = ?");
              
              ps.setInt(1, questIng.getQuestIngProgress());
              ps.setInt(2,questIng.getHeroId());
              ps.setInt(3, questIng.getQuestId());

              
              ps.executeUpdate();
              
              
          }finally {
              DBManager.close(con, ps);
          }
    	

    }


}
