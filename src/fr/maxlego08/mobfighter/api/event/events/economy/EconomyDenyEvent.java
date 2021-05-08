package fr.maxlego08.mobfighter.api.event.events.economy;

import fr.maxlego08.mobfighter.api.event.MobEvent;

public class EconomyDenyEvent extends MobEvent {

	private String message;

	public EconomyDenyEvent() {
		super();
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
