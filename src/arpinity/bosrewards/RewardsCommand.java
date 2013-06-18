package arpinity.bosrewards;

import java.util.Arrays;

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
    			this.plugin.reloadConfig();
    			this.plugin.getDataController().reloadRewardsTable();
    			this.plugin.getDataController().reloadUsersTable();
    			this.plugin.getLogger().info("BOSRewards Reloaded!");
    			return true;
    		} else if (args[0].equalsIgnoreCase("add")){
    			Reward newReward = new Reward();
    			newReward.id = args[1];
    			newReward.summary = ToolBox.arrayToString(Arrays.copyOfRange(args, 2, (args.length - 1))," ");
    			this.plugin.getDataController().writeReward(newReward);
    			Reward readReward = this.plugin.getDataController().getRewardById(newReward.id);
    			this.plugin.getLogger().info(
    					"Created Reward: "
    					+ readReward.id
    					+ " "
    					+ readReward.summary);
    		}
    		return true;
		}
    	return false;
	}
}
