package fr.maxlego08.mobfighter.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.EntityType;

import fr.maxlego08.mobfighter.api.configuration.MobConfiguration;
import fr.maxlego08.mobfighter.api.particles.Particle;
import fr.maxlego08.mobfighter.api.particles.ParticleEffect;
import fr.maxlego08.mobfighter.api.particles.ParticleType;
import fr.maxlego08.mobfighter.particles.ZParticle;
import fr.maxlego08.mobfighter.zcore.utils.ZUtils;

public class ZMobConfiguration extends ZUtils implements MobConfiguration {

	private final EntityType type;
	private double speed;
	private double distance;
	private double pushHeight;
	private double pushSpeed;
	private double minHealth;
	private double maxHealth;
	private double maxDamage;
	private double minDamage;
	private double criticalMultiplier;
	private String displayName;
	private double criticalPercent;
	private final List<Particle> particles;
	private final List<String> sentences;

	/**
	 * @param type
	 * @param speed
	 * @param distance
	 * @param pushHeight
	 * @param pushSpeed
	 * @param minHealth
	 * @param maxHealth
	 * @param maxDamage
	 * @param minDamage
	 * @param criticalMultiplier
	 * @param displayName
	 * @param criticalPercent
	 * @param particles
	 * @param sentences
	 */
	public ZMobConfiguration(EntityType type, double speed, double distance, double pushHeight, double pushSpeed,
			double minHealth, double maxHealth, double maxDamage, double minDamage, double criticalMultiplier,
			String displayName, double criticalPercent, List<Particle> particles, List<String> sentences) {
		super();
		this.type = type;
		this.speed = speed;
		this.distance = distance;
		this.pushHeight = pushHeight;
		this.pushSpeed = pushSpeed;
		this.minHealth = minHealth;
		this.maxHealth = maxHealth;
		this.maxDamage = maxDamage;
		this.minDamage = minDamage;
		this.criticalMultiplier = criticalMultiplier;
		this.displayName = displayName;
		this.criticalPercent = criticalPercent;
		this.particles = particles;
		this.sentences = sentences;
	}

	/**
	 * @param type
	 * @param speed
	 * @param distance
	 * @param pushHeight
	 * @param pushSpeed
	 * @param minHealth
	 * @param maxHealth
	 * @param maxDamage
	 * @param minDamage
	 * @param criticalMultiplier
	 * @param displayName
	 * @param criticalPercent
	 * @param particles
	 */
	public ZMobConfiguration(EntityType type, double speed, double distance, double pushHeight, double pushSpeed,
			double minHealth, double maxHealth, double maxDamage, double minDamage, double criticalMultiplier,
			String displayName, double criticalPercent) {
		super();
		this.type = type;
		this.speed = speed;
		this.distance = distance;
		this.pushHeight = pushHeight;
		this.pushSpeed = pushSpeed;
		this.minHealth = minHealth;
		this.maxHealth = maxHealth;
		this.maxDamage = maxDamage;
		this.minDamage = minDamage;
		this.criticalMultiplier = criticalMultiplier;
		this.displayName = displayName;
		this.criticalPercent = criticalPercent;

		this.sentences = new ArrayList<>();
		this.sentences.add("§fNot §cn§fice!");
		this.sentences.add("Go§beat §fyour dead");
		this.sentences.add("§fR§ca§fn§ad§fo§cm");

		this.particles = new ArrayList<Particle>();
		this.particles.add(new ZParticle(ParticleEffect.SPELL_INSTANT, ParticleType.PUSH, 40));
		this.particles.add(new ZParticle(ParticleEffect.SPELL_INSTANT, ParticleType.FIRE, 30));
		this.particles.add(new ZParticle(ParticleEffect.VILLAGER_HAPPY, ParticleType.FIRE, 10));
		this.particles.add(new ZParticle(ParticleEffect.VILLAGER_HAPPY, ParticleType.FIRE, 10));
		this.particles.add(new ZParticle(ParticleEffect.FLAME, ParticleType.PUSH, 5));
		this.particles.add(new ZParticle(ParticleEffect.LAVA, ParticleType.FIRE, 5));
		this.particles.add(new ZParticle(ParticleEffect.CLOUD, ParticleType.FIRE, 5));
	}

	/**
	 * @return the type
	 */
	public EntityType getType() {
		return type;
	}

	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @return the pushHeight
	 */
	public double getPushHeight() {
		return pushHeight;
	}

	/**
	 * @return the pushSpeed
	 */
	public double getPushSpeed() {
		return pushSpeed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	/**
	 * @param distance
	 *            the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * @param pushHeight
	 *            the pushHeight to set
	 */
	public void setPushHeight(double pushHeight) {
		this.pushHeight = pushHeight;
	}

	/**
	 * @param pushSpeed
	 *            the pushSpeed to set
	 */
	public void setPushSpeed(double pushSpeed) {
		this.pushSpeed = pushSpeed;
	}

	@Override
	public double getMinHealth() {
		return this.minHealth;
	}

	@Override
	public double getMaxHealth() {
		return this.maxHealth;
	}

	/**
	 * @param minHealth
	 *            the minHealth to set
	 */
	public void setMinHealth(double minHealth) {
		this.minHealth = minHealth;
	}

	/**
	 * @param maxHealth
	 *            the maxHealth to set
	 */
	public void setMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
	}

	/**
	 * @return the maxDamage
	 */
	public double getMaxDamage() {
		return maxDamage;
	}

	/**
	 * @return the minDamage
	 */
	public double getMinDamage() {
		return minDamage;
	}

	/**
	 * @return the criticalMultiplier
	 */
	public double getCriticalMultiplier() {
		return criticalMultiplier;
	}

	/**
	 * @param maxDamage
	 *            the maxDamage to set
	 */
	public void setMaxDamage(double maxDamage) {
		this.maxDamage = maxDamage;
	}

	/**
	 * @param minDamage
	 *            the minDamage to set
	 */
	public void setMinDamage(double minDamage) {
		this.minDamage = minDamage;
	}

	/**
	 * @param criticalMultiplier
	 *            the criticalMultiplier to set
	 */
	public void setCriticalMultiplier(double criticalMultiplier) {
		this.criticalMultiplier = criticalMultiplier;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @return the criticalPercent
	 */
	public double getCriticalPercent() {
		return criticalPercent;
	}

	/**
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @param criticalPercent
	 *            the criticalPercent to set
	 */
	public void setCriticalPercent(double criticalPercent) {
		this.criticalPercent = criticalPercent;
	}

	@Override
	public List<Particle> getParticle() {
		return this.particles;
	}

	@Override
	public List<String> getSentences() {
		return this.sentences;
	}

	@Override
	public Particle getRandomParticle() {
		Random random = new Random();
		int percent = random.nextInt(100);
		Particle particle = randomElement(this.particles);
		if (particle.getPercent() >= percent)
			return particle;
		return this.getRandomParticle();
	}

}
