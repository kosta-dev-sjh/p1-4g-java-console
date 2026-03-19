package com.kosta.console_rpg.view;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.session.LoginSession;
import com.kosta.console_rpg.util.InputUtil;

import static com.kosta.console_rpg.test.BattleTest.startBattle;
import static com.kosta.console_rpg.test.ConsoleEffectTest.GREEN;
import static com.kosta.console_rpg.test.ConsoleEffectTest.RED;
import static com.kosta.console_rpg.test.ConsoleEffectTest.YELLOW;
import static com.kosta.console_rpg.test.ConsoleEffectTest.CYAN;
import static com.kosta.console_rpg.test.ConsoleEffectTest.RESET;

/**
 *  스테이지 선택을 위한 스테이지 출력
 *
 * 작성자     : 김재민
 * 생성일     : 2026.03.18
 * 최종 수정자 :
 * 최종 수정일 :
 */
public class StageView {

    public static void showStage() {
        HeroDTO hero = LoginSession.getInstance().getCurrentHero();
        //아직 아무것도 클리어하지 못한경우 에필로그 출력
//        if(hero.getHeroMaxClearStage() == 0) {
//            try {
//                StoryView.epilogue();
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
        int isStageCleared = hero.getHeroMaxClearStage();
        String clearedText = GREEN + " (Cleared)" + RESET;
        String message = "";

        //Stage 1. 스테이지 클리어 여부 출력
        if (isStageCleared >= 1) {
            message = clearedText;
        }
        System.out.println("_________________________________┌ STAGE SELECT ┐_________________________________\n");
        System.out.println("[1][Stage 1 - 초록 들판]" + message);
        System.out.println("    Monster: 슬라임");
        System.out.println("    Difficulty: ★");

        System.out.println();
        
        //Stage 2. 스테이지 클리어 여부 출력
        if (isStageCleared >= 2) {
            message = clearedText;
        }
        System.out.println("[2][Stage 2 - 고블린 동굴]" + message);
        System.out.println("    Monster: 고블린");
        System.out.println("    Difficulty: ★★");
        System.out.println();
        

        // Stage 3. 스테이지 클리어 여부 출력
        if (isStageCleared >= 3) {
            message = clearedText;
        }
        System.out.println("[3][Stage 3 - 드래곤 레어]" + message);
        System.out.println("    Monster: 드래곤");
        System.out.println("    Difficulty: ★★★");
        System.out.println();
        
        
        System.out.println("────────────────── MENU ──────────────────\n");
        System.out.println(" [0] 뒤로가기\n");
        System.out.print("선택 ▶ ");
    }



}
