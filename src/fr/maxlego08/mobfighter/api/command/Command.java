package fr.maxlego08.mobfighter.api.command;

import java.util.List;

import fr.maxlego08.mobfighter.api.inventory.Inventory;

public interface Command {

	/**
	 * 
	 * @return
	 */
	public String getCommand();
	
	/**
	 * 
	 * @return
	 */
	public String getPermission();
	
	/**
	 * 
	 * @return
	 */
	public String getDescription();
	
	/**
	 * 
	 * @return
	 */
	public List<String> getAliases();
	
	/**
	 * 
	 * @return
	 */
	public Inventory getInventory();
	
	
}
