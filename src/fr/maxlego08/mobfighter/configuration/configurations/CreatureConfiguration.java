package fr.maxlego08.mobfighter.configuration.configurations;

import org.bukkit.entity.EntityType;

import fr.maxlego08.mobfighter.configuration.ZMobConfiguration;

public class CreatureConfiguration extends ZMobConfiguration {

	public CreatureConfiguration(EntityType type) {
		super(type, 1.1D, 1.5D, 0.3D, 1.3D);
	}

}
