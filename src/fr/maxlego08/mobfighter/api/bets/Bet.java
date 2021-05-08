package fr.maxlego08.mobfighter.api.bets;

import org.bukkit.entity.Player;

import fr.maxlego08.mobfighter.api.Duel;
import fr.maxlego08.mobfighter.api.Fighter;
import fr.maxlego08.mobfighter.api.IEconomy;

public interface Bet {

	/**
	 * 
	 * @return player
	 */
	Player getPlayer();
	
	/**
	 * 
	 * @return bet
	 */
	long getBet();
	
	/**
	 * 
	 * @return duel
	 */
	Duel getDuel();
	
	/**
	 * 
	 * @return fither
	 */
	Fighter getFighter();

	/**
	 * 
	 * @param economy
	 */
	void refund(IEconomy economy);
	
}
