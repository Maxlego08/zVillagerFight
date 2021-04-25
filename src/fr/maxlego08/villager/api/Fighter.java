package fr.maxlego08.villager.api;

import org.bukkit.Location;
import org.bukkit.entity.Villager;

public interface Fighter {

	Villager getEntity();

	/**
	 * 
	 * @param location
	 */
	void spawn(Location location);

	/**
	 * 
	 * @param location
	 */
	void setPathGoal(Location location);

}
