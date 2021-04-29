package fr.maxlego08.mobfighter;

import java.util.Optional;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import fr.maxlego08.mobfighter.api.Duel;
import fr.maxlego08.mobfighter.api.Fighter;
import fr.maxlego08.mobfighter.api.configuration.MobConfiguration;
import fr.maxlego08.mobfighter.api.particles.Particle;
import fr.maxlego08.mobfighter.zcore.ZPlugin;
import fr.maxlego08.mobfighter.zcore.utils.ZUtils;

public class ZFighter extends ZUtils implements Fighter {

	private final EntityType entityType;
	private final MobConfiguration configuration;
	private LivingEntity entity;
	private double health;

	private ArmorStand armorStandHealth;
	private ArmorStand armorStandWord;
	private int taskID;

	/**
	 * 
	 * @param entityType
	 * @param optional
	 */
	public ZFighter(EntityType entityType, Optional<MobConfiguration> optional) {
		this.entityType = entityType;
		this.configuration = optional.get();
		this.health = getNumberBetween(this.configuration.getMinHealth(), this.configuration.getMaxHealth());
	}

	@Override
	public LivingEntity getEntity() {
		return this.entity;
	}

	@Override
	public void spawn(Location location) {

		if (this.armorStandHealth != null)
			this.armorStandHealth.remove();
		if (this.armorStandWord != null)
			this.armorStandWord.remove();

		World world = location.getWorld();
		this.entity = (LivingEntity) world.spawn(location, entityType.getEntityClass());

		this.armorStandHealth = world.spawn(this.entity.getEyeLocation().add(0, -0.5, 0), ArmorStand.class);
		this.armorStandWord = world.spawn(this.entity.getEyeLocation().add(0, -0.25, 0), ArmorStand.class);

		this.armorStandHealth.setVisible(false);
		this.armorStandHealth.setGravity(false);
		this.armorStandHealth.setSmall(true);
		this.armorStandHealth.setCustomNameVisible(true);
		this.updateDisplayName();

		this.armorStandWord.setVisible(false);
		this.armorStandWord.setGravity(false);
		this.armorStandWord.setSmall(true);
		this.armorStandWord.setCustomNameVisible(true);
		this.updateSentence();

		this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(ZPlugin.z(), () -> {
			if (this.armorStandHealth == null || !this.armorStandHealth.isValid() || this.armorStandWord == null
					|| !this.armorStandWord.isValid()) {
				Bukkit.getScheduler().cancelTask(taskID);
			} else {
				this.armorStandHealth.teleport(this.entity.getEyeLocation().add(0, -0.5, 0));
				this.armorStandWord.teleport(this.entity.getEyeLocation().add(0, -0.25, 0));
			}
		}, 1l, 1L);
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
		return this.entity != null && this.entity.isValid() && this.health > 0;
	}

	@Override
	public void setTarget(Fighter secondFighter) {
		LivingEntity livingEntity = secondFighter.getEntity();
		if (this.entity instanceof Creature)
			((Creature) this.entity).setTarget(livingEntity);
	}

	@Override
	public double getHealth() {
		return new Double(this.health);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onTouch(Fighter fighter, Duel duel) {
		double damage = fighter.getRandomDamage();
		this.applyDamage(damage);

		if (this.health <= 0) { // La mort

			this.armorStandHealth.remove();
			this.armorStandWord.remove();
			this.entity.damage(this.entity.getMaxHealth() * 2);

			duel.win(fighter, this);

		} else {

			this.updateSentence();
			
			Particle particle = this.configuration.getRandomParticle();
			Location location = this.entity.getEyeLocation();
			switch (particle.getType()) {
			case FIRE:
				fire(location, particle.getEffect());
				break;
			case PUSH:
				push(location, particle.getEffect());
				break;
			default:
				break;
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ZFighter [entityType=" + entityType + ", configuration=" + configuration + ", entity=" + entity
				+ ", health=" + health + "]";
	}

	@Override
	public double getRandomDamage() {

		double damage = getNumberBetween(this.configuration.getMinDamage(), this.configuration.getMaxDamage());

		Random random = new Random();
		if (random.nextInt(100) <= this.configuration.getCriticalPercent()) {
			damage *= this.configuration.getCriticalMultiplier();
		}

		return damage;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void applyDamage(double damage) {
		this.health -= damage;
		this.updateDisplayName();
		this.entity.damage(0.1);
		this.entity.setHealth(this.entity.getMaxHealth());
	}

	@Override
	public void updateDisplayName() {
		if (this.isValid()) {

			String string = new String(this.configuration.getDisplayName());

			string = string.replace("%health%", format(this.health, "#.##"));

			this.armorStandHealth.setCustomName(string);
		}
	}

	@Override
	public void remove() {
		if (this.isValid())
			this.entity.remove();
		if (this.armorStandHealth != null && this.armorStandHealth.isValid())
			this.armorStandHealth.remove();
		if (this.armorStandWord != null && this.armorStandWord.isValid())
			this.armorStandWord.remove();
	}

	@Override
	public void loose() {
		// todo
	}

	@Override
	public void win() {
		if (this.armorStandHealth != null)
			this.armorStandHealth.remove();
		if (this.armorStandWord != null)
			this.armorStandWord.remove();
	}

	@Override
	public void updateSentence() {
		if (this.armorStandWord != null) {
			String string = randomElement(this.configuration.getSentences());
			this.armorStandWord.setCustomName(string);
		}
	}

}
