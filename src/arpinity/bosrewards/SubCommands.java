package arpinity.bosrewards;

import java.util.HashMap;

public class SubCommands {
	private BOSRewards plugin;
	
	public BOSRewards getPluginInstance(){
		return this.plugin;
	}
	
	public HashMap<String,SubCommand> commandMap = new HashMap<String,SubCommand>();
	
	public abstract class SubCommand {
		public abstract boolean run(String[] args);
	}
	
	public abstract class SubCommandWithReward {
		public abstract void run(String[] args, Reward reward);
	}
	
	private class reloadCommand extends SubCommand {
		private BOSRewards plugin;
		
		public reloadCommand(BOSRewards plugin){
			this.plugin = plugin;
		}
		
		public boolean run(String[] args){
			this.plugin.reloadConfig();
			this.plugin.getDataController().reloadRewardsTable();
			this.plugin.getLogger().info("BOSRewards Reloaded!");
			return true;
		}
	}
	
	private class addCommand extends SubCommand {
		private BOSRewards plugin;
		
		public addCommand(BOSRewards plugin){
			this.plugin = plugin;
		}
		
		public boolean run(String[] args){
			if (args.length > 3){
    			Reward newReward = new Reward();
    			newReward.setId(args[1]);
    			newReward.setSummary(ToolBox.arrayToString(args,2,args.length));
    			this.plugin.getDataController().writeReward(newReward);
    			return true;
			}
			return false;
		}
	}
	
	private class editCommand extends SubCommand {
		
		private BOSRewards plugin;
		private HashMap<String,SubCommandWithReward> editFlags = new HashMap<String,SubCommandWithReward>();
		
		private class costFlag extends SubCommandWithReward {
			public void run(String[] args, Reward reward) {
				reward.setCost(Integer.parseInt(args[3]));
			}
		}
		
		private class commandsFlag extends SubCommandWithReward {
			public void run(String[] args, Reward reward){
				reward.setFirstCommand(ToolBox.arrayToString(args, 3, args.length));
			}
		}
		
		private class commandsAddFlag extends SubCommandWithReward {
			public void run(String[] args, Reward reward){
				reward.addCommands(ToolBox.arrayToString(args, 3, args.length));
			}
		}
		
		private class summaryFlag extends SubCommandWithReward {
			public void run(String[] args, Reward reward){
				reward.setSummary(ToolBox.arrayToString(args, 3, args.length));
			}
		}
		
		public editCommand(BOSRewards plugin){
			this.plugin = plugin;
			this.editFlags.put("cost", new costFlag());
			this.editFlags.put("commands", new commandsFlag());
			this.editFlags.put("cmds", new commandsFlag());
			this.editFlags.put("+commands", new commandsAddFlag());
			this.editFlags.put("+cmds", new commandsAddFlag());
			this.editFlags.put("summary", new summaryFlag());
			
		}
		public boolean run(String[] args){
			if (args.length > 4
					&& this.editFlags.keySet().contains(args[2])){
				if (this.plugin.getDataController().getRewardExists(args[1])){
					Reward reward = this.plugin.getDataController().getRewardById(args[1]);
					this.editFlags.get(args[2]).run(args,reward);
					this.plugin.getDataController().writeReward(reward);
				}
				return true;
			}
			return false;
		}
	}
	
	private class removeCommand extends SubCommand{
		private BOSRewards plugin;
		
		public removeCommand(BOSRewards plugin){
			this.plugin = plugin;
		}
		
		public boolean run(String[] args){
			if (args.length > 2){
    			int i;
    			for (i=1;i<args.length;i++){
	    			if (this.plugin.getDataController().getRewardExists(args[i])){
		    			this.plugin.getDataController().removeRewardById(args[i]);
	    			}
    			}
    			return true;
			}
			return false;
		}
	}
	
	public SubCommands(BOSRewards plugin) {
		this.plugin = plugin;
		this.commandMap.put("reload", new reloadCommand(this.getPluginInstance()));
		this.commandMap.put("add", new addCommand(this.getPluginInstance()));
		this.commandMap.put("edit", new editCommand(this.getPluginInstance()));
		this.commandMap.put("remove", new removeCommand(this.getPluginInstance()));
		this.commandMap.put("rm", new removeCommand(this.getPluginInstance()));
	}
}
