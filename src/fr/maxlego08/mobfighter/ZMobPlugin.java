package fr.maxlego08.mobfighter;

import fr.maxlego08.mobfighter.api.MobManager;
import fr.maxlego08.mobfighter.api.configuration.ConfigurationManager;
import fr.maxlego08.mobfighter.command.CommandManager;
import fr.maxlego08.mobfighter.command.commands.CommandMobFighter;
import fr.maxlego08.mobfighter.configuration.ZConfigurationManager;
import fr.maxlego08.mobfighter.inventory.InventoryManager;
import fr.maxlego08.mobfighter.listener.AdapterListener;
import fr.maxlego08.mobfighter.save.Config;
import fr.maxlego08.mobfighter.save.MessageLoader;
import fr.maxlego08.mobfighter.zcore.ZPlugin;

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

	@Override
	public void onEnable() {

		preEnable();

		commandManager = new CommandManager(this);

		if (!isEnabled())
			return;
		inventoryManager = InventoryManager.getInstance();

		this.registerCommand("zmf", new CommandMobFighter(), "zmobfigther");

		/* Add Listener */

		this.addListener(new AdapterListener(this));
		// this.addListener(inventoryManager);
		this.addListener(new ZMobListener(this));

		/* Add Saver */
		this.addSave(Config.getInstance());
		this.addSave((ZMobManager) this.manager);
		this.addSave(configurationManager);
		this.addSave(new MessageLoader(this));

		getSavers().forEach(saver -> saver.load(getPersist()));

		postEnable();
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

}
