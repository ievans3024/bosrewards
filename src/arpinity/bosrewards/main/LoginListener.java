package arpinity.bosrewards.main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public final class LoginListener implements Listener {
	
	private final BOSRewards plugin;
	
	public LoginListener(BOSRewards plugin){
		this.plugin = plugin;
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onPlayerLogin(PlayerLoginEvent event){
		String username = event.getPlayer().getName();
		boolean userExists = plugin.getDataController().getUserExists(username);
		String lastOnline = plugin.getDateFormat().format(plugin.getCalendar().getTime());
		if (!userExists){
			User user = new User(username)
			.setPoints(plugin.getConfig().getInt("user-default-points"))
			.setLastOnline(lastOnline);
			plugin.getDataController().writeUser(user);
			plugin.getLogger().info("New user added to BOSRewards user database: " + username);
		} else if (userExists) {
			User user = plugin.getDataController().getUserByName(username).setLastOnline(lastOnline);
			plugin.getDataController().writeUser(user);
			
		}
	}

}
