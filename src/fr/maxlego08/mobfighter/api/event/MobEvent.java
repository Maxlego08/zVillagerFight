package fr.maxlego08.mobfighter.api.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MobEvent extends Event {

	private final static HandlerList handlers = new HandlerList();

	/**
	 * @return the handlers
	 */
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public void callEvent(){
		Bukkit.getPluginManager().callEvent(this);
	}

}
