package fr.maxlego08.mobfighter.command.commands.arena;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.MobManager;
import fr.maxlego08.mobfighter.api.enums.Message;
import fr.maxlego08.mobfighter.command.VCommand;
import fr.maxlego08.mobfighter.zcore.enums.Permission;
import fr.maxlego08.mobfighter.zcore.utils.commands.CommandType;

public class CommandVillagerFightArenaSecond extends VCommand {

	public CommandVillagerFightArenaSecond() {
		this.addSubCommand("second");
		this.setPermission(Permission.ZVILLAGER_ARENA_LOCATION_SECOND);
		this.setDescription(Message.COMMAND_DESCRIPTION_ARENA_SECOND);
		this.addRequireArg("name");
	}

	@Override
	protected CommandType perform(ZMobPlugin plugin) {
		MobManager manager = plugin.getManager();
		String name = argAsString(0);
		manager.setSecondLocation(player, name);
		return CommandType.SUCCESS;
	}

}
