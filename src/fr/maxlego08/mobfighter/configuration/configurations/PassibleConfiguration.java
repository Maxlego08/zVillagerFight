package fr.maxlego08.mobfighter.configuration.configurations;

import org.bukkit.entity.EntityType;

import fr.maxlego08.mobfighter.configuration.ZMobConfiguration;

public class PassibleConfiguration extends ZMobConfiguration {

	public PassibleConfiguration(EntityType type) {
		super(type, 1.3D, 2D, 0.2D, 1.2D);
	}

}
