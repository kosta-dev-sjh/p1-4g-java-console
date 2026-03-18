package com.kosta.console_rpg.controller;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.service.HeroService;
import com.kosta.console_rpg.session.LoginSession;
import com.kosta.console_rpg.view.FailView;

/**
 * 히어로 조회, 생성, 성장 등 캐릭터 관련 요청을 제어하는 컨트롤러
 *
 * 작성자      : 송정현
 * 생성일      : 2026.03.15
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class HeroController {
	// ======= field =======
	private HeroService heroService = new HeroService();

	// ======= public method =======
	
	/**
	 * 현재 로그인한 사용자 기준으로 캐릭터를 생성한다.
	 *
	 * @param heroName 캐릭터 이름
	 */
	public void createHero(String heroName) {
		try {
			int userId = LoginSession.getInstance().getCurrentUser().getUserId();
			heroService.createHero(userId, heroName);

		} catch (GameException e) {
			FailView.errorMessage(e.getMessage());
		}
	}

	/**
	 * 현재 로그인한 캐릭터를 삭제한다.
	 */
	public void deleteHero() {
		try {
            int heroId = LoginSession.getInstance().getCurrentHero().getHeroId();
            
            heroService.deleteHero(heroId);
            LoginSession.getInstance().clearHero();
            
        } catch (GameException e) {
            FailView.errorMessage(e.getMessage());
        }
	}

	/**
	 * 현재 세션의 캐릭터 정보를 DB에 반영한다.
	 */
	public void updateHero() {
		try {
            HeroDTO hero = LoginSession.getInstance().getCurrentHero();
            heroService.updateHero(hero);
            
        } catch (GameException e) {
            FailView.errorMessage(e.getMessage());
        }
	}

	/**
	 * 현재 세션의 캐릭터 최대 클리어 스테이지를 DB에 반영한다.
	 */
	public void updateClearStage() {
		try {
            int heroId = LoginSession.getInstance().getCurrentHero().getHeroId();
            int stage = LoginSession.getInstance().getCurrentHero().getHeroMaxClearStage();
            
            heroService.updateClearStage(heroId, stage);
            
        } catch (GameException e) {
            FailView.errorMessage(e.getMessage());
        }
		
	}

	/**
	 * 현재 세션의 캐릭터 보유 보석 수를 DB에 반영한다.
	 */
	public void updateHeroGem() {
		try {
            int heroId = LoginSession.getInstance().getCurrentHero().getHeroId();
            int gem = LoginSession.getInstance().getCurrentHero().getHeroGem();
            
            heroService.updateHeroGem(heroId, gem);
        } catch (GameException e) {
            FailView.errorMessage(e.getMessage());
        }
	}

}
