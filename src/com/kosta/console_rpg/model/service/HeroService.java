package com.kosta.console_rpg.model.service;

import java.sql.SQLException;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dao.HeroDAO;
import com.kosta.console_rpg.model.dao.HeroDAOImpl;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.session.LoginSession;

/**
 * 히어로 조회, 생성, 성장 등 캐릭터 관련 요청을 제어하는 서비스
 */
public class HeroService {

	private HeroDAO heroDao = new HeroDAOImpl();

	/**
	 * 신규 히어로를 생성하고 로그인 세션에 저장한다.
	 */
	public void createHero(int userId, String heroName) throws GameException {
		try {
			HeroDTO hero = heroDao.selectHeroByUserId(userId);

			if (hero != null) {
				throw new GameException("이미 생성된 캐릭터가 존재합니다.");
			}

			heroDao.insertHero(userId, heroName);

			HeroDTO createdHero = heroDao.selectHeroByUserId(userId);

			if (createdHero == null) {
				throw new GameException("캐릭터 생성 후 조회에 실패했습니다.");
			}

			//새로운 hero 업적 init
			QuestService qs = new QuestService();
			qs.insertQuestInit(createdHero.getHeroId());

			LoginSession.getInstance().setCurrentHero(createdHero);

		} catch (SQLException e) {
			throw new GameException("캐릭터 생성 중 오류가 발생했습니다.");
		}
	}

	/**
	 * 캐릭터 삭제
	 */
	public void deleteHero(int heroId) throws GameException {
		try {
			int result = heroDao.deleteHero(heroId);

			if (result == 0) {
				throw new GameException("캐릭터 삭제에 실패했습니다.");
			}

		} catch (SQLException e) {
			throw new GameException("캐릭터 삭제 중 오류가 발생했습니다.");
		}
	}

	/**
	 * 캐릭터 정보 수정
	 */
	public void updateHero(HeroDTO hero) throws GameException {
		try {
			int result = heroDao.updateHero(hero);

			if (result == 0) {
				throw new GameException("캐릭터 정보 수정에 실패했습니다.");
			}

		} catch (SQLException e) {
			throw new GameException("캐릭터 수정 중 오류가 발생했습니다.");
		}
	}

	/**
	 * 최대 클리어 스테이지 갱신
	 */
	public void updateClearStage(int heroId, int stage) throws GameException {
		try {
			int result = heroDao.updateClearStage(heroId, stage);

			if (result == 0) {
				throw new GameException("스테이지 갱신에 실패했습니다.");
			}

		} catch (SQLException e) {
			throw new GameException("스테이지 갱신 중 오류가 발생했습니다.");
		}
	}

	/**
	 * 보유 젬 갱신
	 */
	public void updateHeroGem(int heroId, int gem) throws GameException {
		try {
			int result = heroDao.updateHeroGem(heroId, gem);

			if (result == 0) {
				throw new GameException("젬 갱신에 실패했습니다.");
			}

		} catch (SQLException e) {
				throw new GameException("젬 갱신 중 오류가 발생했습니다.");
		}
	}
}