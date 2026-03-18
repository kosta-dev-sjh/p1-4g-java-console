package com.kosta.console_rpg.model.dao;

import java.util.List;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.ItemDTO;

public interface ShopDAO {
	
	/*
	 * 상점에서 구매, 판매 가능한 아이템 목록 보기
	 */
	List<ItemDTO> selectShopItems() throws GameException;

	
	/*
	 * 아이템 구매
	 * 
	 * 보유 젬 구매 가능 수량인지 확인 
	 * 맞다면 가격만큼 보유 젬 차감 
	 * 인벤토리에 아이템 insert
	 */
	void buyShopItem(int heroId, int itemId) throws GameException;
	
	
	/*
	 * 아이템 판매
	 * 보유 아이템 수량 확인
	 * 아이템 수량 * 가격으로 젬 추가 
	 * 인벤토리에서 아이템 delete 
	 */
	void sellShopItem(int heroId, int itemId) throws GameException;
	
}
