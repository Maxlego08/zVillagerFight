package fr.maxlego08.mobfighter;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import fr.maxlego08.mobfighter.api.configuration.MobConfiguration;
import fr.maxlego08.mobfighter.configuration.ZMobConfiguration;
import fr.maxlego08.mobfighter.zcore.utils.loader.Loader;

public class ConfigurationLoader implements Loader<MobConfiguration> {

	public ConfigurationLoader() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public MobConfiguration load(YamlConfiguration configuration, String path) {

		try {
			
			EntityType entityType = EntityType.valueOf(configuration.getString(path + "type"));
			double distance = configuration.getDouble(path + "distance");
			double speed = configuration.getDouble(path + "speed");
			double pushHeight = configuration.getDouble(path + "push.height");
			double pushSpeed = configuration.getDouble(path + "push.speed");

			return new ZMobConfiguration(entityType, speed, distance, pushHeight, pushSpeed);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void save(MobConfiguration mobConfiguration, YamlConfiguration configuration, String path) {
		configuration.set(path + "type", mobConfiguration.getType().name());
		configuration.set(path + "speed", mobConfiguration.getSpeed());
		configuration.set(path + "distance", mobConfiguration.getDistance());
		configuration.set(path + "push.height", mobConfiguration.getPushHeight());
		configuration.set(path + "push.speed", mobConfiguration.getPushSpeed());

	}

}
