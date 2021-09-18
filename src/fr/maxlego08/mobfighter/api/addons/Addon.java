package fr.maxlego08.mobfighter.api.addons;

import org.bukkit.plugin.java.JavaPlugin;

public interface Addon {

	AddonDescription getDescription();	
	
	boolean isEnable();
	
	void onEnable();
	
	void onDisable();
	
	JavaPlugin getPlugin();
	
}
