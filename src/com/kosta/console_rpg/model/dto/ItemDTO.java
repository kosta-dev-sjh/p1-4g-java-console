package com.kosta.console_rpg.model.dto;

public class ItemDTO {
	private int itemId;			//item 식별
	private String itemName;	//아이템명
	private String itemType;	//아이템 타입
	private int itemPriceBuy;	//구매가
	private int itemPriceSell;	//판매가
	private int itemEffectHp;	//소비 효과
	private int itemEffectMp;	//소비 효과
	private int itemAtkBonus;	//장비 보너스
	private int itemDefBonus;	//장비 보너스
	private String itemGrade;	//장비 아이템 등급
	public ItemDTO(int itemId, String itemName, String itemType, int itemPriceBuy, int itemPirceSell, int itemEffectHp,
			int itemEffectMp, int itemAtkBonus, int itemDefBonus, String itemGrade) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemType = itemType;
		this.itemPriceBuy = itemPriceBuy;
		this.itemPriceSell = itemPirceSell;
		this.itemEffectHp = itemEffectHp;
		this.itemEffectMp = itemEffectMp;
		this.itemAtkBonus = itemAtkBonus;
		this.itemDefBonus = itemDefBonus;
		this.itemGrade = itemGrade;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public int getItemPriceBuy() {
		return itemPriceBuy;
	}
	public void setItemPriceBuy(int itemPriceBuy) {
		this.itemPriceBuy = itemPriceBuy;
	}
	public int getItemPriceSell() {
		return itemPriceSell;
	}
	public void setItemPirceSell(int itemPirceSell) {
		this.itemPriceSell = itemPirceSell;
	}
	public int getItemEffectHp() {
		return itemEffectHp;
	}
	public void setItemEffectHp(int itemEffectHp) {
		this.itemEffectHp = itemEffectHp;
	}
	public int getItemEffectMp() {
		return itemEffectMp;
	}
	public void setItemEffectMp(int itemEffectMp) {
		this.itemEffectMp = itemEffectMp;
	}
	public int getItemAtkBonus() {
		return itemAtkBonus;
	}
	public void setItemAtkBonus(int itemAtkBonus) {
		this.itemAtkBonus = itemAtkBonus;
	}
	public int getItemDefBonus() {
		return itemDefBonus;
	}
	public void setItemDefBonus(int itemDefBonus) {
		this.itemDefBonus = itemDefBonus;
	}
	public String getItemGrade() {
		return itemGrade;
	}
	public void setItemGrade(String itemGrade) {
		this.itemGrade = itemGrade;
	}
	@Override
	public String toString() {
		return "ItemDTO [itemId=" + itemId + ", itemName=" + itemName + ", itemType=" + itemType + ", itemPriceBuy="
				+ itemPriceBuy + ", itemPirceSell=" + itemPriceSell + ", itemEffectHp=" + itemEffectHp
				+ ", itemEffectMp=" + itemEffectMp + ", itemAtkBonus=" + itemAtkBonus + ", itemDefBonus=" + itemDefBonus
				+ ", itemGrade=" + itemGrade + "]";
	}
	
	
	
}

