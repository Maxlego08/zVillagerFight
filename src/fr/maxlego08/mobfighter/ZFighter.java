package fr.maxlego08.mobfighter;

import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import fr.maxlego08.mobfighter.api.Fighter;
import fr.maxlego08.mobfighter.api.configuration.MobConfiguration;
import fr.maxlego08.mobfighter.zcore.utils.ZUtils;

public class ZFighter extends ZUtils implements Fighter {

	private final EntityType entityType;
	private final MobConfiguration configuration;
	private LivingEntity entity;

	public ZFighter(EntityType entityType, Optional<MobConfiguration> optional) {
		this.entityType = entityType;
		this.configuration = optional.get();
	}

	@Override
	public LivingEntity getEntity() {
		return this.entity;
	}

	@Override
	public void spawn(Location location) {
		World world = location.getWorld();
		this.entity = (LivingEntity) world.spawn(location, entityType.getEntityClass());
	}

	@Override
	public Location getLocation() {
		return this.entity == null ? null : this.entity.getLocation();
	}

	@Override
	public double distance(Fighter fighter) {
		Location location = this.getLocation();
		Location location2 = fighter.getLocation();
		return location == null || location2 == null ? 9999999
				: location.getWorld().equals(location2.getWorld()) ? location.distance(location2) : 9999999;
	}

	@Override
	public void push(Location location) {
		Vector vector = this.entity.getLocation().toVector().subtract(location.toVector()).normalize();
		vector.setY(this.configuration.getPushHeight());
		this.entity.setVelocity(vector.multiply(this.configuration.getPushSpeed()));
	}

	@Override
	public MobConfiguration getConfiguration() {
		return this.configuration;
	}

	@Override
	public boolean isValid() {
		return this.entity != null && this.entity.isValid();
	}

	@Override
	public void setTarget(Fighter secondFighter) {
		LivingEntity livingEntity = secondFighter.getEntity();
		if (this.entity instanceof Creature) 
			((Creature) this.entity).setTarget(livingEntity);
	}

}
