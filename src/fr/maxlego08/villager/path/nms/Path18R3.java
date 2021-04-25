package fr.maxlego08.villager.path.nms;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftVillager;
import org.bukkit.entity.Villager;

import fr.maxlego08.villager.api.Fighter;
import fr.maxlego08.villager.api.path.Path;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.Navigation;
import net.minecraft.server.v1_8_R3.PathEntity;
import net.minecraft.server.v1_8_R3.PathfinderGoal;

public class Path18R3 implements Path {

	public Path18R3() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setPath(Fighter fighter, Location location, double speed) {
		Villager villager = fighter.getEntity();
		CraftVillager craftVillager = (CraftVillager) villager;
		EntityInsentient entityInsentient = craftVillager.getHandle();
		PathFinder finder = new PathFinder(entityInsentient, location, speed);
		entityInsentient.goalSelector.a(1, finder);
	}

	private class PathFinder extends PathfinderGoal {

		private final EntityInsentient entity;
		private Location location;
		private final Navigation navigation;
		private final double speed;

		/**
		 * @param entity
		 * @param loc
		 * @param navigation
		 * @param speed
		 */
		public PathFinder(EntityInsentient entity, Location location, double speed) {
			this.entity = entity;
			this.location = location;
			this.navigation = (Navigation) this.entity.getNavigation();
			this.speed = speed;
		}

		@Override
		public boolean a() {
			return true;
		}

		@Override
		public void c() {
			this.entity.onGround = true;
			Location localLocation = new Location(this.location.getWorld(), this.entity.locX, this.entity.locY,
					this.entity.locZ);
			while (localLocation.distanceSquared(this.location) > 400.0D)
				this.location = localLocation.toVector().midpoint(this.location.toVector())
						.toLocation(this.location.getWorld());
			PathEntity localPathEntity = this.navigation.a(this.location.getX(), this.location.getY(),
					this.location.getZ());
			this.navigation.a(localPathEntity, this.speed);
		}
	}

}
