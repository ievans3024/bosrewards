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
    			this.plugin.reloadConfig();
    			this.plugin.getDataController().reloadRewardsTable();
    			this.plugin.getDataController().reloadUsersTable();
    			this.plugin.getLogger().info("BOSRewards Reloaded!");
    			return true;
    		} else if (args[0].equalsIgnoreCase("add")){
    			Reward newReward = new Reward();
    			newReward.setId(args[1]);
    			newReward.setSummary(ToolBox.arrayToString(args,2,args.length));
    			this.plugin.getDataController().writeReward(newReward);
    			Reward readReward = this.plugin.getDataController().getRewardById(newReward.getId());
    			this.plugin.getLogger().info(
    					"Created Reward: "
    					+ readReward.getId()
    					+ " "
    					+ readReward.getSummary());
    		} else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("rm")){
    			if (this.plugin.getDataController().getRewardExists(args[1])){
	    			Reward reward = this.plugin.getDataController().getRewardById(args[1]);
	    			this.plugin.getLogger().info(
	    					"Deleting Reward: "
	    					+ reward.getId()
	    					+ " "
	    					+ reward.getSummary());
	    			this.plugin.getDataController().removeRewardById(args[1]);
    			}
    		} else if (args[0].equalsIgnoreCase("edit")){
    			if (this.plugin.getDataController().getRewardExists(args[1])){
    				Reward reward = this.plugin.getDataController().getRewardById(args[1]);
    				if (args[2].equalsIgnoreCase("cost")){
    					reward.setCost(Integer.parseInt(args[3]));
    				} else if (args[2].equalsIgnoreCase("summary")){
    					reward.setSummary(ToolBox.arrayToString(args,3,args.length));
    				}
    				this.plugin.getDataController().writeReward(reward);
    			}
    		}
    		return true;
		}
    	return false;
	}
}
