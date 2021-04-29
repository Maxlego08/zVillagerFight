package fr.maxlego08.mobfighter.api.configuration;

import java.util.List;

import org.bukkit.entity.EntityType;

import fr.maxlego08.mobfighter.api.particles.Particle;

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
	 * @return
	 */
	double getMinHealth();

	/**
	 * 
	 * @return
	 */
	double getMaxHealth();

	/**
	 * 
	 * @return
	 */
	double getMinDamage();

	/*
	 * 
	 */
	double getMaxDamage();

	/**
	 * 
	 * @return
	 */
	double getCriticalMultiplier();

	/**
	 * 
	 * @return
	 */
	String getDisplayName();

	/**
	 * 
	 * @return
	 */
	double getCriticalPercent();
	
	/**
	 * 
	 * @return
	 */
	List<Particle> getParticle();
	
	/**
	 * 
	 * @return
	 */
	List<String> getSentences();

	/**
	 * 
	 * @return
	 */
	void setMinHealth(double value);

	/**
	 * 
	 * @return
	 */
	void setMaxHealth(double value);

	/**
	 * 
	 * @return
	 */
	void setMinDamage(double value);

	/**
	 * 
	 * @return
	 */
	void setMaxDamage(double value);

	/**
	 * 
	 * @param value
	 */
	void setCriticalMultiplier(double value);

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

	/**
	 * 
	 * @return
	 */
	Particle getRandomParticle();

}
