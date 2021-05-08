package fr.maxlego08.mobfighter.zcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.IEconomy;
import fr.maxlego08.mobfighter.api.enums.Economy;
import fr.maxlego08.mobfighter.api.event.events.economy.EconomyDepositEvent;
import fr.maxlego08.mobfighter.api.event.events.economy.EconomyMoneyEvent;
import fr.maxlego08.mobfighter.api.event.events.economy.EconomyWithdrawMoney;
import fr.maxlego08.mobfighter.save.Config;
import fr.maxlego08.mobfighter.zcore.logger.Logger;
import me.bukkit.mTokens.Inkzzz.Tokens;
import me.realized.tokenmanager.api.TokenManager;

public class ZEconomy extends ZUtils implements IEconomy {

	private final ZMobPlugin plugin;

	private final TokenManager tokenManager = (TokenManager) Bukkit.getPluginManager().getPlugin("TokenManager");

	/**
	 * @param plugin
	 */
	public ZEconomy(ZMobPlugin plugin) {
		super();
		this.plugin = plugin;
	}

	@Override
	public boolean hasMoney(Economy economy, OfflinePlayer player, long price) {
		return getMoney(economy, player) >= price;
	}

	@Override
	public void depositMoney(Economy economy, OfflinePlayer player, long value) {
		switch (economy) {
		case MYSQLTOKEN:
			if (player.isOnline())
				Tokens.getInstance().getAPI().giveTokens((Player) player, (int) value);
			else
				Logger.info("Cannot give money to the player \"" + player.getName()
						+ "\", is offline. (MySQLToken, price:" + value + ")");
			break;
		case TOKENMANAGER:
			if (player.isOnline())
				tokenManager.addTokens((Player) player, (long) value);
			else
				Logger.info("Cannot give money to the player \"" + player.getName()
						+ "\", is offline. (TokenManager, price:" + value + ")");
			break;
		case PLAYERPOINT:
			plugin.getPlayerPointsAPI().give(player.getUniqueId(), (int) value);
			break;
		case VAULT:
			plugin.getVaultEconomy().depositPlayer(player, value);
			break;
		case CUSTOM:
			EconomyDepositEvent event = new EconomyDepositEvent(player, value);
			event.callEvent();
			break;
		case ITEM1:
		case ITEM3:
		case ITEM2:
			if (player.isOnline())
				giveItem(player.getPlayer(), economy, value);
			else {
				Logger.info("Cannot give " + economy.name() + " to the player \"" + player.getName()
						+ "\", is offline. (Level, price:" + value + ")");
			}
			break;
		case LEVEL:
			if (player.isOnline()) {
				int level = ((Player) player).getLevel();
				((Player) player).setLevel((int) (level + value));
			} else {
				Logger.info("Cannot give money to the player \"" + player.getName() + "\", is offline. (Level, price:"
						+ value + ")");
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Give item to player
	 * 
	 * @param player
	 * @param itemStack
	 * @param economy
	 */
	private void giveItem(Player player, ItemStack itemStack, Economy economy) {
		if (hasInventoryFull(player)) {
			player.getWorld().dropItem(player.getLocation(), itemStack);
		} else
			give(player, itemStack);
	}

	/**
	 * Give items to player
	 * 
	 * @param player
	 * @param economy
	 * @param value
	 */
	private void giveItem(Player player, Economy economy, long value) {
		if (value > 64) {
			value -= 64;
			ItemStack itemStack = new ItemStack(getMaterial(economy), 64);
			this.giveItem(player, itemStack, economy);
			giveItem(player, economy, value);
		} else {
			ItemStack itemStack = new ItemStack(getMaterial(economy), (int) value);
			this.giveItem(player, itemStack, economy);
		}
	}

	@Override
	public void withdrawMoney(Economy economy, OfflinePlayer player, long value) {
		switch (economy) {
		case MYSQLTOKEN:
			if (player.isOnline())
				Tokens.getInstance().getAPI().takeTokens((Player) player, (int) value);
			else
				Logger.info("Cannot take money to the player \"" + player.getName()
						+ "\", is offline. (MySQLToken, price:" + value + ")");
			break;
		case TOKENMANAGER:
			if (player.isOnline())
				tokenManager.removeTokens((Player) player, (long) value);
			else
				Logger.info("Cannot take money to the player \"" + player.getName()
						+ "\", is offline. (TokenManager, price:" + value + ")");
			break;
		case PLAYERPOINT:
			plugin.getPlayerPointsAPI().take(player.getUniqueId(), (int) value);
			break;
		case VAULT:
			plugin.getVaultEconomy().withdrawPlayer(player, value);
			break;
		case CUSTOM:
			EconomyWithdrawMoney event = new EconomyWithdrawMoney(player, value);
			event.callEvent();
			break;
		case LEVEL:
			if (player.isOnline()) {
				int level = ((Player) player).getLevel();
				((Player) player).setLevel((int) (level - value));
			} else {
				Logger.info("Cannot take money to the player \"" + player.getName() + "\", is offline. (Level, price:"
						+ value + ")");
			}
			break;
		case ITEM2:
		case ITEM3:
		case ITEM1:

			if (!player.isOnline()) {
				Logger.info("Cannot take " + economy.name() + " to the player \"" + player.getName()
						+ "\", is offline. (Level, price:" + value + ")");
				return;
			}

			ItemStack itemStack = new ItemStack(getMaterial(economy));
			PlayerInventory playerInventory = player.getPlayer().getInventory();

			int item = (int) value;
			int slot = 0;

			// On retire ensuite les items de l'inventaire du joueur
			for (ItemStack is : playerInventory.getContents()) {

				if (is != null && is.isSimilar(itemStack) && item > 0) {

					int currentAmount = is.getAmount() - item;
					item -= is.getAmount();

					if (currentAmount <= 0) {
						if (slot == 40)
							playerInventory.setItemInOffHand(null);
						else
							playerInventory.removeItem(is);
					} else
						is.setAmount(currentAmount);
				}
				slot++;
			}
			break;
		default:
			break;
		}
	}

	@Override
	public double getMoney(Economy economy, OfflinePlayer player) {
		switch (economy) {
		case MYSQLTOKEN:
			return player.isOnline() ? Tokens.getInstance().getAPI().getTokens((Player) player) : 0;
		case TOKENMANAGER:
			return player.isOnline() ? tokenManager.getTokens((Player) player).getAsLong() : 0;
		case PLAYERPOINT:
			return plugin.getPlayerPointsAPI().look(player.getUniqueId());
		case VAULT:
			return plugin.getVaultEconomy().getBalance(player);
		case CUSTOM:
			EconomyMoneyEvent event = new EconomyMoneyEvent(player);
			event.callEvent();
			return event.getMoney();
		case LEVEL:
			return player.isOnline() ? ((Player) player).getLevel() : 0;
		case ITEM1:
		case ITEM3:
		case ITEM2:
			if (!player.isOnline())
				return 0;
			int item = getAmount(player.getPlayer(), economy);
			return item;
		default:
			return 0.0;
		}
	}

	/**
	 * 
	 * @param player
	 * @param economy
	 * @return
	 */
	public int getAmount(Player player, Economy economy) {
		int item = 0;
		ItemStack itemStack = new ItemStack(getMaterial(economy));
		for (int a = 0; a != 36; a++) {
			ItemStack is = player.getInventory().getItem(a);
			if (is != null && is.isSimilar(itemStack))
				item += is.getAmount();
		}
		return item;
	}

	/**
	 * 
	 * @param economy
	 * @return
	 */
	public Material getMaterial(Economy economy) {
		return economy == Economy.ITEM1 ? Config.materialItem
				: economy == Economy.ITEM2 ? Config.materialItem2 : Config.materialItem3;
	}

}
