package arpinity.bosrewards.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import arpinity.bosrewards.main.BOSRewards;
import arpinity.bosrewards.main.Messages;
import arpinity.bosrewards.main.Reward;
import arpinity.bosrewards.main.ToolBox;


public final class AddCommand extends SubCommand {

	public AddCommand(BOSRewards plugin, RewardsCommand parent, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, parent, name, permission, allowConsole, minArgs);
		String[] usage = {
				Messages.COLOR_SUCCESS + "/rewards add [id] [summary]",
				Messages.COLOR_INFO + "Adds a new reward with the specified id and summary properties"
		};
		this.setDescription("Adds a new reward.")
		.setUsage(usage);
	}
	
	public boolean run(CommandSender sender, Command command, String label, String[] args){
		Reward newReward = new Reward();
		newReward.setId(args[0]);
		newReward.setSummary(ToolBox.arrayToString(args,1,args.length));
		plugin.getDataController().writeReward(newReward);
		sender.sendMessage(Messages.SUCCESS_ADD + newReward.getId());
		return true;
	};

}
