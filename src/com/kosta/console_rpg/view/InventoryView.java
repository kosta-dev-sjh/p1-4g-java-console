package com.kosta.console_rpg.view;

import java.util.List;
import java.util.Scanner;

import com.kosta.console_rpg.controller.InventoryController;
import com.kosta.console_rpg.model.dto.InventoryDTO;
import com.kosta.console_rpg.model.dto.ItemDTO;
import com.kosta.console_rpg.model.service.InventoryService;
import com.kosta.console_rpg.session.LoginSession;

public class InventoryView {

    private static InventoryController controller = new InventoryController();
    private static Scanner sc = new Scanner(System.in);

    public static void start() {
        while (true) {
            System.out.println(render());

            System.out.print("선택 ▶ ");
            String input = sc.nextLine().trim();

            if (input.equals("0")) {
                System.out.println("뒤로가기");
                break;
            }

            handleInput(input);
        }
    }

    private static String render() {

        StringBuilder sb = new StringBuilder();

        List<InventoryDTO> list = controller.showInventory();
        
        if (list == null || list.isEmpty()) {

            var hero = LoginSession.getInstance().getCurrentHero();

            sb.append("\n");
            sb.append("-------------------- [ INVENTORY ] --------------------\n\n");

            sb.append(String.format("Hero : %s\n", hero.getHeroName()));
            sb.append(String.format("보유 Gem : %d\n\n", hero.getHeroGem()));

            sb.append("인벤토리가 비어있습니다.\n\n");

            sb.append("---------------------- MENU ----------------------\n");
            sb.append("[0] 뒤로가기\n\n");

            return sb.toString(); 
        }

        // ===== 로그인 유저 정보 =====
        var hero = LoginSession.getInstance().getCurrentHero();
        String name = hero.getHeroName();
        int gem = hero.getHeroGem();

        sb.append("\n");
        sb.append("-------------------- [ INVENTORY ] --------------------\n\n");
        
        sb.append("\n");
        sb.append(String.format("Hero : %s\n", name));
        sb.append(String.format("보유 Gem : %d\n\n", gem));

        // ===== 장착 장비 =====
        sb.append("-------------------- [ EQUIPPED ] --------------------\n\n");
        boolean hasEquipped = false;
        
        for (InventoryDTO inv : list) {
            if (inv.getInventoryIsEquipped() == 1) {
            	hasEquipped = true;
                ItemDTO item = inv.getItem();

                if (item.getItemType().equals("weapon")) {
                    sb.append(String.format("- 무기 : %s (공격력 +%d)\n",
                            item.getItemName(),
                            item.getItemAtkBonus()));
                }

                if (item.getItemType().equals("armor")) {
                    sb.append(String.format("- 갑옷 : %s (방어력 +%d)\n",
                            item.getItemName(),
                            item.getItemDefBonus()));
                }
            }
        }
        if (!hasEquipped) {
            sb.append("장착 중인 아이템이 없습니다.\n");
        }

        sb.append("\n");

        // ===== 아이템 목록 =====
        sb.append("-------------------- [ ITEMS ] --------------------\n\n");

        int index = 1;

        for (InventoryDTO inv : list) {

            ItemDTO item = inv.getItem();

            sb.append(String.format("[%d] %s ", index, item.getItemName()));

            // 상태 표시
            if (inv.getInventoryIsEquipped() == 1) {
                sb.append("[장착]");
            } else if (item.getItemType().equals("potion")) {
                sb.append("[사용]");
            } else {
                sb.append("[장착 가능]");
            }

            sb.append("\n");

            // 타입
            sb.append(String.format("  타입 : %s\n", item.getItemType()));

            // 효과
            if (item.getItemEffectHp() > 0) {
                sb.append(String.format("  효과 : HP +%d 회복\n", item.getItemEffectHp()));
            }
            if (item.getItemEffectMp() > 0) {
                sb.append(String.format("  효과 : MP +%d 회복\n", item.getItemEffectMp()));
            }
            if (item.getItemAtkBonus() > 0) {
                sb.append(String.format("  효과 : 공격력 +%d\n", item.getItemAtkBonus()));
            }
            if (item.getItemDefBonus() > 0) {
                sb.append(String.format("  효과 : 방어력 +%d\n", item.getItemDefBonus()));
            }

            sb.append(String.format("  보유 : %d\n\n", inv.getInventoryQuantity()));

            index++;
        }

        // ===== 메뉴 =====
        sb.append("---------------------- MENU ----------------------\n");
        sb.append("[A] 모든 아이템 장착 해제\n");
        sb.append("[0] 뒤로가기\n\n");

        return sb.toString();
    }

    private static void handleInput(String input) {

        List<InventoryDTO> list = controller.showInventory();
        
        if (list == null || list.isEmpty()) {
            System.out.println("인벤토리가 비어있습니다.");
            return;
        }

        // 모든 장비 해제
        if (input.equalsIgnoreCase("A")) {
        	InventoryService service = new InventoryService();
            var hero = LoginSession.getInstance().getCurrentHero();
            int heroId = hero.getHeroId();

            for (InventoryDTO inv : list) {
                if (inv.getInventoryIsEquipped() == 1) {
                    try {
                    	service.unequipItem(heroId, inv.getItem().getItemId());
                    } catch (Exception e) {
                        System.out.println("해제 실패: " + e.getMessage());
                    }
                }
            }

            System.out.println("모든 장착 아이템을 해제했습니다.");
            return;
        }

        // 숫자 입력 처리
        int num;
        try {
            num = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("잘못된 입력입니다.");
            return;
        }

        if (num == 0) {
            System.out.println("뒤로가기");
            return;
        }

        if (num < 1 || num > list.size()) {
            System.out.println("잘못된 입력입니다.");
            return;
        }

        InventoryDTO selected = list.get(num - 1);
        ItemDTO item = selected.getItem();

        try {
            if (item.getItemType().equals("potion")) {
                // controller.usePotion(selected.getInventoryId());
                System.out.println("포션 사용 완료!");
            } else {
                controller.equipItem(item.getItemId());
            }
        } catch (Exception e) {
            System.out.println("실행 실패: " + e.getMessage());
        }
    }
}