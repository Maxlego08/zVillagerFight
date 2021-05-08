package fr.maxlego08.mobfighter.path.nms;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftCreature;
import org.bukkit.entity.LivingEntity;

import fr.maxlego08.mobfighter.api.Fighter;
import fr.maxlego08.mobfighter.api.path.Path;
import net.minecraft.server.v1_14_R1.EntityInsentient;
import net.minecraft.server.v1_14_R1.Navigation;
import net.minecraft.server.v1_14_R1.PathEntity;
import net.minecraft.server.v1_14_R1.PathfinderGoal;

public class Path14R1 implements Path {

	public Path14R1() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setPath(Fighter fighter, Location location, double speed) {
		LivingEntity entity = fighter.getEntity();
		CraftCreature craftVillager = (CraftCreature) entity;
		EntityInsentient entityInsentient = craftVillager.getHandle();
		PathFinder finder = new PathFinder(entityInsentient, location, speed);
		entityInsentient.goalSelector.a(0, finder);
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
			Location localLocation = new Location(this.location.getWorld(), this.entity.lastX, this.entity.lastY,
					this.entity.lastZ);
			while (localLocation.distanceSquared(this.location) > 400.0D)
				this.location = localLocation.toVector().midpoint(this.location.toVector())
						.toLocation(this.location.getWorld());
			PathEntity localPathEntity = this.navigation.a(this.location.getX(), this.location.getY(),
					this.location.getZ());
			this.navigation.a(localPathEntity, this.speed);
		}
	}

}
