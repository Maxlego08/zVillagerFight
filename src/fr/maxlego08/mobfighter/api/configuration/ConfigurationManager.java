package fr.maxlego08.mobfighter.api.configuration;

import java.util.Optional;

import org.bukkit.entity.EntityType;

import fr.maxlego08.mobfighter.zcore.utils.storage.Saveable;

public interface ConfigurationManager extends Saveable {

	Optional<MobConfiguration> getConfiguration(EntityType type);

}
