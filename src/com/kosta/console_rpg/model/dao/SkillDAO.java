package com.kosta.console_rpg.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.kosta.console_rpg.model.dto.HeroSkillDTO;
import com.kosta.console_rpg.model.dto.SkillDTO;

/**
 * 히어로 스킬 조회 및 강화 관련 데이터베이스 기능을 정의하는 DAO 인터페이스
 *
 * 작성자      : 송정현
 * 생성일      : 2026.03.15
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public interface SkillDAO {
	
	/**
	 * 캐릭터가 보유한 전체 스킬 정보를 조회하는 메소드
	 * 스킬 기본 정보(skill 테이블)를 조회할 때 사용한다.
	 * 
	 * @param heroId 캐릭터 고유 번호
	 * @return 보유 스킬 목록
	 * @throws SQLException 데이터베이스 처리 중 오류 발생 시
	 */
	public List<SkillDTO> selectSkillsByHeroId(int heroId) throws SQLException;
	
	/**
	 * 캐릭터가 보유한 전체 스킬 강화 정보를 조회하는 메소드
	 * 전투 시 스킬 레벨 계산 및 강화 목록 출력에 사용한다.
	 * 
	 * @param heroId 캐릭터 고유 번호
	 * @return 보유 스킬 강화 정보 목록
	 * @throws SQLException 데이터베이스 처리 중 오류 발생 시
	 */
	public List<HeroSkillDTO> selectHeroSkills(int heroId) throws SQLException;
	
	/**
	 * 특정 스킬의 현재 강화 정보를 조회하는 메소드
	 * 스킬 강화 전 현재 레벨 확인에 사용한다.
	 * 
	 * @param heroId 캐릭터 고유 번호
	 * @param skillId 스킬 고유 번호
	 * @return 해당 스킬 강화 정보
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
}