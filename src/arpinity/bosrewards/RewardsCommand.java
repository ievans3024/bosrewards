package arpinity.bosrewards;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RewardsCommand implements CommandExecutor {
	
	private BOSRewards plugin;
	 
	public RewardsCommand(BOSRewards plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {    		
		if (args.length > 0){
    		if (args[0].equalsIgnoreCase("reload")){
    			plugin.reloadConfig();
    			plugin.getLogger().info("BOSRewards Reloaded!");
    			return true;
    		}
    		return true;
		}
    	return false;
	}
}
