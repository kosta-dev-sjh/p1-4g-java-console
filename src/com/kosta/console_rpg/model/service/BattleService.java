package com.kosta.console_rpg.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dao.MonsterDAO;
import com.kosta.console_rpg.model.dao.MonsterDAOImpl;
import com.kosta.console_rpg.model.dto.BattleHeroDTO;
import com.kosta.console_rpg.model.dto.BattlePotionDTO;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.HeroSkillDTO;
import com.kosta.console_rpg.model.dto.MonsterDTO;
import com.kosta.console_rpg.model.dto.QuestDTO;
import com.kosta.console_rpg.model.enums.BattleResult;
import com.kosta.console_rpg.model.enums.RewardResult;
import com.kosta.console_rpg.session.LoginSession;

/**
 * 전투 관련 비즈니스 로직을 처리하는 Service 클래스
 * - 스테이지별 몬스터 조회
 * - 전투 시작
 * - 일반 공격 / 스킬 공격 계산
 *
 * 작성자 : 김재민
 * 생성일 : 2026.03.13
 * 최종 수정자 : 송정현
 * 최종 수정일 : 2026.03.16
 */
public class BattleService {

	// ======= field =======
	private final MonsterDAO monsterDao = new MonsterDAOImpl();
	private final HeroService heroService = new HeroService();
	private final SkillService skillService = new SkillService();
	private final QuestService questService = new QuestService();
	private final InventoryService inventoryService = new InventoryService();
	private final static int LEVEL_UP_EXP_MULTIPLIER = 100; // 레벨업에 필요한 경험치 공식에서 곱해지는 값
	private final static int SKILL_BONUS = 5; // 스킬 레벨당 추가 데미지
	private final static int HERO_MAX_LEVEL = 10; // 히어로 최대 레벨
	private static final int LEVEL_UP_HP_BONUS = 10; // 레벨업 시 HP 증가량
	private static final int LEVEL_UP_MP_BONUS = 5; // 레벨업 시 MP 증가량
	private static final int LEVEL_UP_ATTACK_BONUS = 2; // 레벨업 시 공격력 증가량
	private static final int LEVEL_UP_DEFENSE_BONUS = 1; // 레벨업 시 방어력 증가량
	private final static int MAX_CLEAR_STAGE = 3; // 클리어 가능한 최대 스테이지

	// ======= public method =======

	/**
	 * 선택한 스테이지에 해당하는 몬스터 정보를 조회한다.
	 * 현재 로그인한 영웅의 최대 클리어 스테이지 + 1 범위까지 조회 가능하다.
	 *
	 * @param selectStage 사용자가 선택한 스테이지 번호
	 * @return MonsterDTO 해당 스테이지 몬스터 정보
	 * @throws GameException 도전 가능한 스테이지 조건을 만족하지 못한 경우,
	 *                       로그인 세션이 만료되었거나 영웅 정보가 없는 경우,
	 *                       선택한 스테이지가 도전 가능한 범위를 벗어난 경우
	 */
	public MonsterDTO selectMonsterByStage(int selectStage) throws GameException {

		MonsterDTO monster = null;

		try {
			// 1. 먼저 몬스터가 존재하는지 조회 [확장성을 생각해 하드코딩 제외]
			monster = monsterDao.selectMonsterByStage(selectStage);

			if (monster == null) {
				throw new GameException("조회된 몬스터가 존재하지 않습니다.");
			}

			// 2. 이후 히어로가 해당 몬스터랑 싸울 수 있는지를 판별
			HeroDTO hero = LoginSession.getInstance().getCurrentHero();

			// 히어로 정보가 없는 경우 (로그인 세션이 만료된 경우 등)
			if (hero == null) {
				throw new GameException("로그인 정보가 없습니다.");
			}

			// ㄴ 선택한 스테이지 값 보다도 히어로 스테이지+1값보다 낮으면 진입불가
			if (selectStage > hero.getHeroMaxClearStage() + 1) {
				throw new GameException("스테이지 조건이 불충분합니다.");
			}

		} catch (SQLException e) {
			throw new GameException("몬스터 조회 중 오류가 발생했습니다.");
		}

		return monster;
	}

