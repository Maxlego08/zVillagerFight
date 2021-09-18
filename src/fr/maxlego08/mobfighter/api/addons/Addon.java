package fr.maxlego08.mobfighter.api.addons;

import java.io.File;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import fr.maxlego08.mobfighter.api.Arena;
import fr.maxlego08.mobfighter.api.Duel;
import fr.maxlego08.mobfighter.api.Fighter;

public interface Addon {

	AddonDescription getDescription();

	boolean isEnable();

	void onEnable();

	void onDisable();

	JavaPlugin getPlugin();

	void registerListener(Listener listener);

	File getFolder();

	void duelStart(Duel duel, Arena arena, Fighter fighter, Fighter opponnant);

	void duelTick(Duel duel, Arena arena, Fighter fighter, Fighter opponnant);

	void duelWin(Duel duel, Arena arena, Fighter firstFighter, Fighter secondFighter);

	void duelStop(Duel duel, Arena arena, Fighter firstFighter, Fighter secondFighter);

}
