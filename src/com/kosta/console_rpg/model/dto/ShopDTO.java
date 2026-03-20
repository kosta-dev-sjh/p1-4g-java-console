package com.kosta.console_rpg.model.dto;

public class ShopDTO {
	private int shopId;		//shop 식별자
	private int itemId;		//아이템 식별자
	
	public ShopDTO(int shopId, int itemId) {
		super();
		this.shopId = shopId;
		this.itemId = itemId;
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	@Override
	public String toString() {
		return "ShopDTO [shopId=" + shopId + ", itemId=" + itemId + "]";
	}
	
	
	
	

}
