package fr.maxlego08.villager;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Villager;
import org.bukkit.util.Vector;

import fr.maxlego08.villager.api.Fighter;
import fr.maxlego08.villager.zcore.utils.ZUtils;

public class ZFighter extends ZUtils implements Fighter {

	private Villager villager;

	public ZFighter() {
	}

	@Override
	public Villager getEntity() {
		return this.villager;
	}

	@Override
	public void spawn(Location location) {
		World world = location.getWorld();
		this.villager = world.spawn(location, Villager.class);
	}

	@Override
	public void setPathGoal(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public Location getLocation() {
		return this.villager == null ? null : this.villager.getLocation();
	}

	@Override
	public double distance(Fighter fighter) {
		Location location = this.getLocation();
		Location location2 = fighter.getLocation();
		return location == null || location2 == null ? 9999999
				: location.getWorld().equals(location2.getWorld()) ? location.distance(location2) : 9999999;
	}

	@Override
	public void push(Location location, double distance) {
		Vector vector = this.villager.getLocation().toVector().subtract(location.toVector()).normalize();
		vector.setY(0.2D);
		this.villager.setVelocity(vector.multiply(distance));
	}

}
