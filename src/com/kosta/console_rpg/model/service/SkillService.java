package com.kosta.console_rpg.model.service;

import java.sql.SQLException;
import java.util.List;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dao.HeroDAO;
import com.kosta.console_rpg.model.dao.SkillDAO;
import com.kosta.console_rpg.model.dao.SkillDAOImpl;
import com.kosta.console_rpg.model.dto.HeroSkillDTO;

/**
 * 히어로 스킬 업그레이드를 요청하는 서비스
 *
 * 작성자      : 이진주
 * 생성일      : 2026.03.17
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class SkillService {
	// ======= field =======
	private SkillDAO skillDao = new SkillDAOImpl();
	//private HeroDAO heroDao = new HeroDAOImpl();
	
	// ======= public method =======
	/**
     * 전체 스킬 조회
	 * @throws SQLException 
     */
    public List<HeroSkillDTO> selectHeroSkills(int heroId) throws GameException, SQLException {
    	List<HeroSkillDTO> skills = skillDao.selectHeroSkills(heroId);
    	
    	if(skills.isEmpty()) {
            throw new GameException("스킬 조회 중 오류가 발생했습니다.");
        }
    	return skills;
    }


    /**
     * 초기 스킬 지급
     */
    public void insertDefaultSkills(int heroId) throws GameException {
        try {
            int result = skillDao.insertDefaultSkills(heroId);

            if (result == 0) {
                throw new GameException("기본 스킬 지급에 실패했습니다.");
            }

        } catch (Exception e) {
            throw new GameException("기본 스킬 지급 중 오류가 발생했습니다.");
        }
    }
    
    /**
     * 스킬 강화
     */
    public void upgradeHeroSkill(int heroId, int skillId) throws GameException {
        try {
            HeroSkillDTO heroSkill = skillDao.selectHeroSkill(heroId, skillId);

            if (heroSkill == null) {
                throw new GameException("보유하지 않은 스킬입니다.");
            }

            // 최대 레벨 체크
            if (heroSkill.getSkillLevel() >= heroSkill.getSkill().getSkillMaxLevel()) {
                throw new GameException("이미 최대 레벨입니다.");
            }

            int result = skillDao.upgradeHeroSkill(heroId, skillId);

            if (result == 0) {
                throw new GameException("스킬 강화에 실패했습니다.");
            }

        } catch (Exception e) {
            throw new GameException("스킬 강화 중 오류가 발생했습니다.");
        }
    }

	/**
	 * 스킬 삭제
	 */
    public void deleteHeroSkills(int heroId) throws GameException {
    	try {
			int result = skillDao.deleteHeroSkills(heroId);

			if (result == 0) {
				throw new GameException("스킬 삭제에 실패했습니다.");
			}

		} catch (SQLException e) {
			throw new GameException("스킬 삭제 중 오류가 발생했습니다.");
		}
    }
}
