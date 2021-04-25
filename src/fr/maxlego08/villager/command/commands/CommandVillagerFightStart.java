package fr.maxlego08.villager.command.commands;

import fr.maxlego08.villager.ZVillagerPlugin;
import fr.maxlego08.villager.api.VillagerManager;
import fr.maxlego08.villager.command.VCommand;
import fr.maxlego08.villager.zcore.enums.Permission;
import fr.maxlego08.villager.zcore.utils.commands.CommandType;

public class CommandVillagerFightStart extends VCommand {

	public CommandVillagerFightStart() {
		this.addSubCommand("start");
		this.setPermission(Permission.ZVILLAGER_START);
		this.addRequireArg("name");
	}
	
	@Override
	protected CommandType perform(ZVillagerPlugin main) {
		String name = this.argAsString(0);
		VillagerManager manager = main.getVillagerManager();
		manager.start(sender, name);
		return CommandType.SUCCESS;
	}

}
