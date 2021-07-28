package fr.maxlego08.mobfighter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

import fr.maxlego08.mobfighter.api.IEconomy;
import fr.maxlego08.mobfighter.api.MobManager;
import fr.maxlego08.mobfighter.api.bets.BetManager;
import fr.maxlego08.mobfighter.api.configuration.ConfigurationManager;
import fr.maxlego08.mobfighter.api.enums.EnumInventory;
import fr.maxlego08.mobfighter.api.enums.InventoryName;
import fr.maxlego08.mobfighter.api.inventory.InventoryManager;
import fr.maxlego08.mobfighter.bet.ZBetManager;
import fr.maxlego08.mobfighter.command.CommandManager;
import fr.maxlego08.mobfighter.command.commands.CommandMobFighter;
import fr.maxlego08.mobfighter.configuration.ZConfigurationManager;
import fr.maxlego08.mobfighter.inventory.InventoryLoader;
import fr.maxlego08.mobfighter.inventory.ZInventoryManager;
import fr.maxlego08.mobfighter.inventory.inventories.InventoryDefault;
import fr.maxlego08.mobfighter.listener.AdapterListener;
import fr.maxlego08.mobfighter.listener.HeadListener;
import fr.maxlego08.mobfighter.placeholder.ZMobExpension;
import fr.maxlego08.mobfighter.placeholder.ZPlaceholderAPI;
import fr.maxlego08.mobfighter.save.Config;
import fr.maxlego08.mobfighter.save.MessageLoader;
import fr.maxlego08.mobfighter.zcore.ZPlugin;
import fr.maxlego08.mobfighter.zcore.utils.ZEconomy;
import fr.maxlego08.mobfighter.zcore.utils.plugins.Metrics;
import fr.maxlego08.mobfighter.zcore.utils.plugins.Plugins;
import fr.maxlego08.mobfighter.zcore.utils.plugins.VersionChecker;

/**
 * System to create your plugins very simply Projet:
 * https://github.com/Maxlego08/TemplatePlugin
 * 
 * @author Maxlego08
 *
 */
public class ZMobPlugin extends ZPlugin {

	private final MobManager manager = new ZMobManager(this);
	private final ConfigurationManager configurationManager = new ZConfigurationManager(this);
	private final IEconomy economy = new ZEconomy(this);
	private final BetManager betManager = new ZBetManager(this);
	private InventoryManager inventoryLoader = new InventoryLoader(this, economy);

	@Override
	public void onEnable() {

		/* Register inventories */

		for (InventoryName inventoryName : InventoryName.values())
			this.registerFile(inventoryName);

		super.preEnable();

		commandManager = new CommandManager(this);

		if (!isEnabled())
			return;
		inventoryManager = ZInventoryManager.getInstance();

		this.registerCommand("zmf", new CommandMobFighter(), "zmobfigther");

		/* Add Listener */

		this.addListener(new AdapterListener(this));
		this.addListener(inventoryManager);
		this.addListener(new ZMobListener(this));

		this.registerInventory(EnumInventory.INVENTORY_DEFAULT, new InventoryDefault());

		/* Add Saver */
		this.addSave(Config.getInstance());
		this.addSave((ZMobManager) this.manager);
		this.addSave(configurationManager);
		this.addSave(new MessageLoader(this));

		Plugin plugin = getPlugin(Plugins.HEADDATABASE);

		if (plugin != null && plugin.isEnabled())
			this.addListener(new HeadListener(enablePlugin()));
		else
			enablePlugin().run();

		plugin = getPlugin(Plugins.PLACEHOLDER);
		if (plugin != null && plugin.isEnabled()) {
			ZMobExpension auctionExpension = new ZMobExpension(this);
			auctionExpension.register();
		}

		ZPlaceholderAPI placeholderAPI = ZPlaceholderAPI.getInstance();
		placeholderAPI.setPlugin(this);

		super.getSavers().forEach(saver -> saver.load(getPersist()));

		new Metrics(this, 11294);

		VersionChecker versionChecker = new VersionChecker(this, 41);
		versionChecker.useLastVersion();

		super.postEnable();
	}

	@Override
	public void onDisable() {

		preDisable();

		getSavers().forEach(saver -> saver.save(getPersist()));
		this.manager.onDisable();

		postDisable();

	}

	public MobManager getManager() {
		return manager;
	}

	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	public IEconomy getEconomy() {
		return economy;
	}

	public BetManager getBetManager() {
		return betManager;
	}

	public InventoryManager getInventories() {
		return this.inventoryLoader;
	}

	public Runnable enablePlugin() {
		return () -> {
			/* Load inventories */
			try {
				this.inventoryLoader.loadInventories();
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		};
	}

	public List<EntityType> getAllowedEntities() {
		List<EntityType> entityTypes = new ArrayList<EntityType>();
		for (EntityType entityType : EntityType.values()) {

			if (!entityType.isAlive())
				continue;

			switch (entityType.name()) {
			case "ARMOR_STAND":
			case "AXOLOTL":
			case "BAT":
			case "BEE":
			case "BLAZE":
			case "DOLPHIN":
			case "DRAGON":
			case "WHITER":
			case "GHAST":
			case "GUARDIN":
			case "PARROT":
			case "PUFFERFISH":
			case "SALMON":
			case "SHULKER":
			case "SQUID":
			case "VEX":
			case "WITHER_SKULL":
				continue;
			default:
				entityTypes.add(entityType);
				break;
			}

		}
		return entityTypes;
	}

}
