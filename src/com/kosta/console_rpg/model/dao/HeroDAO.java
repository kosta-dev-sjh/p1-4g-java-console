package com.kosta.console_rpg.model.dao;

import java.sql.SQLException;

import com.kosta.console_rpg.model.dto.HeroDTO;

/**
 * 히어로 조회, 생성, 성장 등 캐릭터 관련 요청을 제어하는 dao
 *
 * 작성자      : 송정현
 * 생성일      : 2026-03-15
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public interface HeroDAO {
	
	/**
	 * 로그인 시 사용자 번호로 캐릭터 정보 조회한다.
	 * 
	 * @param userId 사용자 고유 번호
	 * @return HeroDTO 캐릭터 정보
	 * @throws SQLException DB 처리 중 오류 발생 시
	 */
	public HeroDTO selectHeroByUserId(int userId) throws SQLException;
	
	/**
	 * 새로운 캐릭터를 생성한다.
	 * 캐릭터 정보가 존재하지 않을 경우 사용한다.
	 * 
	 * @param hero 생성할 캐릭터 정보
	 * @throws SQLException DB 처리 중 오류 발생 시
	 */
	public void insertHero(HeroDTO hero) throws SQLException;
	
	/**
	 * 사용자가 캐릭터 초기화 선택 시 캐릭터를 삭제한다.
	 * 
	 * @param heroId 캐릭터 고유 번호
	 * @throws SQLException DB 처리 중 오류 발생 시
	 */
	public void deleteHero(int heroId) throws SQLException;

	/**
	 * gem, 최고 클리어 스테이지는 제외하고 주요 능력치만 수정한다.
	 * 레벨업, 전투 결과 반영 등 여러 능력치가 함께 변경될 때 사용한다.
	 * 
	 * @param hero 수정할 캐릭터 정보
	 * @throws SQLException DB 처리 중 오류 발생 시
	 */
	public void updateHero(HeroDTO hero) throws SQLException;
	
	/**
	 * 캐릭터의 최고 클리어 스테이지를 수정한다.
	 * 전투 승리 후 최고 스테이지 갱신 시 사용한다.
	 * 
	 * @param heroId 캐릭터 고유 번호
	 * @param stage 최고 클리어 스테이지
	 * @throws SQLException DB 처리 중 오류 발생 시
	 */
	public void updateClearStage(int heroId, int stage) throws SQLException;
    
	/**
	 * 캐릭터의 보유 젬을 수정한다.
	 * 상점 구매, 판매, 전투 보상 지급 시 사용한다.
	 * 
	 * @param heroId 캐릭터 고유 번호
	 * @param gem 보유 젬
	 * @throws SQLException DB 처리 중 오류 발생 시
	 */
	public void updateHeroGem(int heroId, int gem) throws SQLException;
    
    
}
