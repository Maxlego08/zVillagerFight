package fr.maxlego08.mobfighter.command.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.MobManager;
import fr.maxlego08.mobfighter.api.enums.Message;
import fr.maxlego08.mobfighter.command.VCommand;
import fr.maxlego08.mobfighter.zcore.enums.Permission;
import fr.maxlego08.mobfighter.zcore.utils.commands.CommandType;

public class CommandMobFighterStop extends VCommand {

	public CommandMobFighterStop() {
		this.addSubCommand("stop");
		this.setPermission(Permission.ZMOBFIGHTER_START);
		this.setDescription(Message.COMMAND_DESCRIPTION_STOP);
		this.addRequireArg("name");
		this.setTabCompletor();
	}

	@Override
	protected CommandType perform(ZMobPlugin main) {
		String name = this.argAsString(0);
		MobManager manager = main.getManager();
		manager.stop(sender, name);
		return CommandType.SUCCESS;
	}

	@Override
	public List<String> toTab(ZMobPlugin plugin, CommandSender sender2, String[] args) {
		if (args.length == 2) {
			String startWith = args[1];
			List<String> list = plugin.getManager().getArenas();
			return this.generateList(list, startWith);
		}
		return null;
	}

}
