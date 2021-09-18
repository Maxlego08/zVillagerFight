package fr.maxlego08.mobfighter;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import fr.maxlego08.mobfighter.api.Arena;
import fr.maxlego08.mobfighter.api.Duel;
import fr.maxlego08.mobfighter.api.Fighter;
import fr.maxlego08.mobfighter.api.addons.AddonManager;
import fr.maxlego08.mobfighter.api.bets.BetManager;
import fr.maxlego08.mobfighter.api.configuration.ConfigurationManager;
import fr.maxlego08.mobfighter.api.enums.Message;
import fr.maxlego08.mobfighter.api.event.events.DuelStopEvent;
import fr.maxlego08.mobfighter.api.event.events.DuelUpdateEvent;
import fr.maxlego08.mobfighter.api.event.events.DuelWinEvent;
import fr.maxlego08.mobfighter.api.path.PathManager;
import fr.maxlego08.mobfighter.save.Config;
import fr.maxlego08.mobfighter.zcore.logger.Logger;
import fr.maxlego08.mobfighter.zcore.logger.Logger.LogType;
import fr.maxlego08.mobfighter.zcore.utils.BossBarManager;
import fr.maxlego08.mobfighter.zcore.utils.ZUtils;
import fr.maxlego08.mobfighter.zcore.utils.builder.TimerBuilder;
import fr.maxlego08.mobfighter.zcore.utils.nms.NMSUtils;
import fr.maxlego08.mobfighter.zcore.utils.players.ActionBar;

public class ZDuel extends ZUtils implements Duel {

	private final BetManager betManager;
	private final Arena arena;
	private final Fighter firstFighter;
	private Fighter secondFighter;
	private final PathManager manager;
	private final Random random = new Random();
	private AtomicInteger startSecond;
	
	private double lastDistance;
	private long lastDistanceCount;

