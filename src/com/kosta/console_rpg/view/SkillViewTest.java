package com.kosta.console_rpg.view;

import java.util.List;
import java.util.Scanner;

import com.kosta.console_rpg.controller.SkillController;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.HeroSkillDTO;
import com.kosta.console_rpg.session.LoginSession;

/**
 * 게임 히어로 정보 뷰
 *
 * 작성자     : 이진주
 * 생성일     : 2026.03.16
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class SkillViewTest {

    // ✅ Scanner 하나만 사용
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        SkillViewTest view = new SkillViewTest();
        view.start();
    }

    public void start() {

        // ✅ 올바른 세션 설정 (HeroDTO 사용)
        HeroDTO hero = new HeroDTO(0, 0, null, 0, 0, 0, 0, 0, 0, 0, null, 0);
        hero.setHeroId(1); // 테스트용

        LoginSession.getInstance().setCurrentHero(hero);

        while (true) {
            System.out.println(this);

            String input = sc.nextLine();

            if (input.equalsIgnoreCase("S")) {
                skillMenu();
            } else if (input.equals("0")) {
                System.out.println("게임 종료");
                break;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("____________________________┌ HERO STATUS ┐_______________________________\n\n");
        sb.append(" Name : 히어로짱\n");
        sb.append(" 보유 Gem : 128\n\n");

        sb.append("──────────────── BASIC INFO ─────────────────\n\n");
        sb.append(" [1] 레벨  : 7\n");
        sb.append(" [2] HP   : 95 / 120\n");
        sb.append(" [3] MP   : 40 / 60\n");
        sb.append(" [4] 공격력 : 18\n");
        sb.append(" [5] 방어력 : 12\n");
        sb.append(" [6] 경험치 : 140 / 200\n\n");

        sb.append("_________________ SKILLS ___________________\n\n");

        sb.append(" [1] Fire Slash\n");
        sb.append("    ▸ 레벨 : 2\n");
        sb.append("    ▸ 효과 : 적에게 강한 화염 공격\n");
        sb.append("    ▸ MP 소모 : 10\n\n");

        sb.append(" [2] Guard Break\n");
        sb.append("    ▸ 레벨 : 1\n");
        sb.append("    ▸ 효과 : 적 방어력 감소 공격\n");
        sb.append("    ▸ MP 소모 : 8\n\n");

        sb.append(" [3] Healing Light\n");
        sb.append("    ▸ 레벨 : 1\n");
        sb.append("    ▸ 효과 : HP 회복\n");
        sb.append("    ▸ MP 소모 : 12\n\n");

        sb.append("────────────────── MENU ──────────────────\n\n");
        sb.append(" [S] 스킬 강화\n");
        sb.append(" [0] 뒤로가기\n\n");
        sb.append("선택 ▶ ");

        return sb.toString();
    }

    public static void skillMenu() {

        SkillController skillController = new SkillController();

        while (true) {
            System.out.println("\n===== [ 스킬 메뉴 ] =====");

            List<HeroSkillDTO> skillList = skillController.selectHeroSkills();

            if (skillList == null || skillList.isEmpty()) {
                System.out.println("보유한 스킬이 없습니다.");
                return;
            }

            // 1️⃣ 스킬 목록 출력
            for (int i = 0; i < skillList.size(); i++) {
                HeroSkillDTO hs = skillList.get(i);

                System.out.printf("%d. %s (Lv.%d / Max:%d) [MP:%d]\n",
                        i + 1,
                        hs.getSkill().getSkillName(),
                        hs.getSkillLevel(),
                        hs.getSkill().getSkillMaxLevel(),
                        hs.getSkill().getSkillMpCost()
                );
            }

            System.out.println("0. 뒤로가기");
            System.out.print("선택 > ");

            // ✅ nextLine + parseInt 방식으로 통일
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력하세요.");
                continue;
            }

            // 2️⃣ 입력 처리
            if (choice == 0) {
                return;
            }

            if (choice < 1 || choice > skillList.size()) {
                System.out.println("잘못된 입력입니다.");
                continue;
            }

            // 3️⃣ 선택한 스킬
            HeroSkillDTO selected = skillList.get(choice - 1);
            int skillId = selected.getSkill().getSkillId();

            // 4️⃣ 강화 실행
            skillController.upgradeHeroSkill(skillId);

            System.out.println("스킬이 강화되었습니다.");
        }
    }
}
