package fr.maxlego08.mobfighter;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import fr.maxlego08.mobfighter.api.Arena;
import fr.maxlego08.mobfighter.api.Duel;
import fr.maxlego08.mobfighter.api.Fighter;
import fr.maxlego08.mobfighter.api.configuration.ConfigurationManager;
import fr.maxlego08.mobfighter.api.path.PathManager;
import fr.maxlego08.mobfighter.zcore.utils.ZUtils;

public class ZDuel extends ZUtils implements Duel {

	private final Arena arena;
	private final Fighter firstFighter;
	private final Fighter secondFighter;
	private final PathManager manager;
	private final Random random = new Random();

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

			int value = random.nextInt(100);

			if (value > 50) {
				this.firstFighter.onTouch(this.secondFighter, this);
			} else {
				this.secondFighter.onTouch(this.firstFighter, this);
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

	@Override
	public void win(Fighter winner, Fighter looser) {

		System.out.println("Et nous avons un gagnant !");

		winner.remove(); // On retire le winner
		winner.win(); // On fait un truc au cas ou
		looser.loose(); // On fait un truc au cas ou

	}

}
