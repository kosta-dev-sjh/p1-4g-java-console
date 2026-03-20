package com.kosta.console_rpg.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kosta.console_rpg.controller.InventoryController;
import com.kosta.console_rpg.controller.ShopController;
import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.InventoryDTO;
import com.kosta.console_rpg.model.dto.ItemDTO;
import com.kosta.console_rpg.session.LoginSession;
import com.kosta.console_rpg.util.InputUtil;
import com.kosta.console_rpg.util.ConsoleUtils;

public class ShopView {
	private static ShopController shopController = new ShopController();
	private static InventoryController inventoryController = new InventoryController();
	
	public void showShop() {
		 
	    while (true) {
	    	System.out.print(this); 

	        int menu;
	        try {
	        	menu = InputUtil.changeInt(InputUtil.inputString());
	        } catch (GameException e) {
	        	System.out.println(e.getMessage());
	        	continue;
	        }

	        switch (menu) {
	            case 1:
	                buyMenu();
	                break;
	            case 2:
	                sellMenu();
	                break;
	            case 0:
	                System.out.println("상점을 나갑니다.");
	                return;
	            default:
	                System.out.println("잘못된 입력입니다.");
	        }
	    }
	}
	
	public static void buyMenu() {

	    int gem = LoginSession.getInstance()
	            .getCurrentHero()
	            .getHeroGem();
	    ConsoleUtils.printTitleBar("SHOP BUY");
	    System.out.println(" ◈ 보유 Gem(" + gem + ") ◈\n");

	    System.out.println("──────────────────────── POTION ────────────────────────\n");
	    System.out.println("[1] 포션");

	    System.out.println("\n─────────────────────── EQUIPMENT ───────────────────────\n");
	    System.out.println("[2] 장비");
	    System.out.println(" - 무기");
	    System.out.println(" - 갑옷");

	    System.out.println("\n─────────────────────────────────────────────────────────");
	    System.out.println("[0] 뒤로가기");
	    System.out.print("선택 ▶ ");

	    int select;
	    try {
	    	select = InputUtil.changeInt(InputUtil.inputString());
	    } catch (GameException e) {
	    	System.out.println(e.getMessage());
	    	return;
	    }

	    switch(select) {

	        case 1:
	            potionMenu();
	            break;

	        case 2:
	            equipmentMenu();
	            break;

	        case 0:
	            return;
	    }
	}
	
	public static void potionMenu() {

	    while (true) {

	        int gem = LoginSession.getInstance()
	                .getCurrentHero()
	                .getHeroGem();

	        List<ItemDTO> items = shopController.showShop();

	        Map<Integer, ItemDTO> menuMap = new HashMap<>();
	        int no = 1;

	        System.out.println("\n──────────── POTION ────────────\n");
	        System.out.println(" ◈ 보유 Gem(" + gem + ") ◈\n");

	        for(ItemDTO item : items) {

	            if(item.getItemType().equals("potion")) {

	                System.out.println("[" + no + "] " + item.getItemName());
	                System.out.println("효과 : HP +" + item.getItemEffectHp());
	                System.out.println("효과 : MP +" + item.getItemEffectMp());
	                System.out.println("가격 : " + item.getItemPriceBuy() + " Gem\n");

	                menuMap.put(no, item);
	                no++;
	            }
	        }

	        System.out.println("[0] 뒤로가기");
	        System.out.print("선택 ▶ ");

	        int select;
	        try {
	            select = InputUtil.changeInt(InputUtil.inputString());
	        } catch (GameException e) {
	            System.out.println(e.getMessage());
	            continue;
	        }

	        if(select == 0) return;

	        ItemDTO item = menuMap.get(select);
	        if(item == null) continue;

	        int qty;

	        while (true) {
	            System.out.print("구매 수량 ▶ ");

	            String input;

	            try {
	                input = InputUtil.inputString();
	            } catch (GameException e) {
	                System.out.println(e.getMessage());
	                continue;
	            }

	            if (!input.matches("-?\\d+")) {
	                System.out.println("숫자를 입력해주시기 바랍니다.");
	                continue;
	            }

	            try {
	                qty = InputUtil.changeInt(input);

	                if (qty <= 0) {
	                    System.out.println("수량 입력 오류");
	                    continue;
	                }

	                break;

	            } catch (GameException e) {
	                System.out.println("숫자 범위를 초과했습니다.");
	            }
	        }

	        System.out.println("\n▶ " + item.getItemName()
	                + qty +"개를 " + (item.getItemPriceBuy() * qty)
	                + " Gem에 구매하시겠습니까?");
	        System.out.print("[Y] YES   [N] NO ▶ ");

	        String yn;
	        try {
	            yn = InputUtil.inputString();
	        } catch (GameException e) {
	            System.out.println(e.getMessage());
	            continue;
	        }

	        if(yn.equalsIgnoreCase("Y")) {

	            try {
	                shopController.buyItem(item.getItemId(), qty);

	                int afterGem = LoginSession.getInstance()
	                        .getCurrentHero()
	                        .getHeroGem();

	                System.out.println("\n아이템 구매 완료");
	                System.out.println("현재 보유 Gem : " + afterGem);

	            } catch (GameException e) {
	                System.out.println(e.getMessage());
	            }
	        }
	    }
	}
	
