package com.kosta.console_rpg.controller;

import com.kosta.console_rpg.model.dto.MonsterDTO;
import com.kosta.console_rpg.model.service.BattleService;
import com.kosta.console_rpg.view.BattleView;

/**
 * 게임 전투 흐름과 전투 관련 사용자 요청을 제어하는 컨트롤러
 *
 * 작성자      : 김재민
 * 생성일      : 2026-03-13
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class BattleController {
	private BattleService battleService = new BattleService();

	public void selectMonsterByStage(int stage) {
		try {
			MonsterDTO monster = battleService.selectMonsterByStage(stage);
			//BattleView.showMonster(monster);
			System.out.println(monster);
		}catch (Exception e) {
			e.printStackTrace();
			//FailView.errorMessage(e.getMessage());
			
		}
	}

}
