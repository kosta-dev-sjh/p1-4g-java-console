package com.kosta.console_rpg.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kosta.console_rpg.model.dto.UserDTO;
import com.kosta.console_rpg.util.DBManager;
/**
 * UserDAO 인터페이스를 구현하여 사용자(User) 관련 DB 작업을 수행하는 DAO 구현 클래스
 *
 * 작성자      : 송정현
 * 생성일      : 2026.03.14
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class UserDAOImpl implements UserDAO {
	
	// ======= public method =======
	@Override
	public UserDTO selectUser(String loginId) throws SQLException{
		Connection 		  con  = null;
		PreparedStatement ps   = null;
		ResultSet 		  rs   = null;
		UserDTO 		  user = null;
		String 			  sql = "select * from `user` where user_login_id=?";

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, loginId);
			rs = ps.executeQuery();

			if(rs.next()){
				user = new UserDTO(
						rs.getInt("user_id"), 
						rs.getString("user_login_id"), 
						rs.getString("user_pwd"), 
						rs.getString("user_name"), 
						rs.getTimestamp("user_created_at").toLocalDateTime()
						);
			}
		}finally {
			DBManager.close(con, ps, rs);
		}
		return user;
	} 

	@Override
	public int checkUserDuplication(String registerId) throws SQLException{
		UserDTO user = selectUser(registerId);
		return user == null ? 0: 1;
	}

	@Override
	public int insertUser(UserDTO registerUser) throws SQLException{
		int				  result = 0;
		Connection 		  con    = null;
		PreparedStatement ps     = null;
		String 			  sql    = "insert into `user`(user_login_id, user_pwd, user_name) values(?, ?, ?)";
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, registerUser.getUserLoginId());
			ps.setString(2, registerUser.getUserPassword());
			ps.setString(3, registerUser.getUserName());

			result = ps.executeUpdate();
		}finally {
			DBManager.close(con, ps);
		}

		return result;
	}

}
