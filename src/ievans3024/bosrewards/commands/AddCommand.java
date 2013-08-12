package ievans3024.bosrewards.commands;

import ievans3024.bosrewards.main.BOSRewards;
import ievans3024.bosrewards.main.Messages;
import ievans3024.bosrewards.main.Reward;
import ievans3024.bosrewards.main.ToolBox;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;



public final class AddCommand extends SubCommand {

	public AddCommand(BOSRewards plugin, RewardsCommand parent, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, parent, name, permission, allowConsole, minArgs);
		String[] usage = {
				Messages.COLOR_SUCCESS + "/rewards add [id] [summary]" +
				Messages.COLOR_INFO + " - Adds a new reward with the specified id and summary."
		};
		this.setDescription("Adds a new reward.")
		.setUsage(usage);
	}
	
	public boolean run(CommandSender sender, Command command, String label, String[] args){
		plugin.getDataController().writeReward(new Reward(args[0],ToolBox.arrayToString(args, 1, args.length)));
		sender.sendMessage(Messages.SUCCESS_ADD + args[0]);
		return true;
	};

}
