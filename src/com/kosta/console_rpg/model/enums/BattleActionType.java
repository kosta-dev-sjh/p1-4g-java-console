package com.kosta.console_rpg.model.enums;

public enum BattleActionType {
	HERO_ATTACK("(이/가) 공격했다!"),
	HERO_SKILL("(이/가) 스킬을 사용했다!"),
	HERO_ITEM("(이/가) 아이템을 사용했다!"),
	MONSTER_ATTACK("(이/가) 일반 공격을 했다!"),
	MONSTER_SKILL("(이/가) 스킬을 사용했다!"),
	DEFEND("(이/가) 방어 자세를 취했다!"),
	ESCAPE("(이/가) 도망쳤다!");

	private final String message;

	BattleActionType(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
