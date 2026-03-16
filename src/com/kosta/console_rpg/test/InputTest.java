package com.kosta.console_rpg.test;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.util.InputUtil;
/**
 * inputTest 테스트용 코드
 * 
 * */
public class InputTest {

	public static void main(String[] args) {
		utilTest();
	}
	
	public static void utilTest() {
		while(true) {
            try {
                System.out.print("숫자 입력: ");
                int menu = InputUtil.inputInt();

                switch (menu) {
                    case 1 -> {
                        System.out.print("문자 입력: ");
                        String value = InputUtil.inputString();
                        System.out.println("입력값 : " + value);
                    }
                    case 2 -> System.out.println("테스트 성공");
                    default -> System.out.println("1 또는 2 입력");
                }

            } catch (GameException e) {
                System.out.println(e.getMessage());
            }
		}
	}
}

