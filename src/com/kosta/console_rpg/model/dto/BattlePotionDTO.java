package com.kosta.console_rpg.model.dto;

public class BattlePotionDTO {
    private int inventoryId;
    private int itemId;
    private String itemName;
	private int itemEffectHp;
	private int itemEffectMp;
	private int itemAtkBonus;
	private int itemDefBonus;
    private int quantity;

    public BattlePotionDTO(int inventoryId, int itemId, String itemName, int itemEffectHp, int itemEffectMp, int itemAtkBonus, int itemDefBonus, int quantity) {
        this.inventoryId = inventoryId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemEffectHp = itemEffectHp;
        this.itemEffectMp = itemEffectMp;
        this.itemAtkBonus = itemAtkBonus;
        this.itemDefBonus = itemDefBonus;
        this.quantity = quantity;
    }

    public int getInventoryId() {
        return inventoryId;
    }
    
    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemEffectHp() {
        return itemEffectHp;
    }

    public int getItemEffectMp() {
        return itemEffectMp;
    }

    public int getItemAtkBonus() {
        return itemAtkBonus;
    }

    public int getItemDefBonus() {
        return itemDefBonus;
    }
    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return String.format("%s (HP: %d, MP: %d, ATK Bonus: %d, DEF Bonus: %d) x%d",
                itemName, itemEffectHp, itemEffectMp, itemAtkBonus, itemDefBonus, quantity);
    }

}