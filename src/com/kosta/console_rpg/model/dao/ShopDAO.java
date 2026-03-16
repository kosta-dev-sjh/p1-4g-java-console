package com.kosta.console_rpg.model.dao;

import java.util.List;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.ItemDTO;

public interface ShopDAO {
	
	List<ItemDTO> selectShopItems() throws GameException;

	void buyShopItem(int heroId, int itemId) throws GameException;
	
	void sellShopItem(int heroId, int itemId) throws GameException;
	
}
