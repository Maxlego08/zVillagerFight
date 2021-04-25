package fr.maxlego08.villager.command.commands;

import fr.maxlego08.villager.ZVillagerPlugin;
import fr.maxlego08.villager.api.enums.Message;
import fr.maxlego08.villager.command.VCommand;
import fr.maxlego08.villager.command.commands.arena.CommandVillagerFightArena;
import fr.maxlego08.villager.zcore.enums.Permission;
import fr.maxlego08.villager.zcore.utils.commands.CommandType;

public class CommandVillagerFight extends VCommand {

	public CommandVillagerFight() {
		this.setPermission(Permission.ZVILLAGER_USE);
		this.addSubCommand(new CommandVillagerFightArena());
		this.addSubCommand(new CommandVillagerFightStart());
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
