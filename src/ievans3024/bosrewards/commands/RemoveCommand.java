package ievans3024.bosrewards.commands;

import ievans3024.bosrewards.main.BOSRewards;
import ievans3024.bosrewards.main.Messages;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;



public final class RemoveCommand extends SubCommand {

	public RemoveCommand(BOSRewards plugin, RewardsCommand parent, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, parent, name, permission, allowConsole, minArgs);
		String[] usage = {
				Messages.COLOR_SUCCESS + "/rewards remove [id] [another id] [...]" + 
				Messages.COLOR_INFO + " - Removes one or more rewards by a list of IDs."
		};
		this.setDescription("Removes rewards by their IDs.")
		.setUsage(usage);
	}
	
	public boolean run(CommandSender sender, Command command, String label, String[] args) {
		String removed = "";
		for (int i=0;i<args.length;i++){
			if (plugin.getDataController().getRewardExists(args[i])){
				removed += args[i] + " ";
    			plugin.getDataController().removeRewardById(args[i]);
			}
		}
		if (!removed.equalsIgnoreCase("")) {
			String message = Messages.COLOR_INFO
					+ "Removed "
					+ (((args.length - 1) == 1) ? "Reward" : "Rewards") + ": "
					+ removed;
			sender.sendMessage(message);
		}
		return true;
	}

}
