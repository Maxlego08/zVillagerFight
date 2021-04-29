package fr.maxlego08.mobfighter.zcore.utils.particles;

import org.bukkit.Location;

import fr.maxlego08.mobfighter.api.particles.ParticleEffect;
import xyz.xenondevs.particle.ParticleBuilder;

public abstract class ParticleUtils {

	public ParticleUtils() {
		// TODO Auto-generated constructor stub
	}

	protected void push(Location location, ParticleEffect particleEffect) {
		for (float i = -0.2f; i < 0.3; i += 0.1) {
			for (float j = -0.1f; j < 0.3; j += 0.1) {
				for (float k = -0.2f; k < 0.3; k += 0.1) {
					ParticleBuilder builder = new ParticleBuilder(particleEffect, location);
					builder.setAmount(1);
					builder.setOffsetX(i);
					builder.setOffsetY(j);
					builder.setOffsetZ(k);
					builder.setSpeed(0.0f);
					builder.display(
							p -> p.getWorld().equals(location.getWorld()) && p.getLocation().distance(location) < 64);
				}
			}
		}
	}

	protected void fire(Location location, ParticleEffect particleEffect) {
		for (int a = 0; a != 180; a += 2) {
			Location location2 = location.clone();
			location2.setPitch(a);
			ParticleBuilder builder = new ParticleBuilder(particleEffect, location2);
			builder.setSpeed(0.1f);
			builder.setAmount(1);
			builder.display(p -> p.getWorld().equals(location.getWorld()) && p.getLocation().distance(location) < 64);
		}
	}

}
