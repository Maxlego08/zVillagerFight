package fr.maxlego08.mobfighter.api;

import java.util.List;
import java.util.Optional;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import fr.maxlego08.mobfighter.zcore.utils.storage.Saveable;

public interface MobManager extends Saveable {

	/**
	 * 
	 * @param name
	 * @return
	 */
	Optional<Arena> getArena(String name);

	/**
	 * 
	 * @param sender
	 * @param name
	 * @return
	 */
	void createArena(CommandSender sender, String name);

	/**
	 * 
	 * @param player
	 * @param name
	 */
	void setFirstLocation(Player player, String name);

	/**
	 * 
	 * @param player
	 * @param name
	 */
	void setSecondLocation(Player player, String name);

	/**
	 * 
	 * @param player
	 * @param name
	 */
	void setCenterLocation(Player player, String name);

	/**
	 * 
	 * @param sender
	 * @param name
	 */
	void deleteArena(CommandSender sender, String name);

	/**
	 * 
	 * @param sender
	 */
	void showArenas(CommandSender sender);

	/**
	 * 
	 * @param sender
	 * @param name
	 * @param entity2 
	 * @param entity1 
	 * @param second 
	 */
	void start(CommandSender sender, String name, EntityType entity1, EntityType entity2, int second);

	/*
	 * 
	 */
	void onDisable();

	/**
	 * 
	 * @return
	 */
	List<String> getArenas();
	
	/**
	 * 
	 * @return
	 */
	List<Duel> getDuels();

	/**
	 * 
	 * @param name
	 * @return
	 */
	Optional<Duel> getDuelByFighter(String name);
	
	/**
	 * 
	 * @param sender
	 * @param name
	 */
	void stop(CommandSender sender, String name);
	
	/**
	 * 
	 * @return
	 */
	List<String> getFighterNames();
	
	/**
	 * 
	 * @return duel
	 */
	Optional<Duel> getCurrentDuel();

}
