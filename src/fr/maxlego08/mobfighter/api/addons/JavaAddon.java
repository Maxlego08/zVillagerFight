package fr.maxlego08.mobfighter.api.addons;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class JavaAddon implements Addon {

	private List<Listener> listeners = new ArrayList<Listener>();
	private JavaPlugin plugin;
	private AddonDescription description;
	private boolean isEnable;
	private File folder;

	public void init(JavaPlugin plugin, AddonDescription description) {
		this.description = description;
		this.plugin = plugin;
		this.folder = new File(plugin.getDataFolder(), "addons/" + description.getName());
		if (!this.folder.exists())
			this.folder.mkdir();
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

	@Override
	public void registerListener(Listener listener) {
		this.listeners.add(listener);
		Bukkit.getPluginManager().registerEvents(listener, this.plugin);
	}

	public List<Listener> getListeners() {
		return listeners;
	}
	
	@Override
	public File getFolder() {
		return this.folder;
	}

}
