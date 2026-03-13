package com.kosta.console_rpg.model.dto;

public class QuestDTO {
	private int questId;		//업적 식별자
	private String questName;	//업적명
	private String questInfo;	//업적 내용(설명)
	private int questTarget;	//업적 목표 수치
	private String questType;	//업적 종류
	
	public QuestDTO(int questId, String questName, String questInfo, int questTarget, String questType) {
		super();
		this.questId = questId;
		this.questName = questName;
		this.questInfo = questInfo;
		this.questTarget = questTarget;
		this.questType = questType;
	}

	public int getQuestId() {
		return questId;
	}

	public void setQuestId(int questId) {
		this.questId = questId;
	}

	public String getQuestName() {
		return questName;
	}

	public void setQuestName(String questName) {
		this.questName = questName;
	}

	public String getQuestInfo() {
		return questInfo;
	}

	public void setQuestInfo(String questInfo) {
		this.questInfo = questInfo;
	}

	public int getQuestTarget() {
		return questTarget;
	}

	public void setQuestTarget(int questTarget) {
		this.questTarget = questTarget;
	}

	public String getQuestType() {
		return questType;
	}

	public void setQuestType(String questType) {
		this.questType = questType;
	}

	@Override
	public String toString() {
		return "QuestDTO [questId=" + questId + ", questName=" + questName + ", questInfo=" + questInfo
				+ ", questTarget=" + questTarget + ", questType=" + questType + "]";
	}
	
	
	
	
	
	
}
