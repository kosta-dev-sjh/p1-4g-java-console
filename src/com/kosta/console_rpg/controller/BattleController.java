package com.kosta.console_rpg.controller;

import com.kosta.console_rpg.model.dto.MonsterDTO;
import com.kosta.console_rpg.model.service.BattleService;

/**
 * 게임 전투 흐름과 전투 관련 사용자 요청을 제어하는 컨트롤러
 *
 * 작성자      : 
 * 생성일      : 
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class BattleController {
	static BattleService battleService = new BattleService();
	public static void selectMonstersByStage(int stage) {
		try {
			MonsterDTO monster = battleService.selectMonstersByStage(stage);
			System.out.println(monster);
		}catch (Exception e) {
			e.printStackTrace();
			//FailView.errorMessage(e.getMessage());
			
		}
	}

}
