package com.kosta.console_rpg.model.dto;

public class HeroSkillDTO {
	private final static int SKILL_BONUS = 5; // 스킬 레벨당 추가 데미지
	private final static int MP_CONSUMPTION = 5; // MP 소모량

	private int heroId; // 캐릭터 식별자
	private SkillDTO skill; // 스킬 정보
	private int skillLevel; // 현재 스킬 레벨

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

	public int getCalculatedDamage() {
		return skill.getSkillDamage() + (skillLevel - 1) * SKILL_BONUS;
	}

	public int getCalculatedMpCost() {
		return skill.getSkillMpCost() + (skillLevel - 1) * MP_CONSUMPTION;
	}

	public int getUpgradeCost() {
		return skill.getSkillUpgradeCost() * skillLevel;
	}

	@Override
	public String toString() {
		return """
				%s
				▸ 레벨 : %d
				▸ 데미지 : %d
				▸ MP 소모 : %d
				▸ 강화 비용 : %dG
				"""
				.formatted(
						skill.getSkillName(),
						skillLevel,
						getCalculatedDamage(),
						getCalculatedMpCost(),
						getUpgradeCost());
	}

	public String toBattleString() {
		return """
				%s
				   ▸ 레벨 : %d
				   ▸ 데미지 : %d
				   ▸ MP 소모 : %d
				"""
				.formatted(
						skill.getSkillName(),
						skillLevel,
						getCalculatedDamage(),
						getCalculatedMpCost());
	}

}
