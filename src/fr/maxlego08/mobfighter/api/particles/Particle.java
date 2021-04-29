package fr.maxlego08.mobfighter.api.particles;

public interface Particle {

	/**
	 * 
	 * @return
	 */
	ParticleEffect getEffect();
	
	/**
	 * 
	 * @return
	 */
	ParticleType getType();

	/**
	 * 
	 * @return
	 */
	double getPercent();

}
