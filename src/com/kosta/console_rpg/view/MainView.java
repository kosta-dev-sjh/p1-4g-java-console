package com.kosta.console_rpg.view;

/**
 * 게임 메인화면 뷰
 *
 * 작성자     : 이진주
 * 생성일     : 2026.03.16
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class MainView {
	
	public static void main(String[] args) {
		MainView view = new MainView();
        view.start();
    }

    public void start() {
    	System.out.println(this);
    }
    
    private String name = "WARRIOR";
    private String heroClass = "HERO";
    private int hp = 50;
    private int mp = 70;
    private int maxHp = 120;
    private int maxMp = 70;
    private int exp = 80;
    private int maxExp = 140;
    private int gem = 1200;

    private static final int WIDTH = 66;
    
    private String center(String text, int width) {
        int padding = width - text.length();
        int left = padding / 2;
        int right = padding - left;

        return " ".repeat(left) + text + " ".repeat(right);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("____________________________┌ HERO INFO ┐____________________________\n");
        sb.append(String.format("║ %-"+WIDTH+"s ║\n", ""));
        sb.append(String.format("║  Name  : %-"+(WIDTH-9)+"s ║\n", name));
        sb.append(String.format("║  Class : %-"+(WIDTH-9)+"s ║\n", heroClass));
        sb.append(String.format("║ %-"+WIDTH+"s ║\n", ""));
        sb.append(String.format("║  HP  : %3d / %-"+(WIDTH-13)+"d ║\n", hp, maxHp));
        sb.append(String.format("║  MP  : %3d / %-"+(WIDTH-13)+"d ║\n", mp, maxMp));
        String expBar = "██░░░░░░░░░░░░░░░";
        sb.append(String.format("║  EXP : %3d / %3d  %-"+(WIDTH-18)+"s ║\n", exp, maxExp, expBar));
        sb.append(String.format("║ %-"+WIDTH+"s ║\n", ""));
        sb.append(String.format("║  GEM : %-"+(WIDTH-7)+"d ║\n", gem));
        sb.append(String.format("║ %-"+WIDTH+"s ║\n", ""));

        sb.append("╠════════════════════════════════════════════════════════════════════╣\n");

        sb.append(String.format("║ %s ║\n", center("⚔ BATTLE ⚔", WIDTH - 2)));
        sb.append(String.format("║ %-"+WIDTH+"s ║\n", ""));
        sb.append(String.format("║ %s ║\n", center("Choose your stage to fight", WIDTH)));
        sb.append(String.format("║ %-"+WIDTH+"s ║\n", ""));
        sb.append(String.format("║ %s ║\n", center("▸[1] 스테이지 진입하기", WIDTH - 3)));
        sb.append(String.format("║ %-"+WIDTH+"s ║\n", ""));
        sb.append(String.format("║ %s ║\n", center("Monsters are waiting for you...", WIDTH)));
        sb.append(String.format("║ %-"+WIDTH+"s ║\n", ""));

        sb.append("╠════════════════════════════════════════════════════════════════════╣\n");

        sb.append(String.format("║ %-" + WIDTH + "s ║\n", ""));
        sb.append(String.format("║ %-" + WIDTH + "s ║\n", "  MENU"));
        sb.append(String.format("║ %-" + WIDTH + "s ║\n", ""));
    	sb.append(String.format("║ %-" + (WIDTH-2) + "s ║\n", String.format("▸ [2] %-12s  ▸ [3] %-12s", "상점", "히어로 정보")));
    	sb.append(String.format("║ %-" + (WIDTH-2) + "s ║\n", String.format("▸ [4] %-12s ▸ [5] %-12s", "인벤토리", "업적")));
    	sb.append(String.format("║ %-" + (WIDTH-3) + "s ║\n", String.format("▸ [6] %-12s ▸ [7] %-12s", "로그아웃", "프로그램 종료")));
        sb.append(String.format("║ %-" + WIDTH + "s ║\n", ""));
        
        sb.append("║────────────────────────────────────────────────────────────────────║\n");

        sb.append(String.format("║ %-"+WIDTH+"s ║\n", ""));
        sb.append(String.format("║ %-"+(WIDTH-1)+"s ║\n", "선택 ▶"));

        return sb.toString();
    }
}