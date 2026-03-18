package com.kosta.console_rpg.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.kosta.console_rpg.model.dto.HeroSkillDTO;

/**
 * skill 및 hero_skill 관련 데이터베이스 기능을 정의하는 DAO 인터페이스
 *
 * 작성자      : 송정현
 * 생성일      : 2026.03.15
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public interface SkillDAO {
	
	/**
	 * 캐릭터가 보유한 전체 스킬 목록을 조회하는 메소드
	 * 스킬 정보와 현재 강화 레벨을 함께 조회한다.
	 * 
	 * @param heroId 캐릭터 고유 번호
	 * @return 보유 스킬 목록
	 * @throws SQLException 데이터베이스 처리 중 오류 발생 시
	 */
	public List<HeroSkillDTO> selectHeroSkills(int heroId) throws SQLException;
	
	/**
	 * 특정 스킬의 현재 강화 정보를 조회하는 메소드
	 * 스킬 강화 전 현재 레벨 확인에 사용한다.
	 * 
	 * @param heroId 캐릭터 고유 번호
	 * @param skillId 스킬 고유 번호
	 * @return 해당 스킬 정보
	 * @throws SQLException 데이터베이스 처리 중 오류 발생 시
	 */
	public HeroSkillDTO selectHeroSkill(int heroId, int skillId) throws SQLException;
	
	/**
	 * 특정 스킬의 강화 레벨을 1 증가시키는 메소드
	 * 강화 성공 시 hero_skill 테이블을 갱신한다.
	 * 
	 * @param heroId 캐릭터 고유 번호
	 * @param skillId 스킬 고유 번호
	 * @return 수정된 행 수
	 * @throws SQLException 데이터베이스 처리 중 오류 발생 시
	 */
	public int upgradeHeroSkill(int heroId, int skillId) throws SQLException;
	
	/**
	 * 캐릭터 생성 시 기본 스킬 3개를 지급하는 메소드
	 * hero_skill 테이블에 기본 스킬 정보를 저장한다.
	 * 
	 * @param heroId 캐릭터 고유 번호
	 * @return 저장된 행 수
	 * @throws SQLException 데이터베이스 처리 중 오류 발생 시
	 */
	public int insertDefaultSkills(int heroId) throws SQLException;

	/**
	 * 캐릭터 스킬 삭제 메소드
	 * 캐릭터가 삭제될 시 스킬도 함께 삭제한다.
	 * 
	 * @param heroId 캐릭터 고유 번호
	 * @return 삭제된 행 수
	 * @throws SQLException 데이터베이스 처리 중 오류 발생 시
	 */
	public int deleteHeroSkills(int heroId) throws SQLException;
}