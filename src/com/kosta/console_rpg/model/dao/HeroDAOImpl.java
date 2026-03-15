package com.kosta.console_rpg.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kosta.console_rpg.model.dto.HeroDTO;
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

		
		return hero;
	}

	@Override
	public void insertHero(HeroDTO hero) throws SQLException {
		
	}

	@Override
	public void deleteHero(int heroId) throws SQLException {
		
	}

	@Override
	public void updateHero(HeroDTO hero) throws SQLException {
		
	}

	@Override
	public void updateClearStage(int heroId, int stage) throws SQLException {
		
	}

	@Override
	public void updateHeroGem(int heroId, int gem) throws SQLException {
		
	}

}
