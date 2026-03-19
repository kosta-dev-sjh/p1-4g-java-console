package com.kosta.console_rpg.test;

import java.util.List;

import com.kosta.console_rpg.controller.BattleController;
import com.kosta.console_rpg.controller.InventoryController;
import com.kosta.console_rpg.controller.UserController;
import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.BattleActionResultDTO;
import com.kosta.console_rpg.model.dto.BattleHeroDTO;
import com.kosta.console_rpg.model.dto.BattlePotionDTO;
import com.kosta.console_rpg.model.dto.HeroSkillDTO;
import com.kosta.console_rpg.model.dto.MonsterDTO;
import com.kosta.console_rpg.model.enums.BattleActionType;
import com.kosta.console_rpg.model.enums.BattleResult;
import com.kosta.console_rpg.model.enums.RewardResult;
import com.kosta.console_rpg.util.InputUtil;
import com.kosta.console_rpg.view.FailView;
import com.kosta.console_rpg.view.StoryView;

public class BattleTest {

	private static final BattleController battleController = new BattleController();
	private static final InventoryController inventoryController = new InventoryController();

	private static BattleHeroDTO battleHero;
	private static MonsterDTO monster;
	private static List<HeroSkillDTO> heroSkillList;
	private static List<BattlePotionDTO> potionList;

	private static int nowTurn;
	private static int nowStage;
	private static String nowDifficulty;

	public static void main(String[] args) {
		UserController userController = new UserController();
		userController.login("test", "test");

		startBattle();
	}

	/**
	 * 전투 시작
	 */
	public static void startBattle() {
		while (true) {
			try {
				System.out.println("========== BATTLE TEST ==========");
				System.out.println("1. 전투 시작");
				System.out.println("2. 종료");

				int choice = InputUtil.inputInt();

				if (choice == 1) {
					System.out.println("전투를 시작합니다...");
					System.out.print("스테이지를 선택해주세요 : ");

					nowStage = InputUtil.inputInt();
					nowDifficulty = "★".repeat(nowStage);

					battleHero = battleController.createBattleHero();
					monster = battleController.selectMonsterByStage(nowStage);
					heroSkillList = battleController.getHeroSkills();
					potionList = inventoryController.showPotionItems();

					if (battleHero == null || monster == null) {
						FailView.errorMessage("전투 시작에 필요한 정보가 부족합니다.");
						continue;
					}

					if (heroSkillList == null || heroSkillList.isEmpty()) {
						FailView.errorMessage("보유한 스킬이 없습니다.");
						continue;
					}

					if (potionList == null || potionList.isEmpty()) {
						FailView.errorMessage("사용 가능한 포션이 없습니다.");
						continue;
					}

					nowTurn = 1;
					battleLoop();

				} else if (choice == 2) {
					System.out.println("프로그램을 종료합니다.");
					return;
				} else {
					FailView.errorMessage("잘못된 입력입니다.");
				}

			} catch (GameException e) {
				FailView.errorMessage(e.getMessage());
			}
		}
	}

	/**
	 * 전투 루프
	 */
	public static void battleLoop() {
		showBattleStart();

		while (true) {
			System.out.println("현재 턴 : " + nowTurn);
			System.out.println("현재 스테이지 : " + nowStage);
			System.out.println("현재 난이도 : " + nowDifficulty);

			showBattleStatus();

			BattleResult result;

			// INVALID_ACTION만 재입력
			while (true) {
				result = battleMenu();

				if (result == BattleResult.INVALID_ACTION) {
					continue;
				}

				break;
			}

			// 전투 종료 행동
			if (result == BattleResult.WIN
					|| result == BattleResult.LOSE
					|| result == BattleResult.ESCAPE) {
				showBattleResult(result);
				break;
			}

			// CONTINUE / DEFEND 포함 정상 행동 → 몬스터 턴
			result = monsterTurn();

			if (result != BattleResult.CONTINUE) {
				showBattleResult(result);
				break;
			}

			nowTurn++;
		}
	}

	/**
	 * 전투 시작 출력
	 */
	public static void showBattleStart() {
		System.out.println("========== BATTLE START ==========");
		System.out.println("STAGE : " + nowStage);
		System.out.println("MONSTER : " + monster.getMonsterName());
		System.out.println("==================================");
	}

