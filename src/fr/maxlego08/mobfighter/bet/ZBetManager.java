package fr.maxlego08.mobfighter.bet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import com.google.common.util.concurrent.AtomicDouble;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.Duel;
import fr.maxlego08.mobfighter.api.Fighter;
import fr.maxlego08.mobfighter.api.IEconomy;
import fr.maxlego08.mobfighter.api.MobManager;
import fr.maxlego08.mobfighter.api.bets.Bet;
import fr.maxlego08.mobfighter.api.bets.BetManager;
import fr.maxlego08.mobfighter.api.enums.BetSecletType;
import fr.maxlego08.mobfighter.api.enums.EnumInventory;
import fr.maxlego08.mobfighter.api.enums.InventoryType;
import fr.maxlego08.mobfighter.api.enums.Message;
import fr.maxlego08.mobfighter.api.inventory.Inventory;
import fr.maxlego08.mobfighter.api.inventory.InventoryManager;
import fr.maxlego08.mobfighter.inventory.ZInventoryManager;
import fr.maxlego08.mobfighter.save.Config;
import fr.maxlego08.mobfighter.zcore.utils.ZUtils;

public class ZBetManager extends ZUtils implements BetManager {

	private final Map<Player, Bet> bets = new HashMap<Player, Bet>();
	private final ZMobPlugin plugin;

	private final Map<Player, BetSecletType> betSeletedTypes = new HashMap<>();
	private final Map<Player, Long> betPlayers = new HashMap<>();

	/**
	 * @param plugin
	 */
	public ZBetManager(ZMobPlugin plugin) {
		super();
		this.plugin = plugin;
	}

	@Override
	public Optional<Bet> getBet(Player player) {
		return Optional.ofNullable(bets.getOrDefault(player, null));
	}

	@Override
	public void createBet(Player player, String name, long bet) {

		Optional<Bet> optional = getBet(player);
		if (optional.isPresent()) {
			message(player, Message.BET_ALREADY_CREATE);
			return;
		}

		if (!this.validation(player, bet))
			return;

		MobManager mobManager = plugin.getManager();
		Optional<Duel> optional2 = mobManager.getDuelByFighter(name);

		if (!optional2.isPresent()) {
			message(player, Message.BET_NOT_FOUND, "%name%", name);
			return;
		}

		Duel duel = optional2.get();

		if (!duel.isCooldown()) {
			message(player, Message.BET_ALREADY_START, "%name%", name);
			return;
		}

		Fighter fighter = duel.getByName(name);

		if (fighter == null) {
			message(player, Message.BET_NOT_FOUND, "%name%", name);
			return;
		}

		Bet bet2 = new ZBet(player, bet, duel, fighter);
		bets.put(player, bet2);

		IEconomy iEconomy = plugin.getEconomy();
		iEconomy.withdrawMoney(Config.economy, player, bet);
		message(player, Message.BET_CREATE, "%fighter%", name, "%bet%", bet, "%currency%", Config.economy.toCurrency());

	}

	@Override
	public void giveBets(Duel duel, Fighter winner) {

		IEconomy iEconomy = plugin.getEconomy();
		getBets(duel).forEach(bet -> {

			if (bet.getFighter().equals(winner)) {

				long value = bet.getBet();
				double multiplocator = getMultiplicator(bet.getPlayer());

				long newValue = (long) (value * multiplocator);
				iEconomy.depositMoney(Config.economy, bet.getPlayer(), newValue);

				if (bet.getPlayer().isOnline())
					message(bet.getPlayer(), Message.BET_WIN, "%value%", newValue, "%currency%",
							Config.economy.toCurrency(), "%multiplicator%", multiplocator);

			} else {

				if (bet.getPlayer().isOnline())
					message(bet.getPlayer(), Message.BET_LOOSE);

			}
			bets.remove(bet.getPlayer());

		});

	}

	@Override
	public void refundBets(Duel duel) {
		IEconomy economy = plugin.getEconomy();
		getBets(duel).forEach(bet -> {
			bet.refund(economy);
			bets.remove(bet.getDuel());
		});
		this.betPlayers.clear();
		this.betSeletedTypes.clear();
	}

	@Override
	public List<Bet> getBets(Duel duel) {
		return bets.values().stream().filter(e -> e.getDuel().equals(duel)).collect(Collectors.toList());
	}

