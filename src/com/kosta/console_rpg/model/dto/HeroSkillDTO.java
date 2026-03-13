package com.kosta.console_rpg.model.dto;

public class HeroSkillDTO {
	private int heroId; 		//캐릭터 식별자
	private int skillId;		//스킬 식별자
	private int skillLevel;		//현재 스킬 레벨
	
	public HeroSkillDTO(int heroId, int skillId, int skillLevel) {
		super();
		this.heroId = heroId;
		this.skillId = skillId;
		this.skillLevel = skillLevel;
	}

	public int getHeroId() {
		return heroId;
	}

	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public int getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(int skillLevel) {
		this.skillLevel = skillLevel;
	}

	@Override
	public String toString() {
		return "HeroSkillDTO [heroId=" + heroId + ", skillId=" + skillId + ", skillLevel=" + skillLevel + "]";
	}
	
	
}
