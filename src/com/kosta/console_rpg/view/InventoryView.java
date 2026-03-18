package com.kosta.console_rpg.view;

/**
 * 게임 메인화면 뷰
 *
 * 작성자     : 이진주
 * 생성일     : 2026.03.16
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class InventoryView {
	public static void main(String[] args) {
		InventoryView view = new InventoryView();
        view.start();
    }

    public void start() {
        System.out.println(this);
    }
    
    @Override
	public String toString() {

	    StringBuilder sb = new StringBuilder();

	    // 예시 데이터 (실제로는 필드로 두면 된다)
	    String name = "히어로짱";
	    int gem = 128;

	    int level = 7;
	    int hp = 95;
	    int maxHp = 120;
	    int mp = 40;
	    int maxMp = 60;
	    int attack = 18;
	    int defense = 12;
	    int exp = 140;
	    int maxExp = 200;

	    String skill1 = "Fire Slash";
	    int skill1Lv = 2;
	    String skill1Desc = "적에게 강한 화염 공격";
	    int skill1Mp = 10;

	    String skill2 = "Guard Break";
	    int skill2Lv = 1;
	    String skill2Desc = "적 방어력 감소 공격";
	    int skill2Mp = 8;

	    String skill3 = "Healing Light";
	    int skill3Lv = 1;
	    String skill3Desc = "HP 회복";
	    int skill3Mp = 12;

	    sb.append("____________________________┌ HERO STATUS ┐_______________________________\n\n");
	    sb.append(String.format(" Name : %s\n", name));
	    sb.append(String.format(" 보유 Gem : %d\n\n", gem));

	    sb.append("──────────────── BASIC INFO ─────────────────\n\n");
	    sb.append(String.format(" [1] 아아템 구매  : %d\n", level));
	    sb.append(String.format(" [2] 아이템 판매  : %d / %d\n", hp, maxHp));
	    sb.append(String.format(" [3] MP   : %d / %d\n", mp, maxMp));
	    sb.append(String.format(" [4] 공격력 : %d\n", attack));
	    sb.append(String.format(" [5] 방어력 : %d\n", defense));
	    sb.append(String.format(" [6] 경험치 : %d / %d\n\n", exp, maxExp));

	    sb.append("_________________ SKILLS ___________________\n\n");

	    sb.append(String.format(" [1] %s\n", skill1));
	    sb.append("선택 ▶ ");

	    return sb.toString();
	}
    
}

//import java.util.List;
//import java.util.Scanner;
//
//import com.kosta.console_rpg.controller.InventoryController;
//import com.kosta.console_rpg.model.dto.InventoryDTO;
//import com.kosta.console_rpg.model.dto.ItemDTO;
//
//public class InventoryView {
//	
//	private static	InventoryController inventoryController = new InventoryController();
//	
//	public static void showInventory() {
//		
//		List<InventoryDTO> list = inventoryController.showInventory();
//		
//		for(InventoryDTO inv : list) {
//			System.out.println("-------------------");
//		    System.out.println("인벤토리ID: " + inv.getInventoryId());
//		    System.out.println("아이템이름: " + inv.getItem().getItemName());
//		    System.out.println("아이템타입: " + inv.getItem().getItemType());
//		    System.out.println("아이템등급: " + inv.getItem().getItemGrade());
//		    System.out.println("수량: " + inv.getInventoryQuantity());
//		    System.out.println("-------------------");
//		}
//		
//	}
//	
//	public static void equipMenu(int itemId) {
//		/*
//		 * 사용자가 인벤토리에서 아이템 목록을 보고 장착할 아이템 번호 입력
//		 * 그 아이템 번호에 해당하는 아이템 아이디 받아오기
//		 */
//		inventoryController.equipItem(itemId);
//		
//		List<InventoryDTO> list = inventoryController.showInventory();
//		
//		for(InventoryDTO inv : list) {
//			System.out.println("-------------------");
//		    System.out.println("아이템ID: " + inv.getItemId());
//		    System.out.println("아이템이름: " + inv.getItem().getItemName());
//		    System.out.println("아이템타입: " + inv.getItem().getItemType());
//		    System.out.println("아이템등급: " + inv.getItem().getItemGrade());
//		    System.out.println("장착 여부: " + inv.getInventoryIsEquipped());
//		    System.out.println("-------------------");
//		}
//		
//
//		
//	}
//	
//	public static void potionMenu(int inventoryId) {
//		/*
//		 * 사용할 포션 번호를 입력 받아서 그 포션 아이템의 inventory_id 값 받아와야함
//		 */
//		List<InventoryDTO> list = inventoryController.showInventory();
//		
//		for(InventoryDTO inv : list) {
//			if (inv.getItem().getItemType().equals("potion")) {
//				
//			
//				System.out.println("-------------------");
//			    System.out.println("인벤토리ID: " + inv.getInventoryId());
//			    System.out.println("아이템이름: " + inv.getItem().getItemName());
//			    System.out.println("아이템타입: " + inv.getItem().getItemType());
//			    System.out.println("체력회복: " + inv.getItem().getItemEffectHp());
//			    System.out.println("마나회복: " + inv.getItem().getItemEffectMp());
//			    System.out.println("수량: " + inv.getInventoryQuantity());
//			    System.out.println("-------------------");
//			}
//		}
//		
//		inventoryController.usePotion(inventoryId);
//
//       
//	}
//}
