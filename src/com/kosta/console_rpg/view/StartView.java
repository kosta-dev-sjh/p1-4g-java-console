package com.kosta.console_rpg.view;

/**
 * 게임 시작 뷰
 *
 * 작성자     : 이진주
 * 생성일     : 2026.03.16
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class StartView {
	
	public static void main(String[] args) {
        StartView view = new StartView();
        view.start();
    }

    public void start() {
        System.out.println(this);
    }
    
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("╔════════════════════════════════════════════════════════╗\n");
        sb.append("║                                                        ║\n");
        sb.append("║    ███████   ██╗   ██╗  ███████╗   ██╗       ██╗       ║\n");
        sb.append("║    ██╔════╝  ██║   ██║  ██╔════╝   ██║       ██║       ║\n");
        sb.append("║    ███████   ████████║  █████╗     ██║       ██║       ║\n");
        sb.append("║    ╚════██║  ██╔═══██║  ██╔══╝     ██║       ██║       ║\n");
        sb.append("║    ███████║  ██║   ██║  █████████╗ ███████╗  ███████╗  ║\n");
        sb.append("║    ╚══════╝  ╚═╝   ╚═╝  ╚════════╝ ╚══════╝  ╚══════╝  ║\n");
        sb.append("║                                                        ║\n");
        sb.append("║                ██╗   ██╗ ███████╗ ██████╗   ██████╗    ║\n");
        sb.append("║                ██║   ██║ ██╔════╝ ██╔══██╗ ██╔═══██╗   ║\n");
        sb.append("║                ████████║ █████╗   ██████╔╝ ██║   ██║   ║\n");
        sb.append("║                ██╔═══██║ ██╔══╝   ██╔══██╗ ██║   ██║   ║\n");
        sb.append("║                ██║   ██║ ███████╗ ██║  ██║ ╚██████╔╝   ║\n");
        sb.append("║                ╚═╝   ╚═╝ ╚══════╝ ╚═╝  ╚═╝  ╚═════╝    ║\n");
        sb.append("║                                                        ║\n");
        sb.append("║              A Terminal Adventure RPG                  ║\n");
        sb.append("║                                                        ║\n");
        sb.append("║────────────────────────────────────────────────────────║\n");
        sb.append("║                                                        ║\n");
        sb.append("║             [1]  SIGN UP                               ║\n");
        sb.append("║             [2]  LOGIN                                 ║\n");
        sb.append("║             [3]  EXIT                                  ║\n");
        sb.append("║                                                        ║\n");
        sb.append("║────────────────────────────────────────────────────────║\n");
        sb.append("║                                                        ║\n");
        sb.append("║          Press number to begin your adventure...       ║\n");
        sb.append("║                                                        ║\n");
        sb.append("╚════════════════════════════════════════════════════════╝\n");
	    sb.append(" 선택 ▶ ");

        return sb.toString();
    }
    
}