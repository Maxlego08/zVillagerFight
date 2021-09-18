package fr.maxlego08.mobfighter.command.commands.addon;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.addons.Addon;
import fr.maxlego08.mobfighter.api.addons.AddonDescription;
import fr.maxlego08.mobfighter.api.addons.AddonManager;
import fr.maxlego08.mobfighter.api.enums.Message;
import fr.maxlego08.mobfighter.command.VCommand;
import fr.maxlego08.mobfighter.zcore.enums.Permission;
import fr.maxlego08.mobfighter.zcore.utils.commands.CommandType;
import fr.maxlego08.mobfighter.zcore.utils.commands.Tab;

public class CommandAddonVersion extends VCommand {

	public CommandAddonVersion() {
		this.addSubCommand("version", "ver", "v");
		this.setPermission(Permission.ZMOBFIGHTER_ADDON_VERSION);
		this.setDescription(Message.COMMAND_DESCRIPTION_ADDON_VERSION);
		this.addRequireArg("addon");
		this.setTabCompletor();
	}

	@Override
	protected CommandType perform(ZMobPlugin plugin) {

		AddonManager manager = plugin.getAttackManager();
		String name = argAsString(0);
		Optional<Addon> optional = manager.getAddon(name);
		if (!optional.isPresent()) {

			Addon addon = optional.get();
			AddonDescription desc = addon.getDescription();
			messageWO(player, "§fName: §a" + desc.getName() +" v" + desc.getVersion());
			messageWO(player, "§fAuthor: §a" + desc.getAuthor());

		} else {
			message(sender, Message.ADDON_NOT_FOUND, "%name%", name);
		}

		return CommandType.SUCCESS;
	}

	@Override
	public List<String> toTab(ZMobPlugin plugin, CommandSender sender2, String[] args) {
		if (args.length == 3) {
			String current = args[2];
			AddonManager manager = plugin.getAttackManager();
			List<String> strings = manager.getAddons().stream().map(e -> e.getDescription().getName())
					.collect(Collectors.toList());
			return this.generateList(strings, current, Tab.CONTAINS);
		}
		return super.toTab(plugin, sender2, args);
	}

}
