package com.kosta.console_rpg.test;

import com.kosta.console_rpg.controller.BattleController;
import com.kosta.console_rpg.controller.InventoryController;
import com.kosta.console_rpg.controller.UserController;
import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.*;
import com.kosta.console_rpg.model.enums.BattleActionType;
import com.kosta.console_rpg.model.enums.BattleResult;
import com.kosta.console_rpg.session.LoginSession;
import com.kosta.console_rpg.util.InputUtil;
import com.kosta.console_rpg.view.FailView;
import com.kosta.console_rpg.view.StageView;
import com.kosta.console_rpg.view.StoryView;

import java.util.List;

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

//	public static void main(String[] args) {
//		UserController userController = new UserController();
//		userController.login("b5", "b5");
//
//		startBattle();
//	}

	/**
	 * 전투 시작
	 */
	public static void startBattle() {
		HeroDTO hero = LoginSession.getInstance().getCurrentHero();
		while (true) {
			try {
				//스테이지 리스트 출력
				StageView.showStage();
				//원하는 스테이지 입력
				nowStage = InputUtil.inputInt();
				switch (nowStage) {
                case 1 -> {
                    //스테이지 클리어최대치에 따른 스토리 진입
                    if(hero.getHeroMaxClearStage() <= 0) {
                        StoryView.stage1Start();	//스토리 시작
                    }

                }
                case 2 -> {
                    if(hero.getHeroMaxClearStage() <= 1) {
                        StoryView.stage2Start();
                    }

                }
                case 3 -> {
                    if(hero.getHeroMaxClearStage() <= 2) {
                        StoryView.stage2Start();
                    }

                }
                case 0 -> {  // 뒤로가기: 아무것도 안하고 종료
                    break;
                }
                default -> System.out.println("잘못된 입력입니다.");
            }

			battleHero = battleController.createBattleHero();
			monster = battleController.selectMonsterByStage(nowStage);

			if (battleHero == null || monster == null) {
				FailView.errorMessage("전투 시작에 필요한 정보가 부족합니다.");
				continue;
			}

			heroSkillList = battleController.getHeroSkills();
			if (heroSkillList == null || heroSkillList.isEmpty()) {
				FailView.errorMessage("보유한 스킬이 없습니다.");
				continue;
			}

			potionList = inventoryController.showPotionItems();
			if (potionList == null || potionList.isEmpty()) {
				System.out.println("보유한 포션 없이 전투를 진행합니다.");
			}

			nowTurn = 1;
			battleLoop();

			} catch (GameException e) {
				FailView.errorMessage(e.getMessage());
			} catch (Exception e) {
                throw new RuntimeException(e);
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
		System.out.println(battleHero.getHeroName() + result.getAction().getMessage());
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
		System.out.println(battleHero.getHeroName() + result.getAction().getMessage());
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
				FailView.errorMessage("MP가 부족하여 스킬을 사용할 수 없습니다.");
				return BattleResult.INVALID_ACTION;
			}

			System.out.println();
			System.out.println(battleHero.getHeroName() + result.getAction().getMessage());
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
		if (potionList.isEmpty()) {
			System.out.println("사용 가능한 포션이 없습니다.");
			return BattleResult.INVALID_ACTION;
		}
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
			System.out.println(battleHero.getHeroName() + result.getAction().getMessage());
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
			System.out.println(monster.getMonsterName() + result.getAction().getMessage());
			System.out.println("스킬명 : " + result.getActionName());
		} else {
			System.out.println(monster.getMonsterName() + result.getAction().getMessage());
		}
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
		BattleActionResultDTO result = battleController.escape();

		if (result.getBattleResult() == BattleResult.ESCAPE) {
			System.out.println(battleHero.getHeroName() + result.getAction().getMessage());
		}

		return result.getBattleResult();
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

		boolean rewardResult = battleController.reward(monster);

		if (rewardResult) {
			System.out.println("보상 및 성장 정보가 반영되었습니다.\n자세한 정보는 캐릭터 정보에서 확인해주세요.");
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