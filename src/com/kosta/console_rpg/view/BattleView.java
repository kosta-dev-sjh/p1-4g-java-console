package com.kosta.console_rpg.view;

import java.util.List;

import com.kosta.console_rpg.controller.BattleController;
import com.kosta.console_rpg.controller.InventoryController;
import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.BattleActionResultDTO;
import com.kosta.console_rpg.model.dto.BattleHeroDTO;
import com.kosta.console_rpg.model.dto.BattlePotionDTO;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.HeroSkillDTO;
import com.kosta.console_rpg.model.dto.MonsterDTO;
import com.kosta.console_rpg.model.enums.BattleActionType;
import com.kosta.console_rpg.model.enums.BattleResult;
import com.kosta.console_rpg.model.enums.RewardResult;
import com.kosta.console_rpg.session.LoginSession;
import com.kosta.console_rpg.util.ConsoleEffectUtil;
import com.kosta.console_rpg.util.InputUtil;

/**
 * 게임 배틀화면 뷰
 *
 * 작성자     : 김재민
 * 생성일     : 2026.03.16
 * 최종 수정자 : 송정현
 * 최종 수정일 : 2026.03.19
 */
public class BattleView {

	private static final BattleController battleController = new BattleController();
	private static final InventoryController inventoryController = new InventoryController();

	private static BattleHeroDTO battleHero;
	private static MonsterDTO monster;
	private static List<HeroSkillDTO> heroSkillList;
	private static List<BattlePotionDTO> potionList;