	@Override
	public double getMultiplicator(Player player) {
		AtomicDouble atomicDouble = new AtomicDouble(1.0);
		Config.betMultiplicators.forEach((permission, multiplicator) -> {
			double current = atomicDouble.get();
			if (player.hasPermission(permission))
				atomicDouble.set(Math.max(current, multiplicator));
		});
		return atomicDouble.get();
	}

	@Override
	public void refundBet(Player player) {
		
		Optional<Bet> optional = getBet(player);
		
		if (optional.isPresent()) {
			Bet bet = optional.get();
			Duel duel = bet.getDuel();

			if (duel.isCooldown()) {

				IEconomy economy = this.plugin.getEconomy();
				bet.refund(economy);
				
				this.bets.remove(player);
				this.betPlayers.remove(player);
				this.betSeletedTypes.remove(player);
			}
		}
	}

	@Override
	public void sendBet(Player player) {
		Optional<Bet> optional = getBet(player);
		if (optional.isPresent()) {
			Bet bet = optional.get();
			message(player, Message.BET_SHOW, "%fighter%", bet.getFighter().getName(), "%bet%", bet.getBet(),
					"%multiplicator%", this.getMultiplicator(player));
		} else
			message(player, Message.BET_HELP);
	}

	@Override
	public void openInventory(Player player) {

		MobManager manager = plugin.getManager();

		Optional<Duel> duelOptional = manager.getCurrentDuel();

		if (!duelOptional.isPresent()) {
			message(player, Message.BET_NONE);
			return;
		}

		Duel duel = duelOptional.get();

		if (!duel.isCooldown()) {
			message(player, Message.BET_ALREADY_START, "%name%", duel.getArena().getName());
			return;
		}

		Optional<Bet> optional = getBet(player);

		InventoryManager iInventory = plugin.getInventories();
		InventoryType inventoryType = optional.isPresent() ? InventoryType.BET : InventoryType.BET_CREATE;

		Inventory inventory = iInventory.getInventory(inventoryType);

		ZInventoryManager inventoryManager = plugin.getInventoryManager();
		inventoryManager.createInventory(EnumInventory.INVENTORY_DEFAULT, player, 1, inventory, new ArrayList<>(), null,
				null);
	}

	@Override
	public void setPlayerPlaceHolder(Player player, long value, BetSecletType betSecletType) {
		this.betSeletedTypes.put(player, betSecletType);
		this.betPlayers.put(player, value);
	}

	@Override
	public BetSecletType getSelectedType(Player player) {
		return this.betSeletedTypes.getOrDefault(player, null);
	}

	@Override
	public long getTmpBet(Player player) {
		return this.betPlayers.getOrDefault(player, 0l);
	}

	@Override
	public boolean validation(Player player, long betPrice) {
		IEconomy iEconomy = plugin.getEconomy();

		if (!iEconomy.hasMoney(Config.economy, player, betPrice)) {
			message(player, Message.BET_NOT_ENOUGHT_MONEY);
			return false;
		}

		if (betPrice < Config.minBet) {
			message(player, Message.BET_MIN);
			return false;
		}

		if (betPrice > Config.maxBet) {
			message(player, Message.BET_MAX);
			return false;
		}
		return true;
	}

	@Override
	public void createBet(Player player, Duel duel, long betPrice) {
		BetSecletType type = this.getSelectedType(player);

		player.closeInventory();

		if (type == null) {
			message(player, Message.BET_SELECT_EMPTY);
			return;
		}

		if (!this.validation(player, betPrice))
			return;

		if (!duel.isCooldown()) {
			message(player, Message.BET_ALREADY_START, "%name%", duel.getArena().getName());
			return;
		}

		Fighter fighter = (type == BetSecletType.FIRST ? duel.getFirstFighter() : duel.getSecondFighter());
		Bet bet = new ZBet(player, betPrice, duel, fighter);
		bets.put(player, bet);

		IEconomy iEconomy = plugin.getEconomy();
		iEconomy.withdrawMoney(Config.economy, player, betPrice);
		message(player, Message.BET_CREATE, "%fighter%", fighter.getName(), "%bet%", betPrice, "%currency%",
				Config.economy.toCurrency());
	}

}
