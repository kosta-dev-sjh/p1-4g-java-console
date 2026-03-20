package com.kosta.console_rpg.model.dao;

import java.sql.SQLException;

import com.kosta.console_rpg.model.dto.MonsterDTO;

public interface MonsterDAO {
	
	//특정 스테이지에 해당하는 특정 몬스터 정보 조회	
	MonsterDTO selectMonsterByStage(int stage) throws SQLException;

	

	

}
