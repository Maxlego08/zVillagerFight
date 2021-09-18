package fr.maxlego08.mobfighter.command.commands.addon;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.addons.AddonManager;
import fr.maxlego08.mobfighter.api.enums.Message;
import fr.maxlego08.mobfighter.command.VCommand;
import fr.maxlego08.mobfighter.zcore.enums.Permission;
import fr.maxlego08.mobfighter.zcore.utils.commands.CommandType;

public class CommandAddonReload extends VCommand {

	public CommandAddonReload() {
		this.addSubCommand("reload", "rl");
		this.setPermission(Permission.ZMOBFIGHTER_ADDON_RELOAD);
		this.setDescription(Message.COMMAND_DESCRIPTION_ADDON_RELOAD);
	}

	@Override
	protected CommandType perform(ZMobPlugin plugin) {

		AddonManager manager = plugin.getAttackManager();
		manager.reload();
		message(sender, Message.ADDON_RELOAD);

		return CommandType.SUCCESS;
	}

}
