package com.kosta.console_rpg.session;

import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.UserDTO;

/**
 * 현재 로그인한 사용자 및 캐릭터 정보를 관리하는 세션 클래스
 *
 * 작성자      : 송정현
 * 생성일      : 2026.03.14
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class LoginSession {
	// ======= field =======
	private static final LoginSession instance = new LoginSession();
	private HeroDTO currentHero;
	private UserDTO currentUser;

	// ======= constructor =======
	private LoginSession() {}

	/**
	 * LoginSession 싱글톤 객체 반환
	 * 
	 * @return LoginSession 인스턴스
	 */
	public static LoginSession getInstance() {
		return instance;
	}

	/**
	 * 현재 로그인한 유저 정보 반환
	 * 
	 * @return 현재 로그인한 UserDTO
	 */
	public UserDTO getCurrentUser() {
		return currentUser;
	}

	/**
	 * 현재 로그인한 캐릭터 정보 반환
	 * 
	 * @return 현재 로그인한 HeroDTO
	 */
	public HeroDTO getCurrentHero() {
		return currentHero;
	}
	
	
	/**
	 * 현재 로그인한 유저 설정
	 * 
	 * @param user 로그인한 사용자 정보
	 */
	public void setCurrentUser(UserDTO user) {
		this.currentUser = user;
	}
	
	/**
	 * 현재 로그인한 캐릭터 정보 설정
	 * 
	 * @param hero 로그인한 캐릭터 정보
	 */
	public void setCurrentHero(HeroDTO hero) {
		this.currentHero = hero;
	}

	/**
	 * 로그아웃 시 세션 정보 초기화
	 * 
	 */
	public void clear() {
		currentHero = null;
		currentUser = null;
	}
	
	/**
	 * 캐릭터 삭제 시 히어로 정보 초기화
	 * 
	 */
	public void clearHero() {
		currentHero = null;
	}
	
	/**
	 * 로그인 여부 확인
	 * 
	 * @return 로그인 상태면 true
	 */
	public boolean isLogin() {
		return currentUser != null;
	}
}
