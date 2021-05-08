package fr.maxlego08.mobfighter.configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.plugin.java.JavaPlugin;

import fr.maxlego08.mobfighter.api.configuration.ConfigurationManager;
import fr.maxlego08.mobfighter.api.configuration.MobConfiguration;
import fr.maxlego08.mobfighter.configuration.configurations.CreatureConfiguration;
import fr.maxlego08.mobfighter.configuration.configurations.PassibleConfiguration;
import fr.maxlego08.mobfighter.configuration.configurations.VillagerConfiguration;
import fr.maxlego08.mobfighter.loader.ConfigurationLoader;
import fr.maxlego08.mobfighter.zcore.logger.Logger;
import fr.maxlego08.mobfighter.zcore.utils.loader.Loader;
import fr.maxlego08.mobfighter.zcore.utils.storage.Persist;
import fr.maxlego08.mobfighter.zcore.utils.yaml.YamlUtils;

public class ZConfigurationManager extends YamlUtils implements ConfigurationManager {

	private Map<EntityType, MobConfiguration> configurations = new HashMap<EntityType, MobConfiguration>();
	private Map<EntityType, MobConfiguration> customConfigurations = new HashMap<EntityType, MobConfiguration>();

	public ZConfigurationManager(JavaPlugin plugin) {
		super(plugin);
		customConfigurations.put(EntityType.VILLAGER, new VillagerConfiguration());
	}

	@Override
	public void save(Persist persist) {

		if (persist != null) // Pas la sauvegarde par défaut
			return;

		for (EntityType entityType : EntityType.values())
			if (entityType.isAlive())
				saveEntity(entityType);

	}

	private void saveEntity(EntityType entityType) {

		MobConfiguration configuration = customConfigurations.getOrDefault(entityType, null);
		if (configuration == null) {

			Class<? extends Entity> classz = entityType.getEntityClass();
			if (Monster.class.isAssignableFrom(classz))
				configuration = new CreatureConfiguration(entityType);
			else
				configuration = new PassibleConfiguration(entityType);

		}

		this.configurations.put(entityType, configuration);

		String name = entityType.name().toLowerCase();
		File file = new File(plugin.getDataFolder(), "entities/" + name + ".yml");

		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

		Loader<MobConfiguration> loader = new ConfigurationLoader();
		YamlConfiguration fileConfiguration = getConfig(file);

		loader.save(configuration, fileConfiguration, "configuration.");

		try {
			fileConfiguration.save(file);
			Logger.info("Saved " + this.configurations.size() + " entities configurations.");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void load(Persist persist) {

		File folder = new File(plugin.getDataFolder(), "entities");
		if (!folder.exists()) {
			folder.mkdir();
			this.save(null);
			return;
		}

		for (EntityType entityType : EntityType.values())
			if (entityType.isAlive()) {

				String name = entityType.name().toLowerCase();
				File file = new File(plugin.getDataFolder(), "entities/" + name + ".yml");

				if (!file.exists()) {
					this.saveEntity(entityType);
					continue;
				}

				Loader<MobConfiguration> loader = new ConfigurationLoader();
				YamlConfiguration fileConfiguration = getConfig(file);

				MobConfiguration configuration = loader.load(fileConfiguration, "configuration.");
				this.configurations.put(entityType, configuration);

			}

		this.configurations.values().forEach(configuration -> {
			if (configuration.getNames().size() < 2){
				configuration.getNames().add("Maxlego08");
				configuration.getNames().add("AzartoxHD");
				Logger.info("Not enough names in the configuration " + configuration.getType().name()+", addition of two automatic names.");
			}
		});
		
		Logger.info("Loaded " + this.configurations.size() + " entities configurations.");
	}

	@Override
	public Optional<MobConfiguration> getConfiguration(EntityType type) {
		return Optional.ofNullable(this.configurations.getOrDefault(type, null));
	}

}
