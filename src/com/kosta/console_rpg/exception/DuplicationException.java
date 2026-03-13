package com.kosta.console_rpg.exception;

public class DuplicationException extends GameException {
	
	public DuplicationException() {
		super("이미 존재하는 이름입니다.");
	}
}
