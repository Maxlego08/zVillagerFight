package fr.maxlego08.mobfighter.api.button.buttons;

import fr.maxlego08.mobfighter.api.inventory.Inventory;

public interface InventoryButton extends PermissibleButton{

	/**
	 * 
	 * @return {@link Inventory}
	 */
	public Inventory getInventory();
	
}
