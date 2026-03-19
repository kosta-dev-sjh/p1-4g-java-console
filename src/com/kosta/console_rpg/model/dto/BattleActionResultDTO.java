package com.kosta.console_rpg.model.dto;

import com.kosta.console_rpg.model.enums.BattleActionType;
import com.kosta.console_rpg.model.enums.BattleResult;

public class BattleActionResultDTO {

	private BattleResult battleResult;
	private int resultValue;
	private int dice;
	private String actionName;
	private String potionType;
	private BattleActionType action;

	/**
	 * 문제 발생 시 DTO 생성자
	 * @param battleResult
	 */
	public BattleActionResultDTO(BattleResult battleResult) {
		this.battleResult = battleResult;
	}

	/**
	 * 공격 결과, 방어 결과 DTO 생성자
	 * 
	 * @param battleResult
	 * @param resultValue
	 * @param dice
	 * @param action
	 */
	public BattleActionResultDTO(BattleResult battleResult, int resultValue, int dice, BattleActionType action) {
		this.battleResult = battleResult;
		this.resultValue = resultValue;
		this.dice = dice;
		this.action = action;
	}
	/**
	 * 스킬 사용 결과 DTO 생성자
	 * @param battleResult
	 * @param resultValue
	 * @param dice
	 * @param actionName
	 * @param action
	 */
	public BattleActionResultDTO(BattleResult battleResult, int resultValue, int dice, String actionName, BattleActionType action) {
		this.battleResult = battleResult;
		this.resultValue = resultValue;
		this.dice = dice;
		this.actionName = actionName;
		this.action = action;
	}
	/**
	 * 포션 사용 결과 DTO 생성자
	 * @param battleResult
	 * @param potionName
	 * @param potionType
	 * @param action
	 */
	public BattleActionResultDTO(BattleResult battleResult, int resultValue, String potionType, String actionName, BattleActionType action) {
		this.battleResult = battleResult;
		this.resultValue = resultValue;
		this.potionType = potionType;
		this.actionName = actionName;
		this.action = action;
	}

	


	public String getActionName() {
		return actionName;
	}

	public BattleResult getBattleResult() {
		return battleResult;
	}

	public int getResultValue() {
		return resultValue;
	}

	public int getDice() {
		return dice;
	}

	public BattleActionType getAction() {
		return action;
	}

	public String getPotionType() {
		return potionType;
	}
}
