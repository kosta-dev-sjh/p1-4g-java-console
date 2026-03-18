package com.kosta.console_rpg.controller;

import java.util.List;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.BattlePotionDTO;
import com.kosta.console_rpg.model.dto.InventoryDTO;
import com.kosta.console_rpg.model.service.InventoryService;
import com.kosta.console_rpg.session.LoginSession;

/**
 * 인벤토리 내 아이템 조회, 장착/해제, 사용 기능 흐름을 제어하는 컨트롤러
 *
 * 작성자 :
 * 생성일 :
 * 최종 수정자 :
 * 최종 수정일 :
 */
public class InventoryController {

	private InventoryService inventoryService = new InventoryService();

	public List<InventoryDTO> showInventory() {

		int heroId = LoginSession.getInstance().getCurrentHero().getHeroId();

		try {

			List<InventoryDTO> list = inventoryService.showInventory(heroId);

			return list;

		} catch (GameException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void equipItem(int itemId) {

		int heroId = LoginSession.getInstance().getCurrentHero().getHeroId();

		try {
			inventoryService.equipItem(heroId, itemId);
		} catch (GameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<BattlePotionDTO> showPotionItems() {
		int heroId = LoginSession.getInstance().getCurrentHero().getHeroId();

		try {
			List<BattlePotionDTO> potionList = inventoryService.showPotionItems(heroId);
			return potionList;
		} catch (GameException e) {
			e.printStackTrace();
			return null;
		}
	}
}
