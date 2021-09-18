package fr.maxlego08.mobfighter.api.addons;

import java.io.File;
import java.util.List;
import java.util.Optional;

import fr.maxlego08.mobfighter.api.Duel;
import fr.maxlego08.mobfighter.api.Fighter;
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

	void duelStart(Duel duel);

	void duelTick(Duel duel);

	void duelWin(Duel duel, Fighter winner, Fighter looser);

	void duelStop(Duel zDuel);

}
