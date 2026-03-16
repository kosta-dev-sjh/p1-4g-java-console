package com.kosta.console_rpg.model.dao;

import java.util.List;

import com.kosta.console_rpg.exception.DuplicationException;
import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.HeroSkillDTO;

public interface HeroDAO {
	
	/**
	 * hero 생성
	 * select로 중복 검사 후 insert 
	 * hero_name은 이미 캐릭터 생성 화면에서 받았다는 전제하에  HeroDTO에서 가져옴
	 */
	void insertHero(HeroDTO hero) throws DuplicationException, GameException;
	
	
	/**
	 * hero 정보 불러오기
	 */
	HeroDTO selectHeroByUserId(int userId) throws GameException;
	
	
	/**
	 * hero 정보 리셋 (delete 하고 캐릭터 생성 view로 리턴 시키는 로직 구현 필요)
	 * 현재는 hero 정보만 delete함 다른 테이블 정보들은 어떻게 delete할지 구현 필요
	 */
	void resetHero(int heroId) throws GameException;
	
	/**
	 * hero_id로 히어로 보유 스킬 가져오기
	 */
	List<HeroSkillDTO> selectHeroSkillsByHeroId(int heroId) throws GameException;
	
	/**
	 * 히어로 스킬 강화
	 */
	void upgradeHeroSkill(HeroSkillDTO heroSkill) throws GameException;
	
	/*
	 * 히어로가 클리어한 스테이지 업데이트  
	 */
	void updateClearStage(int heroId, int stage) throws GameException;
	
	/*
	 * 히어로의 보유 젬 업데이트
	 */
	void updateHeroGem(int heroId, int gem) throws GameException;
	
}
