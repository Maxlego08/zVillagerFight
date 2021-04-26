package fr.maxlego08.mobfighter.api.path;

import org.bukkit.Location;

import fr.maxlego08.mobfighter.api.Fighter;

public interface Path {

	void setPath(Fighter fighter, Location location, double speed);
	
}
