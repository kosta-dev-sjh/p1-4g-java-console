package com.kosta.console_rpg.test;

import com.kosta.console_rpg.controller.UserController;
import com.kosta.console_rpg.model.dto.UserDTO;
import com.kosta.console_rpg.session.LoginSession;

public class TestUserFlow {
	
	
	public static void main(String[] args) {
		UserController controller = new UserController();
		
		UserDTO registerUser = new UserDTO("sjh1123", "test", "송정현");
		
//		controller.register(registerUser);
		controller.login("sjh1123", "test");
		System.out.println(LoginSession.getInstance().getCurrentHero().getHeroName());
		System.out.println(LoginSession.getInstance().getCurrentUser().getUserName());
		
	}
	

}
