package arpinity.bosrewards.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import arpinity.bosrewards.main.BOSRewards;
import arpinity.bosrewards.main.Messages;
import arpinity.bosrewards.main.ToolBox;


public final class RemoveCommand extends SubCommand {

	public RemoveCommand(BOSRewards plugin, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, name, permission, allowConsole, minArgs);
	}
	
	public boolean run(CommandSender sender, Command command, String label, String[] args) {
		for (int i=1;i<args.length;i++){
			if (this.getPlugin().getDataController().getRewardExists(args[i])){
    			this.getPlugin().getDataController().removeRewardById(args[i]);
			}
		}
		String message = Messages.COLOR_INFO
				+ "Removed "
				+ (((args.length - 1) == 1) ? "Reward" : "Rewards") + ": "
				+ ToolBox.arrayToString(args, 0, (args.length - 1));
		if (sender instanceof Player) {
			sender.sendMessage(message);
		}
		this.getPlugin().getLogger().info(message);
		return true;
	}

}
