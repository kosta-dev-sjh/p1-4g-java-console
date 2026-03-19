package com.kosta.console_rpg.controller;

import java.util.List;
import java.util.Map;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.BattleActionResultDTO;
import com.kosta.console_rpg.model.dto.BattleHeroDTO;
import com.kosta.console_rpg.model.dto.BattlePotionDTO;
import com.kosta.console_rpg.model.dto.HeroSkillDTO;
import com.kosta.console_rpg.model.dto.MonsterDTO;
import com.kosta.console_rpg.model.dto.SkillDTO;
import com.kosta.console_rpg.model.enums.BattleActionType;
import com.kosta.console_rpg.model.enums.BattleResult;
import com.kosta.console_rpg.model.enums.RewardResult;
import com.kosta.console_rpg.model.service.BattleService;
import com.kosta.console_rpg.util.RandomUtil;
import com.kosta.console_rpg.view.FailView;
import com.kosta.console_rpg.util.RandomUtil;
import com.kosta.console_rpg.view.FailView;

/**
 * 게임 전투 흐름과 전투 관련 사용자 요청을 제어하는 컨트롤러
 *
 * 작성자 : 김재민
 * 생성일 : 2026-03-13
 * 최종 수정자 : 송정현
 * 최종 수정일 : 2026-03-16
 * 작성자 : 김재민
 * 생성일 : 2026-03-13
 * 최종 수정자 : 송정현
 * 최종 수정일 : 2026-03-16
 */
public class BattleController {
	private final BattleService battleService = new BattleService();

	/**
	 * 선택한 스테이지에 해당하는 몬스터 정보를 조회한다.
	 * 
	 * @param selectStage
	 * @return MonsterDTO 해당 스테이지 몬스터 정보
	 */
	public MonsterDTO selectMonsterByStage(int selectStage) {
		MonsterDTO monster = null;
		try {
			monster = battleService.selectMonsterByStage(selectStage);

		} catch (GameException e) {
			FailView.errorMessage(e.getMessage());
		}
		return monster;
	}

	/**
	 * 현재 로그인한 영웅 정보를 기반으로 전투용 복사 영웅 객체를 생성하여 반환한다.
	 * 
	 * @param hero 현재 로그인한 영웅 정보
	 * @return BattleHeroDTO 전투용 복사 영웅 객체
	 */
	public BattleHeroDTO createBattleHero() {
		BattleHeroDTO battleHero = null;
		try {
			battleHero = battleService.createBattleHero();
		} catch (GameException e) {
			FailView.errorMessage(e.getMessage());
		}
		return battleHero;
	}

	/**
	 * 1번항목) 일반 공격 선택
	 * - 영웅의 현재 공격력과 몬스터의 방어력을 기반으로 피해량 계산
	 * - 몬스터의 HP에서 피해량만큼 감소
	 * - 몬스터의 HP가 0 이하가 되면 승리, 그렇지 않으면 전투 지속
	 *
	 * @param hero    전투용 영웅 객체
	 * @param monster 전투 대상 몬스터 객체
	 * @return BattleActionResultDTO 공격 결과 정보 (전투 결과, 피해량, 주사위 결과 등)
	 */
	public BattleActionResultDTO attack(BattleHeroDTO hero, MonsterDTO monster) {
		int attack = battleService.getCurrentAttack(hero);

		int dice = RandomUtil.diceRoll();
		int damage = battleService.calculateAttackDamage(attack, monster.getMonsterDefense(), dice);

		monster.setMonsterHp(Math.max(0, monster.getMonsterHp() - damage));

		BattleResult result = monster.getMonsterHp() <= 0
				? BattleResult.WIN
				: BattleResult.CONTINUE;

		return new BattleActionResultDTO(result, damage, dice, BattleActionType.HERO_ATTACK);
	}

