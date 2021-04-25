package fr.maxlego08.villager.zcore.utils.commands;

import java.util.function.BiConsumer;

import fr.maxlego08.villager.ZVillagerPlugin;
import fr.maxlego08.villager.command.VCommand;

public class ZCommand extends VCommand {

	private BiConsumer<VCommand, ZVillagerPlugin> command;

	@Override
	public CommandType perform(ZVillagerPlugin main) {
		
		if (command != null){
			command.accept(this, main);
		}

		return CommandType.SUCCESS;
	}

	public VCommand setCommand(BiConsumer<VCommand, ZVillagerPlugin> command) {
		this.command = command;
		return this;
	}

	public VCommand sendHelp(String command) {
		this.command = (cmd, main) -> main.getCommandManager().sendHelp(command, cmd.getSender());
		return this;
	}

}
