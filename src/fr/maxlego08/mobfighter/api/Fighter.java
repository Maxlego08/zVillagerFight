package fr.maxlego08.mobfighter.api;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import fr.maxlego08.mobfighter.api.configuration.MobConfiguration;

public interface Fighter {

	LivingEntity getEntity();

	/**
	 * 
	 * @param location
	 */
	void spawn(Location location);

	/**
	 * 
	 * @return
	 */
	Location getLocation();

	/**
	 * 
	 * @param fighter
	 * @return distance
	 */
	double distance(Fighter fighter);

	/**
	 * 
	 * @param location
	 * @param distance
	 */
	void push(Location location);
	
	/**
	 * 
	 * @return
	 */
	MobConfiguration getConfiguration();

	/**
	 * 
	 * @return
	 */
	boolean isValid();

	/**
	 * 
	 * @param secondFighter
	 */
	void setTarget(Fighter secondFighter);

}
