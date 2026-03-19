package com.kosta.console_rpg.view;

import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.session.LoginSession;
import com.kosta.console_rpg.util.InputUtil;

import static com.kosta.console_rpg.test.ConsoleEffectTest.GREEN;
import static com.kosta.console_rpg.test.ConsoleEffectTest.RED;
import static com.kosta.console_rpg.test.ConsoleEffectTest.YELLOW;
import static com.kosta.console_rpg.test.ConsoleEffectTest.CYAN;
import static com.kosta.console_rpg.test.ConsoleEffectTest.RESET;

/**
 * 게임 메인화면 뷰
 *
 * 작성자     : 이진주
 * 생성일     : 2026.03.16
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class MainView {
	
//	public static void main(String[] args) {
//		MainView view = new MainView();
//        view.start();
//
//    }

    public void start() {
    	System.out.println(this);
        //InputUtil.backToMenu();
    }
//    HeroDTO hero = LoginSession.getInstance().getCurrentHero();
//    private String name = hero.getHeroName();
//    private int hp =  hero.getHeroHp();
//    private int mp = hero.getHeroMp();
//    private int maxHp =  hero.getHeroHp();
//    private int maxMp = hero.getHeroMp();
//    private int exp = hero.getHeroExp();
//    private int maxExp = hero.getHeroLevel()*100;
//    private int gem = hero.getHeroGem();

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

//        sb.append("____________________________┌ HERO INFO ┐____________________________\n");
//        sb.append(String.format("║ %-"+WIDTH+"s ║\n", ""));
//        sb.append(String.format("║  Name  : %-"+(WIDTH-9)+"s ║\n", name));
//        sb.append(String.format("║ %-"+WIDTH+"s ║\n", ""));
//        sb.append(String.format("║  HP  : %3d / %-"+(WIDTH-13)+"d ║\n", hp, maxHp));
//        sb.append(String.format("║  MP  : %3d / %-"+(WIDTH-13)+"d ║\n", mp, maxMp));
////        String expBar = "██░░░░░░░░░░░░░░░";
////        sb.append(String.format("║  EXP : %3d / %3d  %-"+(WIDTH-18)+"s ║\n", exp, maxExp, expBar));
//        sb.append(String.format("║ %-"+WIDTH+"s ║\n", ""));
//        sb.append(String.format("║  GEM : %-"+(WIDTH-7)+"d ║\n", gem));
//        sb.append(String.format("║ %-"+WIDTH+"s ║\n", ""));

        sb.append("╠════════════════════════════════════════════════════════════════════╣\n");

        sb.append(String.format(" %s \n", center("⚔ BATTLE ⚔", WIDTH - 2)));
        sb.append(String.format(" %-"+WIDTH+"s \n", ""));
        sb.append(String.format(" %s \n", center("Choose your stage to fight", WIDTH)));
        
        sb.append(GREEN);
        sb.append(String.format(" %-"+WIDTH+"s \n", ""));
        sb.append(String.format(" %s \n", center("▸[1] 스테이지 진입하기", WIDTH - 3)));
        sb.append(String.format(" %-"+WIDTH+"s \n", ""));
        sb.append(RESET);
        
        sb.append(String.format(" %s \n", center("Monsters are waiting for you...", WIDTH)));
        sb.append(String.format(" %-"+WIDTH+"s \n", ""));
        

        sb.append("╠════════════════════════════════════════════════════════════════════╣\n");

        sb.append(String.format(" %-" + WIDTH + "s \n", ""));
        sb.append(String.format(" %-" + WIDTH + "s \n", "  MENU"));
        sb.append(String.format(" %-" + WIDTH + "s \n", ""));
        sb.append(GREEN);
    	sb.append(String.format(" %-" + (WIDTH-2) + "s \n", String.format("▸ [2] %-13s ▸ [3] %-12s", "상점", "히어로 정보")));
    	sb.append(String.format(" %-" + (WIDTH-2) + "s \n", String.format("▸ [4] %-12s ▸ [5] %-12s", "인벤토리", "업적")));
    	sb.append(String.format(" %-" + (WIDTH-3) + "s \n", String.format("▸ [6] %-12s ▸ [7] %-12s", "로그아웃", "히어로 삭제")));
        sb.append(String.format(" %-" + (WIDTH-3) + "s \n", String.format("▸ [8] %-12s", "프로그램 종료")));
        sb.append(String.format(" %-" + WIDTH + "s \n", ""));
        sb.append(RESET);
        sb.append("║────────────────────────────────────────────────────────────────────║\n\n");
        sb.append(String.format(" %-"+WIDTH+"s \n", ""));


        return sb.toString();
    }
}