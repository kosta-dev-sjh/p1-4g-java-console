package com.kosta.console_rpg.model.dto;

public class UserDTO {
	private int userId;				//사용자 고유 식별자
	private String userLoginId;		//로그인 아이디
	private String userPassword;	//암호화된 비밀번호
	private String userName;		//게임 내 닉네임
	private String userCreatedAt;	//사용자 계정 생성 일시
	
	public UserDTO(int userId, String userLoginId, String userPassword, String userName, String userCreatedAt) {
		super();
		this.userId = userId;
		this.userLoginId = userLoginId;
		this.userPassword = userPassword;
		this.userName = userName;
		this.userCreatedAt = userCreatedAt;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserCreatedAt() {
		return userCreatedAt;
	}

	public void setUserCreatedAt(String userCreatedAt) {
		this.userCreatedAt = userCreatedAt;
	}

	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", userLoginId=" + userLoginId + ", userPassword=" + userPassword
				+ ", userName=" + userName + ", userCreatedAt=" + userCreatedAt + "]";
	}
	
	
	
	
}
