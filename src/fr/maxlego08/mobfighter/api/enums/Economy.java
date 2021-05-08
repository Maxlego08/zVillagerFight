package fr.maxlego08.mobfighter.api.enums;

import org.bukkit.Bukkit;

import fr.maxlego08.mobfighter.api.event.events.economy.EconomyCurrencyEvent;
import fr.maxlego08.mobfighter.save.Config;

public enum Economy {

	VAULT,

	PLAYERPOINT,

	TOKENMANAGER,

	MYSQLTOKEN,

	CUSTOM,

	LEVEL,

	ITEM1,

	ITEM2,

	ITEM3,

	;

	public String getFormat() {
		switch (this) {
		case CUSTOM:
			return Config.customEcoForm;
		case ITEM1:
			return Config.item1Format;
		case ITEM2:
			return Config.item2Format;
		case ITEM3:
			return Config.item3Format;
		case LEVEL:
			return Config.tokenLevel;
		case MYSQLTOKEN:
			return Config.mySqlTokenFormat;
		case PLAYERPOINT:
			return Config.playerPointFormat;
		case TOKENMANAGER:
			return Config.tokenManagerFormat;
		case VAULT:
			return Config.vaultFormat;
		default:
			return null;
		}
	}

	public String toCurrency() {
		switch (this) {
		case ITEM1:
			return Config.currencyItem1;
		case ITEM2:
			return Config.currencyItem2;
		case ITEM3:
			return Config.currencyItem3;
		case PLAYERPOINT:
			return Config.currencyPlayerPoint;
		case VAULT:
			return Config.currencyVault;
		case TOKENMANAGER:
			return Config.currencyTokenManager;
		case MYSQLTOKEN:
			return Config.currencyMySQLToken;
		case LEVEL:
			return Config.currencyLevel;
		case CUSTOM:
			EconomyCurrencyEvent event = new EconomyCurrencyEvent();
			Bukkit.getPluginManager().callEvent(event);
			return event.getCurrency();
		default:
			return "$";
		}
	}

}
