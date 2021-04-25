package fr.maxlego08.villager;

import org.bukkit.Location;

import fr.maxlego08.villager.api.Arena;
import fr.maxlego08.villager.api.Duel;
import fr.maxlego08.villager.api.Fighter;
import fr.maxlego08.villager.api.path.PathManager;
import fr.maxlego08.villager.zcore.utils.ZUtils;

public class ZDuel extends ZUtils implements Duel {

	private final Arena arena;
	private final Fighter firstFighter;
	private final Fighter secondFighter;
	private final PathManager manager;

	/**
	 * @param arena
	 */
	public ZDuel(Arena arena, PathManager manager) {
		super();
		this.arena = arena;
		this.firstFighter = new ZFighter();
		this.secondFighter = new ZFighter();
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

		this.manager.setPathGoal(firstFighter, centerLocation, 1.0);
		this.manager.setPathGoal(secondFighter, centerLocation, 1.0);

	}

	@Override
	public void update() {

		// On verif si le duel est lancé

		System.out.println(this.firstFighter.distance(this.secondFighter) + " la distance ?");

		if (this.firstFighter.distance(this.secondFighter) < 1.5D) {
			System.out.println("on est proche !");
			
			this.firstFighter.push(this.secondFighter.getLocation(), 1.0);
			this.secondFighter.push(this.firstFighter.getLocation(), 1.0);
			
			
		}
		Location centerLocation = this.arena.getCenterLocation();
		this.manager.setPathGoal(firstFighter, centerLocation, 1.0);
		this.manager.setPathGoal(secondFighter, centerLocation, 1.0);

	}


}