	/**
	 * 몬스터가 영웅을 공격한다.
	 * - 몬스터의 공격력과 영웅의 현재 방어력을 기반으로 피해량 계산
	 * - 확률적으로 몬스터는 스킬을 사용한다.
	 * - 영웅의 HP에서 피해량만큼 감소
	 * - 영웅의 HP가 0 이하가 되면 패배, 그렇지 않으면 전투 지속
	 *
	 * @param hero    전투용 영웅 객체
	 * @param monster 전투 대상 몬스터 객체
	 * @return BattleActionResultDTO 몬스터 공격 결과 정보 (전투 결과, 피해량, 주사위 결과 등)
	 */
	public BattleActionResultDTO monsterAttack(BattleHeroDTO hero, MonsterDTO monster) {
		int defense = battleService.getCurrentDefense(hero);
		int dice = RandomUtil.diceRoll();
		int damage = 0;
		BattleActionResultDTO resultDTO = null;
		boolean usedSkill = false;
		String skillName = null;
		try {
			if (RandomUtil.probabilityCheck(monster.getMonsterSkillProb())) {
				SkillDTO monsterSkill = monster.getMonsterSkillInfo();
				damage = battleService.calculateSkillDamage(
						monster.getMonsterAttack(),
						monsterSkill.getSkillDamage(),
						1, // 몬스터 스킬 레벨은 1로 고정
						defense,
						dice);

				usedSkill = true;
				skillName = monsterSkill.getSkillName();

			} else {
				// 일반 공격 로직
				damage = battleService.calculateAttackDamage(monster.getMonsterAttack(), defense, dice);

			}
		} catch (GameException e) {
			FailView.errorMessage(e.getMessage());
		}

		hero.setHeroHp(Math.max(0, hero.getHeroHp() - damage));

		if (hero.isGuardActive()) {
			hero.clearGuard();
		}

		BattleResult result = hero.getHeroHp() <= 0
				? BattleResult.LOSE
				: BattleResult.CONTINUE;

		if (usedSkill) {
			resultDTO = new BattleActionResultDTO(result, damage, dice, skillName, BattleActionType.MONSTER_SKILL);
		} else {
			resultDTO = new BattleActionResultDTO(result, damage, dice, BattleActionType.MONSTER_ATTACK);
		}

		return resultDTO;
	}

	/**
	 * 2번항목) 방어 선택
	 * - 영웅이 방어 자세를 취하여 다음 몬스터 공격까지 일시적으로 방어력 증가 효과 적용
	 * - 방어 효과는 다음 몬스터 공격이 끝난 후 사라진다.
	 * 
	 * @param hero 전투용 영웅 객체
	 * @return BattleActionResultDTO 방어 결과 정보 (성공 여부, 방어력 증가량, 주사위 결과 등)
	 */
	public BattleActionResultDTO defend(BattleHeroDTO hero) {

		int baseDefense = battleService.getCurrentDefense(hero);
		int dice = RandomUtil.diceRoll();
		int defendValue = battleService.calculateDefense(baseDefense, dice);

		hero.setGuardBonus(defendValue);
		hero.setGuardActive(true);

		return new BattleActionResultDTO(BattleResult.CONTINUE, defendValue, dice, BattleActionType.DEFEND);
	}

	/**
	 * 3번항목) 스킬 사용
	 * - 영웅이 보유한 스킬 목록에서 선택한 스킬을 사용하여 몬스터에게 피해를 입힌다.
	 * - 스킬 사용 시 MP 소모는 별도의 로직에서 처리한다.
	 *
	 * @param hero      전투용 영웅 객체
	 * @param monster   전투 대상 몬스터 객체
	 * @param heroSkill 사용한 스킬 객체
	 * @return BattleActionResultDTO 사용 결과 정보 (성공 여부, 피해량, 주사위 결과 등)
	 */
	public BattleActionResultDTO useSkill(BattleHeroDTO hero, MonsterDTO monster, HeroSkillDTO heroSkill) {
		int dice = RandomUtil.diceRoll();
		SkillDTO skill = heroSkill.getSkill();

		if (battleService.canUseSkill(hero, heroSkill)) {
			battleService.consumeSkillMp(hero, heroSkill);
		} else {
			return new BattleActionResultDTO(BattleResult.CONTINUE);
		}

		int damage = battleService.calculateSkillDamage(
				hero.getHeroAttack(),
				skill.getSkillDamage(),
				heroSkill.getSkillLevel(),
				monster.getMonsterDefense(),
				dice);

		monster.setMonsterHp(Math.max(0, monster.getMonsterHp() - damage));

		BattleResult result = monster.getMonsterHp() <= 0
				? BattleResult.WIN
				: BattleResult.CONTINUE;

		return new BattleActionResultDTO(result, damage, dice, skill.getSkillName(), BattleActionType.HERO_SKILL);
	}

