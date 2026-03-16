package com.kosta.console_rpg.view;

/**
 * 게임 히어로 정보 뷰
 *
 * 작성자     : 이진주
 * 생성일     : 2026.03.16
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class HeroView {
	
	public static void main(String[] args) {
		HeroView view = new HeroView();
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
	

	public static void createHeroView() {
		
	}
	
	public static void showHeroInfo() {

    }
	
	public static void skillMenu() {
		
	}

}
