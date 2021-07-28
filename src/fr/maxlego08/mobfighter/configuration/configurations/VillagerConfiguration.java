package fr.maxlego08.mobfighter.configuration.configurations;

import org.bukkit.entity.EntityType;

import fr.maxlego08.mobfighter.configuration.ZMobConfiguration;

public class VillagerConfiguration extends ZMobConfiguration {

	public VillagerConfiguration() {
		super(EntityType.VILLAGER, 1.1D, 2.0D, 0.3D, 1.2D, 100D, 120D, 10D, 5D, 1.25D, "§f%name% §c%health% §4§l<3", 2);
	}

}
