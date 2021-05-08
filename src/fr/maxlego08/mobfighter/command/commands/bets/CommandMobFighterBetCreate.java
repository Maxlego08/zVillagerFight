package fr.maxlego08.mobfighter.command.commands.bets;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.bets.BetManager;
import fr.maxlego08.mobfighter.command.VCommand;
import fr.maxlego08.mobfighter.zcore.enums.Permission;
import fr.maxlego08.mobfighter.zcore.utils.commands.CommandType;

public class CommandMobFighterBetCreate extends VCommand {

	public CommandMobFighterBetCreate() {
		this.setPermission(Permission.ZVILLAGER_BET_CREATE);
		this.setConsoleCanUse(false);
		this.addSubCommand("create");
		this.addRequireArg("name");
		this.addRequireArg("bet");
	}

	@Override
	protected CommandType perform(ZMobPlugin main) {
		String name = argAsString(0);
		int value = argAsInteger(1);
		BetManager betManager = main.getBetManager();
		betManager.createBet(player, name, value);
		return CommandType.SUCCESS;

	}

}
