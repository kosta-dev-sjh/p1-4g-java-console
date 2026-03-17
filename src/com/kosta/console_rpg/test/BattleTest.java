package com.kosta.console_rpg.test;

import java.util.List;

import com.kosta.console_rpg.controller.BattleController;
import com.kosta.console_rpg.controller.HeroController;
import com.kosta.console_rpg.controller.UserController;
import com.kosta.console_rpg.model.dto.BattleActionResultDTO;
import com.kosta.console_rpg.model.dto.BattleHeroDTO;
import com.kosta.console_rpg.model.dto.ItemDTO;
import com.kosta.console_rpg.model.dto.MonsterDTO;
import com.kosta.console_rpg.model.dto.SkillDTO;
import com.kosta.console_rpg.model.enums.BattleResult;
import com.kosta.console_rpg.session.LoginSession;
import com.kosta.console_rpg.util.InputUtil;
import com.kosta.console_rpg.view.FailView;

public class BattleTest {

	private static final BattleController battleController = new BattleController();
	private static final HeroController heroController = new HeroController();

	private static BattleHeroDTO battleHero;
	private static MonsterDTO monster;
	private static List<SkillDTO> skillList;
	private static List<ItemDTO> itemList;

	public static void main(String[] args) {

		UserController userController = new UserController();
		userController.login("abc", "abc");

		startBattle();
	}

	/**
	 * 전투 시작
	 */
	public static void startBattle() {
		int stage = 1;

		battleHero = battleController.createBattleHero();
		monster = battleController.selectMonsterByStage(stage);

		if (battleHero == null || monster == null) {
			FailView.errorMessage("전투 시작에 필요한 영웅 또는 몬스터 정보가 없습니다.");
			return;
		}

		showBattleStart(stage);

		while (true) {
			BattleResult result = battleMenu();

			if (result != BattleResult.CONTINUE) {
				showBattleResult(result);
				break;
			}

			result = monsterTurn();

			if (result != BattleResult.CONTINUE) {
				showBattleResult(result);
				break;
			}
		}
	}

	/**
	 * 전투 시작 출력
	 */
	public static void showBattleStart(int stage) {
		System.out.println("========== BATTLE START ==========");
		System.out.println("STAGE : " + stage);
		System.out.println("MONSTER : " + monster.getMonsterName());
		System.out.println("==================================");
	}

	/**
	 * 플레이어 턴
	 */
	public static BattleResult battleMenu() {
		while (true) {
			try {
				showBattleStatus();

				System.out.println();
				System.out.println("▶ 다음 행동을 선택하세요");
				System.out.println("[1] 공격");
				System.out.println("[2] 방어");
				System.out.println("[3] 스킬");
				System.out.println("[4] 아이템");
				System.out.println("[5] 전투 포기");

				int menu = InputUtil.inputInt();

				switch (menu) {
					case 1:
						return attackTurn();
					case 2:
						return defendTurn();
					case 3:
						return skillTurn();
					case 4:
						return itemTurn();
					case 5:
						return escapeTurn();
					default:
						System.out.println("잘못된 입력입니다.");
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * 공격 턴
	 */
	public static BattleResult attackTurn() {
		BattleActionResultDTO result = battleController.attack(battleHero, monster);

		System.out.println();
		System.out.println("Hero가 공격을 시도했다!");
		System.out.println("주사위 굴림 : 🎲 " + result.getDice());
		System.out.println("결과 → -" + result.getDamage());

		return result.getBattleResult();
	}

	/**
	 * 방어 턴
	 */
	public static BattleResult defendTurn() {
		BattleResult result = battleController.defend(battleHero);

		System.out.println();
		System.out.println("Hero가 방어 자세를 취했다!");
		System.out.println("추가 방어력 +" + battleHero.getGuardBonus());

		return result;
	}

	/**
	 * 몬스터 턴
	 */
	public static BattleResult monsterTurn() {
		boolean beforeGuard = battleHero.isGuardActive();

		BattleActionResultDTO result = battleController.monsterAttack(battleHero, monster);

		System.out.println();
		System.out.println("몬스터가 반격했다!");
		System.out.println("주사위 굴림 : 🎲 " + result.getDice());
		System.out.println("결과 → -" + result.getDamage());

		if (beforeGuard && !battleHero.isGuardActive()) {
			System.out.println("방어 자세 해제됨");
		}

		return result.getBattleResult();
	}

	/**
	 * 스킬 턴
	 */
	public static BattleResult skillTurn() {
		System.out.println("TODO : 스킬 연결 예정");
		return BattleResult.CONTINUE;
	}

	/**
	 * 아이템 턴
	 */
	public static BattleResult itemTurn() {
		System.out.println("TODO : 아이템 연결 예정");
		return BattleResult.CONTINUE;
	}

	/**
	 * 도망
	 */
	public static BattleResult escapeTurn() {
		return battleController.escape();
	}

	/**
	 * 전투 상태 출력
	 */
	public static void showBattleStatus() {
		System.out.println();
		System.out.println("------------- BATTLE LOG -------------");

		System.out.println("Hero : " + battleHero.getHeroName());
		System.out.println("HP : " + battleHero.getHeroHp());
		System.out.println("MP : " + battleHero.getHeroMp());

		int currentDefense = battleHero.getHeroDefense();

		if (battleHero.isDefBuffActive()) {
			currentDefense += battleHero.getTempDefBonus();
		}

		if (battleHero.isGuardActive()) {
			currentDefense += battleHero.getGuardBonus();
		}

		System.out.println("DEF : " + currentDefense);

		if (battleHero.isGuardActive()) {
			System.out.println("(방어 자세 적용 중 + " + battleHero.getGuardBonus() + ")");
		}

		System.out.println();

		System.out.println(monster.getMonsterName());
		System.out.println("HP : " + monster.getMonsterHp());
		System.out.println("ATK : " + monster.getMonsterAttack());
		System.out.println("--------------------------------------");
	}

	/**
	 * 종료 결과 출력
	 */
	public static void showBattleResult(BattleResult result) {
		System.out.println();
		System.out.println("=========== RESULT ===========");

		switch (result) {
			case WIN -> victory();
			case LOSE -> System.out.println("💀 패배...");
			case ESCAPE -> System.out.println("🏃 도망쳤습니다.");
			default -> System.out.println("종료");
		}

		System.out.println("==============================");
	}

	/**
	 * 승리 시
	 */
	public static void victory() {
		System.out.println("TODO : 승리 보상 연결 예정");
		System.out.println("🏆 승리!");
		LoginSession.getInstance().getCurrentHero().setHeroExp(LoginSession.getInstance().getCurrentHero().getHeroExp() + monster.getMonsterRewardExp());
		LoginSession.getInstance().getCurrentHero().setHeroGem(LoginSession.getInstance().getCurrentHero().getHeroGem() + monster.getMonsterRewardGem());
		
		heroController.updateHero();
	}
}