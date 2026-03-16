package com.kosta.console_rpg.controller;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.MonsterDTO;
import com.kosta.console_rpg.model.enums.BattleResult;
import com.kosta.console_rpg.model.service.BattleService;
import com.kosta.console_rpg.view.FailView;

/**
 * 게임 전투 흐름과 전투 관련 사용자 요청을 제어하는 컨트롤러
 *
 * 작성자      : 김재민
 * 생성일      : 2026-03-13
 * 최종 수정자 : 송정현
 * 최종 수정일 : 2026-03-16
 */
public class BattleController {
	private final BattleService battleService = new BattleService();

	public MonsterDTO selectMonsterByStage(int selectStage) {
		MonsterDTO monster = null;
		try {
			monster = battleService.selectMonsterByStage(selectStage);
			
		}catch (GameException e) {
			FailView.errorMessage(e.getMessage());
		}
		return monster;
	}
	
	/**
	 * 선택한 스테이지 기준으로 전투를 시작한다.
	 *
	 * @param selectStage 사용자가 선택한 스테이지 번호
	 *
	 */
	public BattleResult startBattle(int selectStage) {
		BattleResult result = null;

		return result;
	}
	
	

}
