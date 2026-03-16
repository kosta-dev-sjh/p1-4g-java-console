package com.kosta.console_rpg.model.dto;
/**
 * 전투용 영웅 정보를 담는 DTO 클래스
 */
public class BattleHeroDTO {
    private String heroName;
    private int heroLevel;
    private int heroExp;
    private int heroHp;
    private int heroMp;
    private int heroAttack;
    private int heroDefense;
    
    private int tempAtkBonus;
    private int tempDefBonus;
    private boolean atkBuffActive;
    private boolean defBuffActive;
    private int atkBuffTurn;
    private int defBuffTurn;
    
    /**
     * BattleHeroDTO 생성자
     * @param heroName
     * @param heroLevel
     * @param heroExp
     * @param heroHp
     * @param heroMp
     * @param heroAttack
     * @param heroDefense
     */
    public BattleHeroDTO(String heroName, int heroLevel, int heroExp,
                        int heroHp, int heroMp, int heroAttack, int heroDefense) {
        this.heroName = heroName;
        this.heroLevel = heroLevel;
        this.heroExp = heroExp;
        this.heroHp = heroHp;
        this.heroMp = heroMp;
        this.heroAttack = heroAttack;
        this.heroDefense = heroDefense;

        this.tempAtkBonus = 0;
        this.tempDefBonus = 0;
        this.atkBuffActive = false;
        this.defBuffActive = false;
        this.atkBuffTurn = 0;
        this.defBuffTurn = 0;

    }
    
    public String getHeroName() { return heroName; }
    public void setHeroName(String heroName) { this.heroName = heroName; }
    
    public int getHeroLevel() { return heroLevel; }
    public void setHeroLevel(int heroLevel) { this.heroLevel = heroLevel; }
    
    public int getHeroExp() { return heroExp; }
    public void setHeroExp(int heroExp) { this.heroExp = heroExp; }
    
    public int getHeroHp() { return heroHp; }
    public void setHeroHp(int heroHp) { this.heroHp = heroHp; }
    
    public int getHeroMp() { return heroMp; }
    public void setHeroMp(int heroMp) { this.heroMp = heroMp; }
    
    public int getHeroAttack() { return heroAttack; }
    public void setHeroAttack(int heroAttack) { this.heroAttack = heroAttack; }
    
    public int getHeroDefense() { return heroDefense; }
    public void setHeroDefense(int heroDefense) { this.heroDefense = heroDefense; }
    
    public int getTempAtkBonus() { return tempAtkBonus; }
    public void setTempAtkBonus(int tempAtkBonus) { this.tempAtkBonus = tempAtkBonus; }
    
    public int getTempDefBonus() { return tempDefBonus; }
    public void setTempDefBonus(int tempDefBonus) { this.tempDefBonus = tempDefBonus; }
    
    public boolean isAtkBuffActive() { return atkBuffActive; }
    public void setAtkBuffActive(boolean atkBuffActive) { this.atkBuffActive = atkBuffActive; }
    
    public boolean isDefBuffActive() { return defBuffActive; }
    public void setDefBuffActive(boolean defBuffActive) { this.defBuffActive = defBuffActive; }

    public int getAtkBuffTurn() { return atkBuffTurn; }
    public void setAtkBuffTurn(int atkBuffTurn) { this.atkBuffTurn = atkBuffTurn; }

    public int getDefBuffTurn() { return defBuffTurn; }
    public void setDefBuffTurn(int defBuffTurn) { this.defBuffTurn = defBuffTurn; }


    /**
     * 공격 버프를 해제한다.
     */
    public void clearAtkBuff() {
        this.tempAtkBonus = 0;
        this.atkBuffActive = false;
        this.atkBuffTurn = 0;
    }
    /**
     * 방어 버프를 해제한다.
     */
    public void clearDefBuff() {
        this.tempDefBonus = 0;
        this.defBuffActive = false;
        this.defBuffTurn = 0;
    }

    /**
     * 버프의 남은 턴 수를 감소시킨다.
     */
    public void decrementBuffTurns() {
        if (atkBuffActive) {
            atkBuffTurn--;
            if (atkBuffTurn <= 0) {
                clearAtkBuff();
            }
        }

        if (defBuffActive) {
            defBuffTurn--;
            if (defBuffTurn <= 0) {
                clearDefBuff();
            }
        }
    }

    @Override
    public String toString() {
        return "BattleHeroDTO [heroName=" + heroName +
            ", heroHp=" + heroHp +
            ", heroMp=" + heroMp +
            ", heroAttack=" + heroAttack +
            ", heroDefense=" + heroDefense +
            ", tempAtkBonus=" + tempAtkBonus +
            ", tempDefBonus=" + tempDefBonus +
            "]";
    }
}