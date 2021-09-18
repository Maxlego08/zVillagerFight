package fr.maxlego08.mobfighter.addons;

import org.bukkit.configuration.file.YamlConfiguration;

import fr.maxlego08.mobfighter.api.addons.AddonDescription;

public class ZAddonPluginVersion implements AddonDescription {

	private final String version;
	private final String author;
	private final String name;
	private final String main;

	/**
	 * @param version
	 * @param author
	 * @param name
	 */
	public ZAddonPluginVersion(YamlConfiguration configuration) {
		super();
		this.version = configuration.getString("version");
		this.author = configuration.getString("author");
		this.name = configuration.getString("name");
		this.main = configuration.getString("main");
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public String getMain() {
		return main;
	}
	
}
