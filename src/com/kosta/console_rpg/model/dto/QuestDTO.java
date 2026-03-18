package com.kosta.console_rpg.model.dto;

public class QuestDTO {
	private int questId;				//업적 식별자
	private String questName;			//업적명
	private String questInfo;			//업적 내용(설명)
	private int questTarget;			//업적 목표 수치
	private String questType;			//업적 종류
	private int heroId;					//식별용 heroId
	private boolean questIngComplete;	//업적 완료 여부
	private int questIngProgress;		//업적 진행도
	public QuestDTO(){

	}
    public QuestDTO(int questId, String questName, String questInfo, int questTarget, String questType, boolean questIngComplete, int questIngProgress) {
        this.questId = questId;
        this.questName = questName;
        this.questInfo = questInfo;
        this.questTarget = questTarget;
        this.questType = questType;
        this.questIngComplete = questIngComplete;
        this.questIngProgress = questIngProgress;
    }

    // 생성자 2 (오버로딩)
    public QuestDTO(int questId, String questName, String questInfo, int questTarget, String questType, int heroId, boolean questIngComplete, int questIngProgress) {
        this(questId, questName, questInfo, questTarget, questType, questIngComplete, questIngProgress);
        this.heroId = heroId;
    }
	
	
	
//	//상세 퀘스트
//	public QuestDTO(int questId,String questName, String questInfo, int questTarget, String questType, boolean questIngComplete, int questIngProgress) {		
//		this.questId = questId;
//		this.questName = questName;
//		this.questInfo = questInfo;
//		this.questTarget = questTarget;
//		this.questType = questType;
//		this.questIngComplete = questIngComplete;
//		this.questIngProgress = questIngProgress;
//	}
//	public QuestDTO(int questId, String questName, String questInfo, int questTarget, String questType, int heroId, boolean questIngComplete, int questIngProgress) {
//		this(questId,questName,questInfo,questIngComplete,questIngProgress);
//		this.questId = questId;
//		this.questName = questName;
//		this.questInfo = questInfo;
//		this.questTarget = questTarget;
//		this.questType = questType;
//		this.heroId = heroId;
//		this.questIngComplete = questIngComplete;
//		this.questIngProgress = questIngProgress;
//	}

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

	public int getQuestIngProgress() {
		return questIngProgress;
	}

	public void setQuestIngProgress(int questIngProgress) {
		this.questIngProgress = questIngProgress;
	}

	public boolean isQuestIngComplete() {
		return questIngComplete;
	}

	public void setQuestIngComplete(boolean questIngComplete) {
		this.questIngComplete = questIngComplete;
	}

	public int getHeroId() {
		return heroId;
	}

	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	@Override
	public String toString() {
		return "QuestDTO{" +
				"questId=" + questId +
				", questName='" + questName + '\'' +
				", questInfo='" + questInfo + '\'' +
				", questTarget=" + questTarget +
				", questType='" + questType + '\'' +
				", heroId=" + heroId +
				", questIngComplete=" + questIngComplete +
				", questIngProgress=" + questIngProgress +
				'}';
	}
}
