package com.kosta.console_rpg.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.kosta.console_rpg.exception.GameException;

/**
 * 콘솔 사용자 입력을 공통으로 처리하기 위한 유틸리티 클래스
 *
 * 작성자      : 송정현
 * 생성일      : 2026.03.13
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class InputUtil {
	// ======= field =======
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	private InputUtil() {}
	
	// ======= public method =======
	
	/**
	 * 문자 값을 입력받는 메소드 
	 *
	 * @return String 문자 
	 * @throws GameException 
	 */
	public static String inputString() throws GameException {
		String inputValue = null;
		
		try {
			inputValue = br.readLine();
			return inputValue;
		}catch(IOException e) {
			throw new GameException("입력 중 오류가 발생했습니다.");
		}
	}
	
	/**
	 * 문자열을 받아 정수로 변환하는 메소드 
	 *
	 * @param value 검사할 문자열
	 * @return int 정수값 
	 * @throws GameException 
	 */
	public static int changeInt(String value) throws GameException {
		try {
			int inputvalue = Integer.parseInt(value);
			return inputvalue;
		}catch(NumberFormatException e) {
			throw new GameException("숫자를 입력해주시기 바랍니다.");
		}
	}
	
	/**
	 * 정수 값을 반환하는 메소드 
	 * 
	 * 
	 * @return int 정수값 
	 * @throws GameException 
	 * 
	 * */
	public static int inputInt() throws GameException {
		return changeInt(inputString());
	}
	
	/**
	 * 문자열 길이 검사 
	 * 
	 * @param value 검사할 문자열
	 * @param min 최소 허용 길이
	 * @param max 최대 허용 길이
	 * @throws GameException 
	 * */
	public static void checkStringLength(String value, int min, int max) throws GameException {
		if(value == null || value.length() < min || value.length() > max) {
			throw new GameException("입력 길이는 " + min + "자 이상 " + max + "자 이하이어야 합니다.");
		}
	}
	
	/**
	 * 문자 공백 검사 
	 * 
	 * @param value 검사할 문자열
	 * @throws GameException 
	 * */
	public static void checkBlank(String value) throws GameException{
		if(value == null || value.trim().isEmpty()) {
			throw new GameException("공백 입력은 불가합니다.");
		}
	}
	
	
	/**
	 * Y / N 입력 검사 
	 * 
	 * @param value 검사할 문자열
	 * @throws GameException 
	 * */
	public static void checkYesOrNo(String value) throws GameException{
	    if (value == null || !(value.equalsIgnoreCase("y") || value.equalsIgnoreCase("n"))) {
	        throw new GameException("Y 또는 N으로 입력해주세요.");
	    }
	}
	
	/**
	 * 숫자 범위 검사
	 * 
	 * @param value 검사할 문자열
	 * @parem min
	 * @param max
	 * @throws GameException 
	 */
	public static void checkRange(int value, int min, int max) throws GameException {
	    if (value < min || value > max) {
	        throw new GameException(min + " ~ " + max + " 사이 값만 입력 가능합니다.");
	    }
	}
	/**
	 * 메뉴로 뒤로가기 처리
	 * "0" 입력 시 아무것도 하지 않고 종료, 기타 입력 시 에러 메시지 출력
	 */
	public static void backToMenu() {
		try {
			String menu = InputUtil.inputString();
			switch (menu) {
				case "0" -> {  // 뒤로가기: 아무것도 안하고 종료
					break;
				}
				default -> System.out.println("잘못된 입력입니다.");
			}
		} catch (GameException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
}
