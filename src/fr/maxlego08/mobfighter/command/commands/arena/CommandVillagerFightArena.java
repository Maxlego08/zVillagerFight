package fr.maxlego08.mobfighter.command.commands.arena;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.enums.Message;
import fr.maxlego08.mobfighter.command.VCommand;
import fr.maxlego08.mobfighter.zcore.enums.Permission;
import fr.maxlego08.mobfighter.zcore.utils.commands.CommandType;

public class CommandVillagerFightArena extends VCommand {

	public CommandVillagerFightArena() {
		this.addSubCommand("arena");
		this.setPermission(Permission.ZVILLAGER_ARENA);
		this.setDescription(Message.COMMAND_DESCRIPTION_ARENA);
		this.addSubCommand(new CommandVillagerFightArenaCreate());
		this.addSubCommand(new CommandVillagerFightArenaDelete());
		this.addSubCommand(new CommandVillagerFightArenaShow());
		this.addSubCommand(new CommandVillagerFightArenaFirst());
		this.addSubCommand(new CommandVillagerFightArenaSecond());
		this.addSubCommand(new CommandVillagerFightArenaCenter());
	}
	
	@Override
	protected CommandType perform(ZMobPlugin main) {
		this.subVCommands.forEach(v -> {
			if (v.getPermission() == null || sender.hasPermission(v.getPermission()))
				messageWO(sender, Message.COMMAND_SYNTAXE_HELP, "%syntaxe%", v.getSyntaxe(), "%description%",
						v.getDescription());
		});
		return CommandType.SUCCESS;
	}

}
