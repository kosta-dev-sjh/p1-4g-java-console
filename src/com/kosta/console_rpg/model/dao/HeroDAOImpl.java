package com.kosta.console_rpg.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kosta.console_rpg.exception.DuplicationException;
import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.HeroSkillDTO;
import com.kosta.console_rpg.util.DBManager;

public class HeroDAOImpl implements HeroDAO {
	
	@Override
	public void insertHero(HeroDTO hero) throws DuplicationException, GameException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		
		String checkSql = "select hero_id from hero where hero_name = ?";
		String insertsql ="insert into hero(fk_user_id, hero_name) values(?,?)";
		
		try {
			con = DBManager.getConnection();
			
			ps = con.prepareStatement(checkSql);
	        ps.setString(1, hero.getHeroName());
	        
	        rs = ps.executeQuery();
			
	        if(rs.next()) {
	            throw new DuplicationException("아이디 중복");
	        }
			
			ps = con.prepareStatement(insertsql);
			ps.setInt(1, hero.getUserId());
			ps.setString(2,hero.getHeroName());
			
			int result = ps.executeUpdate();
			
			if(result == 0) {
	            throw new GameException("히어로 생성 실패");
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GameException("DB 오류");
			
		} finally {
			DBManager.close(con, ps);
		}
		
	}
	
	@Override
	public HeroDTO selectHeroByUserId(int userId) throws GameException {
		
		
				
		return null;
	}
	
	@Override
	public void resetHero(int heroId) throws GameException {
		Connection con = null;
		PreparedStatement ps = null;
		
		String sql="delete from hero where hero_id = ?";
		
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, heroId);
			
			int result = ps.executeUpdate();
			
			if(result == 0) {
			    throw new GameException("삭제할 히어로가 없습니다.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			DBManager.close(con, ps);
		}
	}
	
	@Override
	public List<HeroSkillDTO> selectHeroSkillsByHeroId(int heroId) throws GameException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<HeroSkillDTO> list = new ArrayList<>();
		
		String sql="select * from hero_skill where fk_hero_id = ?";
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, heroId);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				HeroSkillDTO heroskill = new HeroSkillDTO
						(rs.getInt(1), rs.getInt(2), rs.getInt(3));
				
				list.add(heroskill);
			}
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new GameException("히어로 스킬 정보를 가져오지 못했습니다.");
			
		}finally {
			DBManager.close(con, ps, rs);
		}			
				
		
		return list;
	}

	@Override
	public void upgradeHeroSkill(HeroSkillDTO heroSkill) throws GameException {
		Connection con=null;
		PreparedStatement ps=null;

		String sql ="update hero_skill set skill_level = skill_level + 1 where hero_skill_id = ? ";
		
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, heroSkill.getSkillId());
			
			int result = ps.executeUpdate();
			
			if(result == 0) {
	            throw new GameException("스킬 업그레이드 실패");
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GameException("DB 오류");
			
		} finally {
			DBManager.close(con, ps);
		}
		
	}
	
	@Override
	public void updateClearStage(int heroId, int stage) throws GameException {
		Connection con=null;
		PreparedStatement ps=null;

		String sql ="update hero set hero_max_clear_stage = ? where hero_id = ?";
		
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, heroId);
			ps.setInt(2, stage);
			
			int result = ps.executeUpdate();
			
			if(result == 0) {
	            throw new GameException("스테이지 업데이트 실패");
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GameException("DB 오류");
			
		} finally {
			DBManager.close(con, ps);
		}
		
	}
	
	@Override
	public void updateHeroGem(int heroId, int gem) throws GameException {
		Connection con=null;
		PreparedStatement ps=null;

		String sql ="update hero set hero_gem = ? where hero_id = ?";
		
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, heroId);
			ps.setInt(2, gem);
			
			int result = ps.executeUpdate();
			
			if(result == 0) {
	            throw new GameException("젬 업데이트 실패");
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GameException("DB 오류");
			
		} finally {
			DBManager.close(con, ps);
		}
	}
}
