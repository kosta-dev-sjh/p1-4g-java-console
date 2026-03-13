package com.kosta.console_rpg.model.dto;

public class QuestIngDTO {
	private int heroId;					//캐릭터 식별자
	private int questId;				//업적 식별자
	private boolean questIngComplete;	//업적 완료 여부
	private int questIngProgress;		//업적 진행도
	
	public QuestIngDTO(int heroId, int questId, boolean questIngComplete, int questIngProgress) {
		super();
		this.heroId = heroId;
		this.questId = questId;
		this.questIngComplete = questIngComplete;
		this.questIngProgress = questIngProgress;
	}

	public int getHeroId() {
		return heroId;
	}

	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	public int getQuestId() {
		return questId;
	}

	public void setQuestId(int questId) {
		this.questId = questId;
	}

	public boolean isQuestIngComplete() {
		return questIngComplete;
	}

	public void setQuestIngComplete(boolean questIngComplete) {
		this.questIngComplete = questIngComplete;
	}

	public int getQuestIngProgress() {
		return questIngProgress;
	}

	public void setQuestIngProgress(int questIngProgress) {
		this.questIngProgress = questIngProgress;
	}

	@Override
	public String toString() {
		return "QuestIngDTO [heroId=" + heroId + ", questId=" + questId + ", questIngComplete=" + questIngComplete
				+ ", questIngProgress=" + questIngProgress + "]";
	}
	
	
	
}
