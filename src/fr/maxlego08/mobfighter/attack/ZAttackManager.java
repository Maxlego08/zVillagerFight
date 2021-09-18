package fr.maxlego08.mobfighter.attack;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import org.bukkit.configuration.file.YamlConfiguration;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.api.attack.AttackManager;
import fr.maxlego08.mobfighter.api.attack.AttackPlugin;
import fr.maxlego08.mobfighter.api.attack.AttackPluginVersion;
import fr.maxlego08.mobfighter.exceptions.InvalidDescriptionException;
import fr.maxlego08.mobfighter.exceptions.InvalidPluginException;
import fr.maxlego08.mobfighter.zcore.utils.ZUtils;

public class ZAttackManager extends ZUtils implements AttackManager {

	private final ZMobPlugin plugin;
	private final List<AttackPluginClassLoader> classLoaders = new ArrayList<AttackPluginClassLoader>();

	/**
	 * @param plugin
	 */
	public ZAttackManager(ZMobPlugin plugin) {
		super();
		this.plugin = plugin;
	}

	@Override
	public void load() {

		//

		File folder = new File(this.plugin.getDataFolder(), "addons");
		List<File> files = Arrays.asList(folder.listFiles()).stream()
				.filter(f -> !f.isDirectory() && f.getAbsolutePath().endsWith(".jar")).collect(Collectors.toList());

		files.forEach(t -> {
			AttackPlugin plugin = null;
			try {
				plugin = this.loadPlugin(t);
			} catch (InvalidPluginException e) {
				e.printStackTrace();
			} finally {
				if (plugin != null) {
					this.plugin.getLogger().info("Load addon " + plugin.getVersion().getName());
				}
			}
		});
	}

	@Override
	public Optional<AttackPlugin> getPlugin(String name) {
		return this.classLoaders.stream().filter(plugin -> plugin.getPlugin().getVersion().getName().equals(name))
				.map(e -> (AttackPlugin) e.getPlugin()).findFirst();
	}

	@Override
	public AttackPlugin loadPlugin(File file) throws InvalidPluginException {

		AttackPluginVersion pluginVersion;
		try {
			pluginVersion = this.loadVersion(file);
		} catch (InvalidDescriptionException e) {
			throw new InvalidPluginException(e);
		}

		try {
			AttackPluginClassLoader attackPluginClassLoader = new AttackPluginClassLoader(file, pluginVersion,
					this.getClass().getClassLoader());

			this.classLoaders.add(attackPluginClassLoader);

			return attackPluginClassLoader.getPlugin();
		} catch (Exception e) {
			throw new InvalidPluginException(e);
		}
	}

	@Override
	public AttackPluginVersion loadVersion(File file) throws InvalidDescriptionException {
		AttackPluginVersion pluginVersion;
		JarFile jarFile = null;
		InputStream stream = null;
		try {
			jarFile = new JarFile(file);
			JarEntry entry = jarFile.getJarEntry("addon.yml");
			stream = jarFile.getInputStream(entry);
			pluginVersion = new ZAttackPluginVersion(
					YamlConfiguration.loadConfiguration(new InputStreamReader(stream)));
			jarFile.close();
		} catch (IOException e) {
			throw new InvalidDescriptionException(e);
		} finally {
			try {
				if (jarFile != null) {
					jarFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (stream != null) {
					stream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pluginVersion;
	}

	@Override
	public void unload() {
		this.classLoaders.forEach(classLoader -> {
			try {
				classLoader.onDisable();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		this.classLoaders.clear();
	}

	@Override
	public void enable() {
		this.classLoaders.forEach(classLoader -> {
			classLoader.onEnable(this.plugin.getLogger());
		});
	}

}
