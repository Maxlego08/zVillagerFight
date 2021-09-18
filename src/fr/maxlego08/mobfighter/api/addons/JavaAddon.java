package fr.maxlego08.mobfighter.api.addons;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class JavaAddon implements Addon {

	private JavaPlugin plugin;
	private AddonDescription description;
	private boolean isEnable;

	public void init(JavaPlugin plugin, AddonDescription description) {
		this.description = description;
		this.plugin = plugin;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	@Override
	public AddonDescription getDescription() {
		return description;
	}

	@Override
	public boolean isEnable() {
		return this.isEnable;
	}

	@Override
	public JavaPlugin getPlugin() {
		return this.plugin;
	}

}
