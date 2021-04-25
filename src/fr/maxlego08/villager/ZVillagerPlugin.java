package fr.maxlego08.villager;

import fr.maxlego08.villager.api.VillagerManager;
import fr.maxlego08.villager.command.CommandManager;
import fr.maxlego08.villager.command.commands.CommandVillagerFight;
import fr.maxlego08.villager.inventory.InventoryManager;
import fr.maxlego08.villager.listener.AdapterListener;
import fr.maxlego08.villager.save.Config;
import fr.maxlego08.villager.zcore.ZPlugin;

/**
 * System to create your plugins very simply Projet:
 * https://github.com/Maxlego08/TemplatePlugin
 * 
 * @author Maxlego08
 *
 */
public class ZVillagerPlugin extends ZPlugin {

	private final VillagerManager villagerManager = new ZVillagerManager();

	@Override
	public void onEnable() {

		preEnable();

		commandManager = new CommandManager(this);

		if (!isEnabled())
			return;
		inventoryManager = InventoryManager.getInstance();

		this.registerCommand("zvillager", new CommandVillagerFight(), "zvillagerfight", "zvf");

		/* Add Listener */

		addListener(new AdapterListener(this));
		addListener(inventoryManager);

		/* Add Saver */
		this.addSave(Config.getInstance());
		this.addSave((ZVillagerManager) this.villagerManager);

		getSavers().forEach(saver -> saver.load(getPersist()));

		postEnable();
	}

	@Override
	public void onDisable() {

		preDisable();

		getSavers().forEach(saver -> saver.save(getPersist()));

		postDisable();

	}

	public VillagerManager getVillagerManager() {
		return villagerManager;
	}

}
