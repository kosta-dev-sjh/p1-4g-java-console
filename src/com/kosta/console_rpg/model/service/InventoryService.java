package com.kosta.console_rpg.model.service;

import com.kosta.console_rpg.model.dao.InventoryDAOImpl;
import com.kosta.console_rpg.model.dto.InventoryDTO;

import java.util.List;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dao.InventoryDAO;

public class InventoryService {
	
	private InventoryDAO inventoryDAO = new InventoryDAOImpl();
	
	private int heroId;
	
	public List<InventoryDTO> showInventory() throws GameException {

	    List<InventoryDTO> list = inventoryDAO.selectInventoryByHeroId(heroId);

	    if(list.isEmpty()) {
	        throw new GameException("인벤토리가 비어있습니다.");
	    }

	    return list;
	} 
	
	public void equipItem(int itemId) throws GameException {
	
		inventoryDAO.equipItem(heroId, itemId);
		
	}
	
	public void unequipItem(int itemId) throws GameException {
		
		inventoryDAO.unequipItem(heroId, itemId);
		
	}
	
	public void usePotion(int inventoryId) {
		
	}
}
