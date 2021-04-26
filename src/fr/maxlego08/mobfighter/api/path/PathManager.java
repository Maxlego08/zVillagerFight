package fr.maxlego08.mobfighter.api.path;

import org.bukkit.Location;

import fr.maxlego08.mobfighter.api.Fighter;

public interface PathManager {

	void setPathGoal(Fighter fighter, Location location);
	
}
