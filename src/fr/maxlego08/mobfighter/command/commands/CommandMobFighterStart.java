package fr.maxlego08.mobfighter.command.commands;

import org.bukkit.entity.EntityType;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.MobManager;
import fr.maxlego08.mobfighter.command.VCommand;
import fr.maxlego08.mobfighter.zcore.enums.Permission;
import fr.maxlego08.mobfighter.zcore.utils.commands.CommandType;

public class CommandMobFighterStart extends VCommand {

	public CommandMobFighterStart() {
		this.addSubCommand("start");
		this.setPermission(Permission.ZVILLAGER_START);
		this.addRequireArg("name");
		this.addRequireArg("fighter");
		this.addRequireArg("opponent");
	}
	
	@Override
	protected CommandType perform(ZMobPlugin main) {
		String name = this.argAsString(0);
		EntityType entity1 = this.argAsEntityType(1);
		EntityType entity2 = this.argAsEntityType(2);
		MobManager manager = main.getVillagerManager();
		manager.start(sender, name, entity1, entity2);
		return CommandType.SUCCESS;
	}

}
