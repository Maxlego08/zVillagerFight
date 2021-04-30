package fr.maxlego08.mobfighter.configuration.configurations;

import org.bukkit.entity.EntityType;

import fr.maxlego08.mobfighter.configuration.ZMobConfiguration;

public class PassibleConfiguration extends ZMobConfiguration {

	public PassibleConfiguration(EntityType type) {
		super(type, 2.5D, 2D, 0.2D, 1.2D, 100D, 120D, 4D, 2D, 1.25D, "§f%name% §c%health% §4§l<3", 2);
	}

}
