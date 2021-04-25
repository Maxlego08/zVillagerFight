package fr.maxlego08.villager.command.commands.arena;

import fr.maxlego08.villager.ZVillagerPlugin;
import fr.maxlego08.villager.api.VillagerManager;
import fr.maxlego08.villager.api.enums.Message;
import fr.maxlego08.villager.command.VCommand;
import fr.maxlego08.villager.zcore.enums.Permission;
import fr.maxlego08.villager.zcore.utils.commands.CommandType;

public class CommandVillagerFightArenaFirst extends VCommand {

	public CommandVillagerFightArenaFirst() {
		this.addSubCommand("first");
		this.setPermission(Permission.ZVILLAGER_ARENA_LOCATION_FIRST);
		this.setDescription(Message.COMMAND_DESCRIPTION_ARENA_FIRST);
		this.addRequireArg("name");
	}

	@Override
	protected CommandType perform(ZVillagerPlugin plugin) {
		VillagerManager manager = plugin.getVillagerManager();
		String name = argAsString(0);
		manager.setFirstLocation(player, name);
		return CommandType.SUCCESS;
	}

}
