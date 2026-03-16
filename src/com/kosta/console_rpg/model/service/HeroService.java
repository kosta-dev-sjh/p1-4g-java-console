package com.kosta.console_rpg.model.service;

import com.kosta.console_rpg.exception.DuplicationException;
import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dao.HeroDAO;
import com.kosta.console_rpg.model.dao.HeroDAOImpl;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.HeroSkillDTO;

public class HeroService {
	
	private HeroDAO heroDAO = new HeroDAOImpl();
	
	public HeroDTO createHero(HeroDTO hero) throws DuplicationException, GameException {
		
		heroDAO.insertHero(hero); 
		
		if(hero == null) {
			throw new GameException("히어로 정보를 가져오지 못했습니다.");
		}
		
		return hero;
	}
	
	public HeroDTO loadHero(int userId) throws GameException {
		
		HeroDTO heroDTO = heroDAO.selectHeroByUserId(userId);
		
		if(heroDTO == null) {
			throw new GameException("유저 아이디로 히어로 정보를 가져오지 못했습니다.");
		}
		
		return heroDTO;
	}

	public HeroDTO getHeroInfo() {
		
		
		return null;
	}
	
	public void levelUpCheck() {
		
	}
	
	public void upgradeSkill(HeroSkillDTO heroSkill) throws GameException {
		
		heroDAO.upgradeHeroSkill(heroSkill);
		
	}
	
	
	public void updateClearStage(int heroId, int stage) {
		
	}
	
}
