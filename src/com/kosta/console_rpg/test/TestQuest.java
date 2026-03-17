package com.kosta.console_rpg.test;

import java.sql.SQLException;

import com.kosta.console_rpg.model.dto.QuestDTO;
import com.kosta.console_rpg.model.service.QuestService;
import com.kosta.console_rpg.view.QuestView;
/**
 *  업적 관련 기능 테스트
 *
 * 작성자      : 김재민
 * 생성일      : 2026.03.16
 * 최종 수정자 :
 * 최종 수정일 : 2026.03.16
 */
public class TestQuest {
    public static void main(String[] args) throws SQLException {
        QuestView.showQuestLists(1);

    	//QuestView.selectQuestIng(1);
    	//QuestView.selectQuestEnd(1);
    	
    	//quest진행도 업데이트
//    	QuestService qs = new QuestService();
//    	//진행 확인을 위한 예시 quest데이터 선택
//    	QuestDTO quest = qs.selectQuestById(1, 1);
//    	System.out.println("현재 :" + quest);
//    	//현재 진척도 조정
// 	    int currentQuestProgress = quest.getQuestIngProgress();
//    	currentQuestProgress += 1;
//    	quest.setQuestIngProgress(currentQuestProgress);
//    	System.out.println("변경 후 :" + quest);
//    	//변경 후 quest update
//    	QuestView.updateQuestProgress(quest);
//    	//확인용
//    	System.out.println("적용 후");
//    	QuestView.selectQuestById(1,1);
    	
    }
}
