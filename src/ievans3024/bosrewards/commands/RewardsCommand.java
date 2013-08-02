package ievans3024.bosrewards.commands;

import ievans3024.bosrewards.main.BOSRewards;
import ievans3024.bosrewards.main.Messages;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public final class RewardsCommand implements CommandExecutor {
	
	private final BOSRewards plugin;
	private Map<String,SubCommand> commandMap = new HashMap<String,SubCommand>();
	private Map<String,SubCommand> aliasMap = new HashMap<String,SubCommand>();
	
	public final BOSRewards getPlugin() {
		return this.plugin;
	}
	
	public final boolean getSubCmdExists(String cmd) {
		if (this.commandMap.containsKey(cmd) || this.aliasMap.containsKey(cmd)) {
			return true;
		}
		return false;
	}
	
	public final String[] getSubCmdUsage(String cmd) {
		if (this.commandMap.containsKey(cmd)) {
			return this.commandMap.get(cmd).getUsage();
		} else if (this.aliasMap.containsKey(cmd)) {
			return this.aliasMap.get(cmd).getUsage();
		}
		return null;
	}
	
	public final String getSubCmdDescription(String cmd) {
		if (this.commandMap.containsKey(cmd)) {
			return this.commandMap.get(cmd).getDescription();
		} else if (this.aliasMap.containsKey(cmd)) {
			return this.aliasMap.get(cmd).getDescription();
		}
		return null;
	}
	
	public final String getSubCmdPermNode(String cmd) {
		if (this.commandMap.containsKey(cmd)) {
			return this.commandMap.get(cmd).getPermNode();
		} else if (this.aliasMap.containsKey(cmd)) {
			return this.aliasMap.get(cmd).getPermNode();
		}
		return null;
	}
	
	public final int getSubCmdCount() {
		return this.commandMap.size();
	}
	
	public final Set<String> getMapKeys() {
		return this.commandMap.keySet();
	}
	
	public RewardsCommand(BOSRewards plugin) {
		this.plugin = plugin;
		
		// register new subcommands here
		// don't forget to set description and usage strings!
		this.commandMap.put("add", new AddCommand(plugin,this,"add","BOSRewards.util.createreward",true,2));
		this.commandMap.put("balance", new BalanceCommand(plugin,this,"balance","BOSRewards.user.balance",false,0));
		this.commandMap.put("edit", new EditCommand(plugin,this,"edit","BOSRewards.util.editreward",true,2));
		this.commandMap.put("give", new GiveCommand(plugin,this,"give","BOSRewards.admin.givepoints",true,2));
		this.commandMap.put("help", new HelpCommand(plugin,this,"help","BOSRewards.user.help",true,0));
		this.commandMap.put("history", new HistoryCommand(plugin,this,"history","BOSRewards.user.history",false,0));
		this.commandMap.put("info", new InfoCommand(plugin,this,"info","BOSRewards.admin.info",true,1));
		this.commandMap.put("list", new ListCommand(plugin,this,"list","BOSRewards.user.list",true,0));
		this.commandMap.put("redeem", new RedeemCommand(plugin,this,"redeem","BOSRewards.user.redeem",false,1));
		this.commandMap.put("reload", new ReloadCommand(plugin,this,"reload","BOSRewards.util.reload",true,0));
		this.commandMap.put("remove", new RemoveCommand(plugin,this,"remove","BOSRewards.util.removereward",true,1));
		this.commandMap.put("set", new SetCommand(plugin,this,"set","BOSRewards.admin.setpoints",true,2));
		this.commandMap.put("take", new TakeCommand(plugin,this,"take","BOSRewards.admin.takepoints",true,2));
		
		// subcommand aliases
		this.aliasMap.put("bal", new BalanceCommand(plugin,this,"balance","BOSRewards.user.balance",false,0));
		this.aliasMap.put("get", new RedeemCommand(plugin,this,"redeem","BOSRewards.user.redeem",false,1));
		this.aliasMap.put("hist", new HistoryCommand(plugin,this,"history","BOSRewards.user.history",false,0));
		this.aliasMap.put("rm", new RemoveCommand(plugin,this,"remove","BOSRewards.util.removereward",true,1));
		this.aliasMap.put("sub", new TakeCommand(plugin,this,"take","BOSRewards.admin.takepoints",true,2));
		this.aliasMap.put("subtract", new TakeCommand(plugin,this,"take","BOSRewards.admin.takepoints",true,2));
		
	}
	
	private boolean doCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length > 0){
			SubCommand subcommand;
			if (this.commandMap.containsKey(args[0])) {
				subcommand = this.commandMap.get(args[0]);
			} else if (this.aliasMap.containsKey(args[0])) {
				subcommand = this.aliasMap.get(args[0]);
			} else {
				sender.sendMessage(Messages.NOT_A_SUBCMD);
				return false;
			}
			String[] subargs;
			if (args.length > 1) {
				subargs = Arrays.copyOfRange(args,1,args.length);
			} else {
				subargs = new String[0];
			}
			if (subcommand.getCanUseSubCommand(sender)) {
				if (subargs.length >= subcommand.getMinArgs()) {
					return (subcommand.run(sender,command,label,subargs));
				} else {
					sender.sendMessage(Messages.NOT_ENOUGH_ARGS);
					return true;
				}
			} else {
				Messages.sendNoPermsError(sender);
				return true;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof BlockCommandSender) {
			if (plugin.getConfig().isSet("command-block." + args[0])
					&& plugin.getConfig().getBoolean("command-block." + args[0])) {
				if (((BlockCommandSender) sender).getBlock().getType().compareTo(Material.COMMAND) == 0) {
					doCommand(sender,command,label,args);
				}
			}
			return true;
		} else if (sender.hasPermission(command.getPermission())) {
			return doCommand(sender,command,label,args);
		} else {
			Messages.sendNoPermsError(sender);
			return true;
		}		
	}
}