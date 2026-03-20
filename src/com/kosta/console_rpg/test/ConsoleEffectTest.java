package com.kosta.console_rpg.test;

public class ConsoleEffectTest {

	public static final String RESET = "\u001B[0m";
	//public static final String RED = "\u001B[31m";
	public static final String RED = "\u001B[38;5;203m";
	public static final String BLACK = "\u001B[30m";
    public static final String WHITE_BG = "\u001B[47m";
    public static final String BLACK_BG = "\u001B[40m";
	//public static final String GREEN = "\u001B[32m";
	public static final String GREEN = "\u001B[38;5;118m";
	public static final String YELLOW = "\u001B[33m";
	public static final String CYAN = "\u001B[36m";
    public static void main(String[] args) throws Exception {
        intro();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void slowPrint(String text, int delay) throws Exception {
        for(char c : text.toCharArray()) {
            System.out.print(c);
            Thread.sleep(delay);
        }
        System.out.println();
    }

    public static void intro() throws Exception {

        clearScreen();

        slowPrint(CYAN + "어두운 숲속..." + RESET, 80);
        Thread.sleep(500);

        slowPrint(YELLOW + "무언가 다가온다..." + RESET, 80);
        Thread.sleep(1000);

        clearScreen();

        slowPrint(RED + "━━━━━━━━━━━━━━━━━━━━" + RESET, 10);
        slowPrint(RED + "⚔ 몬스터 등장 ⚔" + RESET, 50);
        slowPrint(RED + "━━━━━━━━━━━━━━━━━━━━" + RESET, 10);

        Thread.sleep(1000);

        clearScreen();

        System.out.println(GREEN + "Hero HP : [■■■■■■■■■■] 100" + RESET);
        System.out.println(RED + "Goblin HP : [■■■■■■■■□□] 80" + RESET);

        Thread.sleep(1000);

        System.out.println();

        slowPrint(YELLOW + "Hero의 공격!" + RESET, 50);

        Thread.sleep(800);

        slowPrint(RED + "Goblin에게 20의 피해!" + RESET, 50);

        Thread.sleep(1000);

        clearScreen();

        System.out.println(GREEN + "Hero HP : [■■■■■■■■■■] 100" + RESET);
        System.out.println(RED + "Goblin HP : [■■■■■■□□□□] 60" + RESET);

        Thread.sleep(1000);

        slowPrint(GREEN + "전투 승리!" + RESET, 50);
    }
}