	private static int nowTurn;
	private static int nowStage;

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
						            StoryView.epilogue();
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
                        StoryView.stage3Start();
                    }

                }
                case 0 -> {  // 뒤로가기: 아무것도 안하고 종료
                    return;
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
				StringBuilder s = new StringBuilder();
				s.append(ConsoleEffectUtil.YELLOW);
				s.append("주의: 보유한 포션 없이 전투를 진행합니다.\n\n");
				s.append(ConsoleEffectUtil.RESET);
				ConsoleEffectUtil.slowPrint(s.toString(),50);

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
	public static void battleLoop() throws Exception {
		showBattleStart();

		while (true) {
			StringBuilder s = new StringBuilder();
			s.append(ConsoleEffectUtil.GREEN);
			s.append("____________________________┌ Battle ┐_______________________________");
			s.append(ConsoleEffectUtil.RESET);
			System.out.println(s);
			System.out.println("\n현재 턴 : " + nowTurn);
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
		StringBuilder s = new StringBuilder();
		s.append(ConsoleEffectUtil.GREEN);
		s.append("\n____________________________┌ Battle ┐_______________________________\"");
		s.append(ConsoleEffectUtil.RESET);
		s.append(String.format("STAGE :  %s ",nowStage ));
		s.append(String.format("MONSTER : %s",monster.getMonsterName() ));


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
	public static void defaultTurn(BattleActionResultDTO result ) {
		System.out.println();

		StringBuilder s = new StringBuilder();
		s.append(ConsoleEffectUtil.GREEN);
		s.append("____________________________┌ Battle ┐_______________________________\n");
		s.append(ConsoleEffectUtil.RESET);
		s.append(String.format("STAGE :  %s\n",nowStage ));
		s.append(String.format("MONSTER : %s\n",monster.getMonsterName() ));
		System.out.println(s);
		StringBuilder sb = new StringBuilder();
		sb.append(String.format(ConsoleEffectUtil.GREEN+"%s"+ConsoleEffectUtil.RESET+" %s\n",battleHero.getHeroName(), result.getAction().getMessage() ));
		sb.append(String.format("주사위 : . . . 🎲 %d\n",result.getDice() ));
		sb.append(String.format("데미지 : "+ConsoleEffectUtil.RED+"%d\n" + ConsoleEffectUtil.RESET,result.getResultValue()));
		try {
			ConsoleEffectUtil.slowPrint(sb.toString(),30);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}



	/**
	 * 공격
	 */
	public static BattleResult attackTurn()  {
		BattleActionResultDTO result = battleController.attack(battleHero, monster);
		defaultTurn(result);

		return result.getBattleResult();
	}



	/**
	 * 방어
	 */
	public static BattleResult defendTurn() {
		BattleActionResultDTO result = battleController.defend(battleHero);
		defaultTurn(result);

		return result.getBattleResult();
	}

	/**
	 * 스킬 사용
	 */
	public static BattleResult skillTurn() {
		try {
			System.out.println(ConsoleEffectUtil.GREEN + "──────────────── SKILL ─────────────────" + ConsoleEffectUtil.RESET);
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
			defaultTurn(result);

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
		if (potionList==null ||potionList.isEmpty()) {
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
		StringBuilder sb = new StringBuilder();
		System.out.println();
		if (result.getAction() == BattleActionType.MONSTER_SKILL) {
			sb.append(String.format(ConsoleEffectUtil.RED+"%s"+ConsoleEffectUtil.RESET+"%s\n",monster.getMonsterName(), result.getAction().getMessage()));
			sb.append(String.format("스킬명 :  %s\n",result.getActionName()));

		} else {
			sb.append(String.format("%s %s\n",monster.getMonsterName(), result.getAction().getMessage()));
		}
		sb.append(String.format("주사위 : . . . 🎲 %d\n",result.getDice() ));
		sb.append(String.format("데미지 : "+ConsoleEffectUtil.RED+"%d\n" + ConsoleEffectUtil.RESET,result.getResultValue()));




		if (beforeGuard && !battleHero.isGuardActive()) {
			System.out.println("방어 자세 해제");
		}

		try {
			ConsoleEffectUtil.slowPrint(sb.toString(),30);
			//showProgressBar(30,50,"");
			System.out.println("계속하려면 아무키나 눌러주세요");
			InputUtil.inputString();


		} catch (Exception e) {
			throw new RuntimeException(e);
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
		System.out.println("┌──────────────── Battle ──────────────────┐");


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

		System.out.println(ConsoleEffectUtil.GREEN +"   Hero : " + battleHero.getHeroName() +ConsoleEffectUtil.RESET);
		System.out.println("   HP : " + battleHero.getHeroHp());
		System.out.println("   MP : " + battleHero.getHeroMp());
		System.out.println("   ATK : " + currentAttack);
		System.out.println("   DEF : " + currentDefense);

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
		System.out.println("   "+ ConsoleEffectUtil.RED + monster.getMonsterName() + ConsoleEffectUtil.RESET);
		System.out.println("   HP : " + monster.getMonsterHp());
		System.out.println("   ATK : " + monster.getMonsterAttack());
		System.out.println("└──────────────────────────────────────────┘");
	}

	/**
	 * 결과 출력
	 */
	public static void showBattleResult(BattleResult result) {
		System.out.println();
		System.out.println("┌──────────────── Result ──────────────────┐");

		switch (result) {
			case WIN -> victory();
			case LOSE -> defeat();
			case ESCAPE -> escape();
			default -> System.out.println("   종료");
		}

		System.out.println("└──────────────────────────────────────────┘");
	}

	/**
	 * 승리
	 */
	public static void victory() {
		System.out.println("   ▸🏆 승리!");
		System.out.println("   ▸획득 경험치 : " + monster.getMonsterRewardExp());
		System.out.println("   ▸획득 보석 : " + monster.getMonsterRewardGem());

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
			System.out.println("   보상 처리 실패");
		}
	}

	/**
	 * 패배
	 */
	public static void defeat() {
		System.out.println("   ▸💀 패배...");
		applyDefeatPenalty();
	}
	/**
	 * 도망
	 */
	public static void escape() {
		System.out.println("   ▸🏃 도망쳤습니다.");
		applyDefeatPenalty();
	}

	/**
	 * 패널티 부여
	 */
	public static void applyDefeatPenalty() {
		boolean penaltyResult = battleController.defeatPenalty();

		if (penaltyResult) {
			System.out.println("   경험치와 젬이 일부 감소했습니다.");
		} else {
			System.out.println("   패널티 적용 실패");
		}
	}
}
