package fr.maxlego08.mobfighter.api.button.buttons;

import fr.maxlego08.mobfighter.api.inventory.Inventory;

public interface BackButton extends InventoryButton {

	/**
	 * Set back inventory
	 * @param inventory
	 */
	public void setBackInventory(Inventory inventory);
	
}
