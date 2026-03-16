package com.kosta.console_rpg.model.service;

import java.sql.SQLException;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dao.MonsterDAO;
import com.kosta.console_rpg.model.dao.MonsterDAOImpl;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.MonsterDTO;
import com.kosta.console_rpg.model.enums.BattleResult;
import com.kosta.console_rpg.session.LoginSession;
/**
 * 전투 관련 비즈니스 로직을 처리하는 Service 클래스
 * - 스테이지별 몬스터 조회
 * - 전투 시작
 * - 일반 공격 / 스킬 공격 계산
 *
 * 작성자      : 김재민
 * 생성일      : 2026.03.13
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
	 * @throws SQLException DB 조회 중 오류 발생 시
	 * @throws GameException 도전 가능한 스테이지 조건을 만족하지 못한 경우
	 */
	public MonsterDTO selectMonsterByStage(int selectStage) throws GameException {
		
		MonsterDTO monster = null;
		
		try {
			// 1. 먼저 몬스터가 존재하는지 조회 [확장성을 생각해 하드코딩 제외]
			monster = monsterDao.selectMonsterByStage(selectStage);
			
			if(monster == null) {
				throw new GameException("조회된 몬스터가 존재하지 않습니다.");
			}
			
			// 2. 이후 히어로가 해당 몬스터랑 싸울 수 있는지를 판별
			HeroDTO hero = LoginSession.getInstance().getCurrentHero();
			
			//    ㄴ 선택한 스테이지 값 보다도 히어로 스테이지+1값보다 낮으면 진입불가
			if(selectStage > hero.getHeroMaxClearStage() + 1){
				throw new GameException("스테이지 조건이 불충분합니다.");
			}
			
		} catch (SQLException e) {
			throw new GameException("몬스터 조회 중 오류가 발생했습니다.");
		}

		return monster;
	}

	/**
	 * 전투용 영웅 객체를 생성한다.
	 *
	 * @return HeroDTO 전투용 복사 영웅 객체
	 */
	public HeroDTO createBattleHero() {
//		HeroDTO battleHero = new HeroDTO();
		

		
		return null;
	}
	
	/**
	 * 영웅의 일반 공격 데미지를 계산한다.
	 *
	 * @param hero 현재 전투 중인 영웅 객체
	 * @param monster 현재 전투 중인 몬스터 객체
	 * @return int 실제 적용된 일반 공격 데미지
	 */
	public int attack(HeroDTO hero, MonsterDTO monster) {
		int result = 0;
		
		return result;
	}

	/**
	 * 영웅의 스킬 공격 데미지를 계산한다.
	 *
	 * @param hero 현재 전투 중인 영웅 객체
	 * @param monster 현재 전투 중인 몬스터 객체
	 * @return int 실제 적용된 스킬 데미지
	 */
	public int useSkill(HeroDTO hero, MonsterDTO monster) {
		int result = 0;
		
		return result;
	}

	/**
	 * 현재 로그인한 영웅으로 방어를 수행한다.
	 *
	 * @return int 적용된 방어 수치
	 */
	public int defend(HeroDTO hero) {
		return 0;
	}

	/**
	 * 현재 로그인한 영웅이 아이템을 사용한다.
	 *
	 * @return boolean 아이템 사용 성공 여부
	 */
	public boolean useItem(HeroDTO hero) {
		return false;
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