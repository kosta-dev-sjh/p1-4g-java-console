package com.kosta.console_rpg.controller;

import java.util.List;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.ItemDTO;
import com.kosta.console_rpg.model.service.ShopService;

public class ShopController {
	
	private ShopService shopService = new ShopService();
	
	public List<ItemDTO> showShop() {
		
		try {
			List<ItemDTO> list = shopService.showShop();
			
			return list;
			
		} catch (GameException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public void buyItem(int itemId, int quantity) throws GameException {
	    shopService.buyItem(itemId, quantity);
	}
	
	public void sellItem(int itemId) {
		try {
			shopService.sellItem(itemId);
		} catch (GameException e) {
	
			e.printStackTrace();
		}
	}
}
