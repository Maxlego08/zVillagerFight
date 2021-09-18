package fr.maxlego08.mobfighter.attack;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;
import java.util.logging.Logger;

import fr.maxlego08.mobfighter.api.attack.AttackPlugin;
import fr.maxlego08.mobfighter.api.attack.AttackPluginVersion;
import fr.maxlego08.mobfighter.api.attack.AttackJavaPlugin;
import fr.maxlego08.mobfighter.exceptions.InvalidPluginException;

public class AttackPluginClassLoader extends URLClassLoader {

	private final JarFile jarFile;
	private final AttackJavaPlugin plugin;

	public AttackPluginClassLoader(File file, AttackPluginVersion version, ClassLoader parent)
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
			pluginClass = jarClass.asSubclass(AttackJavaPlugin.class);
		} catch (ClassCastException e) {
			throw new InvalidPluginException("main class `" + version.getMain() + "' does not extend JavaPlugin", e);
		}

		try {
			this.plugin = (AttackJavaPlugin) pluginClass.newInstance();
		} catch (IllegalAccessException e) {
			throw new InvalidPluginException("No public constructor", e);
		} catch (InstantiationException e) {
			throw new InvalidPluginException("Abnormal plugin type", e);
		}
		this.plugin.init(version);

	}

	public AttackPlugin getPlugin() {
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
			logger.info("Cant enable " + plugin.getVersion().getName() + ", its already loaded");
			return;
		}
		this.plugin.setEnable(true);
		this.plugin.onEnable();
	}

}
