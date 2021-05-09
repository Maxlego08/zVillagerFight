package fr.maxlego08.mobfighter.command.commands;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.enums.Message;
import fr.maxlego08.mobfighter.command.VCommand;
import fr.maxlego08.mobfighter.command.commands.arena.CommandVillagerFightArena;
import fr.maxlego08.mobfighter.command.commands.bets.CommandMobFighterBet;
import fr.maxlego08.mobfighter.zcore.enums.Permission;
import fr.maxlego08.mobfighter.zcore.utils.commands.CommandType;

public class CommandMobFighter extends VCommand {

	public CommandMobFighter() {
		this.setPermission(Permission.ZVILLAGER_USE);
		this.addSubCommand(new CommandVillagerFightArena());
		this.addSubCommand(new CommandMobFighterStart());
		this.addSubCommand(new CommandMobFighterStop());
		this.addSubCommand(new CommandMobFighterVersion());
		this.addSubCommand(new CommandMobFighterReload());
		this.addSubCommand(new CommandMobFighterBet());
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
