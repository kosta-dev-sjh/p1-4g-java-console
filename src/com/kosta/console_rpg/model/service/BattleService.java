package com.kosta.console_rpg.model.service;

import java.sql.SQLException;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dao.MonsterDAO;
import com.kosta.console_rpg.model.dao.MonsterDAOImpl;
import com.kosta.console_rpg.model.dto.BattleHeroDTO;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.ItemDTO;
import com.kosta.console_rpg.model.dto.MonsterDTO;
import com.kosta.console_rpg.model.enums.BattleResult;
import com.kosta.console_rpg.session.LoginSession;
import com.kosta.console_rpg.util.RandomUtil;

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
		if(hero.isGuardActive()) {
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
		int baseDamage = attack - defense;

		if (baseDamage <= 0) {
			return 0; // 방어력이 공격력보다 높으면 데미지는 0
		}

		double multiplier = switch (dice) {
			case 1 -> 0.7;
			case 6 -> 1.5;
			default -> 1.0;
		};

		int damage = (int) (baseDamage * multiplier);

		return damage;
	}

	/**
	 * 스킬 공격 데미지를 계산한다.
	 * 스킬 데미지 계산 공식: [(공격력 - 상대 방어력) × 주사위 보정] + 스킬 기본 데미지 + (스킬 레벨 - 1) * 5
	 * - 스킬 레벨이 높을수록 추가 데미지가 증가한다.
	 * - 주사위 결과에 따른 데미지 보정은 일반 공격과 동일하게 적용된다.
	 * - 스킬 사용 시 MP 소모는 별도의 로직에서 처리한다.
	 *
	 * @param attack      공격 주체의 공격력
	 * @param skillDamage 공격 주체의 사용 스킬 데미지
	 * @param defense     상대 방어력
	 * @param skillLevel  사용 스킬 레벨
	 * @return int 실제 적용된 스킬 데미지
	 */
	public int useSkill(int attack, int skillDamage, int skillLevel, int defense, int dice) {
		return calculateAttackDamage(attack, defense, dice) + skillDamage + (skillLevel - 1) * 5; // 스킬 레벨당 추가 데미지
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

		double multiplier = switch (dice) {
			case 1 -> 0.7;
			case 6 -> 1.5;
			default -> 1.0;
		};

		int effectiveDefense = (int) (defense * multiplier);

		return effectiveDefense;
	}

	/**
	 * 아이템 효과를 현재 전투용 영웅 객체에 적용한다.
	 * 아이템 효과는 HP 회복, MP 회복, 공격력 증가, 방어력 증가 등 다양한 형태로 존재할 수 있다.
	 * 공격력 증가, 방어력 증가 아이템은 일시적으로 전투 중에만 적용되며, 전투가 종료되면 원래 상태로 돌아간다.
	 *
	 * @param hero 현재 전투 중인 영웅 객체
	 * @param item 사용한 아이템 객체
	 * @return BattleHeroDTO 아이템 효과가 반영된 영웅 객체
	 */
	public BattleHeroDTO useItem(BattleHeroDTO hero, ItemDTO item) {
		// 현재 로그인 세션 영웅의 기본 HP/MP를 최대값 기준으로 사용한다.
		// 전투 중 회복 시 최대치를 초과하지 않도록 제한한다.
		int maxHp = LoginSession.getInstance().getCurrentHero().getHeroHp();
		int maxMp = LoginSession.getInstance().getCurrentHero().getHeroMp();

		if (item.getItemEffectHp() != 0) {
			int newHp = hero.getHeroHp() + item.getItemEffectHp();
			hero.setHeroHp(Math.min(newHp, maxHp)); // 최대 HP를 초과하지 않도록 설정
		}

		if (item.getItemEffectMp() != 0) {
			int newMp = hero.getHeroMp() + item.getItemEffectMp();
			hero.setHeroMp(Math.min(newMp, maxMp)); // 최대 MP를 초과하지 않도록 설정
		}

		// 공격력 증가, 방어력 증가 아이템은 일시적으로 전투 중에만 적용된다.

		if (item.getItemAtkBonus() != 0) {
			hero.clearAtkBuff();
			hero.setTempAtkBonus(item.getItemAtkBonus());
			hero.setAtkBuffActive(true);
			hero.setAtkBuffTurn(1);
		}

		if (item.getItemDefBonus() != 0) {
			hero.clearDefBuff(); // 기존 방어 버프 효과 제거
			hero.setTempDefBonus(item.getItemDefBonus());
			hero.setDefBuffActive(true);
			hero.setDefBuffTurn(1);
		}

		return hero;
	}

	/**
	 * 전투를 포기하고 종료한다.
	 *
	 * @return BattleResult 도주 결과 반환
	 */
	public BattleResult escape() {
		return BattleResult.ESCAPE;
	}
}