package com.kosta.console_rpg.model.enums;
/**
 * 전투 종료 결과를 정의하는 enum
 *
 * 작성자      : 송정현
 * 생성일      : 2026.03.16
 * 최종 수정자 : 송정현
 * 최종 수정일 : 2026.03.18
 */
public enum BattleResult {
	CONTINUE, // 전투 지속
	WIN,      // 승리
	LOSE,     // 패배
	ESCAPE,    // 도주
	INVALID_ACTION // 잘못된 행동
}
