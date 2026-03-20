package com.kosta.console_rpg.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.util.DBManager;

/**
 * HeroDAO 인터페이스를 구현하여 히어로 관련 DB 작업을 수행하는 DAO 구현 클래스
 */
public class HeroDAOImpl implements HeroDAO {

	@Override
	public HeroDTO selectHeroByUserId(int userId) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HeroDTO hero = null;
		String sql = "select * from hero where fk_user_id=?";

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();

			if (rs.next()) {
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
						rs.getInt("hero_max_clear_stage"));
			}
		} finally {
			DBManager.close(con, ps, rs);
		}
		return hero;
	}

	@Override
	public int insertHero(int userId, String heroName) throws SQLException {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;

		try {
			con = DBManager.getConnection();

			if (heroName == null || heroName.trim().isEmpty()) {
				sql = "insert into hero(fk_user_id) values(?)";
				ps = con.prepareStatement(sql);
				ps.setInt(1, userId);
			} else {
				sql = "insert into hero(fk_user_id, hero_name) values(?, ?)";
				ps = con.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setString(2, heroName);
			}

			result = ps.executeUpdate();
		} finally {
			DBManager.close(con, ps);
		}
		return result;
	}

	@Override
	public int deleteHero(int heroId) throws SQLException {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "delete from hero where hero_id=?";

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

	@Override
	public int updateHero(HeroDTO hero) throws SQLException {
		Connection con = null;

		try {
			con = DBManager.getConnection();
			return updateHero(con, hero);
		} finally {
			DBManager.close(con);
		}
	}

	@Override
	public int updateHero(Connection con, HeroDTO hero) throws SQLException {
		int result = 0;
		PreparedStatement ps = null;
		String sql = """
				update hero
				set hero_level=?,
				    hero_exp=?,
				    hero_hp=?,
				    hero_mp=?,
				    hero_attack=?,
				    hero_defense=?
				where hero_id=?
				""";

		try {
			ps = con.prepareStatement(sql);

			ps.setInt(1, hero.getHeroLevel());
			ps.setInt(2, hero.getHeroExp());
			ps.setInt(3, hero.getHeroHp());
			ps.setInt(4, hero.getHeroMp());
			ps.setInt(5, hero.getHeroAttack());
			ps.setInt(6, hero.getHeroDefense());
			ps.setInt(7, hero.getHeroId());

			result = ps.executeUpdate();
		} finally {
			DBManager.close(ps);
		}
		return result;
	}

	@Override
	public int updateClearStage(int heroId, int stage) throws SQLException {
		Connection con = null;

		try {
			con = DBManager.getConnection();
			return updateClearStage(con, heroId, stage);
		} finally {
			DBManager.close(con);
		}
	}

	@Override
	public int updateClearStage(Connection con, int heroId, int stage) throws SQLException {
		int result = 0;
		PreparedStatement ps = null;
		String sql = "update hero set hero_max_clear_stage=? where hero_id=?";

		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, stage);
			ps.setInt(2, heroId);
			result = ps.executeUpdate();
		} finally {
			DBManager.close(ps);
		}
		return result;
	}

	@Override
	public int updateHeroGem(int heroId, int gem) throws SQLException {
		Connection con = null;

		try {
			con = DBManager.getConnection();
			return updateHeroGem(con, heroId, gem);

		} finally {
			DBManager.close(con);
		}
	}

	@Override
	public int updateHeroGem(Connection con, int heroId, int gem) throws SQLException {
		int result = 0;
		PreparedStatement ps = null;
		String sql = "update hero set hero_gem=? where hero_id=?";

		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, gem);
			ps.setInt(2, heroId);
			result = ps.executeUpdate();
		} finally {
			DBManager.close(ps);
		}
		return result;
	}
}