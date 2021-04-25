package fr.maxlego08.villager.path;

import org.bukkit.Location;

import fr.maxlego08.villager.api.Fighter;
import fr.maxlego08.villager.api.path.Path;
import fr.maxlego08.villager.api.path.PathManager;
import fr.maxlego08.villager.path.nms.Path18R3;

public class ZPathManager implements PathManager {

	private final Path path;

	public ZPathManager() {
		this.path = new Path18R3();
	}

	@Override
	public void setPathGoal(Fighter fighter, Location location, double speed) {
		this.path.setPath(fighter, location, speed);
	}

}
