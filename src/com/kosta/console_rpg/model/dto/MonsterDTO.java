package com.kosta.console_rpg.model.dto;

public class MonsterDTO {
	private int monsterId;			//몬스터 식별자
	private String monsterName;		//몬스터명
	private int monsterStage;		//스테이지
	private int monsterHp;			//HP
	private int monsterAttack;		//공격력
	private int monsterDefense;		//방어력
	private int monsterRewardExp;	//보상 경험치
	private int monsterReward;		//보상 골드
	private int skillId;			//스킬 식별자
	private int monsterSkillProb;	//스킬 사용 확률
	
	public MonsterDTO(int monsterId, String monsterName, int monsterStage, int monsterHp, int monsterAttack,
			int monsterDefense, int monsterRewardExp, int monsterReward, int skillId, int monsterSkillProb) {
		super();
		this.monsterId = monsterId;
		this.monsterName = monsterName;
		this.monsterStage = monsterStage;
		this.monsterHp = monsterHp;
		this.monsterAttack = monsterAttack;
		this.monsterDefense = monsterDefense;
		this.monsterRewardExp = monsterRewardExp;
		this.monsterReward = monsterReward;
		this.skillId = skillId;
		this.monsterSkillProb = monsterSkillProb;
	}

	public int getMonsterId() {
		return monsterId;
	}

	public void setMonsterId(int monsterId) {
		this.monsterId = monsterId;
	}

	public String getMonsterName() {
		return monsterName;
	}

	public void setMonsterName(String monsterName) {
		this.monsterName = monsterName;
	}

	public int getMonsterStage() {
		return monsterStage;
	}

	public void setMonsterStage(int monsterStage) {
		this.monsterStage = monsterStage;
	}

	public int getMonsterHp() {
		return monsterHp;
	}

	public void setMonsterHp(int monsterHp) {
		this.monsterHp = monsterHp;
	}

	public int getMonsterAttack() {
		return monsterAttack;
	}

	public void setMonsterAttack(int monsterAttack) {
		this.monsterAttack = monsterAttack;
	}

	public int getMonsterDefense() {
		return monsterDefense;
	}

	public void setMonsterDefense(int monsterDefense) {
		this.monsterDefense = monsterDefense;
	}

	public int getMonsterRewardExp() {
		return monsterRewardExp;
	}

	public void setMonsterRewardExp(int monsterRewardExp) {
		this.monsterRewardExp = monsterRewardExp;
	}

	public int getMonsterReward() {
		return monsterReward;
	}

	public void setMonsterReward(int monsterReward) {
		this.monsterReward = monsterReward;
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public int getMonsterSkillProb() {
		return monsterSkillProb;
	}

	public void setMonsterSkillProb(int monsterSkillProb) {
		this.monsterSkillProb = monsterSkillProb;
	}

	@Override
	public String toString() {
		return "MonsterDTO [monsterId=" + monsterId + ", monsterName=" + monsterName + ", monsterStage=" + monsterStage
				+ ", monsterHp=" + monsterHp + ", monsterAttack=" + monsterAttack + ", monsterDefense=" + monsterDefense
				+ ", monsterRewardExp=" + monsterRewardExp + ", monsterReward=" + monsterReward + ", skillId=" + skillId
				+ ", monsterSkillProb=" + monsterSkillProb + "]";
	}
	
	
	
}
