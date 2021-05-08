package fr.maxlego08.mobfighter.command.commands.arena;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.MobManager;
import fr.maxlego08.mobfighter.api.enums.Message;
import fr.maxlego08.mobfighter.command.VCommand;
import fr.maxlego08.mobfighter.zcore.enums.Permission;
import fr.maxlego08.mobfighter.zcore.utils.commands.CommandType;

public class CommandVillagerFightArenaShow extends VCommand {

	public CommandVillagerFightArenaShow() {
		this.addSubCommand("show");
		this.setPermission(Permission.ZVILLAGER_ARENA_SHOW);
		this.setDescription(Message.COMMAND_DESCRIPTION_ARENA_SHOW);
	}

	@Override
	protected CommandType perform(ZMobPlugin plugin) {
		MobManager manager = plugin.getManager();
		manager.showArenas(sender);
		return CommandType.SUCCESS;
	}

}
