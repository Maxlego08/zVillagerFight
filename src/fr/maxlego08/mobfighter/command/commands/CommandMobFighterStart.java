package fr.maxlego08.mobfighter.command.commands;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.MobManager;
import fr.maxlego08.mobfighter.api.enums.Message;
import fr.maxlego08.mobfighter.command.VCommand;
import fr.maxlego08.mobfighter.zcore.enums.Permission;
import fr.maxlego08.mobfighter.zcore.utils.commands.CommandType;

public class CommandMobFighterStart extends VCommand {

	public CommandMobFighterStart() {
		this.addSubCommand("start");
		this.setPermission(Permission.ZVILLAGER_START);
		this.setDescription(Message.COMMAND_DESCRIPTION_START);
		this.addRequireArg("name");
		this.addRequireArg("fighter");
		this.addRequireArg("opponent");
		this.setTabCompletor();
	}

	@Override
	protected CommandType perform(ZMobPlugin main) {
		String name = this.argAsString(0);
		EntityType entity1 = this.argAsEntityType(1);
		EntityType entity2 = this.argAsEntityType(2);
		MobManager manager = main.getManager();
		manager.start(sender, name, entity1, entity2);
		return CommandType.SUCCESS;
	}

	@Override
	public List<String> toTab(ZMobPlugin plugin, CommandSender sender2, String[] args) {

		if (args.length == 2) {

			String startWith = args[1];
			List<String> list = plugin.getManager().getArenas();
			return this.generateList(list, startWith);

		} else if (args.length == 3 || args.length == 4) {

			String startWith = args[args.length - 1];
			Stream<String> entities = Arrays.asList(EntityType.values()).stream().filter(e -> {
				switch (e) {
				case ARMOR_STAND:
					return false;
				default:
					return e.isAlive();
				}
			}).map(e -> {
				return e.name().toLowerCase();
			});
			return this.generateList(entities.collect(Collectors.toList()), startWith);
		}

		return null;
	}

}
