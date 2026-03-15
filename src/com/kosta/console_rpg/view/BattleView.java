package com.kosta.console_rpg.view;

import com.kosta.console_rpg.controller.BattleController;
import com.kosta.console_rpg.model.dto.MonsterDTO;

public class BattleView {
    public static void showMonster(int stage) {
        BattleController controller = new BattleController();
        controller.selectMonsterByStage(stage);

    }

}
