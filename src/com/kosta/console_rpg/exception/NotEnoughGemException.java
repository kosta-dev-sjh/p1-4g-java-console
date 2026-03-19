package com.kosta.console_rpg.exception;

public class NotEnoughGemException extends Exception {
	
	public NotEnoughGemException() {
		super("사용 가능한 젬이 부족합니다.");
	}
}
