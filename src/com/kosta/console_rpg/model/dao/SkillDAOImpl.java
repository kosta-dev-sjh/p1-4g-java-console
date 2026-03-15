package com.kosta.console_rpg.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.kosta.console_rpg.model.dto.HeroSkillDTO;
import com.kosta.console_rpg.model.dto.SkillDTO;

/**
 * SkillDAO 인터페이스를 구현하여 캐릭터 스킬 관련 DB 작업을 수행하는 DAO 구현 클래스
 *
 * 작성자      : 송정현
 * 생성일      : 2026.03.15
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class SkillDAOImpl implements SkillDAO {

	@Override
	public List<SkillDTO> selectSkillsByHeroId(int heroId) throws SQLException {
		return null;
	}

	@Override
	public List<HeroSkillDTO> selectHeroSkills(int heroId) throws SQLException {
		return null;
	}

	@Override
	public HeroSkillDTO selectHeroSkill(int heroId, int skillId) throws SQLException {
		return null;
	}

	@Override
	public int upgradeHeroSkill(int heroId, int skillId) throws SQLException {
		return 0;
	}


}
