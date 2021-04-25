package fr.maxlego08.villager.command.commands.arena;

import fr.maxlego08.villager.ZVillagerPlugin;
import fr.maxlego08.villager.api.enums.Message;
import fr.maxlego08.villager.command.VCommand;
import fr.maxlego08.villager.zcore.utils.commands.CommandType;

public class CommandVillagerFightArena extends VCommand {

	public CommandVillagerFightArena() {
		this.addSubCommand("arena");
		this.addSubCommand(new CommandVillagerFightArenaCreate());
		this.addSubCommand(new CommandVillagerFightArenaDelete());
		this.addSubCommand(new CommandVillagerFightArenaShow());
		this.addSubCommand(new CommandVillagerFightArenaFirst());
		this.addSubCommand(new CommandVillagerFightArenaSecond());
		this.addSubCommand(new CommandVillagerFightArenaCenter());
	}
	
	@Override
	protected CommandType perform(ZVillagerPlugin main) {
		this.subVCommands.forEach(v -> {
			if (v.getPermission() == null || sender.hasPermission(v.getPermission()))
				messageWO(sender, Message.COMMAND_SYNTAXE_HELP, "%syntaxe%", v.getSyntaxe(), "%description%",
						v.getDescription());
		});
		return CommandType.SUCCESS;
	}

}
