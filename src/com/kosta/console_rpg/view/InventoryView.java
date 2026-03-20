package com.kosta.console_rpg.view;

import java.util.List;

import com.kosta.console_rpg.controller.InventoryController;
import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.InventoryDTO;
import com.kosta.console_rpg.model.dto.ItemDTO;
import com.kosta.console_rpg.model.service.InventoryService;
import com.kosta.console_rpg.session.LoginSession;
import com.kosta.console_rpg.util.ConsoleUtils;
import com.kosta.console_rpg.util.InputUtil;

public class InventoryView {

    private static InventoryController controller = new InventoryController();

    public static void start() {
        while (true) {
            System.out.println(render());

            System.out.print("선택 ▶ ");

            String input;
            try {
                input = InputUtil.inputString().trim();
            } catch (GameException e) {
                System.out.println(e.getMessage());
                continue;
            }

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

        var hero = LoginSession.getInstance().getCurrentHero();

            sb.append("\n");
            ConsoleUtils.printTitleBar("INVENTORY");

            sb.append(String.format(" Hero : %s\n", hero.getHeroName()));
            sb.append(String.format(" 보유 Gem : %d\n\n", hero.getHeroGem()));

        if (list == null || list.isEmpty()) {
            sb.append("인벤토리가 비어있습니다.\n\n");
            sb.append("────────────────── MENU ──────────────────\n");
            sb.append("[0] 뒤로가기\n\n");
            return sb.toString();
        }

        // ===== 장착 장비 =====
        sb.append("-------------------- [ EQUIPPED ] --------------------\n\n");

        boolean hasEquipped = false;

        for (InventoryDTO inv : list) {
            if (inv.getInventoryIsEquipped() == 1) {
                hasEquipped = true;
                ItemDTO item = inv.getItem();

                if (item.getItemType().equals("weapon")) {
                    sb.append(String.format("- 무기 : %s (공격력 +%d)\n",
                            item.getItemName(), item.getItemAtkBonus()));
                }

                if (item.getItemType().equals("armor")) {
                    sb.append(String.format("- 갑옷 : %s (방어력 +%d)\n",
                            item.getItemName(), item.getItemDefBonus()));
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

            if (inv.getInventoryIsEquipped() == 1) { 
            	sb.append("[장착]"); 
            } else if (item.getItemType().equals("potion")) { 
            	sb.append(""); 
            } else { 
            	sb.append("[장착 가능]"); 
            }

            sb.append("\n");

            sb.append(String.format("  타입 : %s\n", item.getItemType()));

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

        // ===== 전체 해제 =====
        if (input.equalsIgnoreCase("A")) {
            InventoryService service = new InventoryService();
            var hero = LoginSession.getInstance().getCurrentHero();

            boolean hasEquipped = false;

            for (InventoryDTO inv : list) {
                if (inv.getInventoryIsEquipped() == 1) {
                    hasEquipped = true;
                    try {
                        service.unequipItem(hero.getHeroId(), inv.getItem().getItemId());
                    } catch (Exception e) {
                        System.out.println("해제 실패: " + e.getMessage());
                    }
                }
            }

            // 🔥 여기 수정 핵심
            if (!hasEquipped) {
                System.out.println("장착 중인 아이템이 없습니다.");
            } else {
                System.out.println("모든 장착 아이템을 해제했습니다.");
            }

            return;
        }

        int num;

        try {
            num = InputUtil.changeInt(input);
        } catch (GameException e) {
            System.out.println(e.getMessage());
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
            // 🔥 포션 관련 로직 제거됨
            controller.equipItem(item.getItemId());
        } catch (Exception e) {
            System.out.println("실행 실패: " + e.getMessage());
        }
    }
}