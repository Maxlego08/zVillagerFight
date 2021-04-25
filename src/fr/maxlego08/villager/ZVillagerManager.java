package fr.maxlego08.villager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.maxlego08.villager.api.Arena;
import fr.maxlego08.villager.api.Duel;
import fr.maxlego08.villager.api.VillagerManager;
import fr.maxlego08.villager.api.enums.Message;
import fr.maxlego08.villager.api.path.PathManager;
import fr.maxlego08.villager.path.ZPathManager;
import fr.maxlego08.villager.zcore.utils.ZUtils;
import fr.maxlego08.villager.zcore.utils.storage.Persist;

public class ZVillagerManager extends ZUtils implements VillagerManager {

	private transient final PathManager pathManager = new ZPathManager();
	private transient final List<Duel> duels = new ArrayList<>();
	private transient boolean isRunning = false;
	private static Map<String, Arena> arenas = new HashMap<String, Arena>();

	@Override
	public void save(Persist persist) {
		persist.save(this, "storages");
	}

	@Override
	public void load(Persist persist) {
		persist.loadOrSaveDefault(this, ZVillagerManager.class, "storages");
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
			message(player, Message.ARENA_ALREADY_DOESNT_EXIST);
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
			message(player, Message.ARENA_ALREADY_DOESNT_EXIST);
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
			message(player, Message.ARENA_ALREADY_DOESNT_EXIST);
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
			message(sender, Message.ARENA_ALREADY_DOESNT_EXIST);
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
	public void start(CommandSender sender, String name) {

		Optional<Arena> optional = getArena(name);
		if (!optional.isPresent()) {
			message(sender, Message.ARENA_ALREADY_DOESNT_EXIST);
			return;
		}

		Arena arena = optional.get();

		// verif si l'arène est libre ici

		Duel duel = new ZDuel(arena, pathManager);
		duel.start();
		this.duels.add(duel);
		this.task();
	}

	private void task() {
		if (this.isRunning)
			return;

		scheduleFix(100, (task, canRun) -> {

			if (!canRun)
				return;

			if (this.duels.size() == 0) {
				task.cancel();
				return;
			}

			this.duels.forEach(Duel::update);
		});
	}

}
