package fr.maxlego08.mobfighter.api.event.events;

import fr.maxlego08.mobfighter.api.Duel;
import fr.maxlego08.mobfighter.api.Fighter;
import fr.maxlego08.mobfighter.api.event.CancelledMobEvent;

public class FighterTouchEvent extends CancelledMobEvent {

	private final Fighter fighter;
	private final Fighter opponent;
	private final Duel duel;
	private double damage;

	/**
	 * @param fighter
	 * @param opponent
	 * @param duel
	 * @param damage
	 */
	public FighterTouchEvent(Fighter fighter, Fighter opponent, Duel duel, double damage) {
		super();
		this.fighter = fighter;
		this.opponent = opponent;
		this.duel = duel;
		this.damage = damage;
	}

	/**
	 * @return the fighter
	 */
	public Fighter getFighter() {
		return fighter;
	}

	/**
	 * @return the opponent
	 */
	public Fighter getOpponent() {
		return opponent;
	}

	/**
	 * @return the duel
	 */
	public Duel getDuel() {
		return duel;
	}

	/**
	 * @return the damage
	 */
	public double getDamage() {
		return damage;
	}

	/**
	 * @param damage
	 *            the damage to set
	 */
	public void setDamage(double damage) {
		this.damage = damage;
	}

}
