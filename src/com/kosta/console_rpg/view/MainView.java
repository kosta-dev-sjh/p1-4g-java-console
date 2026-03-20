package com.kosta.console_rpg.view;

import com.kosta.console_rpg.util.ConsoleEffectUtil;
import com.kosta.console_rpg.util.ConsoleUtils;

/**
 * 게임 메인화면 뷰
 *
 * 작성자     : 이진주
 * 생성일     : 2026.03.16
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class MainView {
	
    public void start() {
    	System.out.println(this);
    }

    private static final int WIDTH = 66;
    
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        
        sb.append("╠════════════════════════════════════════════════════════════════════╣\n\n");
        sb.append(String.format(" %s \n", ConsoleUtils.center("⚔ BATTLE ⚔", WIDTH - 2) ));
        sb.append(String.format(" %-"+WIDTH+"s \n", ""));
        sb.append(String.format(" %s \n", ConsoleUtils.center("Choose your stage to fight", WIDTH)));
        
        sb.append(ConsoleEffectUtil.GREEN);
        sb.append(String.format(" %-"+WIDTH+"s \n", ""));
        sb.append(String.format(" %s \n", ConsoleUtils.center("▸[1] 스테이지 진입하기", WIDTH - 3)));
        sb.append(String.format(" %-"+WIDTH+"s \n", ""));
        sb.append(ConsoleEffectUtil.RESET);
        
        sb.append(String.format(" %s \n", ConsoleUtils.center("Monsters are waiting for you...", WIDTH)));
        sb.append(String.format(" %-"+WIDTH+"s \n", ""));
        

        sb.append("╠════════════════════════════════════════════════════════════════════╣\n");

        sb.append(String.format(" %-" + WIDTH + "s \n", ""));
        sb.append(String.format(" %-" + WIDTH + "s \n", "  MENU"));
        sb.append(String.format(" %-" + WIDTH + "s \n", ""));
        sb.append(ConsoleEffectUtil.GREEN);
    	sb.append(String.format(" %-" + (WIDTH-2) + "s \n", String.format("▸ [2] %-13s ▸ [3] %-12s", "상점", "히어로 정보")));
    	sb.append(String.format(" %-" + (WIDTH-2) + "s \n", String.format("▸ [4] %-12s ▸ [5] %-12s", "인벤토리", "업적")));
    	sb.append(String.format(" %-" + (WIDTH-3) + "s \n", String.format("▸ [6] %-12s ▸ [7] %-12s", "로그아웃", "히어로 삭제")));
        sb.append(String.format(" %-" + (WIDTH-3) + "s \n", String.format("▸ [8] %-12s", "프로그램 종료")));
        sb.append(String.format(" %-" + WIDTH + "s \n", ""));
        sb.append(ConsoleEffectUtil.RESET);
        sb.append("║────────────────────────────────────────────────────────────────────║\n");
        sb.append(String.format(" %-"+WIDTH+"s \n", ""));        

        return sb.toString();
    }
}
