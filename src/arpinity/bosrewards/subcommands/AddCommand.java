package arpinity.bosrewards.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import arpinity.bosrewards.main.BOSRewards;
import arpinity.bosrewards.main.Reward;
import arpinity.bosrewards.main.ToolBox;


public final class AddCommand extends SubCommand {

	public AddCommand(BOSRewards plugin, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, name, permission, allowConsole, minArgs);
	}
	
	public boolean run(CommandSender sender, Command command, String label, String[] args){
		if (args.length > this.getMinArgs()){
			Reward newReward = new Reward();
			newReward.setId(args[1]);
			newReward.setSummary(ToolBox.arrayToString(args,2,args.length));
			this.getPlugin().getDataController().writeReward(newReward);
			return true;
		}
		return false;
	};

}
