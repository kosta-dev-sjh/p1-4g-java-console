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
