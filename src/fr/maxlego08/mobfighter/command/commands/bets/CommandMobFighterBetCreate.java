package fr.maxlego08.mobfighter.command.commands.bets;

import java.util.List;

import org.bukkit.command.CommandSender;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.bets.BetManager;
import fr.maxlego08.mobfighter.command.VCommand;
import fr.maxlego08.mobfighter.zcore.enums.Permission;
import fr.maxlego08.mobfighter.zcore.utils.commands.CommandType;

public class CommandMobFighterBetCreate extends VCommand {

	public CommandMobFighterBetCreate() {
		this.setPermission(Permission.ZMOBFIGHTER_BET_CREATE);
		this.setConsoleCanUse(false);
		this.addSubCommand("create");
		this.addRequireArg("name");
		this.addRequireArg("bet");
		this.setTabCompletor();
	}

	@Override
	protected CommandType perform(ZMobPlugin main) {
		String name = argAsString(0);
		long value = argAsLong(1);
		BetManager betManager = main.getBetManager();
		betManager.createBet(player, name, value);
		return CommandType.SUCCESS;

	}

	@Override
	public List<String> toTab(ZMobPlugin plugin, CommandSender sender2, String[] args) {
		
		if (args.length == 3) {
			String startWith = args[2];
			List<String> strings = plugin.getManager().getFighterNames();
			return this.generateList(strings, startWith);
		}

		return null;
	}

}
