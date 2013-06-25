package arpinity.bosrewards.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import arpinity.bosrewards.main.BOSRewards;


public final class RemoveCommand extends SubCommand {

	public RemoveCommand(BOSRewards plugin, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, name, permission, allowConsole, minArgs);
	}
	
	public boolean run(CommandSender sender, Command command, String label, String[] args) {
		if (args.length > 2){
			int i;
			for (i=1;i<args.length;i++){
    			if (this.getPlugin().getDataController().getRewardExists(args[i])){
	    			this.getPlugin().getDataController().removeRewardById(args[i]);
    			}
			}
			return true;
		}
		return false;
	}

}
