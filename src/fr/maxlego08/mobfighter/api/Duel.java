package fr.maxlego08.mobfighter.api;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

public interface Duel {

	/**
	 * 
	 * @return
	 */
	Arena getArena();

	/**
	 * 
	 * @return
	 */
	Fighter getFirstFighter();
	
	/**
	 * 
	 * @return
	 */
	Fighter getSecondFighter();
	
	/**
	 * @param second 
	 * 
	 */
	void start(int second);
	
	/**
	 * 
	 */
	void update();
	
	/**
	 * Check if duel is valid
	 * 
	 * @return true if is valid
	 */
	boolean isValid();
	
	/**
	 *
	 * @return
	 */
	boolean isFinish();

	/**
	 * 
	 * @param fighter
	 * @param zFighter
	 */
	void win(Fighter winner, Fighter looser);

	/**
	 * 
	 */
	void stop();

	/**
	 * 
	 * @param event
	 * @param cause
	 * @param damage
	 * @param entity
	 * @param entityType
	 */
	void onDamage(EntityDamageEvent event, DamageCause cause, double damage, Entity entity, EntityType entityType);

	/**
	 * 
	 * @param event
	 * @param entity
	 * @return
	 */
	void onDeath(EntityDeathEvent event, Entity entity);
	
	/**
	 * 
	 * @return
	 */
	int getSecond();
	
	/**
	 * 
	 * @return
	 */
	boolean isCooldown();

	/**
	 * 
	 * @param name
	 * @return
	 */
	boolean match(String name);

	/**
	 * 
	 * @param name
	 * @return
	 */
	Fighter getByName(String name);
	
}
