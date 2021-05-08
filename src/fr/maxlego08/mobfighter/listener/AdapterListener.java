package fr.maxlego08.mobfighter.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.maxlego08.mobfighter.ZMobPlugin;
import fr.maxlego08.mobfighter.zcore.utils.ZUtils;

public class AdapterListener extends ZUtils implements Listener {

	private final ZMobPlugin template;

	public AdapterListener(ZMobPlugin template) {
		this.template = template;
	}

	@EventHandler
	public void onConnect(PlayerJoinEvent event) {
		template.getListenerAdapters().forEach(adapter -> adapter.onConnect(event, event.getPlayer()));
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		template.getListenerAdapters().forEach(adapter -> adapter.onQuit(event, event.getPlayer()));
	}

	/*@EventHandler
	public void onMove(PlayerMoveEvent event) {
		template.getListenerAdapters().forEach(adapter -> adapter.onMove(event, event.getPlayer()));
		if (event.getFrom().getBlockX() >> 1 == event.getTo().getBlockX() >> 1
				&& event.getFrom().getBlockZ() >> 1 == event.getTo().getBlockZ() >> 1
				&& event.getFrom().getWorld() == event.getTo().getWorld())
			return;
		template.getListenerAdapters().forEach(adapter -> adapter.onPlayerWalk(event, event.getPlayer(), 1));
	}*/

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		template.getListenerAdapters()
				.forEach(adapter -> adapter.onInventoryClick(event, (Player) event.getWhoClicked()));
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		template.getListenerAdapters().forEach(adapter -> adapter.onEntityDeath(event, event.getEntity()));
	}

	@EventHandler
	public void onDrag(InventoryDragEvent event) {
		template.getListenerAdapters()
				.forEach(adapter -> adapter.onInventoryDrag(event, (Player) event.getWhoClicked()));
	}

	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		template.getListenerAdapters().forEach(adapter -> adapter.onInventoryClose(event, (Player) event.getPlayer()));
	}
	
	@EventHandler
	public void onClose(EntityDamageEvent event) {
		template.getListenerAdapters().forEach(adapter -> adapter.onDamage(event, event.getCause(), event.getDamage(), event.getEntity(), event.getEntityType()));
	}

}
