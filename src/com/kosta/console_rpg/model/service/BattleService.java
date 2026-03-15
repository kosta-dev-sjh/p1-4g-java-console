package com.kosta.console_rpg.model.service;

import java.sql.SQLException;
import java.util.List;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dao.MonsterDAO;
import com.kosta.console_rpg.model.dao.MonsterDAOImpl;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.MonsterDTO;
/**
 * hero의 maxStage값을 이용하여 해당하는 정보를 받는 Service 클래스
 *
 * 작성자      : 김재민
 * 생성일      : 2026.03.13
 * 최종 수정자 : 김재민
 * 최종 수정일 : 2026.03.15
 */
public class BattleService {
	MonsterDAO monsterDao = new MonsterDAOImpl();
	public MonsterDTO selectMonsterByStage(int stage) throws SQLException, GameException {

		//hero값 받으면 수정
		int heroMaxClearStage =3;

		if(heroMaxClearStage >= stage){
			//monster 정보 반환
			MonsterDTO monster = monsterDao.selectMonsterByStage(stage);

			return monster;
		}
		else{
			throw new GameException("스테이지 조건 불충분");
		}



	}
	
	
}
