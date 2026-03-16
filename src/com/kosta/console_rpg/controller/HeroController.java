package com.kosta.console_rpg.controller;

import com.kosta.console_rpg.exception.DuplicationException;
import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.HeroSkillDTO;
import com.kosta.console_rpg.model.service.HeroService;

/**
 * 캐릭터 조회, 생성, 성장 등 캐릭터 관련 요청을 제어하는 컨트롤러
 *
 * 작성자      : 
 * 생성일      : 
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class HeroController {
	
	private static HeroService heroService = new HeroService();
	
	public static void createHero(HeroDTO hero) throws DuplicationException, GameException {
		
		heroService.createHero(hero);
		
	}
	
	public static HeroDTO showHeroInfo(int userId) throws GameException {
		
		HeroDTO heroDTO = heroService.loadHero(userId);
		
		return heroDTO;
		
	}
	
	public static void upgradeSkill(HeroSkillDTO heroSkill) throws GameException {
		
		heroService.upgradeSkill(heroSkill);
	}
}
