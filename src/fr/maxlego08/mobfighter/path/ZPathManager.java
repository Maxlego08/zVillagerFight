package fr.maxlego08.mobfighter.path;

import org.bukkit.Location;

import fr.maxlego08.mobfighter.api.Fighter;
import fr.maxlego08.mobfighter.api.path.Path;
import fr.maxlego08.mobfighter.api.path.PathManager;
import fr.maxlego08.mobfighter.path.nms.Path18R3;

public class ZPathManager implements PathManager {

	private final Path path;

	public ZPathManager() {
		this.path = new Path18R3();
	}

	@Override
	public void setPathGoal(Fighter fighter, Location location) {
		this.path.setPath(fighter, location, fighter.getConfiguration().getSpeed());
	}

}
