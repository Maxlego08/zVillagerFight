package fr.maxlego08.mobfighter.zcore.utils.commands;

import java.util.function.BiConsumer;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.command.VCommand;

public class ZCommand extends VCommand {

	private BiConsumer<VCommand, ZMobPlugin> command;

	@Override
	public CommandType perform(ZMobPlugin main) {
		
		if (command != null){
			command.accept(this, main);
		}

		return CommandType.SUCCESS;
	}

	public VCommand setCommand(BiConsumer<VCommand, ZMobPlugin> command) {
		this.command = command;
		return this;
	}

	public VCommand sendHelp(String command) {
		this.command = (cmd, main) -> main.getCommandManager().sendHelp(command, cmd.getSender());
		return this;
	}

}
