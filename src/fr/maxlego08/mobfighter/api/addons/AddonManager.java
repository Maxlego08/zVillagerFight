package fr.maxlego08.mobfighter.api.addons;

import java.io.File;
import java.util.List;
import java.util.Optional;

import fr.maxlego08.mobfighter.exceptions.InvalidDescriptionException;
import fr.maxlego08.mobfighter.exceptions.InvalidPluginException;

public interface AddonManager {

	void load();
	
	void unload();
	
	void reload();

	Optional<Addon> getAddon(String name);

	Addon loadPlugin(File file) throws InvalidPluginException;

	AddonDescription loadVersion(File file) throws InvalidDescriptionException;
	
	void enable();

	long count();
	
	List<Addon> getAddons();

}
