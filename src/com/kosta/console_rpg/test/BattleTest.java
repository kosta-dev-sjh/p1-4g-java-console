package com.kosta.console_rpg.test;

import com.kosta.console_rpg.controller.BattleController;
import com.kosta.console_rpg.model.dto.MonsterDTO;

public class BattleTest {

	private static final BattleController battleController = new BattleController();

	public static void main(String[] args) {
		startBattle();
	}

	/**
	 * 전투 시작 및 몬스터 조회 테스트
	 */
	public static void startBattle() {
		int selectStage = 1;

		MonsterDTO monster = battleController.selectMonsterByStage(selectStage);

		if(monster == null) return;

		showBattleStart(monster);

		battleMenu(monster);
	}

	/**
	 * 전투 시작 출력
	 */
	public static void showBattleStart(MonsterDTO monster) {
		System.out.println("===== 전투 시작 =====");
		System.out.println(monster);
	}

	/**
	 * 전투 메뉴 출력
	 */
	public static void battleMenu(MonsterDTO monster) {
		System.out.println("1. 공격");
		System.out.println("2. 방어");
		System.out.println("3. 스킬");
		System.out.println("4. 아이템");
		System.out.println("5. 전투포기");
	}
}