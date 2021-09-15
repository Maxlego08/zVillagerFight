package fr.maxlego08.mobfighter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import fr.maxlego08.mobfighter.api.Arena;
import fr.maxlego08.mobfighter.api.Duel;
import fr.maxlego08.mobfighter.api.MobManager;
import fr.maxlego08.mobfighter.api.bets.BetManager;
import fr.maxlego08.mobfighter.api.configuration.ConfigurationManager;
import fr.maxlego08.mobfighter.api.enums.Message;
import fr.maxlego08.mobfighter.api.event.events.DuelStartEvent;
import fr.maxlego08.mobfighter.api.path.PathManager;
import fr.maxlego08.mobfighter.path.ZPathManager;
import fr.maxlego08.mobfighter.save.Config;
import fr.maxlego08.mobfighter.zcore.utils.ZUtils;
import fr.maxlego08.mobfighter.zcore.utils.storage.Persist;

public class ZMobManager extends ZUtils implements MobManager {

	private transient final PathManager pathManager = new ZPathManager();
	private transient final List<Duel> duels = new ArrayList<>();
	private transient final ZMobPlugin plugin;
	private transient boolean isRunning = false;
	private static Map<String, Arena> arenas = new HashMap<String, Arena>();

	/**
	 * @param plugin
	 */
	public ZMobManager(ZMobPlugin plugin) {
		super();
		this.plugin = plugin;
	}

	@Override
	public void save(Persist persist) {
		persist.save(this, "storages");
	}

	@Override
	public void load(Persist persist) {
		persist.loadOrSaveDefault(this, ZMobManager.class, "storages");
	}

	@Override
	public Optional<Arena> getArena(String name) {
		return Optional.ofNullable(arenas.getOrDefault(name, null));
	}

	@Override
	public void createArena(CommandSender sender, String name) {

		Optional<Arena> optional = getArena(name);
		if (optional.isPresent()) {
			message(sender, Message.ARENA_ALREADY_EXIST);
			return;
		}

		Arena arena = new ZArena(name);
		arenas.put(name, arena);
		message(sender, Message.ARENA_CREATE, "%name%", name);
	}

	@Override
	public void setFirstLocation(Player player, String name) {
		Optional<Arena> optional = getArena(name);
		if (!optional.isPresent()) {
			message(player, Message.ARENA_DOESNT_EXIST);
			return;
		}
		Arena arena = optional.get();
		arena.setFirstLocation(player.getLocation());
		message(player, Message.ARENA_LOCATION_FIRST, "%name%", name);
	}

	@Override
	public void setSecondLocation(Player player, String name) {
		Optional<Arena> optional = getArena(name);
		if (!optional.isPresent()) {
			message(player, Message.ARENA_DOESNT_EXIST);
			return;
		}
		Arena arena = optional.get();
		arena.setSecondLocation(player.getLocation());
		message(player, Message.ARENA_LOCATION_SECOND, "%name%", name);
	}

	@Override
	public void setCenterLocation(Player player, String name) {
		Optional<Arena> optional = getArena(name);
		if (!optional.isPresent()) {
			message(player, Message.ARENA_DOESNT_EXIST);
			return;
		}
		Arena arena = optional.get();
		arena.setCenterLocation(player.getLocation());
		message(player, Message.ARENA_LOCATION_CENTER, "%name%", name);
	}

	@Override
	public void deleteArena(CommandSender sender, String name) {
		Optional<Arena> optional = getArena(name);
		if (!optional.isPresent()) {
			message(sender, Message.ARENA_DOESNT_EXIST);
			return;
		}

		Arena arena = optional.get();
		if (arena.isActive()) {
			message(sender, Message.ARENA_DELETE_ERROR_ACTIVE);
			return;
		}

		arenas.remove(name);
		message(sender, Message.ARENA_DELETE, "%name%", name);
	}

