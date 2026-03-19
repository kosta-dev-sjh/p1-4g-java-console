package com.kosta.console_rpg.test;

import com.kosta.console_rpg.controller.QuestController;
import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.session.LoginSession;
import com.kosta.console_rpg.util.InputUtil;
import com.kosta.console_rpg.view.*;

import static com.kosta.console_rpg.view.LoginView.*;
import static com.kosta.console_rpg.view.QuestView.printQuest;
/**
 * main menu를 위한 view
 *
 * 작성자     : 김재민
 * 생성일     : 2026.03.18
 * 최종 수정자 :
 * 최종 수정일 :
 */
public class mainTest {

    public static void main(String[] args) {
        QuestController questController = new QuestController();
        HeroView heroView = new HeroView();
        MainView mainView = new MainView();
        ShopView shopView = new ShopView();
        
        while (true) {
            try {
                System.out.println("____________________________┌ Main Menu ┐_______________________________\n");

                boolean isLogin = LoginSession.getInstance().isLogin();
                HeroDTO hero = LoginSession.getInstance().getCurrentHero();

                if (!isLogin) {     //로그인 되지않았을경우
                    System.out.println("▸[1] 회원가입");
                    System.out.println("▸[2] 로그인");
                    System.out.println("▸[0] 종료");
                } else if (hero == null) {  //로그인은 됐지만, 아직 히어로 생성은 하지않은 경우
                    System.out.println("▸[1] 로그아웃");
                    System.out.println("▸[2] 히어로 생성");
                    System.out.println("▸[0] 종료");
                } else {    //히어로 생성 및 로그인 완료
                    mainView.start();
                }

                System.out.print("선택 ▶ ");
                int menu = InputUtil.inputInt();

                if (!isLogin) {     //로그인 되지않았을경우
                    switch (menu) {
                        case 1 -> register();
                        case 2 -> login();
                        case 0 -> {
                            System.out.println("종료합니다.");
                            return;
                        }
                        default -> System.out.println("잘못된 입력입니다.");
                    }
                } else if (hero == null) {  //로그인은 됐지만, 아직 히어로 생성은 하지않은 경우
                    switch (menu) {
                        case 2 -> createHeroTest();
                        case 1 -> logout();
                        case 0 -> {
                            System.out.println("종료합니다.");
                            return;
                        }
                        default -> System.out.println("잘못된 입력입니다.");
                    }
                } else {    //히어로 생성 및 로그인 완료
                    switch (menu) {
                        case 1 -> BattleTest.startBattle(); //스테이지 및 배틀 진입
                        case 2 -> shopView.showShop();    //상점 구현
                        case 3 -> heroView.start();  //히어로 정보
                        case 4 -> InventoryView.start();  //인벤토리 정보

                        //업적
                        case 5 -> {
                            QuestView.printQuest();
                            questController.selectQuestEnd(hero.getHeroId());
                            questController.selectQuestIng(hero.getHeroId());
                            QuestView.printBackToMenu();

                        }
                        case 6 -> logout();
                        case 7 -> deleteHeroTest();

                        case 8 -> {
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
