package com.kosta.console_rpg.exception;

public class NotEnoughHpException extends Exception {
	
	public NotEnoughHpException() {
		super("Hp가 부족합니다.");
	}
}
