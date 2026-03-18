package com.kosta.console_rpg.model.dto;

import java.time.LocalDateTime;

public class HeroDTO {
	private int heroId;				//캐릭터 고유 식별자
	private int userId; 			//사용자 고유 식별자(fk)
	private String heroName;		//캐릭터 이름
	private int heroLevel;			//캐릭터 레벨
	private int heroExp;			//현재 경험치
	private int heroHp;				//현재 체력
	private int heroMp;				//현재 마력
	private int heroAttack;			//공격력
	private int heroDefense;		//방어력
	private int heroGem;			//젬 (소지금 / 인게임 재화)
	private LocalDateTime heroCreatedAt;	//캐릭터 생성 일시
	private int heroMaxClearStage;	//현재까지 클리어한 최고 스테이지 번호
	public HeroDTO(int heroId, int userId, String heroName, int heroLevel, int heroExp, int heroHp, int heroMp,
			int heroAttack, int heroDefense, int heroGem, LocalDateTime heroCreatedAt, int heroMaxClearStage) {
		super();
		this.heroId = heroId;
		this.userId = userId;
		this.heroName = heroName;
		this.heroLevel = heroLevel;
		this.heroExp = heroExp;
		this.heroHp = heroHp;
		this.heroMp = heroMp;
		this.heroAttack = heroAttack;
		this.heroDefense = heroDefense;
		this.heroGem = heroGem;
		this.heroCreatedAt = heroCreatedAt;
		this.heroMaxClearStage = heroMaxClearStage;
	}
	
	/**
	 * 전투용 HeroDTO 생성자
	 *
	 * @param heroName 영웅 이름
	 * @param heroLevel 영웅 레벨
	 * @param heroExp 영웅 경험치
	 * @param heroHp 영웅 체력
	 * @param heroMp 영웅 마나
	 * @param heroAttack 영웅 공격력
	 * @param heroDefense 영웅 방어력
	 */
	public HeroDTO(String heroName, int heroLevel, int heroExp,int heroHp, int heroMp, int heroAttack, int heroDefense) {
		this.heroName = heroName;
		this.heroLevel = heroLevel;
		this.heroExp = heroExp;
		this.heroHp = heroHp;
		this.heroMp = heroMp;
		this.heroAttack = heroAttack;
		this.heroDefense = heroDefense;
	}

	public int getHeroId() {
		return heroId;
	}
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getHeroName() {
		return heroName;
	}
	public void setHeroName(String heroName) {
		this.heroName = heroName;
	}
	public int getHeroLevel() {
		return heroLevel;
	}
	public void setHeroLevel(int heroLevel) {
		this.heroLevel = heroLevel;
	}
	public int getHeroExp() {
		return heroExp;
	}
	public void setHeroExp(int heroExp) {
		this.heroExp = heroExp;
	}
	public int getHeroHp() {
		return heroHp;
	}
	public void setHeroHp(int heroHp) {
		this.heroHp = heroHp;
	}
	public int getHeroMp() {
		return heroMp;
	}
	public void setHeroMp(int heroMp) {
		this.heroMp = heroMp;
	}
	public int getHeroAttack() {
		return heroAttack;
	}
	public void setHeroAttack(int heroAttack) {
		this.heroAttack = heroAttack;
	}
	public int getHeroDefense() {
		return heroDefense;
	}
	public void setHeroDefense(int heroDefense) {
		this.heroDefense = heroDefense;
	}
	public int getHeroGem() {
		return heroGem;
	}
	public void setHeroGem(int heroGem) {
		this.heroGem = heroGem;
	}
	public LocalDateTime getHeroCreatedAt() {
		return heroCreatedAt;
	}
	public void setHeroCreatedAt(LocalDateTime heroCreatedAt) {
		this.heroCreatedAt = heroCreatedAt;
	}
	public int getHeroMaxClearStage() {
		return heroMaxClearStage;
	}
	public void setHeroMaxClearStage(int heroMaxClearStage) {
		this.heroMaxClearStage = heroMaxClearStage;
	}

	@Override
	public String toString() {
		return "HeroDTO [heroId=" + heroId + ", userId=" + userId + ", heroName=" + heroName + ", heroLevel="
				+ heroLevel + ", heroExp=" + heroExp + ", heroHp=" + heroHp + ", heroMp=" + heroMp + ", heroAttack="
				+ heroAttack + ", heroDefense=" + heroDefense + ", heroGem=" + heroGem + ", heroCreatedAt="
				+ heroCreatedAt + ", heroMaxClearStage=" + heroMaxClearStage + "]";
	}
	
	
	
	
	
	
}
