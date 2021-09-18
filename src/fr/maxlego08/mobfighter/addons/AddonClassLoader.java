package fr.maxlego08.mobfighter.addons;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import fr.maxlego08.mobfighter.api.addons.JavaAddon;
import fr.maxlego08.mobfighter.api.addons.Addon;
import fr.maxlego08.mobfighter.api.addons.AddonDescription;
import fr.maxlego08.mobfighter.exceptions.InvalidPluginException;

public class AddonClassLoader extends URLClassLoader {

	private final JarFile jarFile;
	private final JavaAddon plugin;

	public AddonClassLoader(JavaPlugin plugin, File file, AddonDescription version, ClassLoader parent)
			throws InvalidPluginException, IOException {
		super(new URL[] { file.toURI().toURL() }, parent);

		this.jarFile = new JarFile(file);

		Class<?> jarClass;
		try {
			jarClass = Class.forName(version.getMain(), true, this);
		} catch (ClassNotFoundException e) {
			throw new InvalidPluginException("Cannot find main class `" + version.getMain() + "'", e);
		}

		Class<?> pluginClass;
		try {
			pluginClass = jarClass.asSubclass(JavaAddon.class);
		} catch (ClassCastException e) {
			throw new InvalidPluginException("main class `" + version.getMain() + "' does not extend JavaPlugin", e);
		}

		try {
			this.plugin = (JavaAddon) pluginClass.newInstance();
		} catch (IllegalAccessException e) {
			throw new InvalidPluginException("No public constructor", e);
		} catch (InstantiationException e) {
			throw new InvalidPluginException("Abnormal plugin type", e);
		}
		this.plugin.init(plugin, version);

	}

	public Addon getPlugin() {
		return plugin;
	}

	@Override
	public void close() throws IOException {
		this.jarFile.close();
		super.close();
	}

	public void onDisable() throws IOException {
		this.plugin.onDisable();
		this.plugin.setEnable(false);
		this.close();
	}

	public void onEnable(Logger logger) {
		if (this.plugin.isEnable()) {
			logger.info("Cant enable " + plugin.getDescription().getName() + ", its already loaded");
			return;
		}
		this.plugin.setEnable(true);
		this.plugin.onEnable();
	}

}
