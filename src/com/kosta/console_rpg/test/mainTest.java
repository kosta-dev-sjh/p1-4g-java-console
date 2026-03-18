package com.kosta.console_rpg.test;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.session.LoginSession;
import com.kosta.console_rpg.util.InputUtil;

import static com.kosta.console_rpg.view.LoginView.*;
import static com.kosta.console_rpg.view.QuestView.printQuest;

public class mainTest {
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
                    System.out.println("8. 히어로 생성");
                    System.out.println("0. 종료");
                } else {

                    System.out.println("4. 전투");
                    System.out.println("5. 상점");
                    System.out.println("6. 히어로 정보");
                    System.out.println("7. 인벤토리");
                    System.out.println("8. 업적");
                    System.out.println("9. 로그아웃");
                    System.out.println("10. 히어로 삭제");
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
                        case 8 -> createHeroTest();
                        case 9 -> logout();
                        case 0 -> {
                            System.out.println("종료합니다.");
                            return;
                        }
                        default -> System.out.println("잘못된 입력입니다.");
                    }
                } else {
                    switch (menu) {
                        //case 4 -> showSession();      //전투 구현
                        //case 5 -> updateGemTest();    //상점 구현
                        //case 6 -> updateStageTest();  //히어로 정보
                        //case 7 -> updateStageTest();  //인벤토리 정보
                        case 8 -> printQuest();         //업적
                        case 9 -> logout();
                        case 10 -> deleteHeroTest();

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
}
