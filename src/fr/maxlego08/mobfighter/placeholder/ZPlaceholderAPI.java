package fr.maxlego08.mobfighter.placeholder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.Duel;
import fr.maxlego08.mobfighter.api.Fighter;
import fr.maxlego08.mobfighter.api.MobManager;
import fr.maxlego08.mobfighter.api.bets.BetManager;
import fr.maxlego08.mobfighter.api.enums.BetSecletType;
import fr.maxlego08.mobfighter.api.enums.Message;

public class ZPlaceholderAPI {

	public static final String prefix = "zmobfighter";

	private ZMobPlugin plugin;

	/**
	 * 
	 * @param plugin
	 */
	public void setPlugin(ZMobPlugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * static Singleton instance.
	 */
	private static volatile ZPlaceholderAPI instance;

	/**
	 * Private constructor for singleton.
	 */
	private ZPlaceholderAPI() {
	}

	/**
	 * Return a singleton instance of ZPlaceholderAPI.
	 */
	public static ZPlaceholderAPI getInstance() {
		// Double lock for thread safety.
		if (instance == null) {
			synchronized (ZPlaceholderAPI.class) {
				if (instance == null) {
					instance = new ZPlaceholderAPI();
				}
			}
		}
		return instance;
	}

	/**
	 * 
	 * @param player
	 * @param displayName
	 * @return
	 */
	public String setPlaceholders(Player player, String displayName) {

		if (displayName == null)
			return null;

		final String realPrefix = "%" + prefix + "_";

		String str = removeColor(displayName);

		for (String string : str.split(" "))
			if (string.startsWith(realPrefix) && string.endsWith("%")) {

				String request = string.replace(realPrefix, "");
				request = request.substring(0, request.length() - 1);

				String replace = this.onRequest(player, request);
				if (replace != null)
					displayName = displayName.replace(string, replace);
			}

		return displayName;
	}

	/**
	 * 
	 * @param player
	 * @param lore
	 * @return
	 */
	public List<String> setPlaceholders(Player player, List<String> lore) {
		return lore == null ? null
				: lore.stream().map(e -> e = setPlaceholders(player, e)).collect(Collectors.toList());
	}

	/**
	 * 
	 * @param string
	 * @return string
	 */
	private String removeColor(String string) {
		if (string == null)
			return string;
		for (ChatColor chatColor : ChatColor.values())
			string = string.replace("§" + chatColor.getChar(), "");
		return string;
	}

	/**
	 * Custom placeholder
	 * 
	 * @param player
	 * @param string
	 * @return
	 */
	public String onRequest(Player player, String string) {

		MobManager manager = plugin.getManager();
		BetManager betManager = plugin.getBetManager();

		Optional<Duel> optional = manager.getCurrentDuel();
		boolean isPresent = optional.isPresent();

		if (isPresent) { // Si le duel est présent
			Duel duel = optional.get();

			switch (string) {
			case "first_name": {
				Fighter fighter = duel.getFirstFighter();
				return fighter.getName();
			}
			case "second_name": {
				Fighter fighter = duel.getSecondFighter();
				return fighter.getName();
			}
			case "selected_fighter_name": {
				BetSecletType betSecletType = betManager.getSelectedType(player);
				if (betSecletType == null)
					return Message.BET_NOT_SELECTED.getMessage();
				else {
					switch (betSecletType) {
					case FIRST: {
						Fighter fighter = duel.getFirstFighter();
						return fighter.getName();
					}
					case SECOND: {
						Fighter fighter = duel.getSecondFighter();
						return fighter.getName();
					}
					default:
						break;
					}
				}
				break;
			}
			case "bet":
				return String.valueOf(betManager.getTmpBet(player));
			default:
				break;
			}
		}

		return null;
	}

}