	/**
	 * 현재 로그인한 영웅 정보를 기반으로 전투용 BattleHeroDTO 객체를 생성한다.
	 * 전투용 BattleHeroDTO는 실제 전투에서 사용되는 영웅의 상태를 나타내며, 로그인 세션의 영웅 정보와는 별도의 객체로 관리된다.
	 *
	 * @return BattleHeroDTO 전투용 복사 영웅 객체
	 */
	public BattleHeroDTO createBattleHero() throws GameException {
		HeroDTO hero = LoginSession.getInstance().getCurrentHero();

		if (hero == null) {
			throw new GameException("로그인 정보가 없습니다.");
		}

		return new BattleHeroDTO(
				hero.getHeroName(),
				hero.getHeroLevel(),
				hero.getHeroExp(),
				hero.getHeroHp(),
				hero.getHeroMp(),
				hero.getHeroAttack(),
				hero.getHeroDefense());
	}

	/**
	 * 공격력 조회 메서드
	 * 공격 버프가 활성화된 경우, 일시적으로 공격력에 보너스를 적용한다.
	 *
	 * @param hero 전투용 영웅 객체
	 * @return int 최종 공격력
	 */
	public int getCurrentAttack(BattleHeroDTO hero) {
		int attack = hero.getHeroAttack();

		if (hero.isAtkBuffActive()) {
			attack += hero.getTempAtkBonus();
		}

		return attack;
	}

	/**
	 * 현재 전투 중 적용되는 방어력을 조회한다.
	 * - 아이템 방어 버프가 활성화된 경우 추가 방어력을 반영한다.
	 * - 방어 자세(guard)가 활성화된 경우 해당 턴 방어 보너스를 반영한다.
	 *
	 * @param hero 전투용 영웅 객체
	 * @return int 최종 방어력
	 */
	public int getCurrentDefense(BattleHeroDTO hero) {
		int defense = hero.getHeroDefense();

		if (hero.isDefBuffActive()) {
			defense += hero.getTempDefBonus();
		}
		if (hero.isGuardActive()) {
			defense += hero.getGuardBonus();
		}
		return defense;
	}

	/**
	 * 공격 데미지를 계산한다.
	 * 데미지 계산 공식: (공격력 - 상대 방어력) * 주사위 보정
	 * - 주사위 보정: 1이 나오면 0.7배
	 * - 주사위 보정: 6이 나오면 1.5배
	 * - 주사위 보정: 그 외에는 1.0배
	 * - 방어력이 공격력보다 높으면 데미지는 0으로 처리한다
	 *
	 * @param attack  공격 주체의 공격력
	 * @param defense 상대 방어력
	 * @return int 실제 적용된 데미지
	 */
	public int calculateAttackDamage(int attack, int defense, int dice) {
		double multiplier = getDiceMultiplier(dice);
		int baseDamage = (int) (attack * multiplier);

		int damage = baseDamage - defense;

		if (damage <= 0) {
			return 0; // 방어력이 공격력보다 높으면 데미지는 0
		}

		return damage;
	}

	/**
	 * 스킬 공격 데미지를 계산한다.
	 * 스킬 데미지 계산 공식: [(공격력 - 상대 방어력) × 주사위 보정] + 스킬 기본 데미지 + (스킬 레벨 - 1) *
	 * SKILL_BONUS
	 * - 스킬 레벨이 높을수록 추가 데미지가 증가한다.
	 * - 주사위 결과에 따른 데미지 보정은 일반 공격과 동일하게 적용된다.
	 *
	 * @param attack      공격 주체의 공격력
	 * @param skillDamage 공격 주체의 사용 스킬 데미지
	 * @param defense     상대 방어력
	 * @param skillLevel  사용 스킬 레벨
	 * @return int 실제 적용된 스킬 데미지
	 */
	public int calculateSkillDamage(int attack, int skillDamage, int skillLevel, int defense, int dice) {
		return calculateAttackDamage(attack, defense, dice) + skillDamage + (skillLevel - 1) * SKILL_BONUS; // 스킬 레벨당 추가
	}

	/**
	 * 스킬 사용 시 MP 소모량이 충분한지 확인한다.
	 *
	 * @param hero      전투용 영웅 객체
	 * @param heroSkill 사용하려는 스킬 정보
	 * @return boolean MP가 충분하면 true, 그렇지 않으면 false
	 */
	public boolean canUseSkill(BattleHeroDTO hero, HeroSkillDTO heroSkill) {
		return hero.getHeroMp() >= heroSkill.getCalculatedMpCost();
	}

