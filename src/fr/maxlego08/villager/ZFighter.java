package fr.maxlego08.villager;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Villager;

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

}
