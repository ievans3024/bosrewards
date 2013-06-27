package arpinity.bosrewards.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import arpinity.bosrewards.main.BOSRewards;
import arpinity.bosrewards.main.Messages;

public final class RewardsCommand implements CommandExecutor {
	
	private final BOSRewards plugin;
	private Map<String,SubCommand> commandMap = new HashMap<String,SubCommand>();
	
	public final BOSRewards getPlugin() {
		return this.plugin;
	}
	
	public RewardsCommand(BOSRewards plugin) {
		this.plugin = plugin;
		
		// register new subcommands here
		// don't forget to set description and usage strings!
		// create /rewards help <subcmd> that uses description/usage fields
		this.commandMap.put("reload", new ReloadCommand(plugin,"reload","BOSRewards.util.reload",true,0));
		this.commandMap.put("add", new AddCommand(plugin,"add","BOSRewards.util.createreward",true,2));
		this.commandMap.put("edit", new EditCommand(plugin,"edit","BOSRewards.util.editreward",true,2));
		this.commandMap.put("remove", new RemoveCommand(plugin,"remove","BOSRewards.util.removereward",true,1));
		this.commandMap.put("rm", this.commandMap.get("remove"));
		this.commandMap.put("list", new ListCommand(plugin,"list","BOSRewards.user.list",true,0));
		this.commandMap.put("give", new GiveCommand(plugin,"give","BOSRewards.admin.givepoints",true,2));
		this.commandMap.put("take", new TakeCommand(plugin,"take","BOSRewards.admin.takepoints",true,2));
		this.commandMap.put("subtract", this.commandMap.get("take"));
		this.commandMap.put("sub", this.commandMap.get("take"));
		this.commandMap.put("set", new SetCommand(plugin,"set","BOSRewards.admin.setpoints",true,2));
		this.commandMap.put("redeem", new RedeemCommand(plugin,"redeem","BOSRewards.user.redeem",false,1));
		this.commandMap.put("balance", new BalanceCommand(plugin,"balance","BOSRewards.user.balance",false,0));
		this.commandMap.put("history", new HistoryCommand(plugin,"history","BOSRewards.user.history",false,0));
		this.commandMap.put("hist", this.commandMap.get("history"));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender.hasPermission(command.getPermission())) {
			if (args.length > 0){
				if (this.commandMap.containsKey(args[0])){
					SubCommand subcommand = this.commandMap.get(args[0]);
					String[] subargs;
					if (args.length > 1) {
						subargs = Arrays.copyOfRange(args,1,args.length);
					} else {
						subargs = new String[0];
					}
					if (subcommand.getCanUseSubCommand(sender)){
						if (subargs.length >= subcommand.getMinArgs()) {
							return (subcommand.run(sender,command,label,subargs));
						}
						sender.sendMessage(Messages.NOT_ENOUGH_ARGS);
						return true;
					}
					Messages.sendNoPermsError(sender);
					return true;
				}
				sender.sendMessage(Messages.NOT_A_SUBCMD);
			}
			return false;
		}
		Messages.sendNoPermsError(sender);
		return true;
	}
}