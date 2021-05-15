package fr.maxlego08.mobfighter.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import fr.maxlego08.mobfighter.api.configuration.MobConfiguration;
import fr.maxlego08.mobfighter.api.particles.Particle;
import fr.maxlego08.mobfighter.api.particles.ParticleEffect;
import fr.maxlego08.mobfighter.api.particles.ParticleType;
import fr.maxlego08.mobfighter.configuration.ZMobConfiguration;
import fr.maxlego08.mobfighter.particles.ZParticle;
import fr.maxlego08.mobfighter.zcore.utils.ZUtils;
import fr.maxlego08.mobfighter.zcore.utils.loader.Loader;

public class ConfigurationLoader extends ZUtils implements Loader<MobConfiguration> {

	public ConfigurationLoader() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public MobConfiguration load(YamlConfiguration configuration, String path, Object... args) {

		try {

			EntityType entityType = EntityType.valueOf(configuration.getString(path + "type"));
			double distance = configuration.getDouble(path + "distance");
			double speed = configuration.getDouble(path + "speed");

			String displayName = color(configuration.getString(path + "displayname"));
			List<String> setences = color(configuration.getStringList(path + "setences"));
			List<String> names = color(configuration.getStringList(path + "names"));

			double criticalMultiplier = configuration.getDouble(path + "critical.multiplier");
			double criticalPercent = configuration.getDouble(path + "critical.percent");

			double pushHeight = configuration.getDouble(path + "push.height");
			double pushSpeed = configuration.getDouble(path + "push.speed");

			double maxHealth = configuration.getDouble(path + "health.max");
			double minHealth = configuration.getDouble(path + "health.min");

			double maxDamage = configuration.getDouble(path + "damage.max");
			double minDamage = configuration.getDouble(path + "damage.min");

			List<Particle> particles = new ArrayList<Particle>();

			ConfigurationSection configurationSection = configuration.getConfigurationSection(path + "particles.");
			if (configurationSection != null)
				for (String key : configurationSection.getKeys(false)) {
					String currentPath = path + "particles." + key + ".";
					ParticleEffect effect = ParticleEffect.valueOf(configuration.getString(currentPath + "effect"));
					ParticleType type = ParticleType.valueOf(configuration.getString(currentPath + "type"));
					double percent = configuration.getDouble(currentPath + "percent", 3.0);
					Particle particle = new ZParticle(effect, type, percent);
					particles.add(particle);
				}

			return new ZMobConfiguration(entityType, speed, distance, pushHeight, pushSpeed, minHealth, maxHealth,
					maxDamage, minDamage, criticalMultiplier, displayName, criticalPercent, particles, setences, names);
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
		configuration.set(path + "displayname", colorReverse(mobConfiguration.getDisplayName()));
		configuration.set(path + "critical.multiplier", mobConfiguration.getCriticalMultiplier());
		configuration.set(path + "critical.percent", mobConfiguration.getCriticalPercent());
		configuration.set(path + "health.max", mobConfiguration.getMaxHealth());
		configuration.set(path + "health.min", mobConfiguration.getMinHealth());
		configuration.set(path + "damage.max", mobConfiguration.getMaxDamage());
		configuration.set(path + "damage.min", mobConfiguration.getMinDamage());
		configuration.set(path + "push.height", mobConfiguration.getPushHeight());
		configuration.set(path + "push.speed", mobConfiguration.getPushSpeed());
		configuration.set(path + "setences", colorReverse(mobConfiguration.getSentences()));
		configuration.set(path + "names", colorReverse(mobConfiguration.getNames()));
		AtomicInteger atomicInteger = new AtomicInteger(1);
		mobConfiguration.getParticle().forEach(particle -> {
			String currentPath = path + "particles." + atomicInteger.getAndIncrement() + ".";
			configuration.set(currentPath + "effect", particle.getEffect().toString());
			configuration.set(currentPath + "type", particle.getType().toString());
			configuration.set(currentPath + "percent", particle.getPercent());
		});
	}

}
