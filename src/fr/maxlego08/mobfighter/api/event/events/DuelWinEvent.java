package fr.maxlego08.mobfighter.api.event.events;

import fr.maxlego08.mobfighter.api.Duel;
import fr.maxlego08.mobfighter.api.Fighter;
import fr.maxlego08.mobfighter.api.event.MobEvent;

public class DuelWinEvent extends MobEvent {

	private final Fighter winner;
	private final Fighter looser;
	private final Duel duel;

	/**
	 * @param winner
	 * @param looser
	 * @param duel
	 */
	public DuelWinEvent(Fighter winner, Fighter looser, Duel duel) {
		super();
		this.winner = winner;
		this.looser = looser;
		this.duel = duel;
	}

	/**
	 * @return the winner
	 */
	public Fighter getWinner() {
		return winner;
	}

	/**
	 * @return the looser
	 */
	public Fighter getLooser() {
		return looser;
	}

	/**
	 * @return the duel
	 */
	public Duel getDuel() {
		return duel;
	}

}
