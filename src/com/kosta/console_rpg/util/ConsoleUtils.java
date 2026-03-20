package com.kosta.console_rpg.util;


public class ConsoleUtils {
	// 공통 상수 정의 (이미 정의되어 있다면 생략 가능)
    public static final int WIDTH = 70;

    /**
     * 원하는 타이틀을 받아 콘솔 상단 바 형식으로 출력
     * @param title 출력할 제목 (예: "Main Menu", "Inventory")
     */
    public static void printTitleBar(String title) {
        StringBuilder sb = new StringBuilder();
        
        // 1. 중앙 정렬 계산
        // 한글이 포함될 경우 대비하여 간단한 여백 계산
        int padding = (WIDTH - title.length()) / 2;
        if (padding < 0) padding = 0; // 제목이 너무 길 경우 방지
        
        String centeredMenu = " ".repeat(padding) + title;

        // 2. 타이틀 바 조립
        sb.append("\n"+ConsoleEffectUtil.WHITE_BG).append(ConsoleEffectUtil.BLACK)
          .append(String.format("%-" + WIDTH + "s", centeredMenu))
          .append(ConsoleEffectUtil.RESET).append("\n\n");

        // 3. 출력
        System.out.print(sb.toString());
        
    }
    
    // 중앙 정렬(공통)
    public static String center(String text, int width) {
        int padding = width - text.length();
        int left = padding / 2;
        int right = padding - left;

        return " ".repeat(left) + text + " ".repeat(right);
    }

    
    
}
