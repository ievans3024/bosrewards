package arpinity.bosrewards;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RewardsCommand implements CommandExecutor {
	
	private BOSRewards plugin;
	private Map<String,SubCommand> commandMap = new HashMap<String,SubCommand>();
	
	public BOSRewards getPlugin() {
		return this.plugin;
	}
	
	public RewardsCommand(BOSRewards plugin) {
		this.plugin = plugin;
		
		// register new subcommands here
		// don't forget to set description and usage strings!
		this.commandMap.put("reload", new ReloadCommand(this.getPlugin(),"reload","BOSRewards.util.reload",true,0));
		this.commandMap.put("add", new AddCommand(this.getPlugin(),"add","BOSRewards.util.createreward",true,2));
		this.commandMap.put("edit", new EditCommand(this.getPlugin(),"edit","BOSRewards.util.editreward",true,2));
		this.commandMap.put("remove", new RemoveCommand(this.getPlugin(),"remove","BOSRewards.util.removereward",true,1));
		this.commandMap.put("rm", this.commandMap.get("remove"));
		this.commandMap.put("list", new ListCommand(this.getPlugin(),"list","BOSRewards.user.*",true,0));
		this.commandMap.put("give", new GiveCommand(this.getPlugin(),"give","BOSRewards.admin.givepoints",true,2));
		this.commandMap.put("take", new TakeCommand(this.getPlugin(),"take","BOSRewards.admin.takepoints",true,2));
		this.commandMap.put("set", new SetCommand(this.getPlugin(),"set","BOSRewards.admin.setpoints",true,2));
		this.commandMap.put("redeem", new RedeemCommand(this.getPlugin(),"redeem","BOSRewards.user.*",false,1));
		this.commandMap.put("balance", new BalanceCommand(this.getPlugin(),"balance","BOSRewards.user.*",false,0));
		this.commandMap.put("history", new HistoryCommand(this.getPlugin(),"history","BOSRewards.user.*",false,0));
		this.commandMap.put("hist", this.commandMap.get("history"));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {    		
		if (args.length > 0){
			if (this.commandMap.keySet().contains(args[0])){
				return (this.commandMap.get(args[0])).run(sender,command,label,args);
			}
			return false;
		}
		return false;
	}
}