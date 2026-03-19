package com.kosta.console_rpg.view;

import java.util.List;
import java.util.Scanner;
import com.kosta.console_rpg.controller.SkillController;
import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.HeroSkillDTO;
import com.kosta.console_rpg.model.dto.SkillDTO;
import com.kosta.console_rpg.model.service.SkillService;
import com.kosta.console_rpg.session.LoginSession;
import com.kosta.console_rpg.util.InputUtil;

/**
 * 게임 히어로 정보 뷰
 *
 * 작성자     : 이진주
 * 생성일     : 2026.03.16
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class HeroView {

//	public static void main(String[] args) {
//
//		HeroView view = new HeroView();
//        view.start();
//    }


    public void start() {
		SkillUpgradeView sv = new SkillUpgradeView();
		System.out.println(this);
		System.out.print("선택 ▶ ");
		try {
			String menu = InputUtil.inputString();
			switch (menu) {
				case "s" -> sv.start();
				case "S" -> sv.start();
				case "0" -> {  // 뒤로가기: 아무것도 안하고 종료
					break;
				}
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

	    sb.append("──────────────── BASIC INFO ─────────────────\n\n");
	    sb.append(String.format(" [1] 레벨  : %d\n", hero.getHeroLevel()));
	    sb.append(String.format(" [2] HP   : %d / %d\n", hero.getHeroHp(), hero.getHeroHp()));
	    sb.append(String.format(" [3] MP   : %d / %d\n", hero.getHeroMp(), hero.getHeroMp()));
	    sb.append(String.format(" [4] 공격력 : %d\n", hero.getHeroAttack()));
	    sb.append(String.format(" [5] 방어력 : %d\n", hero.getHeroDefense()));
	    sb.append(String.format(" [6] 경험치 : %d / %d\n\n", hero.getHeroExp(), hero.getHeroLevel()*100));	//max경험치

		sb.append("──────────────── SKILL INFO ─────────────────\n\n");

	    sb.append(String.format(" [1] %s\n", skillList.get(0).getSkill().getSkillName()));
	    sb.append(String.format("    ▸ 레벨 : %d\n", skillList.get(0).getSkillLevel()));
	    sb.append(String.format("    ▸ 데미지 : %s\n", skillList.get(0).getSkill().getSkillDamage()));
	    sb.append(String.format("    ▸ MP 소모 : %d\n\n", skillList.get(0).getCalculatedMpCost()));
	    sb.append(String.format(" [2] %s\n", skillList.get(1).getSkill().getSkillName()));
		sb.append(String.format("    ▸ 레벨 : %d\n", skillList.get(1).getSkillLevel()));
		sb.append(String.format("    ▸ 데미지 : %s\n", skillList.get(1).getSkill().getSkillDamage()));
		sb.append(String.format("    ▸ MP 소모 : %d\n\n", skillList.get(1).getCalculatedMpCost()));
	    sb.append(String.format(" [3] %s\n",skillList.get(2).getSkill().getSkillName()));
		sb.append(String.format("    ▸ 레벨 : %d\n", skillList.get(2).getSkillLevel()));
		sb.append(String.format("    ▸ 데미지 : %s\n", skillList.get(2).getSkill().getSkillDamage()));
		sb.append(String.format("    ▸ MP 소모 : %d\n\n", skillList.get(2).getCalculatedMpCost()));

	    sb.append("────────────────── MENU ──────────────────\n\n");
	    sb.append(" [S] 스킬 강화\n");
	    sb.append(" [0] 뒤로가기\n\n");
	    //sb.append("선택 ▶ ");

        return sb.toString();
	}
}
