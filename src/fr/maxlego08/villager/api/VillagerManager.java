package fr.maxlego08.villager.api;

import java.util.Optional;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.maxlego08.villager.zcore.utils.storage.Saveable;

public interface VillagerManager extends Saveable {

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
	 */
	void start(CommandSender sender, String name);

}