	/**
	 * 플레이어 행동 선택
	 */
	public static BattleResult battleMenu() {
		while (true) {
			try {

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
						FailView.errorMessage("잘못된 입력입니다.");
				}

			} catch (Exception e) {
				FailView.errorMessage(e.getMessage());
			}
		}
	}

	/**
	 * 공격
	 */
	public static BattleResult attackTurn() {
		BattleActionResultDTO result = battleController.attack(battleHero, monster);

		System.out.println();
		System.out.println(result.getAction().getMessage());
		System.out.println("주사위 : 🎲 " + result.getDice());
		System.out.println("데미지 : -" + result.getResultValue());
		
		return result.getBattleResult();
	}

	/**
	 * 방어
	 */
	public static BattleResult defendTurn() {
		BattleActionResultDTO result = battleController.defend(battleHero);

		System.out.println();
		System.out.println(result.getAction().getMessage());
		System.out.println("주사위 : 🎲 " + result.getDice());
		System.out.println("추가 방어력 : +" + result.getResultValue());

		return result.getBattleResult();
	}

	/**
	 * 스킬 사용
	 */
	public static BattleResult skillTurn() {
		try {
			for (int i = 0; i < heroSkillList.size(); i++) {
	System.out.println("[" + (i + 1) + "] " + heroSkillList.get(i).toBattleString());
}

			System.out.print("사용할 스킬 번호 입력 > ");

			int choice = InputUtil.inputInt();
			InputUtil.checkRange(choice, 1, heroSkillList.size());

			BattleActionResultDTO result = battleController.useSkill(
					battleHero,
					monster,
					heroSkillList.get(choice - 1));

			if (result.getBattleResult() == BattleResult.INVALID_ACTION) {
				return BattleResult.INVALID_ACTION;
			}

			System.out.println();
			System.out.println(result.getAction().getMessage());
			System.out.println("스킬명 : " + result.getActionName());
			System.out.println("주사위 : 🎲 " + result.getDice());
			System.out.println("데미지 : -" + result.getResultValue());

			return result.getBattleResult();

		} catch (GameException e) {
			FailView.errorMessage(e.getMessage());
			return BattleResult.INVALID_ACTION;
		}
	}

	/**
	 * 아이템 사용
	 */
	public static BattleResult itemTurn() {
		try {
			for (int i = 0; i < potionList.size(); i++) {
				System.out.println("[" + (i + 1) + "] " + potionList.get(i));
			}

			System.out.print("사용할 포션 번호 입력 > ");
			int choice = InputUtil.inputInt();
			InputUtil.checkRange(choice, 1, potionList.size());

			BattlePotionDTO selectedPotion = potionList.get(choice - 1);

			BattleActionResultDTO result = battleController.useItem(battleHero, selectedPotion);

			if (result.getBattleResult() == BattleResult.INVALID_ACTION) {
				return BattleResult.INVALID_ACTION;
			}

			System.out.println();
			System.out.println(result.getAction().getMessage());
			System.out.println("아이템명 : " + result.getActionName());

			switch (result.getPotionType()) {
				case "hpRecovered" -> System.out.println("HP 회복 : " + result.getResultValue());
				case "mpRecovered" -> System.out.println("MP 회복 : " + result.getResultValue());
				case "atkBonus" -> System.out.println("공격력 증가 : " + result.getResultValue());
				case "defBonus" -> System.out.println("방어력 증가 : " + result.getResultValue());
				default -> System.out.println("효과 적용 : " + result.getResultValue());
			}

			potionList = inventoryController.showPotionItems();

			return result.getBattleResult();

		} catch (GameException e) {
			FailView.errorMessage(e.getMessage());
			return BattleResult.INVALID_ACTION;
		}
	}

	/**
	 * 몬스터 공격
	 */
	public static BattleResult monsterTurn() {
		boolean beforeGuard = battleHero.isGuardActive();

		BattleActionResultDTO result = battleController.monsterAttack(battleHero, monster);

		System.out.println();
		if (result.getAction() == BattleActionType.MONSTER_SKILL) {
			System.out.println();
			System.out.println("몬스터가 스킬을 사용했다!");
			System.out.println("스킬명 : " + result.getActionName());
		} else {
			System.out.println("몬스터가 공격했다!");
		}
		System.out.println(result.getAction().getMessage());
		System.out.println("주사위 : 🎲 " + result.getDice());
		System.out.println("데미지 : -" + result.getResultValue());

		if (beforeGuard && !battleHero.isGuardActive()) {
			System.out.println("방어 자세 해제됨");
		}

		return result.getBattleResult();
	}

	/**
	 * 도망
	 */
	public static BattleResult escapeTurn() {
		BattleResult result = battleController.escape();

		if (result == BattleResult.ESCAPE) {
			System.out.println("Hero가 도망쳤다!");
		}

		return result;
	}

	/**
	 * 상태 출력
	 */
	public static void showBattleStatus() {
		System.out.println();
		System.out.println("------------- BATTLE LOG -------------");

		int currentAttack = battleHero.getHeroAttack();
		int currentDefense = battleHero.getHeroDefense();

		if (battleHero.isAtkBuffActive()) {
			currentAttack += battleHero.getTempAtkBonus();
		}

		if (battleHero.isDefBuffActive()) {
			currentDefense += battleHero.getTempDefBonus();
		}

		if (battleHero.isGuardActive()) {
			currentDefense += battleHero.getGuardBonus();
		}

		System.out.println("Hero : " + battleHero.getHeroName());
		System.out.println("HP : " + battleHero.getHeroHp());
		System.out.println("MP : " + battleHero.getHeroMp());
		System.out.println("ATK : " + currentAttack);
		System.out.println("DEF : " + currentDefense);

		if (battleHero.isAtkBuffActive()) {
			System.out.println("(공격 버프 적용 중 +" + battleHero.getTempAtkBonus() + ")");
		}

		if (battleHero.isDefBuffActive()) {
			System.out.println("(방어 버프 적용 중 +" + battleHero.getTempDefBonus() + ")");
		}

		if (battleHero.isGuardActive()) {
			System.out.println("(방어 자세 적용 중 +" + battleHero.getGuardBonus() + ")");
		}

		System.out.println();
		System.out.println(monster.getMonsterName());
		System.out.println("HP : " + monster.getMonsterHp());
		System.out.println("ATK : " + monster.getMonsterAttack());
		System.out.println("--------------------------------------");
	}

	/**
	 * 결과 출력
	 */
	public static void showBattleResult(BattleResult result) {
		System.out.println();
		System.out.println("=========== RESULT ===========");

		switch (result) {
			case WIN -> victory();
			case LOSE -> defeat();
			case ESCAPE -> escape();
			default -> System.out.println("종료");
		}

		System.out.println("==============================");
	}

	/**
	 * 승리
	 */
	public static void victory() {
		System.out.println("🏆 승리!");
		System.out.println("획득 경험치 : " + monster.getMonsterRewardExp());
		System.out.println("획득 보석 : " + monster.getMonsterRewardGem());

		List<RewardResult> results = battleController.reward(monster);

		if (results != null) {
			System.out.println("보상 및 성장 정보가 반영되었습니다.\n자세한 정보는 캐릭터 정보에서 확인해주세요.");
			 if (results.contains(RewardResult.FIRST_CLEAR)) {
		            try {
		                StoryView.printStageEndStory(monster.getMonsterStage());
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
		} else {
			System.out.println("보상 처리 실패");
		}
	}

	/**
	 * 패배
	 */
	public static void defeat() {
		System.out.println("💀 패배...");
		applyDefeatPenalty();
	}
	/**
	 * 도망
	 */
	public static void escape() {
		System.out.println("🏃 도망쳤습니다.");
		applyDefeatPenalty();
	}

	/**
	 * 패널티 부여
	 */
	public static void applyDefeatPenalty() {
		boolean penaltyResult = battleController.defeatPenalty();

		if (penaltyResult) {
			System.out.println("경험치와 젬이 일부 감소했습니다.");
		} else {
			System.out.println("패널티 적용 실패");
		}
	}
}