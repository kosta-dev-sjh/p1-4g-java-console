package com.kosta.console_rpg.view;

import java.util.List;
import java.util.Scanner;
import com.kosta.console_rpg.controller.SkillController;
import com.kosta.console_rpg.model.dto.HeroSkillDTO;

/**
 * 게임 히어로 정보 뷰
 *
 * 작성자     : 이진주
 * 생성일     : 2026.03.16
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class skillView {

    private Scanner sc = new Scanner(System.in);
    private SkillController skillController = new SkillController();
    
	public static void main(String[] args) {
		skillView view = new skillView();
        view.start();
    }


    public void start() {
    	while (true) {
            System.out.println(this);

            String input = sc.nextLine().toUpperCase();

            if (input.equals("S")) {
                skillMenu(); // 👉 스킬 강화 진입
            } else if (input.equals("0")) {
                System.out.println("종료");
                return;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
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
	    sb.append(String.format(" [1] 레벨  : %d\n", level));
	    sb.append(String.format(" [2] HP   : %d / %d\n", hp, maxHp));
	    sb.append(String.format(" [3] MP   : %d / %d\n", mp, maxMp));
	    sb.append(String.format(" [4] 공격력 : %d\n", attack));
	    sb.append(String.format(" [5] 방어력 : %d\n", defense));
	    sb.append(String.format(" [6] 경험치 : %d / %d\n\n", exp, maxExp));

	    sb.append("_________________ SKILLS ___________________\n\n");

	    sb.append(String.format(" [1] %s\n", skill1));
	    sb.append(String.format("    ▸ 레벨 : %d\n", skill1Lv));
	    sb.append(String.format("    ▸ 효과 : %s\n", skill1Desc));
	    sb.append(String.format("    ▸ MP 소모 : %d\n\n", skill1Mp));
	    sb.append(String.format(" [2] %s\n", skill2));
	    sb.append(String.format("    ▸ 레벨 : %d\n", skill2Lv));
	    sb.append(String.format("    ▸ 효과 : %s\n", skill2Desc));
	    sb.append(String.format("    ▸ MP 소모 : %d\n\n", skill2Mp));
	    sb.append(String.format(" [3] %s\n", skill3));
	    sb.append(String.format("    ▸ 레벨 : %d\n", skill3Lv));
	    sb.append(String.format("    ▸ 효과 : %s\n", skill3Desc));
	    sb.append(String.format("    ▸ MP 소모 : %d\n\n", skill3Mp));

	    sb.append("────────────────── MENU ──────────────────\n\n");
	    sb.append(" [S] 스킬 강화\n");
	    sb.append(" [0] 뒤로가기\n\n");
	    sb.append("선택 ▶ ");

	    return sb.toString();
	}
	
	
	public void skillMenu() {
		int heroId = 1;

	    while (true) {
	        System.out.println("\n===== [ 스킬 메뉴 ] =====");

	        List<HeroSkillDTO> skillList = skillController.selectHeroSkills();

	        if (skillList == null || skillList.isEmpty()) {
	            System.out.println("보유한 스킬이 없습니다.");
	            return;
	        }

	        // 1️. 스킬 목록 출력
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

	        String input = sc.nextLine();
	        int choice;
	        try {
                choice = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("숫자만 입력하세요.");
                continue;
            }

	        // 2️. 입력 처리
	        if (choice == 0) {
	            return;
	        }

	        if (choice < 1 || choice > skillList.size()) {
	            System.out.println("잘못된 입력입니다.");
	            continue;
	        }

	        // 3️. 선택한 스킬
	        HeroSkillDTO selected = skillList.get(choice - 1);
	        int skillId = selected.getSkill().getSkillId();

	        // 4️. 강화 실행
	        try {
                // 👉 heroId 같이 넘기도록 수정
                skillController.upgradeHeroSkill(skillId);
                System.out.println("스킬이 강화되었습니다.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

	    }
	}

}
