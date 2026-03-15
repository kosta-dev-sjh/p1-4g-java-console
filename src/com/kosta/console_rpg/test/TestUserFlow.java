package com.kosta.console_rpg.test;

import com.kosta.console_rpg.controller.UserController;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.UserDTO;
import com.kosta.console_rpg.session.LoginSession;

public class TestUserFlow {
	private static final UserController controller = new UserController();
	
	public static void main(String[] args) {
//		register();
		
		controller.login("sjh1123", "test");
		
		HeroDTO hero = LoginSession.getInstance().getCurrentHero();
		
		setHero(hero);

		
		System.out.println(LoginSession.getInstance().getCurrentUser().getUserName());
	}
	
	public static void register() {
		UserDTO registerUser = new UserDTO("sjh1123", "test", "송정현");
		controller.register(registerUser);
	}
	
	public static void setHero(HeroDTO hero) {
		if(hero != null) {
		    System.out.println(hero.getHeroName());
		}else {
			System.out.println("히어로 정보가 없습니다.");
			
			
			System.out.println(hero.getHeroName());
		}
	}
}
