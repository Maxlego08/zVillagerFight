package fr.maxlego08.mobfighter;

import fr.maxlego08.mobfighter.api.MobManager;
import fr.maxlego08.mobfighter.api.configuration.ConfigurationManager;
import fr.maxlego08.mobfighter.command.CommandManager;
import fr.maxlego08.mobfighter.command.commands.CommandMobFighter;
import fr.maxlego08.mobfighter.configuration.ZConfigurationManager;
import fr.maxlego08.mobfighter.inventory.InventoryManager;
import fr.maxlego08.mobfighter.listener.AdapterListener;
import fr.maxlego08.mobfighter.save.Config;
import fr.maxlego08.mobfighter.zcore.ZPlugin;

/**
 * System to create your plugins very simply Projet:
 * https://github.com/Maxlego08/TemplatePlugin
 * 
 * @author Maxlego08
 *
 */
public class ZMobPlugin extends ZPlugin {

	private final MobManager villagerManager = new ZMobManager(this);
	private final ConfigurationManager configurationManager = new ZConfigurationManager(this);

	@Override
	public void onEnable() {

		preEnable();

		commandManager = new CommandManager(this);

		if (!isEnabled())
			return;
		inventoryManager = InventoryManager.getInstance();

		this.registerCommand("zmf", new CommandMobFighter(), "zmobfigther");

		/* Add Listener */

		addListener(new AdapterListener(this));
		addListener(inventoryManager);

		/* Add Saver */
		this.addSave(Config.getInstance());
		this.addSave((ZMobManager) this.villagerManager);
		this.addSave(configurationManager);

		getSavers().forEach(saver -> saver.load(getPersist()));

		postEnable();
	}

	@Override
	public void onDisable() {

		preDisable();

		getSavers().forEach(saver -> saver.save(getPersist()));

		postDisable();

	}

	public MobManager getVillagerManager() {
		return villagerManager;
	}

	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}
	
}
