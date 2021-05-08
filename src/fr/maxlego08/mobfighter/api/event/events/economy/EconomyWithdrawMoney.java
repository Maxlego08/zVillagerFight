package fr.maxlego08.mobfighter.api.event.events.economy;

import org.bukkit.OfflinePlayer;

import fr.maxlego08.mobfighter.api.event.MobEvent;

public class EconomyWithdrawMoney extends MobEvent {

	private final OfflinePlayer player;
	private final double money;

	public EconomyWithdrawMoney(OfflinePlayer player, double money) {
		super();
		this.player = player;
		this.money = money;
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
	
}
