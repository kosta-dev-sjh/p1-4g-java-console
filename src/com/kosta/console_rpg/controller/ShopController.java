package com.kosta.console_rpg.controller;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.service.ShopService;

public class ShopController {
	
	private ShopService shopService = new ShopService();
	
	public void showShop() {
		
		try {
			shopService.showShop();
		} catch (GameException e) {
			
			e.printStackTrace();
		}
	}
	
	void buyItem(int itemId) {
		
	}
	
	void sellItem(int itemId) {
		
	}
}
