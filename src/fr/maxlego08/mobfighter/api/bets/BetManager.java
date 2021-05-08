package fr.maxlego08.mobfighter.api.bets;

import java.util.List;
import java.util.Optional;

import org.bukkit.entity.Player;

import fr.maxlego08.mobfighter.api.Duel;
import fr.maxlego08.mobfighter.api.Fighter;

public interface BetManager {

	/**
	 * 
	 * @param player
	 * @return
	 */
	Optional<Bet> getBet(Player player);
	
	/**
	 * 
	 * @param player
	 * @param name
	 * @param bet
	 */
	void createBet(Player player, String name, long bet);
	
	/**
	 * 
	 * @param duel
	 * @param winner
	 */
	void giveBets(Duel duel, Fighter winner);
	
	/**
	 * 
	 * @param duel
	 */
	void refundBets(Duel duel);
	
	/**
	 * 
	 * @param player
	 */
	void refundBet(Player player);

	/**
	 * 
	 * @param duel
	 * @return bets
	 */
	List<Bet> getBets(Duel duel);
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	double getMultiplicator(Player player);

	/**
	 * 
	 * @param player
	 */
	void sendBet(Player player);
	
}
