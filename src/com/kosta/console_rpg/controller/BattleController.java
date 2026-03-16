package com.kosta.console_rpg.controller;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.BattleHeroDTO;
import com.kosta.console_rpg.model.dto.ItemDTO;
import com.kosta.console_rpg.model.dto.MonsterDTO;
import com.kosta.console_rpg.model.enums.BattleResult;
import com.kosta.console_rpg.model.service.BattleService;
import com.kosta.console_rpg.view.FailView;

/**
 * 게임 전투 흐름과 전투 관련 사용자 요청을 제어하는 컨트롤러
 *
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
	 * @return BattleResult 전투 결과 (승리, 패배, 지속)
	 */
	public BattleResult attack(BattleHeroDTO hero, MonsterDTO monster) {
		int attack = battleService.getCurrentAttack(hero);
		int damage = battleService.calculateAttackDamage(attack, monster.getMonsterDefense());

		monster.setMonsterHp(Math.max(0, monster.getMonsterHp() - damage));

		if (monster.getMonsterHp() <= 0) {
			return BattleResult.WIN;
		}

		return BattleResult.CONTINUE;
	}

	/**
	 * 몬스터가 영웅을 공격한다.
	 * - 몬스터의 공격력과 영웅의 현재 방어력을 기반으로 피해량 계산
	 * - 영웅의 HP에서 피해량만큼 감소
	 * - 영웅의 HP가 0 이하가 되면 패배, 그렇지 않으면 전투 지속
	 *
	 * @param hero    전투용 영웅 객체
	 * @param monster 전투 대상 몬스터 객체
	 * @return BattleResult 전투 결과 (승리, 패배, 지속)
	 */
	public BattleResult monsterAttack(BattleHeroDTO hero, MonsterDTO monster) {
		int defense = battleService.getCurrentDefense(hero);
		int damage = battleService.calculateAttackDamage(monster.getMonsterAttack(), defense);

		hero.setHeroHp(Math.max(0, hero.getHeroHp() - damage));

		if (hero.getHeroHp() <= 0) {
			return BattleResult.LOSE;
		}

		return BattleResult.CONTINUE;
	}

	/**
	 * 2번항목) 방어 선택
	 * - 영웅이 방어 자세를 취하여 다음 몬스터 공격까지 일시적으로 방어력 증가 효과 적용
	 * - 방어 효과는 다음 몬스터 공격이 끝난 후 사라진다.
	 * 
	 * @param hero    전투용 영웅 객체
	 * @param monster 전투 대상 몬스터 객체
	 * @return BattleResult 전투 결과 (승리, 패배, 지속)
	 */
	public BattleResult defend(BattleHeroDTO hero, MonsterDTO monster) {
		// TODO : 방어 시 일시적으로 방어력 증가 효과 적용 (예: 다음 몬스터 공격까지 방어력 증가)

		return BattleResult.CONTINUE;
	}

	/**
	 * 3번항목) 스킬 사용
	 * - 영웅이 보유한 스킬 목록에서 선택한 스킬을 사용하여 몬스터에게 피해를 입힌다.
	 *   - 스킬 사용 시 MP 소모는 별도의 로직에서 처리한다.
	 *
	 * @param hero    전투용 영웅 객체
	 * @param monster 전투 대상 몬스터 객체
	 * @return BattleResult 전투 결과 (승리, 패배, 지속)
	 */
	public BattleResult useSkill(BattleHeroDTO hero, MonsterDTO monster) {
		// TODO : SkillDTO 연동 후 구현 예정
		return BattleResult.CONTINUE;
	}

	/**
	 * 4번항목) 아이템 사용
	 * 
	 * @param hero    전투용 영웅 객체
	 * @param monster 전투 대상 몬스터 객체
	 * @param item    사용한 아이템 객체
	 * @return BattleResult 전투 결과 (승리, 패배, 지속)
	 */
	public BattleResult useItem(BattleHeroDTO hero, MonsterDTO monster, ItemDTO item) {
		battleService.useItem(hero, item);

		return BattleResult.CONTINUE;
	}

	/**
	 * 5번항목) 전투에서 도망
	 *
	 * @return BattleResult 전투 결과 (승리, 패배, 지속)
	 */
	public BattleResult escape() {
		return battleService.escape();
	}
}
