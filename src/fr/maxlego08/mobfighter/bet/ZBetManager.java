package fr.maxlego08.mobfighter.bet;

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
import fr.maxlego08.mobfighter.api.enums.Message;
import fr.maxlego08.mobfighter.save.Config;
import fr.maxlego08.mobfighter.zcore.utils.ZUtils;

public class ZBetManager extends ZUtils implements BetManager {

	private final Map<Player, Bet> bets = new HashMap<Player, Bet>();
	private final ZMobPlugin plugin;

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

		IEconomy iEconomy = plugin.getEconomy();

		if (!iEconomy.hasMoney(Config.economy, player, bet)) {
			message(player, Message.BET_NOT_ENOUGHT_MONEY);
			return;
		}

		MobManager mobManager = plugin.getManager();
		Optional<Duel> optional2 = mobManager.getDuelByFighter(name);

		if (!optional2.isPresent()) {
			message(player, Message.BET_CREATE, "%name%", name);
			return;
		}

		Duel duel = optional2.get();

		if (!duel.isCooldown()) {
			message(player, Message.BET_CREATE, "%name%", name);
			return;
		}

		Fighter fighter = duel.getByName(name);

		Bet bet2 = new ZBet(player, bet, duel, fighter);
		bets.put(player, bet2);
		iEconomy.withdrawMoney(Config.economy, player, bet);
		message(player, Message.BET_CREATE, "%fighter%", name, "%bet%", bet, "%economy%", Config.economy.getFormat());

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
				bets.remove(bet.getPlayer());

				if (bet.getPlayer().isOnline())
					message(bet.getPlayer(), Message.BET_WIN, "%value%", newValue, "%economy%",
							Config.economy.getFormat(), "%multiplicator%", multiplocator);

			} else {

				if (bet.getPlayer().isOnline())
					message(bet.getPlayer(), Message.BET_LOOSE);

			}

		});

	}

	@Override
	public void refundBets(Duel duel) {
		IEconomy economy = plugin.getEconomy();
		getBets(duel).forEach(bet -> {
			bet.refund(economy);
			bets.remove(bet.getDuel());
		});
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
		if (optional.isPresent()){
			Bet bet = optional.get();
			IEconomy economy = plugin.getEconomy();
			economy.depositMoney(Config.economy, player, bet.getBet());
			bets.remove(player);
		}
	}

}
