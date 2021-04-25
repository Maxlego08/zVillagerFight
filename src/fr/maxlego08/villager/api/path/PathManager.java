package fr.maxlego08.villager.api.path;

import org.bukkit.Location;

import fr.maxlego08.villager.api.Fighter;

public interface PathManager {

	void setPathGoal(Fighter fighter, Location location, double speed);
	
}
