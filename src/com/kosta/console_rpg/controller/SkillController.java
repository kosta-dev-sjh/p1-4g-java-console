package com.kosta.console_rpg.controller;

import java.sql.SQLException;
import java.util.List;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.HeroSkillDTO;
import com.kosta.console_rpg.model.service.SkillService;
import com.kosta.console_rpg.session.LoginSession;
import com.kosta.console_rpg.view.FailView;

/**
 * 히어로 스킬 업그레이드를 요청하는 컨트롤러
 *
 * 작성자      : 이진주
 * 생성일      : 2026.03.17
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class SkillController {
	
	private SkillService skillService = new SkillService();

	
	/**
	 * 특정 스킬의 현재 강화 정보를 조회하는 메소드
	 * 
	 */
	public List<HeroSkillDTO> selectHeroSkills() {
		 try {
            int heroId = LoginSession.getInstance().getCurrentHero().getHeroId();
            return skillService.selectHeroSkills(heroId);

        } catch (GameException e) {
            FailView.errorMessage(e.getMessage());
            return null;
        }
	}	
	
	/**
	 * 캐릭터 생성 시 기본 스킬 3개를 지급하는 메소드
	 * 
	 */
	public void insertDefaultSkills() {
		try {
			int heroId = LoginSession.getInstance().getCurrentHero().getHeroId();
			skillService.insertDefaultSkills(heroId);
		} catch(GameException e) {
			FailView.errorMessage(e.getMessage());
		}
	}
	
	/**
	 * 특정 스킬의 강화 레벨을 1 증가시키는 메소드
	 * 현재 강화된 스킬 정보들을 DB에 반영한다
	 * @throws GameException 
	 * 
	 */
	public void upgradeHeroSkill(int skillId) throws GameException {
		try {
			int heroId = LoginSession.getInstance().getCurrentHero().getHeroId();
			skillService.upgradeHeroSkill(heroId, skillId);
		} catch(GameException e) {
			FailView.errorMessage(e.getMessage());
		}
	}
	
	/**
	 * 히어로가 삭제될 때 스킬 삭제하는 메소드
	 * 
	 */
	public void deleteHeroSkills() {
		try {
			int heroId = LoginSession.getInstance().getCurrentHero().getHeroId();
			skillService.deleteHeroSkills(heroId);
			LoginSession.getInstance().clearHero();
		} catch(GameException e) {
            FailView.errorMessage(e.getMessage());
		}
	}
}
