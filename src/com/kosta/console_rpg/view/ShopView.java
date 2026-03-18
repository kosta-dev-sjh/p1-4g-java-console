package com.kosta.console_rpg.view;

import java.util.List;
import java.util.Scanner;

import com.kosta.console_rpg.controller.InventoryController;
import com.kosta.console_rpg.controller.ShopController;
import com.kosta.console_rpg.model.dto.InventoryDTO;
import com.kosta.console_rpg.model.dto.ItemDTO;
import com.kosta.console_rpg.session.LoginSession;

/**
 * 게임 샵 정보 뷰
 *
 * 작성자     : 이진주
 * 생성일     : 2026.03.16
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class ShopView {
	private static ShopController shopController = new ShopController();
	private static	InventoryController inventoryController = new InventoryController();
	
	public static void showShop() {
		 Scanner sc = new Scanner(System.in);
		 
		 // 보유 젬 가져오기 
		 int gem = LoginSession.getInstance().getCurrentHero().getHeroGem();
		 
		    while (true) {
		    	System.out.println("보유 Gem : " + gem);
		        System.out.println("_________________________________┌ ITEM SHOP ┐_________________________________\n");
		        System.out.println("    [1] 아이템 구매");
		        System.out.println("    [2] 아이템 판매");
		        System.out.println("    [3] 소지 Gem 확인");
		        System.out.println("    [0] 상점 나가기");
		        System.out.print("\n선택 ▶ ");

		        int menu = sc.nextInt();

		        switch (menu) {
		            case 1:
		                buyMenu();
		                break;
		            case 2:
		                sellMenu();
		                break;
		            case 3:
		                System.out.println("보유 Gem : 128");
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

	    Scanner sc = new Scanner(System.in);

	    List<ItemDTO> items = shopController.showShop();

	    if (items == null || items.isEmpty()) {
	        System.out.println("구매 가능한 아이템이 없습니다.");
	        return;
	    }

	    System.out.println("\n=========== 구매 가능 아이템 ===========");

	    for (ItemDTO item : items) {
	        System.out.println(item.getItemId() + ". "
	                + item.getItemName()
	                + " (가격 : " + item.getItemPriceBuy() + ")");
	    }

	    System.out.println("0. 뒤로가기");
	    System.out.print("구매할 아이템 선택 ▶ ");

	    int itemId = sc.nextInt();

	    if (itemId == 0) return;
	    
	    shopController.buyItem(itemId);
	}
	
	public static void sellMenu() {
		 Scanner sc = new Scanner(System.in);

		 	List<InventoryDTO> items = inventoryController.showInventory();

		    if (items == null || items.isEmpty()) {
		        System.out.println("판매 가능한 아이템이 없습니다.");
		        return;
		    }

		    System.out.println("\n=========== 판매 가능 아이템 ===========");

		    for (InventoryDTO item : items) {
		        System.out.println(item.getItemId() + ". "
		                + item.getItem().getItemName()
		                + " (가격 : " + item.getItem().getItemPirceSell() + ")"
		        		+ " (수량 : " + item.getInventoryQuantity() + ")");	
		    }

		    System.out.println("0. 뒤로가기");
		    System.out.print("판매할 아이템 선택 ▶ ");

		    int itemId = sc.nextInt();

		    if (itemId == 0) return;
		    
		    shopController.sellItem(itemId);
	}
	
	public static void main(String[] args) {
		ShopView view = new ShopView();
		view.start();
		view.showShop();
	
	}
	public void start() {
		System.out.println(this);
	}
	

    private static final int WIDTH = 33;
    
    private String center(String text, int width) {
        int padding = width - text.length();
        int left = padding / 2;
        int right = padding - left;

        return " ".repeat(left) + text + " ".repeat(right);
    }
    
//	@Override
//	public String toString() {
//
//	    StringBuilder sb = new StringBuilder();
//
//	    int gem = 128; // 실제로는 필드나 DTO에서 가져오면 된다.
//
//	    sb.append("_________________________________┌ ITEM SHOP ┐_________________________________\n\n");
//
//	    sb.append(String.format(" ◈ 보유 Gem(%d) ◈\n\n", gem));
//
//        sb.append(String.format("\n", center("┌─────────── MENU ───────────┐", WIDTH - 2)));
//        
//	    sb.append("┌─────────── MENU ───────────┐\n\n");
//
//	    sb.append("    [1] 아이템 구매\n");
//	    sb.append("    [2] 아이템 판매\n");
//	    sb.append("    [3] 소지 Gem 확인\n");
//	    sb.append("    [0] 상점 나가기\n\n");
//
//	    sb.append("└────────────────────────────┘\n\n");
//
//	    sb.append("선택 ▶ ");
//
//	    return sb.toString();
//	}
}
