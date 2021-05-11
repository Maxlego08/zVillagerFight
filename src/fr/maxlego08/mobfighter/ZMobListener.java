package fr.maxlego08.mobfighter;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.maxlego08.mobfighter.api.MobManager;
import fr.maxlego08.mobfighter.listener.ListenerAdapter;
import fr.maxlego08.mobfighter.zcore.utils.BossBarManager;

public class ZMobListener extends ListenerAdapter {

	private final ZMobPlugin plugin;

	/**
	 * @param plugin
	 */
	public ZMobListener(ZMobPlugin plugin) {
		super();
		this.plugin = plugin;
	}

	@Override
	protected void onEntityDeath(EntityDeathEvent event, Entity entity) {
		MobManager manager = plugin.getManager();
		manager.getDuels().forEach(e -> e.onDeath(event, entity));
	}
	
	@Override
	public void onDamage(EntityDamageEvent event, DamageCause cause, double damage, Entity entity,
			EntityType entityType) {
		MobManager manager = plugin.getManager();
		manager.getDuels().forEach(e -> e.onDamage(event, cause, damage, entity, entityType));
	}
	
	@Override
	protected void onQuit(PlayerQuitEvent event, Player player) {
		plugin.getBetManager().refundBet(player);
		BossBarManager.getInstance().removePlayer(player);
	}
	
}
