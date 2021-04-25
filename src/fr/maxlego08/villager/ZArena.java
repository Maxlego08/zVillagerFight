package fr.maxlego08.villager;

import org.bukkit.Location;

import fr.maxlego08.villager.api.Arena;

public class ZArena implements Arena {

	private final String name;
	private Location firstLocation;
	private Location secondLocation;
	private Location centerLocation;
	private boolean isActive = false;

	/**
	 * @param name
	 */
	public ZArena(String name) {
		super();
		this.name = name;
	}

	/**
	 * @param name
	 * @param firstLocation
	 * @param secondLocation
	 * @param centerLocation
	 * @param isActive
	 */
	public ZArena(String name, Location firstLocation, Location secondLocation, Location centerLocation) {
		super();
		this.name = name;
		this.firstLocation = firstLocation;
		this.secondLocation = secondLocation;
		this.centerLocation = centerLocation;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the firstLocation
	 */
	public Location getFirstLocation() {
		return firstLocation.clone();
	}

	/**
	 * @return the secondLocation
	 */
	public Location getSecondLocation() {
		return secondLocation.clone();
	}

	/**
	 * @return the centerLocation
	 */
	public Location getCenterLocation() {
		return centerLocation.clone();
	}

	/**
	 * @param firstLocation
	 *            the firstLocation to set
	 */
	public void setFirstLocation(Location firstLocation) {
		this.firstLocation = firstLocation;
	}

	/**
	 * @param secondLocation
	 *            the secondLocation to set
	 */
	public void setSecondLocation(Location secondLocation) {
		this.secondLocation = secondLocation;
	}

	/**
	 * @param centerLocation
	 *            the centerLocation to set
	 */
	public void setCenterLocation(Location centerLocation) {
		this.centerLocation = centerLocation;
	}

	@Override
	public boolean isValid() {
		return this.centerLocation != null && this.firstLocation != null && this.secondLocation != null;
	}

	@Override
	public boolean isActive() {
		return this.isActive;
	}

}
