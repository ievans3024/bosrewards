package arpinity.bosrewards.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import arpinity.bosrewards.main.BOSRewards;
import arpinity.bosrewards.main.Messages;
import arpinity.bosrewards.main.Reward;
import arpinity.bosrewards.main.ToolBox;


public final class AddCommand extends SubCommand {

	public AddCommand(BOSRewards plugin, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, name, permission, allowConsole, minArgs);
	}
	
	public boolean run(CommandSender sender, Command command, String label, String[] args){
		Reward newReward = new Reward();
		newReward.setId(args[0]);
		newReward.setSummary(ToolBox.arrayToString(args,1,args.length));
		this.getPlugin().getDataController().writeReward(newReward);
		sender.sendMessage(Messages.SUCCESS_ADD + newReward.getId());
		return true;
	};

}
