package com.kosta.console_rpg.test;

import com.kosta.console_rpg.controller.HeroController;
import com.kosta.console_rpg.controller.UserController;
import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.UserDTO;
import com.kosta.console_rpg.session.LoginSession;
import com.kosta.console_rpg.util.InputUtil;
import com.kosta.console_rpg.view.StartView;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class dynamicViewTest {
    private static final UserController userController = new UserController();
    private static final HeroController heroController = new HeroController();
    public static void showProgressBar(int length, int interval, String message) {
        char incomplete = '░';
        char complete = '█';
        StringBuilder progress = new StringBuilder();
        // Initialize
        Stream.generate(() -> incomplete).limit(length).forEach(progress::append);
        StringBuilder percent = new StringBuilder("0");
        String format = "\r[%s] %s%%";

        System.out.println(message);
        IntStream.range(0, length).forEach(i -> {
            progress.replace(i, i + 1, String.valueOf(complete));
            percent.delete(0, percent.length()).append(((i + 1) * 100) / length);

            System.out.print(String.format(format, progress, percent));

            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(); // New line after completion
    }

    public static void main(String[] args) {
        //showProgressBar(60, 100, "Loading...");


        while (true) {
            try {
                System.out.println("\n===== USER TEST =====");

                boolean isLogin = LoginSession.getInstance().isLogin();
                HeroDTO hero = LoginSession.getInstance().getCurrentHero();

                if (!isLogin) {
                    StartView view = new StartView();
                    view.start();
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

                //System.out.print("선택 > ");
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
     */
    public static void showSession() {
        System.out.println("현재 로그인 유저 : " + LoginSession.getInstance().getCurrentUser().getUserName());

        HeroDTO hero = LoginSession.getInstance().getCurrentHero();

        if (hero != null) {
            System.out.println("===== HERO SESSION =====");
            System.out.println("캐릭터 번호 : " + hero.getHeroId());
            System.out.println("캐릭터 이름 : " + hero.getHeroName());
            System.out.println("레벨 : " + hero.getHeroLevel());
            System.out.println("경험치 : " + hero.getHeroExp());
            System.out.println("HP : " + hero.getHeroHp());
            System.out.println("MP : " + hero.getHeroMp());
            System.out.println("공격력 : " + hero.getHeroAttack());
            System.out.println("방어력 : " + hero.getHeroDefense());
            System.out.println("젬 : " + hero.getHeroGem());
            System.out.println("최대 클리어 스테이지 : " + hero.getHeroMaxClearStage());
        } else {
            System.out.println("캐릭터 없음");
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
