package fr.maxlego08.mobfighter.save;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;

import fr.maxlego08.mobfighter.api.enums.Economy;
import fr.maxlego08.mobfighter.zcore.logger.Logger;
import fr.maxlego08.mobfighter.zcore.logger.Logger.LogType;
import fr.maxlego08.mobfighter.zcore.utils.nms.NMSUtils;
import fr.maxlego08.mobfighter.zcore.utils.storage.Persist;
import fr.maxlego08.mobfighter.zcore.utils.storage.Saveable;

public class Config implements Saveable {

	public static boolean enableInventoryPreRender = false;
	public static boolean enableDebugMode = false;
	public static boolean enableOpenSyncInventory = true;
	public static boolean enableBetInventory = false;
	
	public static boolean enableActionBar = true;
	public static String actionBarMessage = "§fzMobFighter §8- §a%first_name% §7| §b%first_health% §0- §d%second_health% §7| §2%second_name%";
	public static String actionBarHealthFormat =  "#.##";
	
	public static boolean enableBossBar = true;
	public static String actionBosMessage = "§fzMobFighter §8- §a%first_name% §7| §b%first_health% §0- §d%second_health% §7| §2%second_name%";
	public static String actionBosHealthFormat =  "#.##";
	public static Economy economy = Economy.VAULT;
	
	public static long minBet = 100;
	public static long maxBet = 100000;

	public static long duelTaskMilliSecond = 100;
	public static List<Integer> displayMessageCooldown = Arrays.asList(300, 120, 60, 30, 10, 5, 4, 3, 2, 1);

	public static String playerPointFormat = "p";
	public static String tokenManagerFormat = "t";
	public static String tokenLevel = "l";
	public static String customEcoForm = "c";
	public static String mySqlTokenFormat = "mt";
	public static String item1Format = "i";
	public static String item2Format = "o";
	public static String item3Format = "b";
	public static String vaultFormat = "$";

	public static String currencyVault = "$";
	public static String currencyLevel = "L";
	public static String currencyPlayerPoint = "£";
	public static String currencyTokenManager = "T";
	public static String currencyMySQLToken = "MT";
	public static String currencyItem1 = "gold nuggets";
	public static String currencyItem2 = "gold ingot";
	public static String currencyItem3 = "gold block";

	public static Material materialItem = Material.GOLD_NUGGET;
	public static Material materialItem2 = Material.GOLD_INGOT;
	public static Material materialItem3 = Material.GOLD_BLOCK;

	public static Map<String, Double> betMultiplicators = new HashMap<String, Double>();

	static {

		betMultiplicators.put("zmobfighter.bet.1.5", 1.5);
		betMultiplicators.put("zmobfighter.bet.2.5", 2.5);
		betMultiplicators.put("zmobfighter.bet.3", 3.0);

	}

	/**
	 * static Singleton instance.
	 */
	private static volatile Config instance;

	/**
	 * Private constructor for singleton.
	 */
	private Config() {
	}

	/**
	 * Return a singleton instance of Config.
	 */
	public static Config getInstance() {
		// Double lock for thread safety.
		if (instance == null) {
			synchronized (Config.class) {
				if (instance == null) {
					instance = new Config();
				}
			}
		}
		return instance;
	}

	public void save(Persist persist) {
		persist.save(getInstance());
	}

	public void load(Persist persist) {
		persist.loadOrSaveDefault(getInstance(), Config.class);
		if (Config.enableBossBar && NMSUtils.isOneHand()){
			Config.enableActionBar = false;
			Logger.info("You cannot use the bossbar in 1.8 and 1.7.10 !", LogType.WARNING);
		}
	}

}