	/**
	 * 4번항목) 아이템 사용
	 * 
	 * @param hero   전투용 영웅 객체
	 * @param potion 사용한 포션 객체
	 * @return BattleActionResultDTO 사용 성공 시 효과 정보 반환
	 */
	public BattleActionResultDTO useItem(BattleHeroDTO hero, BattlePotionDTO potion) {
		Map<String, Integer> effectMap = null;

		try {
			effectMap = battleService.useItem(hero, potion);

		} catch (GameException e) {
			FailView.errorMessage(e.getMessage());
			return new BattleActionResultDTO(BattleResult.INVALID_ACTION);
		}

		if (effectMap.isEmpty()) {
			FailView.errorMessage("포션 사용 실패... 이미 최대치입니다. 행동을 다시 선택해주세요.");
			return new BattleActionResultDTO(BattleResult.INVALID_ACTION);
		}

		String potionType = effectMap.keySet().iterator().next();
		int resultValue = effectMap.get(potionType);

		return new BattleActionResultDTO(
				BattleResult.CONTINUE,
				resultValue,
				potionType,
				potion.getItemName(),
				BattleActionType.HERO_ITEM);
	}

	/**
	 * 5번항목) 전투에서 도망
	 *
	 * @return BattleResult 전투 결과 (승리, 패배, 지속)
	 */
	public BattleResult escape() {
		return battleService.escape();
	}

	/**
	 * 승리 보상 처리
	 * - 몬스터가 제공하는 경험치와 보석을 현재 로그인한 영웅에게 추가한다.
	 * - 영웅 정보 업데이트는 HeroService의 updateHero 메서드를 통해 수행한다.
	 * - 보상 처리 중 예외가 발생할 경우 FailView를 통해 오류 메시지를 출력한다.
	 * 
	 * @param monster 승리한 몬스터 객체 (보상 정보 포함)
	 * @throws GameException 보상 처리 중 발생할 수 있는 예외
	 * @return void
	 */
	public List<RewardResult> reward(MonsterDTO monster) {
		try {
			return battleService.reward(monster);
		} catch (GameException e) {
			FailView.errorMessage(e.getMessage());
		}
		return null;
	}

	/**
	 * 영웅이 보유한 스킬 목록을 조회한다.
	 * 
	 * @return List<HeroSkillDTO> 영웅이 보유한 스킬 목록
	 * @throws GameException 스킬 조회 중 발생할 수 있는 예외
	 */
	public List<HeroSkillDTO> getHeroSkills() {
		List<HeroSkillDTO> skills = null;
		try {
			skills = battleService.getHeroSkills();
			if (skills == null || skills.isEmpty()) {
				FailView.errorMessage("보유한 스킬이 없습니다.");
			}
		} catch (GameException e) {
			FailView.errorMessage(e.getMessage());
		}
		return skills;
	}

	/**
	 * 전투 패배 후 패널티를 적용한다.
	 *
	 * @return boolean 패널티 처리 성공 여부
	 */
	public boolean defeatPenalty() {
		try {
			battleService.defeatPenalty();
			return true;
		} catch (GameException e) {
			FailView.errorMessage(e.getMessage());
		}
		return false;
	}
}
