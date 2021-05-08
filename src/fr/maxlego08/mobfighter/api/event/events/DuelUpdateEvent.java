package fr.maxlego08.mobfighter.api.event.events;

import fr.maxlego08.mobfighter.api.Duel;
import fr.maxlego08.mobfighter.api.event.CancelledMobEvent;

public class DuelUpdateEvent extends CancelledMobEvent {

	private double d1;
	private double d2;
	private double distance;
	private final Duel duel;

	/**
	 * @param d1
	 * @param d2
	 * @param distance
	 * @param duel
	 */
	public DuelUpdateEvent(double d1, double d2, double distance, Duel duel) {
		super();
		this.d1 = d1;
		this.d2 = d2;
		this.distance = distance;
		this.duel = duel;
	}

	/**
	 * @return the d1
	 */
	public double getD1() {
		return d1;
	}

	/**
	 * @return the d2
	 */
	public double getD2() {
		return d2;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @return the duel
	 */
	public Duel getDuel() {
		return duel;
	}

	/**
	 * @param d1
	 *            the d1 to set
	 */
	public void setD1(double d1) {
		this.d1 = d1;
	}

	/**
	 * @param d2
	 *            the d2 to set
	 */
	public void setD2(double d2) {
		this.d2 = d2;
	}

	/**
	 * @param distance
	 *            the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

}
