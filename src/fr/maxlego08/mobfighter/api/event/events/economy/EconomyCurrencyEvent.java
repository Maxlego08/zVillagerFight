package fr.maxlego08.mobfighter.api.event.events.economy;

import fr.maxlego08.mobfighter.api.event.MobEvent;

public class EconomyCurrencyEvent extends MobEvent {

	private String currency = "$";

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
