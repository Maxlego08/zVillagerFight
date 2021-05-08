package fr.maxlego08.mobfighter.command.commands;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.MobManager;
import fr.maxlego08.mobfighter.api.enums.Message;
import fr.maxlego08.mobfighter.command.VCommand;
import fr.maxlego08.mobfighter.zcore.enums.Permission;
import fr.maxlego08.mobfighter.zcore.utils.commands.CommandType;

public class CommandMobFighterReload extends VCommand {

	public CommandMobFighterReload() {
		this.setDescription(Message.COMMAND_DESCRIPTION_VERSION);
		this.setPermission(Permission.ZVILLAGER_RELOAD);
		this.addSubCommand("reload", "rl");
	}

	@Override
	protected CommandType perform(ZMobPlugin plugin) {
		plugin.getSavers().forEach(e -> {
			if (!(e instanceof MobManager))
				e.load(plugin.getPersist());
		});
		message(sender, Message.COMMAND_RELOAD);
		return CommandType.SUCCESS;
	}

}
