package fr.maxlego08.mobfighter.command.commands.arena;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.MobManager;
import fr.maxlego08.mobfighter.api.enums.Message;
import fr.maxlego08.mobfighter.command.VCommand;
import fr.maxlego08.mobfighter.zcore.enums.Permission;
import fr.maxlego08.mobfighter.zcore.utils.commands.CommandType;

public class CommandVillagerFightArenaCenter extends VCommand {

	public CommandVillagerFightArenaCenter() {
		this.addSubCommand("center");
		this.setPermission(Permission.ZMOBFIGHTER_ARENA_LOCATION_CENTER);
		this.setDescription(Message.COMMAND_DESCRIPTION_ARENA_CENTER);
		this.addRequireArg("name");
	}

	@Override
	protected CommandType perform(ZMobPlugin plugin) {
		MobManager manager = plugin.getManager();
		String name = argAsString(0);
		manager.setCenterLocation(player, name);
		return CommandType.SUCCESS;
	}

}
