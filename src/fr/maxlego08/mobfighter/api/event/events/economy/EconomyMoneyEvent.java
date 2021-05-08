package fr.maxlego08.mobfighter.api.event.events.economy;

import org.bukkit.OfflinePlayer;

import fr.maxlego08.mobfighter.api.event.MobEvent;

public class EconomyMoneyEvent extends MobEvent {

	private final OfflinePlayer player;
	private double money;

	public EconomyMoneyEvent(OfflinePlayer player) {
		super();
		this.player = player;
	}

	/**
	 * @return the player
	 */
	public OfflinePlayer getPlayer() {
		return player;
	}

	/**
	 * @return the money
	 */
	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

}
