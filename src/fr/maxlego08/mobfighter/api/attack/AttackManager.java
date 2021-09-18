package fr.maxlego08.mobfighter.api.attack;

import java.io.File;
import java.util.Optional;

import fr.maxlego08.mobfighter.exceptions.InvalidDescriptionException;
import fr.maxlego08.mobfighter.exceptions.InvalidPluginException;

public interface AttackManager {

	void load();
	
	void unload();

	Optional<AttackPlugin> getPlugin(String name);

	AttackPlugin loadPlugin(File file) throws InvalidPluginException;

	AttackPluginVersion loadVersion(File file) throws InvalidDescriptionException;
	
	void enable();

}
