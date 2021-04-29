package fr.maxlego08.mobfighter.particles;

import fr.maxlego08.mobfighter.api.particles.Particle;
import fr.maxlego08.mobfighter.api.particles.ParticleEffect;
import fr.maxlego08.mobfighter.api.particles.ParticleType;

public class ZParticle implements Particle {

	private final ParticleEffect effect;
	private final ParticleType type;
	private final double percent;

	/**
	 * @param effect
	 * @param type
	 * @param percent
	 */
	public ZParticle(ParticleEffect effect, ParticleType type, double percent) {
		super();
		this.effect = effect;
		this.type = type;
		this.percent = percent;
	}

	/**
	 * @return the effect
	 */
	public ParticleEffect getEffect() {
		return effect;
	}

	/**
	 * @return the type
	 */
	public ParticleType getType() {
		return type;
	}

	/**
	 * @return the percent
	 */
	public double getPercent() {
		return percent;
	}

}
