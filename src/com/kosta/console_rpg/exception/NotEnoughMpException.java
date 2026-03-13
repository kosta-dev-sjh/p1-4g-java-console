package com.kosta.console_rpg.exception;

public class NotEnoughMpException extends Exception {
	
	public NotEnoughMpException() {
		super("사용 가능한 Mp가 부족합니다.");
	}
}
