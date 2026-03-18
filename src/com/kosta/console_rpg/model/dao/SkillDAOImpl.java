package com.kosta.console_rpg.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kosta.console_rpg.model.dto.HeroSkillDTO;
import com.kosta.console_rpg.model.dto.SkillDTO;
import com.kosta.console_rpg.util.DBManager;

/**
 * skill 및 hero_skill 테이블 기반의 스킬 조회, 강화, 초기 지급 관련 DB 작업을 수행하는 DAO 구현 클래스
 *
 * 작성자      : 송정현
 * 생성일      : 2026.03.15
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class SkillDAOImpl implements SkillDAO {
	// ======= public method =======
	@Override
	public List<HeroSkillDTO> selectHeroSkills(int heroId) throws SQLException {
		List<HeroSkillDTO> heroSkillList = new ArrayList<>();

		Connection 		  con  = null;
		PreparedStatement ps   = null;
		ResultSet 		  rs   = null;

		String sql = """
					select hs.fk_hero_id, hs.skill_level,
					       s.skill_id, s.skill_name, s.skill_damage,
					       s.skill_mp_cost, s.skill_max_level,
					       s.skill_required_hero_level, s.skill_upgrade_cost
					from hero_skill hs
					join skill s 
					on hs.fk_skill_id = s.skill_id
					where hs.fk_hero_id = ?
					""";

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, heroId);

			rs = ps.executeQuery();

			while(rs.next()) {
				SkillDTO skill = new SkillDTO(
						rs.getInt("skill_id"),
						rs.getString("skill_name"),
						rs.getInt("skill_damage"),
						rs.getInt("skill_mp_cost"),
						rs.getInt("skill_max_level"),
						rs.getInt("skill_required_hero_level"),
						rs.getInt("skill_upgrade_cost")
						);

				HeroSkillDTO heroSkill = new HeroSkillDTO(
						rs.getInt("fk_hero_id"),
						skill,
						rs.getInt("skill_level")
						);

				heroSkillList.add(heroSkill);
			}

		} finally {
			DBManager.close(con, ps, rs);
		}

		return heroSkillList;
	}

	@Override
	public HeroSkillDTO selectHeroSkill(int heroId, int skillId) throws SQLException {
		Connection 		  con  = null;
		PreparedStatement ps   = null;
		ResultSet 		  rs   = null;

		HeroSkillDTO heroSkill = null;

		String sql = """
					select hs.fk_hero_id, hs.skill_level,
					       s.skill_id, s.skill_name, s.skill_damage,
					       s.skill_mp_cost, s.skill_max_level,
					       s.skill_required_hero_level, s.skill_upgrade_cost
					from hero_skill hs
					join skill s on hs.fk_skill_id = s.skill_id
					where hs.fk_hero_id = ? and hs.fk_skill_id = ?
					""";

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, heroId);
			ps.setInt(2, skillId);

			rs = ps.executeQuery();

			if(rs.next()) {
				SkillDTO skill = new SkillDTO(
						rs.getInt("skill_id"),
						rs.getString("skill_name"),
						rs.getInt("skill_damage"),
						rs.getInt("skill_mp_cost"),
						rs.getInt("skill_max_level"),
						rs.getInt("skill_required_hero_level"),
						rs.getInt("skill_upgrade_cost")
						);

				heroSkill = new HeroSkillDTO(rs.getInt("fk_hero_id"), skill, rs.getInt("skill_level"));
			}

		} finally {
			DBManager.close(con, ps, rs);
		}

		return heroSkill;
	}

	@Override
	public int upgradeHeroSkill(int heroId, int skillId) throws SQLException {
		int result = 0;

		Connection con = null;
		PreparedStatement ps = null;

		String sql = "update hero_skill	set skill_level = skill_level + 1 where fk_hero_id = ? and fk_skill_id = ?";

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, heroId);
			ps.setInt(2, skillId);

			result = ps.executeUpdate();

		} finally {
			DBManager.close(con, ps);
		}

		return result;
	}

	@Override
	public int insertDefaultSkills(int heroId) throws SQLException {
		
		int 			  result = 0;
		Connection 		  con    = null;
		PreparedStatement ps     = null;

		String sql = "insert into hero_skill(fk_hero_id, fk_skill_id, skill_level)values (?, 1, 1), (?, 2, 1), (?, 3, 1)";

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, heroId);
			ps.setInt(2, heroId);
			ps.setInt(3, heroId);

			result = ps.executeUpdate();

		} finally {
			DBManager.close(con, ps);
		}

		return result;
	}
	
	@Override
	public int deleteHeroSkills(int heroId) throws SQLException {

		int 			  result = 0;
		Connection 		  con    = null;
		PreparedStatement ps     = null;
		String sql = "delete from hero_skill where fk_hero_id=?";
		
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, heroId);
			
			result = ps.executeUpdate();
		} finally {
			DBManager.close(con, ps);
		}
		
		return result;

	}
}