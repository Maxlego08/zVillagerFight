package fr.maxlego08.mobfighter.configuration;

import org.bukkit.entity.EntityType;

import fr.maxlego08.mobfighter.api.configuration.MobConfiguration;

public class ZMobConfiguration implements MobConfiguration {

	private final EntityType type;
	private double speed;
	private double distance;
	private double pushHeight;
	private double pushSpeed;

	/**
	 * @param type
	 * @param speed
	 * @param distance
	 * @param pushHeight
	 * @param pushSpeed
	 */
	public ZMobConfiguration(EntityType type, double speed, double distance, double pushHeight, double pushSpeed) {
		super();
		this.type = type;
		this.speed = speed;
		this.distance = distance;
		this.pushHeight = pushHeight;
		this.pushSpeed = pushSpeed;
	}

	/**
	 * @return the type
	 */
	public EntityType getType() {
		return type;
	}

	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @return the pushHeight
	 */
	public double getPushHeight() {
		return pushHeight;
	}

	/**
	 * @return the pushSpeed
	 */
	public double getPushSpeed() {
		return pushSpeed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	/**
	 * @param distance
	 *            the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * @param pushHeight
	 *            the pushHeight to set
	 */
	public void setPushHeight(double pushHeight) {
		this.pushHeight = pushHeight;
	}

	/**
	 * @param pushSpeed
	 *            the pushSpeed to set
	 */
	public void setPushSpeed(double pushSpeed) {
		this.pushSpeed = pushSpeed;
	}

}
