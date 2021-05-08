package fr.maxlego08.mobfighter.api.event;

import org.bukkit.event.Cancellable;

public class CancelledMobEvent extends MobEvent implements Cancellable {

	private boolean cancelled;

	/**
	 * @return the cancelled
	 */
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	 * @param cancelled
	 *            the cancelled to set
	 */
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}
