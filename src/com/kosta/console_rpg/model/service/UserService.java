package com.kosta.console_rpg.model.service;

import java.sql.SQLException;

import com.kosta.console_rpg.exception.DuplicationException;
import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.exception.LoginFailException;
import com.kosta.console_rpg.model.dao.HeroDAO;
import com.kosta.console_rpg.model.dao.HeroDAOImpl;
import com.kosta.console_rpg.model.dao.UserDAO;
import com.kosta.console_rpg.model.dao.UserDAOImpl;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.UserDTO;
import com.kosta.console_rpg.session.LoginSession;
import com.kosta.console_rpg.util.EncryptUtil;
/**
 * 사용자(User) 관련 비즈니스 로직을 처리하는 Service 클래스
 *
 * 작성자      : 송정현
 * 생성일      : 2026.03.14
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class UserService {
	// ======= field =======
	private final UserDAO userDao = new UserDAOImpl();
	private final HeroDAO heroDao = new HeroDAOImpl();
	
	// ======= public method =======
	/**
	 * 로그인 정보를 검증하고 로그인 처리를 수행한다.
	 * 
	 * @param loginId 로그인 아이디
	 * @param pwd 비밀번호
	 * @throws SQLException 
	 */
	public void login(String loginId, String pwd) throws LoginFailException, GameException{
		try {
			UserDTO user = userDao.selectUser(loginId);
			if (user == null) {
				throw new LoginFailException("아이디 또는 비밀번호가 올바르지 않습니다.");
			}
			
			String encryptPwd = EncryptUtil.md5(pwd);
			
			if(!encryptPwd.equals(user.getUserPassword())) {
				throw new LoginFailException("아이디 또는 비밀번호가 올바르지 않습니다.");
			}
			
			LoginSession.getInstance().setCurrentUser(user);
			
			
			HeroDTO hero = heroDao.selectHeroByUserId(user.getUserId());
			
			LoginSession.getInstance().setCurrentHero(hero);
			
			// 임시 데이터 ============
//			HeroDTO tempHero = new HeroDTO(
//					0,
//					user.getUserId(),
//					"임시캐릭터",
//					1,
//					0,
//					100,
//					50,
//					10,
//					5,
//					0,
//					null,
//					0
//				); 
//			
//			LoginSession.getInstance().setCurrentHero(tempHero);
			// 임시 데이터 끝 ---------
			
			
			
		} catch (SQLException e) {
			throw new GameException("로그인 처리 중 오류가 발생했습니다.");
		}

	}
	
	/**
	 * 신규 사용자 회원가입을 처리한다.
	 * 
	 * @param user 회원가입할 사용자 정보
	 * @throws SQLException 
	 */
	public void register(UserDTO registerUser) throws DuplicationException, GameException {
		try {
			int result = userDao.checkUserDuplication(registerUser.getUserLoginId());

			if (result > 0) {
				throw new DuplicationException("이미 사용 중인 아이디입니다.");
			}

			registerUser.setUserPassword(
				EncryptUtil.md5(registerUser.getUserPassword())
			);

			userDao.insertUser(registerUser);

		} catch (SQLException e) {
			throw new GameException("회원가입 처리 중 오류가 발생했습니다.");
		}
		
	}
}
