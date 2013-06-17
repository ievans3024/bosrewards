package arpinity.bosrewards;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class BOSRewards extends JavaPlugin {
	 
    @Override
    public void onEnable(){
    	this.saveDefaultConfig();
    	getLogger().info("BOSRewards Enabled!");
        // TODO Insert logic to be performed when the plugin is enabled
    }
 
    @Override
    public void onDisable() {
    	getLogger().info("BOSRewards Disabled!");
        // TODO Insert logic to be performed when the plugin is disabled
    }
    
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if (cmd.getName().equalsIgnoreCase("rewards")
    			|| cmd.getName().equalsIgnoreCase("rw")
    			|| cmd.getName().equalsIgnoreCase("bosrewards")){
    		if (args[0].equalsIgnoreCase("reload")){
    			getLogger().info("BOSRewards Reloaded!");
        		//TODO: create reloadConfig() code	
    		}
    	}
    	return true;
    }

}