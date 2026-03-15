package com.kosta.console_rpg.controller;

import com.kosta.console_rpg.model.dto.UserDTO;
import com.kosta.console_rpg.model.service.UserService;
import com.kosta.console_rpg.view.FailView;

/**
 * 사용자(User) 관련 요청을 받아 흐름을 제어하는 Controller 클래스
 *
 * 작성자      : 송정현
 * 생성일      : 2026.03.14
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class UserController {
	// ======= field =======
	private UserService userService = new UserService();
	
	// ======= public method =======
	/**
	 * 로그인 요청 처리 메소드
	 * 
	 */
	public void login(String loginId, String pwd) {
		try {
			userService.login(loginId, pwd);
			
		}catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
		
	}
	
	/**
	 * 회원가입 요청 처리 메소드
	 * 
	 */
	public void register(UserDTO registerUser) {
		try {
			userService.register(registerUser);
		}catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
		
	}
}
