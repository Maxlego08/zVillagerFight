package fr.maxlego08.mobfighter.command.commands.arena;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.MobManager;
import fr.maxlego08.mobfighter.api.enums.Message;
import fr.maxlego08.mobfighter.command.VCommand;
import fr.maxlego08.mobfighter.zcore.enums.Permission;
import fr.maxlego08.mobfighter.zcore.utils.commands.CommandType;

public class CommandVillagerFightArenaFirst extends VCommand {

	public CommandVillagerFightArenaFirst() {
		this.addSubCommand("first");
		this.setPermission(Permission.ZVILLAGER_ARENA_LOCATION_FIRST);
		this.setDescription(Message.COMMAND_DESCRIPTION_ARENA_FIRST);
		this.addRequireArg("name");
	}

	@Override
	protected CommandType perform(ZMobPlugin plugin) {
		MobManager manager = plugin.getVillagerManager();
		String name = argAsString(0);
		manager.setFirstLocation(player, name);
		return CommandType.SUCCESS;
	}

}
