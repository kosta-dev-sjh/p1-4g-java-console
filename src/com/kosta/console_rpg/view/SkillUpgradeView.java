package com.kosta.console_rpg.view;

import com.kosta.console_rpg.controller.SkillController;
import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.HeroSkillDTO;
import com.kosta.console_rpg.model.dto.SkillDTO;
import com.kosta.console_rpg.session.LoginSession;
import com.kosta.console_rpg.util.InputUtil;

import java.util.List;

/**
 * 게임 히어로 정보 뷰
 *
 * 작성자     : 이진주
 * 생성일     : 2026.03.16
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class SkillUpgradeView {

    public void start() {
        System.out.println(this);
		System.out.print("선택 ▶ ");
		try {
			String menu = InputUtil.inputString();
			switch (menu) {
				case "0" -> {
					break;
				}
				default -> System.out.println("잘못된 입력입니다.");
			}
		} catch (GameException e) {
			System.out.println(e.getMessage());
		}
    }
	public void upgrade() {
		System.out.println("──────────────── SKILL UPGRADE ─────────────────");
		System.out.println("강화할 스킬의 숫자를 입력해주세요");
		try {
			int menu = InputUtil.inputInt();
			switch (menu) {
				//case 1 ->
				//case 2 ->
				//case 3 ->
				default -> System.out.println("잘못된 입력입니다.");
			}
		} catch (GameException e) {
			System.out.println(e.getMessage());
		}
	}
    
	@Override
	public String toString() {
		HeroDTO hero = LoginSession.getInstance().getCurrentHero();
		SkillController skillController = new SkillController();

		List<HeroSkillDTO> skillList = skillController.selectHeroSkills();
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
	    sb.append(String.format(" 히어로 : %s\n", hero.getHeroName()));
	    sb.append(String.format(" 보유 Gem : %d\n\n", hero.getHeroGem()));


		sb.append("──────────────── SKILL INFO ─────────────────\n\n");

		for (int i = 0; i < 3; i++) {
			HeroSkillDTO skill = skillList.get(i);
			sb.append(String.format(" [%d] %s\n", i+1,skillList.get(i).getSkill().getSkillName()));
			sb.append(String.format("    ▸ 레벨 : %d\n", skillList.get(i).getSkillLevel()));
			sb.append(String.format("    ▸ 데미지 : %s\n", skillList.get(i).getSkill().getSkillDamage()));
			sb.append(String.format("    ▸ MP 소모 : %d\n", skillList.get(i).getSkill().getSkillMpCost()));
			if (skill.getSkillLevel() <= 2) {
				int upgradeCost = skill.getSkill().getSkillUpgradeCost() * skill.getSkillLevel();
				sb.append(String.format("    ▸ 스킬 강화 비용: %d\n",  upgradeCost));
			} else if (skill.getSkillLevel() == 3) {
				sb.append(String.format("스킬: 강화 불가\n"));
			}
		}

	    sb.append("────────────────── MENU ──────────────────\n\n");
		sb.append(" [U] Upgrade \n");
	    sb.append(" [0] Main\n\n");
	    //sb.append("선택 ▶ ");



        return sb.toString();
	}
	

	public static void createHeroView() {
		
	}
	
	public static void showHeroInfo() {

    }
	



}
