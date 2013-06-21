package arpinity.bosrewards;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RewardsCommand implements CommandExecutor {
	
	public RewardsCommand(BOSRewards plugin) {
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {    		
		if (args.length > 0){
			if (SubCommands.commandMap.keySet().contains(args[0])){
				SubCommands.commandMap.get(args[0]).run(args);
				return true;
			}
			return false;
		}
		return false;
	}
}