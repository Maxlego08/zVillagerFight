package fr.maxlego08.mobfighter.bet;

import org.bukkit.entity.Player;

import fr.maxlego08.mobfighter.api.Duel;
import fr.maxlego08.mobfighter.api.Fighter;
import fr.maxlego08.mobfighter.api.IEconomy;
import fr.maxlego08.mobfighter.api.bets.Bet;
import fr.maxlego08.mobfighter.save.Config;

public class ZBet implements Bet {

	private final Player player;
	private final long bet;
	private final Duel duel;
	private final Fighter fighter;

	/**
	 * @param player
	 * @param bet
	 * @param duel
	 * @param fighter
	 */
	public ZBet(Player player, long bet, Duel duel, Fighter fighter) {
		super();
		this.player = player;
		this.bet = bet;
		this.duel = duel;
		this.fighter = fighter;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return the bet
	 */
	public long getBet() {
		return bet;
	}

	/**
	 * @return the duel
	 */
	public Duel getDuel() {
		return duel;
	}

	/**
	 * @return the fighter
	 */
	public Fighter getFighter() {
		return fighter;
	}

	@Override
	public void refund(IEconomy economy) {
		economy.depositMoney(Config.economy, player, bet);
	}

}
