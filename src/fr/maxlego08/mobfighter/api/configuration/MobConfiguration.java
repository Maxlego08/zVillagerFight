package fr.maxlego08.mobfighter.api.configuration;

import org.bukkit.entity.EntityType;

public interface MobConfiguration {

	/**
	 * 
	 * @return
	 */
	EntityType getType();

	/**
	 * 
	 * @return
	 */
	double getDistance();

	/**
	 * 
	 * @return
	 */
	double getSpeed();

	/*
	 * /*
	 * 
	 */
	double getPushSpeed();

	/**
	 * 
	 * @return
	 */
	double getPushHeight();

	/**
	 * 
	 * @param value
	 */
	void setPushHeight(double value);

	/**
	 * 
	 * @param value
	 */
	void setPushSpeed(double value);

	/**
	 * 
	 * @param value
	 */
	void setSpeed(double value);

	/**
	 * 
	 * @param value
	 */
	void setDistance(double value);

}
