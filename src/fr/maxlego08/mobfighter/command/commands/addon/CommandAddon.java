package fr.maxlego08.mobfighter.command.commands.addon;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.addons.AddonManager;
import fr.maxlego08.mobfighter.api.enums.Message;
import fr.maxlego08.mobfighter.command.VCommand;
import fr.maxlego08.mobfighter.zcore.enums.Permission;
import fr.maxlego08.mobfighter.zcore.utils.commands.CommandType;

public class CommandAddon extends VCommand {

	public CommandAddon() {
		this.addSubCommand("addon");
		this.setPermission(Permission.ZMOBFIGHTER_ADDON);
		this.setDescription(Message.COMMAND_DESCRIPTION_ADDON);
		this.addSubCommand(new CommandAddonReload());
		this.addSubCommand(new CommandAddonVersion());
	}

	@Override
	protected CommandType perform(ZMobPlugin plugin) {

		AddonManager manager = plugin.getAttackManager();
		StringBuilder builder = new StringBuilder();
		manager.getAddons().forEach(addon -> {
			builder.append(addon.isEnable() ? "§a" : "§c");
			builder.append(addon.getDescription().getName());
			builder.append(", ");
		});

		String addons = builder.toString();
		if (addons.length() > 0) {
			addons = addons.substring(0, addons.length() - 2);
		}
		message(sender, Message.ADDON_LIST, "%amount%", manager.count(), "%addons%", addons);

		return CommandType.SUCCESS;
	}

}
