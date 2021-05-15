package fr.maxlego08.mobfighter.zcore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.maxlego08.mobfighter.api.Arena;
import fr.maxlego08.mobfighter.api.enums.EnumInventory;
import fr.maxlego08.mobfighter.api.enums.InventoryName;
import fr.maxlego08.mobfighter.command.CommandManager;
import fr.maxlego08.mobfighter.command.VCommand;
import fr.maxlego08.mobfighter.inventory.VInventory;
import fr.maxlego08.mobfighter.inventory.ZInventoryManager;
import fr.maxlego08.mobfighter.listener.ListenerAdapter;
import fr.maxlego08.mobfighter.zcore.logger.Logger;
import fr.maxlego08.mobfighter.zcore.logger.Logger.LogType;
import fr.maxlego08.mobfighter.zcore.utils.ItemDecoder;
import fr.maxlego08.mobfighter.zcore.utils.gson.ArenaAdapter;
import fr.maxlego08.mobfighter.zcore.utils.gson.LocationAdapter;
import fr.maxlego08.mobfighter.zcore.utils.gson.PotionEffectAdapter;
import fr.maxlego08.mobfighter.zcore.utils.plugins.Plugins;
import fr.maxlego08.mobfighter.zcore.utils.storage.Persist;
import fr.maxlego08.mobfighter.zcore.utils.storage.Saveable;
import net.milkbowl.vault.economy.Economy;

public abstract class ZPlugin extends JavaPlugin {

	private final Logger log = new Logger(this.getDescription().getFullName());
	private Gson gson;
	private Persist persist;
	private static ZPlugin plugin;
	private long enableTime;
	private List<Saveable> savers = new ArrayList<>();
	private List<ListenerAdapter> listenerAdapters = new ArrayList<>();
	private Economy economy = null;
	private List<String> files = new ArrayList<>();

	protected CommandManager commandManager;
	protected ZInventoryManager inventoryManager;
	
	private PlayerPoints playerPoints;
	private PlayerPointsAPI playerPointsAPI;

	public ZPlugin() {
		plugin = this;
	}

	/**
	 * @return boolean
	 */
	protected void hookPlayerPoints() {
		try {
			final Plugin plugin = (Plugin) this.getServer().getPluginManager().getPlugin("PlayerPoints");
			if (plugin == null)
				return;
			playerPoints = PlayerPoints.class.cast(plugin);
			if (playerPoints != null) {
				playerPointsAPI = playerPoints.getAPI();
				log.log("PlayerPoint plugin detection performed successfully", LogType.SUCCESS);
			} else
				log.log("Impossible de charger player point !", LogType.SUCCESS);
		} catch (Exception e) {
		}
	}
	
	protected boolean preEnable() {

		enableTime = System.currentTimeMillis();

		log.log("=== ENABLE START ===");
		log.log("Plugin Version V<&>c" + getDescription().getVersion(), LogType.INFO);

		getDataFolder().mkdirs();
		
		hookPlayerPoints();

		gson = getGsonBuilder().create();
		persist = new Persist(this);

		if (getPlugin(Plugins.VAULT) != null)
			economy = getProvider(Economy.class);

		
		boolean isNew = ItemDecoder.isNewVersion();
		for (String file : files) {
			if (isNew) {
				if (!new File(getDataFolder() + "/inventories/" + file + ".yml").exists())
					saveResource("inventories/1_13/" + file + ".yml", "inventories/" + file + ".yml", false);
			} else {
				if (!new File(getDataFolder() + "/inventories/" + file + ".yml").exists())
					saveResource("inventories/" + file + ".yml", false);
			}
		}
		
		return true;

	}
	
	public PlayerPoints getPlayerPoints() {
		return playerPoints;
	}
	
	public PlayerPointsAPI getPlayerPointsAPI() {
		return playerPointsAPI;
	}

	protected void postEnable() {

		if (inventoryManager != null)
			inventoryManager.sendLog();

		if (commandManager != null)
			commandManager.registerCommands();

		log.log("=== ENABLE DONE <&>7(<&>6" + Math.abs(enableTime - System.currentTimeMillis()) + "ms<&>7) <&>e===");

	}

	protected void preDisable() {

		enableTime = System.currentTimeMillis();
		log.log("=== DISABLE START ===");

	}

	protected void postDisable() {

		log.log("=== DISABLE DONE <&>7(<&>6" + Math.abs(enableTime - System.currentTimeMillis()) + "ms<&>7) <&>e===");

	}