	/**
	 * 스킬 사용 시 MP를 소모한다.
	 * MP 소모량은 스킬의 기본 MP 소모량에 스킬 레벨에 따른 추가 소모량을 더한 값으로 계산된다.
	 * 
	 *
	 * @param hero      전투용 영웅 객체
	 * @param heroSkill 사용하려는 스킬 정보
	 */
	public void consumeSkillMp(BattleHeroDTO hero, HeroSkillDTO heroSkill) {
		hero.setHeroMp(hero.getHeroMp() - heroSkill.getCalculatedMpCost());
	}

	/**
	 * 방어 계산을 수행한다.
	 * 방어 계산은 상대의 공격을 얼마나 효과적으로 막아내는지를 결정한다.
	 * 방어 계산 공식: 방어력 × 주사위 보정
	 * - 주사위 보정이 적용되어 방어 효과가 변동될 수 있다.
	 * - 주사위 보정: 1이 나오면 방어 효과가 0.7배로 감소한다.
	 * - 주사위 보정: 6이 나오면 방어 효과가 1.5배로 증가한다.
	 * - 주사위 보정: 그 외에는 방어 효과가 1.0배로 유지된다.
	 *
	 * @param defense 방어 주체의 방어력
	 * @return int 적용된 방어 수치
	 */
	public int calculateDefense(int defense, int dice) {

		double multiplier = getDiceMultiplier(dice);

		int effectiveDefense = (int) (defense * multiplier);

		return effectiveDefense;
	}

	/**
	 * 아이템 효과를 현재 전투용 영웅 객체에 적용한다.
	 * 아이템 효과는 HP 회복, MP 회복, 공격력 증가, 방어력 증가 등 다양한 형태로 존재할 수 있다.
	 * 공격력 증가, 방어력 증가 아이템은 일시적으로 전투 중에만 적용되며, 전투가 종료되면 원래 상태로 돌아간다.
	 *
	 * @param hero 현재 전투 중인 영웅 객체
	 * @param item 사용한 포션 객체
	 * @return Map<String, Integer> 사용 성공 시 효과 정보 반환
	 *         - "hpRecovered" : 회복된 HP 값
	 *         - "mpRecovered" : 회복된 MP 값
	 *         - "atkBonus" : 증가한 공격력 값
	 *         - "defBonus" : 증가한 방어력 값
	 *         사용 가능한 효과만 key로 포함한다.
	 *
	 *         아이템 사용 불가 시 빈 Map 반환
	 */
	public Map<String, Integer> useItem(BattleHeroDTO hero, BattlePotionDTO item) throws GameException {
		Map<String, Integer> effectMap = new HashMap<>();

		if (item.getQuantity() <= 0) {
			throw new GameException("해당 아이템의 수량이 부족합니다.");
		}

		HeroDTO loginHero = LoginSession.getInstance().getCurrentHero();

		if (loginHero == null) {
			throw new GameException("로그인 정보가 없습니다.");
		}

		int maxHp = loginHero.getHeroHp();
		int maxMp = loginHero.getHeroMp();

		int finalHp = hero.getHeroHp();
		int finalMp = hero.getHeroMp();

		// HP 회복 계산
		if (item.getItemEffectHp() != 0) {
			if (hero.getHeroHp() < maxHp) {
				int newHp = hero.getHeroHp() + item.getItemEffectHp();
				int actualHpRecovered = Math.min(newHp, maxHp) - hero.getHeroHp();

				finalHp = Math.min(newHp, maxHp);
				effectMap.put("hpRecovered", actualHpRecovered);
			}
		}

		// MP 회복 계산
		if (item.getItemEffectMp() != 0) {
			if (hero.getHeroMp() < maxMp) {
				int newMp = hero.getHeroMp() + item.getItemEffectMp();
				int actualMpRecovered = Math.min(newMp, maxMp) - hero.getHeroMp();

				finalMp = Math.min(newMp, maxMp);
				effectMap.put("mpRecovered", actualMpRecovered);
			}
		}

		// 공격 버프
		if (item.getItemAtkBonus() != 0) {
			effectMap.put("atkBonus", item.getItemAtkBonus());
		}

		// 방어 버프
		if (item.getItemDefBonus() != 0) {
			effectMap.put("defBonus", item.getItemDefBonus());
		}

		// 적용 가능한 효과가 없으면 종료
		if (effectMap.isEmpty()) {
			return effectMap;
		}

		// DB 차감 먼저
		try {
			inventoryService.usePotion(loginHero.getHeroId(), item.getInventoryId());
		} catch (GameException e) {
			throw new GameException("아이템 사용 중 오류가 발생했습니다");
		}

		// 실제 hero 반영 (DB 성공 후)
		hero.setHeroHp(finalHp);
		hero.setHeroMp(finalMp);

		// 공격 버프 적용
		if (item.getItemAtkBonus() != 0) {
			hero.clearAtkBuff();
			hero.setTempAtkBonus(item.getItemAtkBonus());
			hero.setAtkBuffActive(true);
			hero.setAtkBuffTurn(1);
		}

		// 방어 버프 적용
		if (item.getItemDefBonus() != 0) {
			hero.clearDefBuff();
			hero.setTempDefBonus(item.getItemDefBonus());
			hero.setDefBuffActive(true);
			hero.setDefBuffTurn(1);
		}

		return effectMap;
	}

