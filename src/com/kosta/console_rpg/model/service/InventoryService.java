package com.kosta.console_rpg.model.service;

import com.kosta.console_rpg.model.dao.InventoryDAOImpl;
import com.kosta.console_rpg.model.dto.BattlePotionDTO;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.InventoryDTO;
import com.kosta.console_rpg.model.dto.ItemDTO;
import com.kosta.console_rpg.session.LoginSession;

import java.sql.SQLException;
import java.util.List;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dao.InventoryDAO;

public class InventoryService {
	
	private InventoryDAO inventoryDAO = new InventoryDAOImpl();
	private HeroService heroService = new HeroService();
	
	
	public List<InventoryDTO> showInventory(int heroId) throws GameException {

		List<InventoryDTO> list = inventoryDAO.selectInventoryByHeroId(heroId);

	    return list;
	} 
	
	public boolean equipStatus(int heroId, int itemId) throws GameException {
		
		boolean status = inventoryDAO.equipStatus(heroId, itemId);
		
		return status;
	}
	
	
	public void equipItem(int heroId, int itemId) throws GameException {

	    List<InventoryDTO> list = inventoryDAO.selectInventoryByHeroId(heroId);

	    InventoryDTO target = null;

	    // 선택한 아이템 찾기
	    for (InventoryDTO inv : list) {
	        if (inv.getItem().getItemId() == itemId) {
	            target = inv;
	            break;
	        }
	    }

	    if (target == null) {
	        throw new GameException("아이템이 존재하지 않습니다.");
	    }

	    String type = target.getItem().getItemType();

	    // 장착 가능한 타입인지 검사 
	    if (!type.equals("weapon") && !type.equals("armor")) {
	        throw new GameException("장착할 수 없는 아이템입니다.");
	    }

	    // 이미 장착된 아이템인지 먼저 검사
	    boolean nowStatus = inventoryDAO.equipStatus(heroId, itemId);

	    if (nowStatus) {
	        System.out.println("이미 장착되어 있는 아이템입니다.");
	        return;
	    }

	    // 같은 타입 장착 아이템 있는지 검사 (슬롯 시스템)
	    for (InventoryDTO inv : list) {

	        String invType = inv.getItem().getItemType();

	        if (type.equals(invType)) {

	            boolean status =
	                inventoryDAO.equipStatus(heroId, inv.getItem().getItemId());

	            if (status) {
	                inventoryDAO.unequipItem(heroId, inv.getItem().getItemId());
	                System.out.println("기존 장비가 해제되었습니다.");
	                break;
	            }
	        }
	    }

	    // 아이템 장착
	    inventoryDAO.equipItem(heroId, itemId);
	    System.out.println("아이템이 장착되었습니다.");
	    
	    // 장착 후 영웅 스탯 갱신
	    updateHeroStatsWithEquippedItems();
	}
	
	public void unequipItem(int heroId, int itemId) throws GameException {
		
		inventoryDAO.unequipItem(heroId, itemId);
		
		updateHeroStatsWithEquippedItems();
		
	}
	
	 /**
     * 현재 장착 중인 장비의 공격력/방어력 반영
     */
    public void updateHeroStatsWithEquippedItems() throws GameException {
        HeroDTO hero = LoginSession.getInstance().getCurrentHero();
        if (hero == null) {
            throw new GameException("로그인된 캐릭터가 없습니다.");
        }

        int heroId = hero.getHeroId();

        List<InventoryDTO> list = inventoryDAO.selectInventoryByHeroId(heroId);

        int totalAttack = 0;
        int totalDefense = 0;

        for (InventoryDTO inv : list) {
            if (inv.getInventoryIsEquipped() == 1) { // 장착 중인 장비만
                ItemDTO item = inv.getItem();
                if (item.getItemType().equals("weapon")) {
                    totalAttack += item.getItemAtkBonus();
                }
                if (item.getItemType().equals("armor")) {
                    totalDefense += item.getItemDefBonus();
                }
            } 
        }
        
        int baseAttack = 10;   
        int baseDefense = 5;

        // 기본 공격력/방어력에 장비 스탯 더하기
        hero.setHeroAttack(baseAttack + totalAttack);
        hero.setHeroDefense(baseDefense + totalDefense);

        // 세션 갱신
        LoginSession.getInstance().setCurrentHero(hero);

        // DB 갱신
        heroService.updateHero(hero);
    }
	
	public void usePotion(int heroId, int inventoryId) throws GameException {
		
		InventoryDTO inv = inventoryDAO.selectInventoryById(inventoryId);
		
		if (inv == null) {
	        throw new GameException("아이템이 존재하지 않습니다.");
	    }
		
		if(inv.getItem().getItemType().equals("potion")) {
			inventoryDAO.updateUsedPotion(heroId, inventoryId);
		} else {
			throw new GameException("포션만 사용할 수 있습니다.");
		}
		
		
	}

	public List<BattlePotionDTO> showPotionItems(int heroId) throws GameException {
		List<BattlePotionDTO> potionList = inventoryDAO.selectBattlePotionList(heroId);
		
		if (potionList == null || potionList.isEmpty()) {
			throw new GameException("포션 아이템이 없습니다.");
		}
		
		return potionList;	
	}
	public void deleteInventory(int heroId) throws SQLException, GameException {

		inventoryDAO.deleteInventory(heroId);
	}
}
