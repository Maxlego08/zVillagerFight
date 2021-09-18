package fr.maxlego08.mobfighter.addons;

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
import fr.maxlego08.mobfighter.api.addons.AddonManager;
import fr.maxlego08.mobfighter.api.addons.Addon;
import fr.maxlego08.mobfighter.api.addons.AddonDescription;
import fr.maxlego08.mobfighter.exceptions.InvalidDescriptionException;
import fr.maxlego08.mobfighter.exceptions.InvalidPluginException;
import fr.maxlego08.mobfighter.zcore.utils.ZUtils;

public class ZAddonManager extends ZUtils implements AddonManager {

	private final ZMobPlugin plugin;
	private final List<AddonClassLoader> classLoaders = new ArrayList<AddonClassLoader>();

	/**
	 * @param plugin
	 */
	public ZAddonManager(ZMobPlugin plugin) {
		super();
		this.plugin = plugin;
	}

	@Override
	public void load() {

		File folder = new File(this.plugin.getDataFolder(), "addons");
		List<File> files = Arrays.asList(folder.listFiles()).stream()
				.filter(f -> !f.isDirectory() && f.getAbsolutePath().endsWith(".jar")).collect(Collectors.toList());

		files.forEach(t -> {
			Addon plugin = null;
			try {
				plugin = this.loadPlugin(t);
			} catch (InvalidPluginException e) {
				e.printStackTrace();
			} finally {
				if (plugin != null) {
					AddonDescription desc = plugin.getDescription();
					this.plugin.getLogger().info("Loading " + desc.getName() + " v" + desc.getVersion());
				}
			}
		});
	}

	@Override
	public Optional<Addon> getAddon(String name) {
		return this.classLoaders.stream().filter(plugin -> plugin.getPlugin().getDescription().getName().equals(name))
				.map(e -> (Addon) e.getPlugin()).findFirst();
	}

	@Override
	public Addon loadPlugin(File file) throws InvalidPluginException {

		AddonDescription description;
		try {
			description = this.loadVersion(file);
		} catch (InvalidDescriptionException e) {
			throw new InvalidPluginException(e);
		}

		Optional<Addon> optional = this.getAddon(description.getName());
		if (optional.isPresent()) {
			throw new InvalidPluginException("Addon " + description.getName() + " already exist !");
		}

		try {
			AddonClassLoader attackPluginClassLoader = new AddonClassLoader(this.plugin, file, description,
					this.getClass().getClassLoader());

			this.classLoaders.add(attackPluginClassLoader);

			return attackPluginClassLoader.getPlugin();
		} catch (Exception e) {
			throw new InvalidPluginException(e);
		}
	}

	@Override
	public AddonDescription loadVersion(File file) throws InvalidDescriptionException {
		AddonDescription description;
		JarFile jarFile = null;
		InputStream stream = null;
		try {
			jarFile = new JarFile(file);
			JarEntry entry = jarFile.getJarEntry("addon.yml");
			stream = jarFile.getInputStream(entry);
			description = new ZAddonPluginVersion(YamlConfiguration.loadConfiguration(new InputStreamReader(stream)));
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
		return description;
	}

	@Override
	public void unload() {
		this.classLoaders.forEach(classLoader -> {
			try {
				classLoader.onDisable();
				AddonDescription desc = classLoader.getPlugin().getDescription();
				this.plugin.getLogger().info("Disabling " + desc.getName() + " v" + desc.getVersion());
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

	@Override
	public void reload() {
		this.unload();
		this.load();
		this.plugin.getLogger().info("Reload complete.");
	}

	@Override
	public long count() {
		return classLoaders.size();
	}

	@Override
	public List<Addon> getAddons() {
		return this.classLoaders.stream().map(e -> e.getPlugin()).collect(Collectors.toList());
	}

}
