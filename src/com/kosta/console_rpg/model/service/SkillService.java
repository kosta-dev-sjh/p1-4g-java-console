package com.kosta.console_rpg.model.service;

import java.sql.SQLException;
import java.util.List;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dao.HeroDAO;
import com.kosta.console_rpg.model.dao.HeroDAOImpl;
import com.kosta.console_rpg.model.dao.SkillDAO;
import com.kosta.console_rpg.model.dao.SkillDAOImpl;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.HeroSkillDTO;
import com.kosta.console_rpg.session.LoginSession;

/**
 * 히어로 스킬 업그레이드를 요청하는 서비스
 *
 * 작성자      : 이진주
 * 생성일      : 2026.03.17
 * 최종 수정자 : 
 * 최종 수정일 : 2026.03.18
 */

public class SkillService {
	// ======= field =======
	private SkillDAO skillDao = new SkillDAOImpl();
    private HeroDAO heroDao = new HeroDAOImpl();

    // ===== 상수 (외부 접근 차단) =====
    private static final int DAMAGE_INCREASE = 5;
    private static final int MP_COST_INCREASE = 5;
    private static final int REQUIRED_LEVEL_INCREASE = 1;
    
	// ======= public method =======
	/**
     * 전체 스킬 조회
     * 
     * @throws SQLException
     */
    public List<HeroSkillDTO> selectHeroSkills(int heroId) throws GameException {
        try {
            List<HeroSkillDTO> skills = skillDao.selectHeroSkills(heroId);

            if (skills.isEmpty()) {
                throw new GameException("스킬 조회 중 오류가 발생했습니다.");
            }
            return skills; 
        } catch (SQLException e) {
            throw new GameException("스킬 조회 중 오류가 발생했습니다.");
        } 
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
     * 스킬 강화 조건 체크 + 강화 실행
     */
    public void upgradeHeroSkill(int heroId, int skillId) throws GameException {
        try {
            // 1. 스킬 조회
            HeroSkillDTO heroSkill = skillDao.selectHeroSkill(heroId, skillId);
            if (heroSkill == null) {
                throw new GameException("보유하지 않은 스킬입니다.");
            }

            int currentLevel = heroSkill.getSkillLevel();
            int nextLevel = currentLevel + 1;

            // 2. 최대 레벨 체크
            if (currentLevel >= heroSkill.getSkill().getSkillMaxLevel()) {
                throw new GameException("이미 최대 레벨입니다.");
            }

            // 3. 히어로 조회
            HeroDTO hero = LoginSession.getInstance().getCurrentHero();
            if (hero == null) {
                throw new GameException("히어로 정보가 없습니다.");
            }

            // 4. 요구 레벨 계산
            int baseRequiredLevel = heroSkill.getSkill().getSkillRequiredHeroLevel();
            int requiredLevel = baseRequiredLevel + (nextLevel - 1) * REQUIRED_LEVEL_INCREASE;

            if (hero.getHeroLevel() < requiredLevel) {
                throw new GameException("히어로 레벨이 부족합니다.");
            }

            // 5. 강화 비용 계산
            int baseUpgradeCost = heroSkill.getSkill().getSkillUpgradeCost();
            int upgradeCost = baseUpgradeCost * currentLevel;

            if (hero.getHeroGem() < upgradeCost) {
                throw new GameException("젬이 부족합니다.");
            }

            // 6. 스킬 강화
            int result = skillDao.upgradeHeroSkill(heroId, skillId);
            if (result == 0) {
                throw new GameException("스킬 강화에 실패했습니다.");
            }

            // 7. 젬 차감
            int newGem = hero.getHeroGem() - upgradeCost;
            hero.setHeroGem(newGem);

            int gemResult = heroDao.updateHeroGem(heroId, newGem);
            if (gemResult == 0) {
                throw new GameException("젬 차감에 실패했습니다.");
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
