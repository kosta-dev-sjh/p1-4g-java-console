package com.kosta.console_rpg.view;

/**
 * 게임 샵 정보 뷰
 *
 * 작성자     : 이진주
 * 생성일     : 2026.03.16
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class ShopView {
	
	public static void main(String[] args) {
		ShopView view = new ShopView();
		view.start();
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
    
	@Override
	public String toString() {

	    StringBuilder sb = new StringBuilder();

	    int gem = 128; // 실제로는 필드나 DTO에서 가져오면 된다.

	    sb.append("_________________________________┌ ITEM SHOP ┐_________________________________\n\n");

	    sb.append(String.format(" ◈ 보유 Gem(%d) ◈\n\n", gem));

        sb.append(String.format("\n", center("┌─────────── MENU ───────────┐", WIDTH - 2)));
        
	    sb.append("┌─────────── MENU ───────────┐\n\n");

	    sb.append("    [1] 아이템 구매\n");
	    sb.append("    [2] 아이템 판매\n");
	    sb.append("    [3] 소지 Gem 확인\n");
	    sb.append("    [0] 상점 나가기\n\n");

	    sb.append("└────────────────────────────┘\n\n");

	    sb.append("선택 ▶ ");

	    return sb.toString();
	}
}
