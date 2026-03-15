package com.kosta.console_rpg.controller;

import com.kosta.console_rpg.model.dto.QuestDTO;
import com.kosta.console_rpg.model.service.QuestService;

import java.sql.SQLException;
import java.util.List;

public class QuestController {
    private QuestService questService = new QuestService();

    public void selectQuestsByHeroId(int heroId) {
        try{
            List<QuestDTO> questList = questService.selectQuestsByHeroId(heroId);
            System.out.println(questList);
        } catch (SQLException e) {
           e.printStackTrace();
            //FailView.errorMessage(e.getMessage());
        }
    }
}
