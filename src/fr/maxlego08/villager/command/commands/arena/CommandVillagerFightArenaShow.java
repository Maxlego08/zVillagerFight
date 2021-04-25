package fr.maxlego08.villager.command.commands.arena;

import fr.maxlego08.villager.ZVillagerPlugin;
import fr.maxlego08.villager.api.VillagerManager;
import fr.maxlego08.villager.api.enums.Message;
import fr.maxlego08.villager.command.VCommand;
import fr.maxlego08.villager.zcore.enums.Permission;
import fr.maxlego08.villager.zcore.utils.commands.CommandType;

public class CommandVillagerFightArenaShow extends VCommand {

	public CommandVillagerFightArenaShow() {
		this.addSubCommand("show");
		this.setPermission(Permission.ZVILLAGER_ARENA_SHOW);
		this.setDescription(Message.COMMAND_DESCRIPTION_ARENA_SHOW);
	}

	@Override
	protected CommandType perform(ZVillagerPlugin plugin) {
		VillagerManager manager = plugin.getVillagerManager();
		manager.showArenas(sender);
		return CommandType.SUCCESS;
	}

}
