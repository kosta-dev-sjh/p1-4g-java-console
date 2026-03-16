package com.kosta.console_rpg.test;

import com.kosta.console_rpg.controller.BattleController;
import com.kosta.console_rpg.model.dto.BattleHeroDTO;
import com.kosta.console_rpg.model.dto.HeroDTO;
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
		int stage = 1;

		BattleHeroDTO battleHero = battleController.createBattleHero();
		MonsterDTO monster = battleController.selectMonsterByStage(stage);
		// TODO : 전투 시작 시 현재 영웅의 보유 스킬 목록 조회 후 전투 메뉴에서 사용하도록 연결

		if(monster == null) return;

		while(battleHero.getHeroHp() > 0 && monster.getMonsterHp() > 0) {
			battleMenu(battleHero, monster);
		}
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
	public static void battleMenu(BattleHeroDTO hero, MonsterDTO monster) {
		System.out.println("1. 공격");
		System.out.println("2. 방어");
		System.out.println("3. 스킬");
		System.out.println("4. 아이템");
		System.out.println("5. 전투포기");
	}
}