package fr.maxlego08.mobfighter.command.commands.bets;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.bets.BetManager;
import fr.maxlego08.mobfighter.api.enums.Message;
import fr.maxlego08.mobfighter.command.VCommand;
import fr.maxlego08.mobfighter.zcore.enums.Permission;
import fr.maxlego08.mobfighter.zcore.utils.commands.CommandType;

public class CommandMobFighterBet extends VCommand {

	public CommandMobFighterBet() {
		this.setPermission(Permission.ZVILLAGER_BET);
		this.setDescription(Message.COMMAND_DESCRIPTION_BET);
		this.setConsoleCanUse(false);
		this.addSubCommand("bet", "mise");
		this.addSubCommand(new CommandMobFighterBetCreate());
	}

	@Override
	protected CommandType perform(ZMobPlugin main) {
		BetManager betManager = main.getBetManager();
		betManager.sendBet(player);
		return CommandType.SUCCESS;
	}

}
