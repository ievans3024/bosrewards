package arpinity.bosrewards.main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public final class LoginListener implements Listener {
	
	private final BOSRewards plugin;
	
	public BOSRewards getPlugin(){
		return this.plugin;
	}
	
	public LoginListener(BOSRewards plugin){
		this.plugin = plugin;
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onPlayerLogin(PlayerLoginEvent event){
		String username = event.getPlayer().getName();
		if (!this.getPlugin().getDataController().getUserExists(username)){
			User user = new User()
			.setName(username)
			.setPoints(this.getPlugin().getConfig().getInt("user-default-points"));
			this.getPlugin().getDataController().writeUser(user);
			this.getPlugin().getLogger().info("New user added to BOSRewards user database: " + username);
		}
	}

}