	public static void equipmentMenu() {

	    while (true) {

	        int gem = LoginSession.getInstance()
	                .getCurrentHero()
	                .getHeroGem();

	        List<ItemDTO> items = shopController.showShop();

	        Map<Integer, ItemDTO> menuMap = new HashMap<>();
	        int no = 1;

	        System.out.println("\n──────────── WEAPON ────────────\n");
	        System.out.println(" ◈ 보유 Gem(" + gem + ") ◈\n");

	        for(ItemDTO item : items) {

	            if(item.getItemType().equals("weapon")) {

	                System.out.println("[" + no + "] " + item.getItemName());
	                System.out.println("공격력 : +" + item.getItemAtkBonus());
	                System.out.println("가격 : " + item.getItemPriceBuy() + " Gem\n");

	                menuMap.put(no, item);
	                no++;
	            }
	        }

	        System.out.println("\n──────────── ARMOR ─────────────\n");

	        for(ItemDTO item : items) {

	            if(item.getItemType().equals("armor")) {

	                System.out.println("[" + no + "] " + item.getItemName());
	                System.out.println("방어력 : +" + item.getItemDefBonus());
	                System.out.println("가격 : " + item.getItemPriceBuy() + " Gem\n");

	                menuMap.put(no, item);
	                no++;
	            }
	        }

	        System.out.println("[0] 뒤로가기");
	        System.out.print("선택 ▶ ");

	        int select;
	        try {
	            select = InputUtil.changeInt(InputUtil.inputString());
	        } catch (GameException e) {
	            System.out.println(e.getMessage());
	            continue;
	        }

	        if(select == 0) return;

	        ItemDTO item = menuMap.get(select);
	        if(item == null) continue;

	        int qty;

	        while (true) {
	            System.out.print("구매 수량 ▶ ");

	            String input;

	            try {
	                input = InputUtil.inputString();
	            } catch (GameException e) {
	                System.out.println(e.getMessage());
	                continue;
	            }

	            if (!input.matches("-?\\d+")) {
	                System.out.println("숫자를 입력해주시기 바랍니다.");
	                continue;
	            }

	            try {
	                qty = InputUtil.changeInt(input);

	                if (qty <= 0) {
	                    System.out.println("수량 입력 오류");
	                    continue;
	                }

	                break;

	            } catch (GameException e) {
	                System.out.println("숫자 범위를 초과했습니다.");
	            }
	        }

	        System.out.println("\n▶ " + item.getItemName()
	                + qty +"개를 " + (item.getItemPriceBuy() * qty)
	                + " Gem에 구매하시겠습니까?");
	        System.out.print("[Y] YES   [N] NO ▶ ");

	        String yn;
	        try {
	            yn = InputUtil.inputString();
	        } catch (GameException e) {
	            System.out.println(e.getMessage());
	            continue;
	        }

	        if(yn.equalsIgnoreCase("Y")) {

	            try {
	                shopController.buyItem(item.getItemId(), qty);

	                int afterGem = LoginSession.getInstance()
	                        .getCurrentHero()
	                        .getHeroGem();

	                System.out.println("\n아이템 구매 완료");
	                System.out.println("현재 보유 Gem : " + afterGem);

	            } catch (GameException e) {
	                System.out.println(e.getMessage());
	            }
	        }
	    }
	}
	
