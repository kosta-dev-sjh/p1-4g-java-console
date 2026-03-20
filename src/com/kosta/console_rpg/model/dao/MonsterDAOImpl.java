package com.kosta.console_rpg.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.kosta.console_rpg.model.dto.MonsterDTO;
import com.kosta.console_rpg.model.dto.SkillDTO;
import com.kosta.console_rpg.util.DBManager;
/**
 *  Monster정보 db 서버에서 받아오는 DAO
 *
 * 작성자      : 김재민
 * 생성일      : 2026.03.13
 * 최종 수정자 :
 * 최종 수정일 :
 */
public class MonsterDAOImpl implements MonsterDAO{
	//몬스터 관련 정보 select all
	@Override
	public MonsterDTO selectMonsterByStage(int stage) throws SQLException {
		  Connection con=null;
		  PreparedStatement ps=null;
		  ResultSet rs=null;	
		  MonsterDTO monsterDTO = null;
		  SkillDTO monsterSkillInfo = null;
		 try {
		   con = DBManager.getConnection();
		   //ps= con.prepareStatement("select * from monster where monster_stage = ?");
		   ps= con.prepareStatement("select monster_id, monster_name, monster_stage, monster_hp, monster_attack, monster_defense, monster_reward_exp, monster_reward_gem, fk_skill_id, monster_skill_prob, skill_name, skill_damage from monster join skill on monster.fk_skill_id = skill.skill_id where monster_stage = ?;");
		   ps.setInt(1, stage);
	       rs = ps.executeQuery(); 
	        
	        if(rs.next()) {
	        	
	        	monsterSkillInfo = new SkillDTO(rs.getInt("fk_skill_id"), rs.getString("skill_name"),rs.getInt("skill_damage"));
	        	
	        	monsterDTO = new MonsterDTO();
	        	
	        	monsterDTO.setMonsterId(rs.getInt("monster_id"));
	        	monsterDTO.setMonsterName(rs.getString("monster_name"));
	        	monsterDTO.setMonsterStage(rs.getInt("monster_stage"));
	        	monsterDTO.setMonsterHp(rs.getInt("monster_hp"));
	        	monsterDTO.setMonsterAttack(rs.getInt("monster_attack"));
	        	monsterDTO.setMonsterDefense(rs.getInt("monster_defense"));
	        	monsterDTO.setMonsterRewardExp(rs.getInt("monster_reward_exp"));
	        	monsterDTO.setMonsterRewardGem(rs.getInt("monster_reward_gem"));	        	
	        	monsterDTO.setMonsterSkillProb(rs.getInt("monster_skill_prob"));	        		        	
	        	monsterDTO.setMonsterSkillInfo(monsterSkillInfo);
	        	
	        }
      }finally {
      	DBManager.close(con, ps, rs);
      }
		return monsterDTO;
	}

}
