package com.kosta.console_rpg.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.util.DBManager;
/**
 * HeroDAO 인터페이스를 구현하여 히어로 관련 DB 작업을 수행하는 DAO 구현 클래스
 *
 * 작성자      : 송정현
 * 생성일      : 2026-03-15
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class HeroDAOImpl implements HeroDAO {
	
	// ======= public method =======
	@Override
	public HeroDTO selectHeroByUserId(int userId) throws SQLException {
		Connection 		  con  = null;
		PreparedStatement ps   = null;
		ResultSet 		  rs   = null;
		HeroDTO 		  hero = null;
		String 			  sql = "select * from hero where fk_user_id=?";

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				hero = new HeroDTO(
						rs.getInt("hero_id"), 
						rs.getInt("fk_user_id"), 
						rs.getString("hero_name"), 
						rs.getInt("hero_level"), 
						rs.getInt("hero_exp"),
						rs.getInt("hero_hp"), 
						rs.getInt("hero_mp"), 
						rs.getInt("hero_attack"), 
						rs.getInt("hero_defense"), 
						rs.getInt("hero_gem"),
						rs.getTimestamp("hero_created_at").toLocalDateTime(), 
						rs.getInt("hero_max_clear_stage")
						);
			}
		}finally {
			DBManager.close(con, ps, rs);
		}
		return hero;
	}

	@Override
	public int insertHero(int userId, String heroName) throws SQLException {
		int				  result = 0;
		Connection 		  con    = null;
		PreparedStatement ps     = null;
		String 			  sql    = null; 
		
		try {
			con = DBManager.getConnection();
			
			if(heroName == null || heroName.trim().isEmpty()) {	// [캐릭터 이름 미입력 시 DB 기본값 사용]
				sql = "insert into hero(fk_user_id) values(?)";
				ps = con.prepareStatement(sql);
				ps.setInt(1, userId);
			} else { // ------------------------------------------ [사용자가 입력한 캐릭터 이름 저장]
				sql = "insert into hero(fk_user_id, hero_name) values(?, ?)";
				
				ps = con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setString(2, heroName);
			} // if문 끝
			
			result = ps.executeUpdate();
		}finally {
			DBManager.close(con, ps);
		}
		return result;
	}

	@Override
	public int deleteHero(int heroId) throws SQLException {
		int				  result = 0;
		Connection 		  con    = null;
		PreparedStatement ps     = null;
		return result;
	}

	@Override
	public int updateHero(HeroDTO hero) throws SQLException {
		int				  result = 0;
		Connection 		  con    = null;
		PreparedStatement ps     = null;
		return result;
	}

	@Override
	public int updateClearStage(int heroId, int stage) throws SQLException {
		int				  result = 0;
		Connection 		  con    = null;
		PreparedStatement ps     = null;
		return 0;
	}

	@Override
	public int updateHeroGem(int heroId, int gem) throws SQLException {
		int				  result = 0;
		Connection 		  con    = null;
		PreparedStatement ps     = null;
		
		return 0;
	}

}
