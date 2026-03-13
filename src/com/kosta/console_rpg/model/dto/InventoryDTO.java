package com.kosta.console_rpg.model.dto;

public class InventoryDTO {
	private int inventoryId;			//inventory 식별자
	private int heroId;					//캐릭터 식별자
	private int itemId;					//아이템 식별자
	private int inventoryQuantity;		//수량
	private int inventoryIsEquipped;	//장비 착용 유무
	
	public InventoryDTO(int inventoryId, int heroId, int itemId, int inventoryQuantity, int inventoryIsEquipped) {
		super();
		this.inventoryId = inventoryId;
		this.heroId = heroId;
		this.itemId = itemId;
		this.inventoryQuantity = inventoryQuantity;
		this.inventoryIsEquipped = inventoryIsEquipped;
	}

	public int getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}

	public int getHeroId() {
		return heroId;
	}

	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getInventoryQuantity() {
		return inventoryQuantity;
	}

	public void setInventoryQuantity(int inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}

	public int getInventoryIsEquipped() {
		return inventoryIsEquipped;
	}

	public void setInventoryIsEquipped(int inventoryIsEquipped) {
		this.inventoryIsEquipped = inventoryIsEquipped;
	}

	@Override
	public String toString() {
		return "InventoryDTO [inventoryId=" + inventoryId + ", heroId=" + heroId + ", itemId=" + itemId
				+ ", inventoryQuantity=" + inventoryQuantity + ", inventoryIsEquipped=" + inventoryIsEquipped + "]";
	}
	
	
	
}
