package arpinity.bosrewards;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RewardsCommand implements CommandExecutor {
	
	private BOSRewards plugin;
	private SubCommands subcommands;
	
	public BOSRewards getPluginInstance(){
		return this.plugin;
	}
	
	public RewardsCommand(BOSRewards plugin) {
		this.plugin = plugin;
		this.subcommands = new SubCommands(this.getPluginInstance());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {    		
		if (args.length > 0){
			if (this.subcommands.commandMap.keySet().contains(args[0])){
				return this.subcommands.commandMap.get(args[0]).run(args);
			}
			return false;
		}
		return false;
	}
}