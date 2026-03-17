package com.kosta.console_rpg.model.service;

import java.util.List;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dao.ShopDAO;
import com.kosta.console_rpg.model.dao.ShopDAOImpl;
import com.kosta.console_rpg.model.dto.ItemDTO;

public class ShopService {
	
	private ShopDAO shopDAO = new ShopDAOImpl();

	public List<ItemDTO> showShop() throws GameException {
		
		List<ItemDTO> list = shopDAO.selectShopItems();
		
		return list;
		
	}
	
	public void buyItem(int itemId) {
		
	}
	
	public void sellItem(int itemId) {
		
	}
	
	
}
