package com.kosta.console_rpg.view;

import com.kosta.console_rpg.controller.BattleController;
import com.kosta.console_rpg.model.dto.MonsterDTO;

/**
 * 게임 배틀화면 뷰
 *
 * 작성자     : 김재민
 * 생성일     : 2026.03.16
 * 최종 수정자 : 이진주
 * 최종 수정일 : 
 */
public class BattleView {
    public static void showMonster(int stage) {
        BattleController controller = new BattleController();
        controller.selectMonsterByStage(stage);

    }

}
