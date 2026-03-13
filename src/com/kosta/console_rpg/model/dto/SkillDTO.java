package com.kosta.console_rpg.model.dto;

public class SkillDTO {
	private int skillId;				//스킬 식별자
	private String skillName;			//스킬명
	private int skillDamage;			//기본 데미지
	private int skillMpCost;			//MP 소모
	private int skillMaxLevel;			//스킬 최대 강화 레벨
	private int skillRequiredHeroLevel;	//사용 가능한 캐릭터 레벨
	private int skillUpgradeCost;		//스킬 강화 비용
	
	public SkillDTO(int skillId, String skillName, int skillDamage, int skillMpCost, int skillMaxLevel,
			int skillRequiredHeroLevel, int skillUpgradeCost) {
		super();
		this.skillId = skillId;
		this.skillName = skillName;
		this.skillDamage = skillDamage;
		this.skillMpCost = skillMpCost;
		this.skillMaxLevel = skillMaxLevel;
		this.skillRequiredHeroLevel = skillRequiredHeroLevel;
		this.skillUpgradeCost = skillUpgradeCost;
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public int getSkillDamage() {
		return skillDamage;
	}

	public void setSkillDamage(int skillDamage) {
		this.skillDamage = skillDamage;
	}

	public int getSkillMpCost() {
		return skillMpCost;
	}

	public void setSkillMpCost(int skillMpCost) {
		this.skillMpCost = skillMpCost;
	}

	public int getSkillMaxLevel() {
		return skillMaxLevel;
	}

	public void setSkillMaxLevel(int skillMaxLevel) {
		this.skillMaxLevel = skillMaxLevel;
	}

	public int getSkillRequiredHeroLevel() {
		return skillRequiredHeroLevel;
	}

	public void setSkillRequiredHeroLevel(int skillRequiredHeroLevel) {
		this.skillRequiredHeroLevel = skillRequiredHeroLevel;
	}

	public int getSkillUpgradeCost() {
		return skillUpgradeCost;
	}

	public void setSkillUpgradeCost(int skillUpgradeCost) {
		this.skillUpgradeCost = skillUpgradeCost;
	}

	@Override
	public String toString() {
		return "SkillDTO [skillId=" + skillId + ", skillName=" + skillName + ", skillDamage=" + skillDamage
				+ ", skillMpCost=" + skillMpCost + ", skillMaxLevel=" + skillMaxLevel + ", skillRequiredHeroLevel="
				+ skillRequiredHeroLevel + ", skillUpgradeCost=" + skillUpgradeCost + "]";
	}

	
	
}
