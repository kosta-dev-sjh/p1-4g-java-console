package com.kosta.console_rpg.model.dao;

import java.util.List;
import java.util.Map;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.InventoryDTO;
import com.kosta.console_rpg.model.dto.ItemDTO;

public interface InventoryDAO {
	
	/*
	 * 인벤토리 정보 조회
	 */
	List<InventoryDTO> selectInventoryByHeroId(int heroId) throws GameException;
	
	
	InventoryDTO selectInventoryById(int inventoryId) throws GameException;
	
	/*
	 * 아이템 장착 inventory_is_equipped = true == 1
	 */
	void equipItem(int heroId, int itemId) throws GameException;
	
	
	/*
	 * 아이템 해제 inventory_is_equipped = false == 0
	 */
	void unequipItem(int heroId, int itemId) throws GameException;
	
	/*
	 * 아이템 장착 여부
	 */
	boolean equipStatus(int heroId, int itemId) throws GameException;
	
	/*
	 * 포션 사용 업데이트
	 */
	void updateUsedPotion(int heroId, int inventoryId) throws GameException;
	
	
	/*
	 * 아이템 수량 업데이트 
	 */
	void updateQuantity(int heroId, int itemId, int quantity) throws GameException;
	
	
	/*
	 * 인벤토리에 아이템 등록
	 */
	void insertItem(int heroId, int itemId) throws GameException;
	
}
