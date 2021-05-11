package fr.maxlego08.mobfighter.command.commands.arena;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.MobManager;
import fr.maxlego08.mobfighter.api.enums.Message;
import fr.maxlego08.mobfighter.command.VCommand;
import fr.maxlego08.mobfighter.zcore.enums.Permission;
import fr.maxlego08.mobfighter.zcore.utils.commands.CommandType;

public class CommandVillagerFightArenaDelete extends VCommand {

	public CommandVillagerFightArenaDelete() {
		this.addSubCommand("delete");
		this.setPermission(Permission.ZMOBFIGHTER_ARENA_DELETE);
		this.setDescription(Message.COMMAND_DESCRIPTION_ARENA_DELETE);
		this.addRequireArg("name");
	}

	@Override
	protected CommandType perform(ZMobPlugin plugin) {
		MobManager manager = plugin.getManager();
		String name = argAsString(0);
		manager.deleteArena(sender, name);
		return CommandType.SUCCESS;
	}

}
