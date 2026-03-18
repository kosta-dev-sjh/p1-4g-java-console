package com.kosta.console_rpg.model.dto;

import com.kosta.console_rpg.model.enums.BattleResult;

public class BattleActionResultDTO {

	private BattleResult battleResult;
	private int damage;
	private int dice;

	public BattleActionResultDTO(BattleResult battleResult, int damage, int dice) {
		this.battleResult = battleResult;
		this.damage = damage;
		this.dice = dice;
	}
    
	public BattleResult getBattleResult() {
		return battleResult;
	}

	public int getDamage() {
		return damage;
	}

	public int getDice() {
		return dice;
	}
}
