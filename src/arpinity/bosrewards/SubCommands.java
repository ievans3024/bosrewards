package arpinity.bosrewards;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SubCommands {
	private BOSRewards plugin;
	
	public BOSRewards getPluginInstance(){
		return this.plugin;
	}
	
	public HashMap<String,SubCommand> commandMap = new HashMap<String,SubCommand>();
	
	public abstract class SubCommand {
		public abstract boolean run(CommandSender sender, String[] args);
	}
	
	public abstract class SubCommandWithReward {
		public abstract void run(String[] args, Reward reward);
	}
	
	private class reloadCommand extends SubCommand {
		private BOSRewards plugin;
		
		public reloadCommand(BOSRewards plugin){
			this.plugin = plugin;
		}
		
		public boolean run(CommandSender sender, String[] args){
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
		
		public boolean run(CommandSender sender, String[] args){
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
		public boolean run(CommandSender sender, String[] args){
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
	
	private class removeCommand extends SubCommand {
		private BOSRewards plugin;
		
		public removeCommand(BOSRewards plugin){
			this.plugin = plugin;
		}
		
		public boolean run(CommandSender sender, String[] args){
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
	
	private class listCommand extends SubCommand {
		private BOSRewards plugin;
		
		public listCommand(BOSRewards plugin){
			this.plugin = plugin;
		}
		
		public boolean run(CommandSender sender, String[] args){
			int pageNumber = 1;
			List<Reward> rewardsList = this.plugin.getDataController().getRewards();
			if (!rewardsList.isEmpty()){
				int maximumPages = rewardsList.size() / 5;
				if (rewardsList.size() % 5 != 0){
					maximumPages += 1;
				}
				if (args.length > 2){
					pageNumber = Integer.parseInt(args[2]);
					if (pageNumber > maximumPages){
						this.plugin.getLogger().info("There are not that many pages in the catalogue. There are " + maximumPages + ((maximumPages == 1) ? " page." : " pages.") );
					}
				}
				int listStart = (pageNumber * 5) - 5;
				int i = 0;
				this.plugin.getLogger().info("Rewards Catalogue - Page " + pageNumber + "/" + maximumPages);
				this.plugin.getLogger().info("-----------------------------");
				this.plugin.getLogger().info("ID    Summary    Cost");
				while (i < 5){
					Reward reward = rewardsList.get(listStart + i);
					this.plugin.getLogger().info(reward.getId() + "    " + reward.getSummary() + "    " + reward.getCost());
					i++;
					if (listStart + i > rewardsList.size() - 1){
						i = 5;
					}
				}
			}
			return true;
		}
	}
	
	private class giveCommand extends SubCommand {
		private BOSRewards plugin;
		
		public giveCommand(BOSRewards plugin){
			this.plugin = plugin;
		}
		
		public boolean run(CommandSender sender, String[] args){
			if (args.length > 3){
				if (this.plugin.getDataController().getUserExists(args[1])){
					User user = this.plugin.getDataController().getUserByName(args[1]);
					user.addPoints(Integer.parseInt(args[2]));
					this.plugin.getDataController().writeUser(user);
				}
			}
			return true;
		}
	}
	
	private class takeCommand extends SubCommand {
		private BOSRewards plugin;
		
		public takeCommand(BOSRewards plugin){
			this.plugin = plugin;
		}
		
		public boolean run(CommandSender sender, String[] args){
			if (args.length > 3){
				if (this.plugin.getDataController().getUserExists(args[1])){
					User user = this.plugin.getDataController().getUserByName(args[1]);
					user.subtractPoints(Integer.parseInt(args[2]));
					this.plugin.getDataController().writeUser(user);
				}
			}
			return true;
		}
	}
	
	private class setPointsCommand extends SubCommand {
		private BOSRewards plugin;
		
		public setPointsCommand(BOSRewards plugin){
			this.plugin = plugin;
		}
		
		public boolean run(CommandSender sender, String[] args){
			if (args.length > 3){
				if (this.plugin.getDataController().getUserExists(args[1])){
					User user = this.plugin.getDataController().getUserByName(args[1]);
					user.setPoints(Integer.parseInt(args[2]));
					this.plugin.getDataController().writeUser(user);
				}
			}
			return true;
		}
	}
	
	private class redeemCommand extends SubCommand {
		private BOSRewards plugin;
		
		public redeemCommand(BOSRewards plugin){
			this.plugin = plugin;
		}
		
		public boolean run(CommandSender sender, String[] args){
			if (args.length > 1){
				User user = this.plugin.getDataController().getUserByName(sender.getName());
				if (this.plugin.getDataController().getRewardExists(args[1])){
					Reward reward = this.plugin.getDataController().getRewardById(args[1]);
					if (reward.getCost() >= 0){
						if (user.getPoints() >= reward.getCost()){
							Calendar calendar = Calendar.getInstance();
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
							String date = dateFormat.format(calendar.getTime());
							String recieptString = date + " " + reward.getSummary() + " " + reward.getCost();
							user.subtractPoints(reward.getCost());
							user.addReciept(recieptString);
							this.plugin.getLogger().info(user.getName() + recieptString);
							List<String> commands = reward.getCommands();
							while (commands.iterator().hasNext()){
								//execute commands.iterator().next() as console command sender
							}
						}
					}
				}
			}
			return true;
		}
	}
	
	private class balanceCommand extends SubCommand {
		private BOSRewards plugin;
		
		public balanceCommand(BOSRewards plugin){
			this.plugin = plugin;
		}
		
		public boolean run(CommandSender sender, String[] args){
			User user;
			String sentencePrefix;
			String pointSingular = this.plugin.getConfig().getString("point-name");
			String pointPlural = this.plugin.getConfig().getString("point-name-plural");
			if (args.length > 1){
				user = this.plugin.getDataController().getUserByName(args[1]);
				sentencePrefix = user.getName() + " has ";
			} else {
				if (sender instanceof Player){
					user = this.plugin.getDataController().getUserByName(sender.getName());
					sentencePrefix = "You have ";
				} else {
					this.plugin.getLogger().info("Console never gets points. Console will never be rewarded.");
					return true;
				}
			}
			if (sender instanceof Player){
				sender.sendMessage(sentencePrefix
						+ user.getPoints()
						+ ((user.getPoints() == 1) ? pointSingular : pointPlural));
			} else {
				this.plugin.getLogger().info(sentencePrefix
						+ user.getPoints()
						+ ((user.getPoints() == 1) ? pointSingular : pointPlural));
			}
			return true;
		}
	}
	
	private class historyCommand extends SubCommand {
		private BOSRewards plugin;
		
		public historyCommand(BOSRewards plugin){
			this.plugin = plugin;
		}
		
		public boolean run(CommandSender sender, String[] args){
			User user;
			String header;
			int pageNumber = 1;
			if (args.length > 1){
				if (this.plugin.getDataController().getUserExists(args[1])){
					user = this.plugin.getDataController().getUserByName(args[1]);
					header = "Redemption history for " + this.plugin.getDataController().getUserByName(args[1]).getName();
					if (args.length > 2){
						pageNumber = Integer.parseInt(args[2]);
					}
				} else if (sender instanceof Player){
					user = this.plugin.getDataController().getUserByName(sender.getName());
					header = "Your redemption history: ";
					pageNumber = Integer.parseInt(args[1]);
				} else {
					this.plugin.getLogger().info("Corrupt to its core, Console has never and will never have a redemption history. Try specifying a player.");
					return true;
				}
			} else {
				if (sender instanceof Player){
					user = this.plugin.getDataController().getUserByName(sender.getName());
					header = "Your redemption history: ";
				} else {
					this.plugin.getLogger().info("Corrupt to its core, Console has never and will never have a redemption history. Try specifying a player.");
					return true;
				}
			}
			List<String> userReciepts = user.getReciepts();
			if (!userReciepts.isEmpty()){
				int maximumPages = userReciepts.size() / 5;
				if (maximumPages % 5 != 0){
					maximumPages += 1;
				}
				if (pageNumber > maximumPages){
					String message = "There are not that many pages. There are " + maximumPages + ((maximumPages == 1) ? " page." : " pages.");
					if (sender instanceof Player){
						sender.sendMessage(message);
					} else if (sender instanceof ConsoleCommandSender){
						this.plugin.getLogger().info(message);						
					}
				}				
				int i = 0;
				int listStart = (pageNumber * 5) - 5;
				if (sender instanceof Player){
					sender.sendMessage(header);
				} else {
					this.plugin.getLogger().info(header);
				}
				while (i < 5){
					if (sender instanceof Player){
						sender.sendMessage(userReciepts.get(listStart + i));
					} else if (sender instanceof ConsoleCommandSender){
						this.plugin.getLogger().info(userReciepts.get(listStart + i));
					}
					i++;
					if ((listStart + i) > (userReciepts.size() - 1)){
						i = 5;
					}
				}
			}
			return true;			
		}
	}
	
	public SubCommands(BOSRewards plugin) {
		this.plugin = plugin;
		this.commandMap.put("reload", new reloadCommand(this.getPluginInstance()));
		this.commandMap.put("add", new addCommand(this.getPluginInstance()));
		this.commandMap.put("edit", new editCommand(this.getPluginInstance()));
		this.commandMap.put("remove", new removeCommand(this.getPluginInstance()));
		this.commandMap.put("rm", new removeCommand(this.getPluginInstance()));
		this.commandMap.put("list", new listCommand(this.getPluginInstance()));
		this.commandMap.put("give", new giveCommand(this.getPluginInstance()));
		this.commandMap.put("take", new takeCommand(this.getPluginInstance()));
		this.commandMap.put("set", new setPointsCommand(this.getPluginInstance()));
		this.commandMap.put("redeem", new redeemCommand(this.getPluginInstance()));
		this.commandMap.put("balance", new balanceCommand(this.getPluginInstance()));
		this.commandMap.put("history", new historyCommand(this.getPluginInstance()));
		this.commandMap.put("hist", new historyCommand(this.getPluginInstance()));
	}
}
