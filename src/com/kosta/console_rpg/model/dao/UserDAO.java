package com.kosta.console_rpg.model.dao;

import java.sql.SQLException;

import com.kosta.console_rpg.model.dto.UserDTO;

/**
 * 사용자(User) 관련 데이터베이스 조회 및 저장 기능을 정의하는 DAO 인터페이스
 *
 * 작성자      : 송정현
 * 생성일      : 2026.03.14
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public interface UserDAO {
	
	// ======= public method =======
	/**
	 * 로그인 아이디를 기준으로 사용자 정보를 조회하는 메소드
	 * 
	 * @param loginId 로그인 아이디
	 * @return 조회된 사용자 정보, 없으면 null
	 * @throws SQLException 데이터베이스 처리 중 오류 발생 시
	 */
	public UserDTO selectUser(String loginId) throws SQLException;
	
	/**
	 * 입력한 아이디의 중복 여부를 확인하는 메소드
	 * 
	 * @param registerId 확인할 로그인 아이디
	 * @return 중복 개수 (0: 사용 가능, 1 이상: 중복)
	 * @throws SQLException 데이터베이스 처리 중 오류 발생 시
	 */
	public int checkUserDuplication(String registerId) throws SQLException;
	
	/**
	 * 신규 사용자 정보를 저장하는 메소드
	 * 
	 * @param registerUser 저장할 사용자 정보
	 * @throws SQLException 데이터베이스 처리 중 오류 발생 시
	 */
	public int insertUser(UserDTO registerUser) throws SQLException;
	
}