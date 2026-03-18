package com.kosta.console_rpg.model.dao;

import com.kosta.console_rpg.model.dto.HeroDTO;

import java.sql.Connection;
import java.sql.SQLException;


public interface HeroDAO {
	
	/**
	 * 로그인 시 사용자 번호로 캐릭터 정보 조회한다.
	 * 
	 * @param userId 사용자 고유 번호
	 * @return HeroDTO 조회된 캐릭터 정보
	 * @throws SQLException DB 처리 중 오류 발생 시
	 */
	public HeroDTO selectHeroByUserId(int userId) throws SQLException;
	
	/**
	 * 새로운 캐릭터를 생성한다.
	 * 캐릭터 정보가 존재하지 않을 경우 사용한다.
	 * 
	 * @param userId 로그인된 유저 아이디
	 * @param heroName 사용자가 지정한 히어로 이름
	 * @return int SQL 실행 결과(성공 시 1, 실패 시 0)
	 * @throws SQLException DB 처리 중 오류 발생 시
	 */
	public int insertHero(int userId, String heroName) throws SQLException;
	
	/**
	 * 사용자가 캐릭터 초기화 선택 시 캐릭터를 삭제한다.
	 * 
	 * @param heroId 캐릭터 고유 번호
	 * @return int SQL 실행 결과(성공 시 1, 실패 시 0)
	 * @throws SQLException DB 처리 중 오류 발생 시
	 */
	public int deleteHero(int heroId) throws SQLException;

	/**
	 * gem, 최고 클리어 스테이지는 제외하고 주요 능력치만 수정한다.
	 * 레벨업, 전투 결과 반영 등 여러 능력치가 함께 변경될 때 사용한다.
	 * 
	 * @param hero 수정할 캐릭터 정보
	 * @return int SQL 실행 결과(성공 시 1, 실패 시 0)
	 * @throws SQLException DB 처리 중 오류 발생 시
	 */
	public int updateHero(HeroDTO hero) throws SQLException;
	
	/**
	 * 캐릭터의 최고 클리어 스테이지를 수정한다.
	 * 전투 승리 후 최고 스테이지 갱신 시 사용한다.
	 * 
	 * @param heroId 캐릭터 고유 번호
	 * @param stage 최고 클리어 스테이지
	 * @return int SQL 실행 결과(성공 시 1, 실패 시 0)
	 * @throws SQLException DB 처리 중 오류 발생 시
	 */
	public int updateClearStage(int heroId, int stage) throws SQLException;
    
	/**
	 * 캐릭터의 보유 젬을 수정한다.
	 * 상점 구매, 판매, 전투 보상 지급 시 사용한다.
	 * 
	 * @param heroId 캐릭터 고유 번호
	 * @param gem 보유 젬
	 * @return int SQL 실행 결과(성공 시 1, 실패 시 0)
	 * @throws SQLException DB 처리 중 오류 발생 시
	 */
	public int updateHeroGem(int heroId, int gem) throws SQLException;
	
	
	/*
	 * 캐릭터의 보유 잼을 수정 트랜잭션 처리용
	 */
	public int updateHeroGem(Connection con, int heroId, int gem) throws SQLException;
    
}
