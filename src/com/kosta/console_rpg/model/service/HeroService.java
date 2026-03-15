package com.kosta.console_rpg.model.service;

import java.sql.SQLException;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dao.HeroDAO;
import com.kosta.console_rpg.model.dao.HeroDAOImpl;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.session.LoginSession;

/**
 * 히어로 조회, 생성, 성장 등 캐릭터 관련 요청을 제어하는 서비스
 *
 * 작성자      : 송정현
 * 생성일      : 2026.03.16
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class HeroService {
	// ======= field =======
	private HeroDAO heroDao = new HeroDAOImpl();
	
	// ======= public method =======
	/**
	 * 신규 히어로를 생성하고 로그인 세션에 저장한다.
	 *
	 * @param userId 사용자 번호
	 * @param heroName 캐릭터 이름
	 * @throws GameException
	 */
	public void createHero(int userId, String heroName) throws GameException{
		try {
			// 기존 캐릭터 존재 여부 확인
	        HeroDTO hero = heroDao.selectHeroByUserId(userId);

	        if(hero != null) {
	        	throw new GameException("이미 생성된 캐릭터가 존재합니다.");
	        }
	        
	        // 캐릭터 생성
	        heroDao.insertHero(userId, heroName);
	        
	        HeroDTO createdHero = heroDao.selectHeroByUserId(userId);
	        
	        if(createdHero == null) {
	            throw new GameException("캐릭터 생성 후 조회에 실패했습니다.");
	        }
	        
	        LoginSession.getInstance().setCurrentHero(createdHero);

	    } catch (SQLException e) {
	        throw new GameException("캐릭터 생성 중 오류가 발생했습니다.");
	    }

	}
	
	
}