	/**
	 * 전투를 포기하고 종료한다.
	 *
	 * @return BattleResult 도주 결과 반환
	 */
	public BattleResult escape() {
		return BattleResult.ESCAPE;
	}

	/**
	 * 전투 종료 후 보상 지급 로직을 처리한다.
	 * - 몬스터가 제공하는 경험치와 젬을 영웅에게 지급한다.
	 * - 영웅이 경험치를 획득하여 레벨업이 필요한 경우, 레벨업 처리를 수행한다.
	 * - 레벨업 시 영웅의 최대 HP, 최대 MP, 공격력, 방어력이 증가한다.
	 * - 레벨업 시 경험치가 초기화되고, 최대 레벨에 도달한 경우에는 경험치 초기화 및 레벨업 불가 처리를 한다.
	 * - 보상 지급 과정에서 문제가 발생한 경우 예외를 발생시킨다.
	 * - 보상 지급과 레벨업 처리 후, 업적 진행도 증가 로직도 함께 처리한다.
	 * - 레벨업과 업적 진행도 증가 여부에 따라 반환값이 달라질 수 있다.
	 * - 레벨업과 업적 진행도 증가가 동시에 발생할 수도 있다.
	 * 
	 * @param monster
	 * @return
	 * @throws GameException
	 */
	public List<RewardResult> reward(MonsterDTO monster) throws GameException {

		List<RewardResult> rewardResults = new ArrayList<>();
		HeroDTO hero = LoginSession.getInstance().getCurrentHero();
		Connection con = null;

		// 보상 처리 전 기본적인 유효성 검사 -----------------------------------------
		if (monster == null) {
			throw new GameException("보상 처리할 몬스터 정보가 없습니다.");
		}
		if (LoginSession.getInstance().getCurrentHero() == null) {
			throw new GameException("로그인 정보가 없습니다.");
		}
		if (monster.getMonsterRewardExp() < 0 || monster.getMonsterRewardGem() < 0) {
			throw new GameException("몬스터 보상 정보가 유효하지 않습니다.");
		}
		if (monster.getMonsterRewardExp() == 0 && monster.getMonsterRewardGem() == 0) {
			throw new GameException("몬스터 보상이 존재하지 않습니다.");
		}
		if (monster.getMonsterRewardExp() > 0 && monster.getMonsterRewardGem() > 0) {
			rewardResults.add(RewardResult.REWARD);
			hero.setHeroExp(hero.getHeroExp() + monster.getMonsterRewardExp());
			hero.setHeroGem(hero.getHeroGem() + monster.getMonsterRewardGem());
		}
		// 레벨업 여부 계산 ----------------------------------------
		if (hero.getHeroLevel() >= HERO_MAX_LEVEL) {
			hero.setHeroExp(0); // 최대 레벨 도달 시 경험치 초기화
			rewardResults.add(RewardResult.MAX_LEVEL);
		} else {
			int expForNextLevel = hero.getHeroLevel() * LEVEL_UP_EXP_MULTIPLIER; // 예시: 레벨업에 필요한 경험치 공식
			boolean levelUp = hero.getHeroExp() >= expForNextLevel;

			if (levelUp) {
				// 레벨업 후 남은 경험치 계산
				hero.setHeroLevel(hero.getHeroLevel() + 1);
				hero.setHeroExp(hero.getHeroExp() - expForNextLevel);

				// HP는 레벨 당 10씩 증가, MP는 레벨 당 5씩 증가
				hero.setHeroHp(hero.getHeroHp() + LEVEL_UP_HP_BONUS);
				hero.setHeroMp(hero.getHeroMp() + LEVEL_UP_MP_BONUS);

				// 공격력 레벨 당 2씩 증가
				hero.setHeroAttack(hero.getHeroAttack() + LEVEL_UP_ATTACK_BONUS);
				// 방어력 레벨 당 1씩 증가
				hero.setHeroDefense(hero.getHeroDefense() + LEVEL_UP_DEFENSE_BONUS);

				rewardResults.add(RewardResult.LEVEL_UP);
			}
		}
		// 영웅 정보 업데이트 ----------------------------------------
		heroService.updateHero(con, hero);
		heroService.updateHeroGem(con, hero.getHeroId(), hero.getHeroGem());

		// 업적 진행도 증가 로직 ----------------------------------------
		int stage = monster.getMonsterStage();
		boolean questUpdated = false;

		try {
			List<QuestDTO> questList = questService.selectQuestIng(hero.getHeroId());

			for (QuestDTO quest : questList) {
				boolean updated = false;

				if (quest.getQuestId() == 1 && stage == 1) {
					int next = Math.min(quest.getQuestIngProgress() + 1, quest.getQuestTarget());
					quest.setQuestIngProgress(next);
					updated = true;
				}

				if (quest.getQuestId() == 2 && stage == 3) {
					int next = Math.min(quest.getQuestIngProgress() + 1, quest.getQuestTarget());
					quest.setQuestIngProgress(next);
					updated = true;
				}

				if (quest.getQuestId() == 3 && stage >= 3 && quest.getQuestIngProgress() == 0) {
					quest.setQuestIngProgress(1);
					updated = true;
				}

				if (quest.getQuestIngProgress() >= quest.getQuestTarget()) {
					quest.setQuestIngComplete(true);
				}

				if (updated) {
					questService.updateQuestProgress(quest);
					questUpdated = true;
				}
			}

			if (questUpdated) {
				rewardResults.add(RewardResult.QUEST_PROGRESS);
			}
		} catch (SQLException e) {
			throw new GameException("퀘스트 반영 중 오류가 발생했습니다.");
		}

		// 현재 진행중인 스테이지보다 높은 스테이지 클리어 시 최대 클리어 스테이지 갱신 ----------------------------------------
		if (stage > hero.getHeroMaxClearStage() && stage <= MAX_CLEAR_STAGE) {
			hero.setHeroMaxClearStage(stage);
			heroService.updateClearStage(con, hero.getHeroId(), stage);
		}


		LoginSession.getInstance().setCurrentHero(hero); // 세션 정보도 업데이트
		return rewardResults;
	} // reward 메서드 끝

	/**
	 * 
	 * 현재 로그인한 영웅이 보유한 스킬 정보를 조회한다.
	 * 
	 * @return
	 * @throws GameException
	 */
	public List<HeroSkillDTO> getHeroSkills() throws GameException {
		List<HeroSkillDTO> skills = null;
		try {
			skills = skillService.selectHeroSkills(LoginSession.getInstance().getCurrentHero().getHeroId());
		} catch (GameException e) {
			throw new GameException("스킬 조회 중 오류가 발생했습니다");
		}

		return skills;
	}

	/**
	 * 주사위 결과에 따른 공격력/방어력 보정값을 반환한다.
	 * - 주사위 결과가 1인 경우, 보정값은 0.7배로 감소한다.
	 * - 주사위 결과가 6인 경우, 보정값은 1.5배로 증가한다.
	 * - 그 외의 주사위 결과인 경우, 보정값은 1.0배로 유지된다.
	 * 
	 * @param dice
	 * @return double 주사위 보정값
	 * @throws GameException 주사위 값이 1~6 범위를 벗어난 경우 예외 발생
	 */
	private double getDiceMultiplier(int dice) {
		return switch (dice) {
			case 1 -> 0.7;
			case 6 -> 1.5;
			default -> 1.0;
		};
	}

}