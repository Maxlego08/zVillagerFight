package fr.maxlego08.mobfighter.api.event.events;

import fr.maxlego08.mobfighter.api.Duel;
import fr.maxlego08.mobfighter.api.event.CancelledMobEvent;

public class DuelStopEvent extends CancelledMobEvent {

	private final Duel duel;

	/**
	 * @param duel
	 */
	public DuelStopEvent(Duel duel) {
		super();
		this.duel = duel;
	}

	/**
	 * @return the duel
	 */
	public Duel getDuel() {
		return duel;
	}

}
