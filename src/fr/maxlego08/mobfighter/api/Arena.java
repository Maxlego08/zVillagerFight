package fr.maxlego08.mobfighter.api;

import org.bukkit.Location;

public interface Arena {

	/**
	 * Get arena name
	 * 
	 * @return name
	 */
	String getName();

	/**
	 * Get first location
	 * 
	 * @return {@link Location}
	 */
	Location getFirstLocation();

	/**
	 * Get second location
	 * 
	 * @return {@link Location}
	 */
	Location getSecondLocation();

	/**
	 * Get center location
	 * 
	 * @return {@link Location}
	 */
	Location getCenterLocation();

	/**
	 * 
	 * @param location
	 */
	void setCenterLocation(Location location);

	/**
	 * 
	 * @param location
	 */
	void setFirstLocation(Location location);

	/**
	 * 
	 * @param location
	 */
	void setSecondLocation(Location location);

	/**
	 * 
	 * @return true if arena is valid
	 */
	boolean isValid();

	/**
	 * 
	 * @return
	 */
	boolean isActive();

	/**
	 * 
	 * @param duel
	 */
	void setDuel(Duel duel);

	/**
	 * 
	 * @return
	 */
	Duel getDuel();

	/**
	 * 
	 * @return
	 */
	boolean isReady();

}