	/**
	 * @param arena
	 * @param entity2
	 * @param entity1
	 */
	public ZDuel(BetManager betManager, Arena arena, PathManager manager, ConfigurationManager configurationManager,
			EntityType entity1, EntityType entity2) {
		super();
		this.arena = arena;
		this.betManager = betManager;
		this.firstFighter = new ZFighter(entity1, configurationManager.getConfiguration(entity1));
		this.secondFighter = new ZFighter(entity2, configurationManager.getConfiguration(entity2));
		if (this.firstFighter.getName().equals(this.secondFighter.getName())) {

			// On va modifier leur nom
			while (this.firstFighter.getName().equals(this.secondFighter.getName()))
				this.secondFighter = new ZFighter(entity2, configurationManager.getConfiguration(entity2));

		}
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
	public void start(int second) {

		Location firstLocation = this.arena.getFirstLocation();
		Location secondLocation = this.arena.getSecondLocation();
		Location centerLocation = this.arena.getCenterLocation();

		if (firstLocation == null) {
			Logger.info("Impossible to create the duel, the first locations is null!", LogType.ERROR);
			return;
		}

		if (secondLocation == null) {
			Logger.info("Impossible to create the duel, the second locations is null!", LogType.ERROR);
			return;
		}

		if (centerLocation == null) {
			Logger.info("Impossible to create the duel, the center locations is null!", LogType.ERROR);
			return;
		}

		if (this.arena == null || !this.arena.isValid())
			return; // On cancel le début du combat

		this.startSecond = new AtomicInteger(second);

		scheduleFix(0, Config.enableDebugMode ? 100 : 1000, (task, canRun) -> {

			if (!canRun)
				return;

			if (this.arena.getDuel() != this) {
				task.cancel();
				return;
			}

			int current = this.startSecond.getAndDecrement();
			if (Config.displayMessageCooldown.contains(current)) {
				broadcast(Message.DUEL_COOLDOWN, "%first%", this.firstFighter.getName(), "%second%",
						this.secondFighter.getName(), "%timer%", TimerBuilder.getStringTime(current));
			}

			if (current <= 0) {

				task.cancel();

				this.firstFighter.spawn(firstLocation);
				this.secondFighter.spawn(secondLocation);

				this.manager.setPathGoal(firstFighter, this.secondFighter.getLocation());
				this.manager.setPathGoal(secondFighter, this.firstFighter.getLocation());

				this.startSecond = null;

				broadcast(Message.DUEL_START, "%first%", this.firstFighter.getName(), "%second%",
						this.secondFighter.getName());
			}

		});

	}

	@Override
	public void update() {

		if (this.isCooldown() || !this.isValid())
			return;

		this.updateActionBar();
		this.updateBossBar();

		// On verif si le duel est lancé

		double d1 = this.firstFighter.getConfiguration().getDistance();
		double d2 = this.secondFighter.getConfiguration().getDistance();
		double distance = Math.max(d1, d2);

		DuelUpdateEvent event = new DuelUpdateEvent(d1, d2, distance, this);
		event.callEvent();

		if (event.isCancelled())
			return;

		d1 = event.getD1();
		d2 = event.getD2();
		distance = event.getDistance();
				
		AddonManager addonManager = this.plugin.getAddonsManager();
		addonManager.duelTick(this);
		
		if (this.firstFighter.distance(this.secondFighter) <= distance || lastDistanceCount > Config.distanceSameExplosion) {

			this.lastDistanceCount = 0;
			
			this.firstFighter.push(this.secondFighter.getLocation());
			this.secondFighter.push(this.firstFighter.getLocation());

			int value = random.nextInt(100);

			if (value > 50) {
				this.firstFighter.onTouch(this.secondFighter, this);
			} else {
				this.secondFighter.onTouch(this.firstFighter, this);
			}

		}

		if (distance == this.lastDistance){
			lastDistanceCount++;
		}
		
		this.lastDistance = distance;
		
		Location centerLocation = this.arena.getCenterLocation();
		this.manager.setPathGoal(this.firstFighter, centerLocation);
		this.manager.setPathGoal(this.secondFighter, centerLocation);

		this.firstFighter.setTarget(this.secondFighter);
		this.secondFighter.setTarget(this.firstFighter);

	}

	private void updateActionBar() {
		if (Config.enableActionBar) {

			String message = Config.actionBarMessage;

			message = message.replace("%first_health%",
					format(this.firstFighter.getHealth(), Config.actionBarHealthFormat));
			message = message.replace("%second_health%",
					format(this.secondFighter.getHealth(), Config.actionBarHealthFormat));
			message = message.replace("%first_name%", this.firstFighter.getName());
			message = message.replace("%second_name%", this.secondFighter.getName());

			ActionBar.sendActionBarToAllPlayers(message);

		}
	}

	private void updateBossBar(){
		
		if (NMSUtils.isOneHand())
			return;
		
		String message = Config.actionBarMessage;

		message = message.replace("%first_health%",
				format(this.firstFighter.getHealth(), Config.actionBarHealthFormat));
		message = message.replace("%second_health%",
				format(this.secondFighter.getHealth(), Config.actionBarHealthFormat));
		message = message.replace("%first_name%", this.firstFighter.getName());
		message = message.replace("%second_name%", this.secondFighter.getName());
		
		BossBarManager.getInstance().start(message);
	}
	
	@Override
	public boolean isValid() {
		return this.firstFighter != null && this.secondFighter != null && this.firstFighter.isValid()
				&& this.secondFighter.isValid();
	}

	@Override
	public boolean isFinish() {
		return !this.isValid() && this.startSecond == null;
	}

	@Override
	public void win(Fighter winner, Fighter looser) {

		DuelWinEvent event = new DuelWinEvent(winner, looser, this);
		event.callEvent();

		AddonManager addonManager = this.plugin.getAddonsManager();
		addonManager.duelWin(this, winner, looser);
		
		broadcast(Message.DUEL_WIN, "%winner%", winner.getName(), "%looser%", looser.getName());

		winner.remove(); // On retire le winner
		winner.win(); // On fait un truc au cas ou
		looser.loose(); // On fait un truc au cas ou

		this.arena.setDuel(null);
		this.betManager.giveBets(this, winner);
		
		BossBarManager.getInstance().clearBossBar();

	}

	@Override
	public void stop() {

		DuelStopEvent event = new DuelStopEvent(this);
		event.callEvent();

		if (event.isCancelled())
			return;

		AddonManager addonManager = this.plugin.getAddonsManager();
		addonManager.duelStop(this);
		
		this.firstFighter.remove();
		this.secondFighter.remove();
		this.arena.setDuel(null);
		
		BossBarManager.getInstance().clearBossBar();
	}

	@Override
	public void onDamage(EntityDamageEvent event, DamageCause cause, double damage, Entity entity,
			EntityType entityType) {

		if (!isValid())
			return;

		if (this.firstFighter.isValid() && this.firstFighter.getEntity().equals(entity)) {
			event.setDamage(0);
		} else if (this.secondFighter.isValid() && this.secondFighter.getEntity().equals(entity)) {
			event.setDamage(0);
		}

	}

	@Override
	public void onDeath(EntityDeathEvent event, Entity entity) {

		if (!isValid())
			return;

		if (this.firstFighter.getEntity().equals(entity) || this.secondFighter.getEntity().equals(entity))
			event.getDrops().clear();
	}

	@Override
	public int getSecond() {
		return this.startSecond == null ? 0 : this.startSecond.get();
	}

	@Override
	public boolean isCooldown() {
		return (this.startSecond == null ? 0 : this.startSecond.get()) != 0;
	}

	@Override
	public boolean match(String name) {
		if (this.firstFighter != null && this.firstFighter.getName().equalsIgnoreCase(name))
			return true;
		if (this.secondFighter != null && this.secondFighter.getName().equalsIgnoreCase(name))
			return true;
		return false;
	}

	@Override
	public Fighter getByName(String name) {
		if (this.firstFighter != null && this.firstFighter.getName().equalsIgnoreCase(name))
			return this.firstFighter;
		if (this.secondFighter != null && this.secondFighter.getName().equalsIgnoreCase(name))
			return this.secondFighter;
		return null;
	}

}
