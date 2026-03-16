package com.kosta.console_rpg.controller;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.service.InventoryService;

/**
 * 인벤토리 내 아이템 조회, 장착/해제, 사용 기능 흐름을 제어하는 컨트롤러
 *
 * 작성자      : 
 * 생성일      : 
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class InventoryController {
	
	private static	InventoryService inventoryService = new InventoryService();
	
	
	public static void showInventory() throws GameException {
		inventoryService.showInventory();
	}
	
	public static void equipItem(int itemId) throws GameException {
		inventoryService.equipItem(itemId);
	}
	
	public static void usePotion() {
		
	}
}
