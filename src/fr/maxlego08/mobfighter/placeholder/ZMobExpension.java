package fr.maxlego08.mobfighter.placeholder;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class ZMobExpension extends PlaceholderExpansion {

	private final Plugin plugin;

	/**
	 * @param plugin
	 */
	public ZMobExpension(Plugin plugin) {
		super();
		this.plugin = plugin;
	}

	@Override
	public String getAuthor() {
		return "Maxlego08";
	}

	@Override
	public String getIdentifier() {
		return ZPlaceholderAPI.prefix;
	}

	@Override
	public String getVersion() {
		return plugin.getDescription().getVersion();
	}

	@Override
	public String onPlaceholderRequest(Player player, String params) {
		return ZPlaceholderAPI.getInstance().onRequest(player, params);
	}

}
