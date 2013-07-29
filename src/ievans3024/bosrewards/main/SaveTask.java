package ievans3024.bosrewards.main;

import org.bukkit.scheduler.BukkitRunnable;

public final class SaveTask extends BukkitRunnable {
	
	private final BOSRewards plugin;
	
	public SaveTask(BOSRewards plugin) {
		this.plugin = plugin;
	}
	
	public void run() {
		plugin.getLogger().info("Saving user and rewards tables...");
		plugin.getDataController().writeRewards();
		plugin.getDataController().writeUsers();
		plugin.getLogger().info("User and rewards tables saved!");
	}
}
