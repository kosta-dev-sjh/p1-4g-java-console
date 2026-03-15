package com.kosta.console_rpg.view;

/**
 * 예외 발생 시 출력하는 뷰
 *
 * 작성자      : 송정현
 * 생성일      : 2026.03.14
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class FailView {
	
	/**
	 * 예외가 발생 시 예외메세지 출력
	 * */
	public static void errorMessage(String message) {
		System.out.println(message);
	}
}