	public static void sellMenu() {

	    List<InventoryDTO> items = inventoryController.showInventory();

	    if (items == null || items.isEmpty()) {
	        System.out.println("판매 가능한 아이템이 없습니다.");
	        return;
	    }

	    int gem = LoginSession.getInstance()
	            .getCurrentHero()
	            .getHeroGem();

	    System.out.println("\n__________________________┌ SHOP SELL ┐__________________________\n");
	    System.out.println(" ◈ 보유 Gem(" + gem + ") ◈\n");

	    System.out.println("──────────────────────── INVENTORY ───────────────────────\n");

	    Map<Integer, InventoryDTO> menuMap = new HashMap<>();
	    int no = 1;

	    for (InventoryDTO inv : items) {

	        ItemDTO item = inv.getItem();

	        System.out.println("[ " + no + " ] " + item.getItemName() 
	        + "  (보유 : " + inv.getInventoryQuantity() + ")");

	        if(item.getItemType().equals("POTION")) {
	            System.out.println(" ▸ 타입 : 소모품");
	            System.out.println(" ▸ 효과 : HP +" + item.getItemEffectHp());
	        }
	        else if(item.getItemType().equals("WEAPON")) {
	            System.out.println(" ▸ 타입 : 무기");
	            System.out.println(" ▸ 효과 : 공격력 +" + item.getItemAtkBonus());
	        }
	        else if(item.getItemType().equals("ARMOR")) {
	            System.out.println(" ▸ 타입 : 갑옷");
	            System.out.println(" ▸ 효과 : 방어력 +" + item.getItemDefBonus());
	        }

	        System.out.println(" ▸ 판매가 : "
	                + item.getItemPriceSell()
	                + " Gem [판매]");
	        System.out.println();

	        menuMap.put(no, inv);
	        no++;
	    }

	    System.out.println("─────────────────────────────────────────────────────────");
	    System.out.println("[0] 뒤로가기");
	    System.out.print("선택 ▶ ");

	    int select;
	    try {
	    	select = InputUtil.changeInt(InputUtil.inputString());
	    } catch (GameException e) {
	    	System.out.println(e.getMessage());
	    	return;
	    }

	    if(select == 0) return;

	    InventoryDTO inv = menuMap.get(select);
	    if(inv == null) return;

	    ItemDTO item = inv.getItem();

	    System.out.println("\n▶ "
	            + item.getItemName()
	            + "(을)를 "
	            + item.getItemPriceSell()
	            + " Gem에 판매하시겠습니까?");
	    System.out.print("[Y] YES   [N] NO ▶ ");

	    String yn;
	    try {
	    	yn = InputUtil.inputString();
	    } catch (GameException e) {
	    	System.out.println(e.getMessage());
	    	return;
	    }

	    if(yn.equalsIgnoreCase("Y")) {

	        shopController.sellItem(item.getItemId());

	        int afterGem = LoginSession.getInstance()
	                .getCurrentHero()
	                .getHeroGem();

	        System.out.println("\n아이템 판매 완료");
	        System.out.println("현재 보유 Gem : " + afterGem);
	    }
	}

	public void start() {
		System.out.println(this);
	}
	

    private static final int WIDTH = 33;
    
	@Override
	public String toString() {

	    StringBuilder sb = new StringBuilder();

        int gem = LoginSession.getInstance()
                .getCurrentHero()
                .getHeroGem();

	    ConsoleUtils.printTitleBar("ITEM SHOP");

	    sb.append(String.format(" ◈ 보유 Gem(%d) ◈\n\n", gem));
        
	    sb.append(ConsoleUtils.center("┌─────────── MENU ───────────┐\n\n", WIDTH));
	    sb.append("   [1] 아이템 구매\n");
	    sb.append("    [2] 아이템 판매\n");
	    sb.append("    [0] 상점 나가기\n\n");
	    sb.append(ConsoleUtils.center("└────────────────────────────┘\n\n", WIDTH));

	    sb.append("선택 ▶ ");

	    return sb.toString();
	}
}