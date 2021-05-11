package fr.maxlego08.mobfighter.zcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import fr.maxlego08.mobfighter.save.Config;

public class BossBarManager {

	private BossBar bossBar;

	/**
	 * static Singleton instance.
	 */
	private static volatile BossBarManager instance;

	/**
	 * Private constructor for singleton.
	 */
	private BossBarManager() {
	}

	/**
	 * Return a singleton instance of BossBarManager.
	 */
	public static BossBarManager getInstance() {
		// Double lock for thread safety.
		if (instance == null) {
			synchronized (BossBarManager.class) {
				if (instance == null) {
					instance = new BossBarManager();
				}
			}
		}
		return instance;
	}

	public void removePlayer(Player player) {
		if (Config.enableBossBar)
			if (bossBar != null)
				bossBar.removePlayer(player);
	}

	public void clearBossBar() {
		if (Config.enableBossBar)
			if (bossBar != null)
				bossBar.removeAll();
	}

	public void start(String message) {
		if (Config.enableBossBar)
			if (bossBar == null) {

				bossBar = Bukkit.createBossBar(message, BarColor.RED, BarStyle.SOLID);
				Bukkit.getOnlinePlayers().forEach(player -> bossBar.addPlayer(player));

			} else {

				bossBar.setTitle(message);
				Bukkit.getOnlinePlayers().forEach(e -> {
					if (!bossBar.getPlayers().contains(e))
						bossBar.addPlayer(e);
				});

			}

	}

}
