package fr.maxlego08.mobfighter.zcore.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.maxlego08.mobfighter.placeholder.ZPlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPI;

public class PapiUtils {

	private final transient boolean usePlaceHolder;

	public PapiUtils() {
		usePlaceHolder = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
	}

	/**
	 * 
	 * @param itemStack
	 * @param player
	 * @return
	 */
	protected ItemStack papi(ItemStack itemStack, Player player) {

		if (itemStack == null)
			return itemStack;

		ItemMeta itemMeta = itemStack.getItemMeta();

		if (itemMeta.hasDisplayName()) {
			if (usePlaceHolder)
				itemMeta.setDisplayName(PlaceholderAPI.setPlaceholders(player, itemMeta.getDisplayName()));
			else
				itemMeta.setDisplayName(
						ZPlaceholderAPI.getInstance().setPlaceholders(player, itemMeta.getDisplayName()));
		}

		if (itemMeta.hasLore()) {
			if (usePlaceHolder)
				itemMeta.setLore(PlaceholderAPI.setPlaceholders(player, itemMeta.getLore()));
			else
				itemMeta.setLore(ZPlaceholderAPI.getInstance().setPlaceholders(player, itemMeta.getLore()));
		}

		itemStack.setItemMeta(itemMeta);
		return itemStack;

	}

	public String papi(String placeHolder, Player player) {
		if (placeHolder == null)
			return null;
		if (usePlaceHolder)
			return PlaceholderAPI.setPlaceholders(player, placeHolder);
		else
			return ZPlaceholderAPI.getInstance().setPlaceholders(player, placeHolder);
	}

	public List<String> papi(List<String> placeHolder, Player player) {
		return placeHolder == null ? null : placeHolder.stream().map(e -> papi(e, player)).collect(Collectors.toList());
	}

}
