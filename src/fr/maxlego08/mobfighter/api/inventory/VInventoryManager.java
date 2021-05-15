package fr.maxlego08.mobfighter.api.inventory;

import org.bukkit.entity.Player;

import fr.maxlego08.mobfighter.api.enums.EnumInventory;

public interface VInventoryManager {

	void createInventory(EnumInventory inv, Player player, int page, Object... objects);

}
