package com.kosta.console_rpg.model.service;

import java.sql.SQLException;
import java.util.List;

import com.kosta.console_rpg.model.dao.MonsterDAO;
import com.kosta.console_rpg.model.dao.MonsterDAOImpl;
import com.kosta.console_rpg.model.dto.MonsterDTO;

public class BattleService {
	MonsterDAO monsterDao = new MonsterDAOImpl();
	
	public MonsterDTO selectMonstersByStage(int stage) throws SQLException{
		//monster 정보 반환 
		MonsterDTO monster = monsterDao.selectMonsterByStage(stage);
				
		return monster;
	}
	
	
}
