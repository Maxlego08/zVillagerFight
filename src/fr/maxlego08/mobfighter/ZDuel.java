package fr.maxlego08.mobfighter;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import fr.maxlego08.mobfighter.api.Arena;
import fr.maxlego08.mobfighter.api.Duel;
import fr.maxlego08.mobfighter.api.Fighter;
import fr.maxlego08.mobfighter.api.configuration.ConfigurationManager;
import fr.maxlego08.mobfighter.api.path.PathManager;
import fr.maxlego08.mobfighter.zcore.utils.ZUtils;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class ZDuel extends ZUtils implements Duel {

	private final Arena arena;
	private final Fighter firstFighter;
	private final Fighter secondFighter;
	private final PathManager manager;

	/**
	 * @param arena
	 * @param entity2
	 * @param entity1
	 */
	public ZDuel(Arena arena, PathManager manager, ConfigurationManager configurationManager, EntityType entity1,
			EntityType entity2) {
		super();
		this.arena = arena;
		this.firstFighter = new ZFighter(entity1, configurationManager.getConfiguration(entity1));
		this.secondFighter = new ZFighter(entity2, configurationManager.getConfiguration(entity2));
		this.manager = manager;
	}

	@Override
	public Arena getArena() {
		return this.arena;
	}

	@Override
	public Fighter getFirstFighter() {
		return this.firstFighter;
	}

	@Override
	public Fighter getSecondFighter() {
		return this.secondFighter;
	}

	@Override
	public void start() {

		if (this.arena == null || !this.arena.isValid())
			return; // On cancel le début du combat

		Location firstLocation = this.arena.getFirstLocation();
		Location secondLocation = this.arena.getSecondLocation();
		Location centerLocation = this.arena.getCenterLocation();

		this.firstFighter.spawn(firstLocation);
		this.secondFighter.spawn(secondLocation);

		this.manager.setPathGoal(firstFighter, centerLocation);
		this.manager.setPathGoal(secondFighter, centerLocation);

	}

	@Override
	public void update() {

		// On verif si le duel est lancé

		double d1 = this.firstFighter.getConfiguration().getDistance();
		double d2 = this.secondFighter.getConfiguration().getDistance();
		double distannce = Math.max(d1, d2);

		if (this.firstFighter.distance(this.secondFighter) <= distannce) {

			this.firstFighter.push(this.secondFighter.getLocation());
			this.secondFighter.push(this.firstFighter.getLocation());

			Location location = this.firstFighter.getEntity().getEyeLocation();

			for (float i = -0.2f; i < 0.3; i += 0.1) {
				for (float j = -0.1f; j < 0.3; j += 0.1) {
					for (float k = -0.2f; k < 0.3; k += 0.1) {

						final float ii = i;
						final float jj = j;
						final float kk = k;
						ParticleBuilder builder = new ParticleBuilder(ParticleEffect.SPELL_INSTANT, location);
						builder.setAmount(1);
						builder.setOffsetX(ii);
						builder.setOffsetY(jj);
						builder.setOffsetZ(kk);
						builder.setSpeed(0.0f);
						builder.display(p -> p.getWorld().equals(location.getWorld())
								&& p.getLocation().distance(location) < 64);
					}
				}
			}

		}

		Location centerLocation = this.arena.getCenterLocation();
		this.manager.setPathGoal(this.firstFighter, centerLocation);
		this.manager.setPathGoal(this.secondFighter, centerLocation);
		
		this.firstFighter.setTarget(this.secondFighter);
		this.secondFighter.setTarget(this.firstFighter);

	}

	@Override
	public boolean isValid() {
		return this.firstFighter != null && this.secondFighter != null && this.firstFighter.isValid()
				&& this.secondFighter.isValid();
	}

	@Override
	public boolean isFinish() {
		return !this.isValid();
	}

}