	@Override
	public void showArenas(CommandSender sender) {
		if (arenas.size() == 0) {
			message(sender, Message.ARENA_EMPTY);
		} else {
			message(sender, Message.ARENA_SHOW_HEADER);
			arenas.values().forEach(arena -> {
				String valid = (!arena.isValid() ? Message.ARENA_SHOW_INVALID : Message.ARENA_SHOW_VALID).getMessage();
				messageWO(sender, Message.ARENA_SHOW_CONTENT, "%is_valid%", valid, "%name%", arena.getName());
			});
		}
	}

	@Override
	public void start(CommandSender sender, String name, EntityType entity1, EntityType entity2, int second) {

		Optional<Arena> optional = getArena(name);
		if (!optional.isPresent()) {
			message(sender, Message.ARENA_DOESNT_EXIST);
			return;
		}

		if (!entity1.isAlive() || !entity2.isAlive()) {
			message(sender, Message.DUEL_START_ERROR_ALIVE);
			return;
		}

		Arena arena = optional.get();

		if (!arena.isReady()) {
			message(sender, Message.DUEL_START_ERROR_DUEL);
			return;
		}

		if (!arena.isValid()) {
			message(sender, Message.ARENA_INVALID);
			return;
		}

		ConfigurationManager configurationManager = this.plugin.getConfigurationManager();
		Duel duel = new ZDuel(this.plugin.getBetManager(), arena, this.pathManager, configurationManager, entity1,
				entity2);

		DuelStartEvent event = new DuelStartEvent(duel);
		event.callEvent();

		if (event.isCancelled())
			return;

		message(sender, Message.DUEL_LAUNCH);
		
		arena.setDuel(duel);
		duel.start(second);
		this.duels.add(duel);
		this.task();
	}

	private void task() {
		if (this.isRunning)
			return;

		scheduleFix(Config.duelTaskMilliSecond, Config.duelTaskMilliSecond, (task, canRun) -> {

			if (!canRun)
				return;

			if (this.duels.size() == 0) {
				task.cancel();
				return;
			}

			this.duels.removeIf(Duel::isFinish);
			this.duels.forEach(Duel::update);
		});
	}

	@Override
	public void onDisable() {
		BetManager betManager = this.plugin.getBetManager();
		this.duels.forEach(e -> {
			betManager.refundBets(e);
			e.getFirstFighter().remove();
			e.getSecondFighter().remove();
		});
	}

	@Override
	public List<String> getArenas() {
		return arenas.values().stream().map(e -> e.getName()).collect(Collectors.toList());
	}

	@Override
	public void stop(CommandSender sender, String name) {

		Optional<Arena> optional = getArena(name);
		if (!optional.isPresent()) {
			message(sender, Message.ARENA_DOESNT_EXIST);
			return;
		}

		Arena arena = optional.get();
		if (arena.isReady()) {
			message(sender, Message.DUEL_STOP_ERROR_DUEL);
			return;
		}

		Duel duel = arena.getDuel();
		duel.stop();

		BetManager betManager = this.plugin.getBetManager();
		betManager.refundBets(duel);

		message(sender, Message.DUEL_STOP_SUCCESS);

	}

	@Override
	public List<Duel> getDuels() {
		return arenas.values().stream().filter(e -> !e.isReady()).map(e -> e.getDuel()).collect(Collectors.toList());
	}

	@Override
	public Optional<Duel> getDuelByFighter(String name) {
		return arenas.values().stream().filter(e -> e != null && e.getDuel().match(name)).map(e -> e.getDuel())
				.findFirst();
	}

	@Override
	public List<String> getFighterNames() {
		List<String> strings = new ArrayList<>();
		duels.forEach(e -> {
			strings.add(e.getFirstFighter().getName());
			strings.add(e.getSecondFighter().getName());
		});
		return strings;
	}

	@Override
	public Optional<Duel> getCurrentDuel() {
		if (duels.size() == 0)
			return Optional.empty();
		return Optional.of(duels.get(0));
	}

}
