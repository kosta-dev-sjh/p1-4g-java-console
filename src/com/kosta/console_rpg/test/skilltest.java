package com.kosta.console_rpg.test;

import com.kosta.console_rpg.controller.HeroController;
import com.kosta.console_rpg.controller.SkillController;
import com.kosta.console_rpg.controller.UserController;
import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.SkillDTO;
import com.kosta.console_rpg.model.dto.UserDTO;
import com.kosta.console_rpg.session.LoginSession;
import com.kosta.console_rpg.util.InputUtil;
import com.kosta.console_rpg.view.ShopView;

/**
 * 로그인 및 회원가입에 대한 플로우 추적용 테스트 뷰
 *
 * 작성자      : 송정현
 * 생성일      : 2026.03.14
 * 최종 수정자 : 송정현
 * 최종 수정일 : 2026.03.16
 */
public class skilltest {

	private static final UserController userController = new UserController();
	private static final HeroController heroController = new HeroController();

	/**
	 * 로그인 상태에 따라 회원가입/로그인/로그아웃 메뉴 반복
	 */
	public static void main(String[] args) {
		while (true) {
			try {
				System.out.println("\n===== USER TEST =====");

				boolean isLogin = LoginSession.getInstance().isLogin();
				HeroDTO hero = LoginSession.getInstance().getCurrentHero();

				if (!isLogin) {
					System.out.println("1. 회원가입");
					System.out.println("2. 로그인");
					System.out.println("0. 종료");
				} else if (hero == null) {
					System.out.println("3. 로그아웃");
					System.out.println("4. 현재 세션 확인");
					System.out.println("8. 캐릭터 생성");
					System.out.println("0. 종료");
				} else {
					System.out.println("3. 로그아웃");
					System.out.println("4. 현재 세션 확인");
					System.out.println("5. 젬 변경");
					System.out.println("6. 스테이지 변경");
					System.out.println("7. 캐릭터 삭제");
					System.out.println("0. 종료");
				}

				System.out.print("선택 > ");
				int menu = InputUtil.inputInt();

				if (!isLogin) {
					switch (menu) {
						case 1 -> register();
						case 2 -> login();
						case 0 -> {
							System.out.println("종료합니다.");
							return;
						}
						default -> System.out.println("잘못된 입력입니다.");
					}
				} else if (hero == null) {
					switch (menu) {
						case 3 -> logout();
						case 4 -> showSession();
						case 8 -> createHeroTest();
						case 0 -> {
							System.out.println("종료합니다.");
							return;
						}
						default -> System.out.println("잘못된 입력입니다.");
					}
				} else {
					switch (menu) {
						case 3 -> logout();
						case 4 -> showSession();
						case 5 -> updateGemTest();
						case 6 -> updateStageTest();
						case 7 -> deleteHeroTest();
						case 0 -> {
							System.out.println("종료합니다.");
							return;
						}
						default -> System.out.println("잘못된 입력입니다.");
					}
				}

			} catch (GameException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * 사용자 회원가입 입력을 받아 회원 정보 생성
	 *
	 * @throws GameException 입력 또는 처리 중 오류 발생 시
	 */
	public static void register() throws GameException {
		System.out.print("아이디 입력 : ");
		String loginId = InputUtil.inputString();
		InputUtil.checkBlank(loginId);
		InputUtil.checkStringLength(loginId, 1, 30);

		System.out.print("비밀번호 입력 : ");
		String pwd = InputUtil.inputString();
		InputUtil.checkBlank(pwd);
		InputUtil.checkStringLength(pwd, 1, 20);

		System.out.print("이름 입력 : ");
		String userName = InputUtil.inputString();
		InputUtil.checkBlank(userName);
		InputUtil.checkStringLength(userName, 1, 10);

		UserDTO registerUser = new UserDTO(loginId, pwd, userName);

		userController.register(registerUser);

		System.out.println("회원가입 완료");
	}

	/**
	 * 로그인 입력을 받아 세션 생성 후 캐릭터 확인
	 *
	 * @throws GameException 입력 또는 처리 중 오류 발생 시
	 */
	public static void login() throws GameException {
		System.out.print("아이디 입력 : ");
		String loginId = InputUtil.inputString();

		System.out.print("비밀번호 입력 : ");
		String pwd = InputUtil.inputString();

		userController.login(loginId, pwd);

		HeroDTO hero = LoginSession.getInstance().getCurrentHero();

		if (hero != null) {
			System.out.println("현재 캐릭터명 : " + hero.getHeroName());
		} else {
			System.out.println("히어로 정보가 없습니다.");
			createHeroTest();
		}

		System.out.println("현재 로그인 유저 : " + LoginSession.getInstance().getCurrentUser().getUserName());
	}

	/**
	 * 캐릭터 이름을 입력받아 신규 캐릭터 생성
	 *
	 * @throws GameException 입력 또는 처리 중 오류 발생 시
	 */
	public static void createHeroTest() throws GameException {
		System.out.print("캐릭터 이름 입력 (엔터만 입력 시 기본값 적용): ");
		String heroName = InputUtil.inputString();

		if (!heroName.isBlank()) {
			InputUtil.checkStringLength(heroName, 1, 30);
		}

		heroController.createHero(heroName);

		HeroDTO createdHero = LoginSession.getInstance().getCurrentHero();

		System.out.println("생성 완료 : " + createdHero.getHeroName());
	}

	/**
	 * 현재 세션 초기화
	 */
	public static void logout() {
		LoginSession.getInstance().clear();
		System.out.println("로그아웃 완료");
	}

	/**
	 * 현재 로그인한 사용자 및 캐릭터 세션 정보 출력
	 * @throws GameException 
	 */
	public static void showSession() throws GameException {
		System.out.println("현재 로그인 유저 : " + LoginSession.getInstance().getCurrentUser().getUserName());
		System.out.println("현재 로그인 된 유저 정보 : " + LoginSession.getInstance().getCurrentUser().getUserId());
		
		
		HeroDTO hero = LoginSession.getInstance().getCurrentHero();

		SkillController skillController = new SkillController();
		if(hero.getHeroGem() > 100) {

			skillController.upgradeHeroSkill(1, 1);

		}


	}

	/**
	 * 젬 수정 테스트
	 *
	 * @throws GameException 입력 오류 발생 시
	 */
	public static void updateGemTest() throws GameException {
		System.out.print("변경할 젬 입력 : ");
		int gem = InputUtil.inputInt();

		LoginSession.getInstance().getCurrentHero().setHeroGem(gem);
		heroController.updateHeroGem();

		System.out.println("젬 변경 완료");
	}

	/**
	 * 최대 클리어 스테이지 수정 테스트
	 *
	 * @throws GameException 입력 오류 발생 시
	 */
	public static void updateStageTest() throws GameException {
		System.out.print("변경할 스테이지 입력 : ");
		int stage = InputUtil.inputInt();

		LoginSession.getInstance().getCurrentHero().setHeroMaxClearStage(stage);
		heroController.updateClearStage();

		System.out.println("스테이지 변경 완료");
	}

	/**
	 * 현재 캐릭터 삭제 테스트
	 */
	public static void deleteHeroTest() {
		heroController.deleteHero();
		System.out.println("캐릭터 삭제 완료");
	}
}
