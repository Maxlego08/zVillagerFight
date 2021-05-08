package fr.maxlego08.mobfighter.command.commands;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.enums.Message;
import fr.maxlego08.mobfighter.command.VCommand;
import fr.maxlego08.mobfighter.zcore.utils.commands.CommandType;

public class CommandMobFighterVersion extends VCommand {

	public CommandMobFighterVersion() {
		this.setDescription(Message.COMMAND_DESCRIPTION_VERSION);
		this.addSubCommand("version", "v", "ver");
	}

	@Override
	protected CommandType perform(ZMobPlugin plugin) {

		message(sender, "§aVersion du plugin§7: §2" + plugin.getDescription().getVersion());
		message(sender, "§aAuteur§7: §2Maxlego08");
		message(sender, "§aDiscord§7: §2http://discord.groupez.dev/");
		message(sender, "§aBuy it for §d10€§7: §2https://groupez.dev/resources/zMobFighter.XX");

		return CommandType.SUCCESS;
	}

}