	/**
	 * Build gson
	 * 
	 * @return
	 */
	public GsonBuilder getGsonBuilder() {
		return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().serializeNulls()
				.excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.VOLATILE)
				.registerTypeAdapter(PotionEffect.class, new PotionEffectAdapter())
				.registerTypeAdapter(Arena.class, new ArenaAdapter())
				.registerTypeAdapter(Location.class, new LocationAdapter());
	}

	/**
	 * Add a listener
	 * 
	 * @param listener
	 */
	public void addListener(Listener listener) {
		if (listener instanceof Saveable)
			addSave((Saveable) listener);
		Bukkit.getPluginManager().registerEvents(listener, this);
	}

	/**
	 * Add a listener from ListenerAdapter
	 * 
	 * @param adapter
	 */
	public void addListener(ListenerAdapter adapter) {
		if (adapter instanceof Saveable)
			addSave((Saveable) adapter);
		listenerAdapters.add(adapter);
	}

	/**
	 * Add a Saveable
	 * 
	 * @param saver
	 */
	public void addSave(Saveable saver) {
		this.savers.add(saver);
	}

	/**
	 * Get logger
	 * 
	 * @return loggers
	 */
	public Logger getLog() {
		return this.log;
	}

	/**
	 * Get gson
	 * 
	 * @return {@link Gson}
	 */
	public Gson getGson() {
		return gson;
	}

	public Persist getPersist() {
		return persist;
	}

	/**
	 * Get all saveables
	 * 
	 * @return savers
	 */
	public List<Saveable> getSavers() {
		return savers;
	}

	public static ZPlugin z() {
		return plugin;
	}

	/**
	 * 
	 * @param classz
	 * @return
	 */
	protected <T> T getProvider(Class<T> classz) {
		RegisteredServiceProvider<T> provider = getServer().getServicesManager().getRegistration(classz);
		if (provider == null) {
			log.log("Unable to retrieve the provider " + classz.toString(), LogType.WARNING);
			return null;
		}
		return provider.getProvider() != null ? (T) provider.getProvider() : null;
	}

	public Economy getVaultEconomy() {
		return economy;
	}

	/**
	 * 
	 * @return listenerAdapters
	 */
	public List<ListenerAdapter> getListenerAdapters() {
		return listenerAdapters;
	}

	/**
	 * @return the commandManager
	 */
	public CommandManager getCommandManager() {
		return commandManager;
	}

	/**
	 * @return the inventoryManager
	 */
	public ZInventoryManager getInventoryManager() {
		return inventoryManager;
	}

	/**
	 * 
	 * @param pluginName
	 * @return
	 */
	protected boolean isEnable(Plugins pl) {
		Plugin plugin = getPlugin(pl);
		return plugin == null ? false : plugin.isEnabled();
	}

	/**
	 * 
	 * @param pluginName
	 * @return
	 */
	protected Plugin getPlugin(Plugins plugin) {
		return Bukkit.getPluginManager().getPlugin(plugin.getName());
	}

	/**
	 * Register command
	 * 
	 * @param command
	 * @param vCommand
	 * @param aliases
	 */
	protected void registerCommand(String command, VCommand vCommand, String... aliases) {
		commandManager.registerCommand(command, vCommand, aliases);
	}

	/**
	 * Register Inventory
	 * 
	 * @param inventory
	 * @param vInventory
	 */
	protected void registerInventory(EnumInventory inventory, VInventory vInventory) {
		inventoryManager.addInventory(inventory, vInventory);
	}
	
	/**
	 * 
	 * @param resourcePath
	 * @param toPath
	 * @param replace
	 */
	protected void saveResource(String resourcePath, String toPath, boolean replace) {
		if (resourcePath != null && !resourcePath.equals("")) {
			resourcePath = resourcePath.replace('\\', '/');
			InputStream in = this.getResource(resourcePath);
			if (in == null) {
				throw new IllegalArgumentException(
						"The embedded resource '" + resourcePath + "' cannot be found in " + this.getFile());
			} else {
				File outFile = new File(getDataFolder(), toPath);
				int lastIndex = toPath.lastIndexOf(47);
				File outDir = new File(getDataFolder(), toPath.substring(0, lastIndex >= 0 ? lastIndex : 0));
				if (!outDir.exists()) {
					outDir.mkdirs();
				}

				try {
					if (outFile.exists() && !replace) {
						getLogger().log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile
								+ " because " + outFile.getName() + " already exists.");
					} else {
						OutputStream out = new FileOutputStream(outFile);
						byte[] buf = new byte[1024];

						int len;
						while ((len = in.read(buf)) > 0) {
							out.write(buf, 0, len);
						}

						out.close();
						in.close();
					}
				} catch (IOException var10) {
					getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, var10);
				}

			}
		} else {
			throw new IllegalArgumentException("ResourcePath cannot be null or empty");
		}
	}
	
	protected void registerFile(InventoryName file) {
		this.files.add(file.getName());
	}
	
	public List<String> getFiles() {
		return files;
	}

}
