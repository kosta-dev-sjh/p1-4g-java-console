package com.kosta.console_rpg.model.dto;

public class HeroSkillDTO {
	private int heroId;          // 캐릭터 식별자
	private SkillDTO skill;      // 스킬 정보
	private int skillLevel;      // 현재 스킬 레벨

	public HeroSkillDTO(int heroId, SkillDTO skill, int skillLevel) {
		super();
		this.heroId = heroId;
		this.skill = skill;
		this.skillLevel = skillLevel;
	}
	
	public int getHeroId() {
		return heroId;
	}

	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	public SkillDTO getSkill() {
		return skill;
	}

	public void setSkill(SkillDTO skill) {
		this.skill = skill;
	}

	public int getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(int skillLevel) {
		this.skillLevel = skillLevel;
	}

	@Override
	public String toString() {
		return "HeroSkillDTO [heroId=" + heroId + ", skill=" + skill + ", skillLevel=" + skillLevel + "]";
	}
	
	
}
