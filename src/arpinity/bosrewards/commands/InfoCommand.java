package arpinity.bosrewards.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import arpinity.bosrewards.main.BOSRewards;
import arpinity.bosrewards.main.Messages;
import arpinity.bosrewards.main.Reward;

public final class InfoCommand extends SubCommand {

	public InfoCommand(BOSRewards plugin, RewardsCommand parent, String name,
			String permission, boolean allowConsole, int minArgs) {
		super(plugin, parent, name, permission, allowConsole, minArgs);
	}

	@Override
	public boolean run(CommandSender sender, Command command, String label,
			String[] args) {
		if (plugin.getDataController().getRewardExists(args[0])) {
			Reward reward = plugin.getDataController().getRewardById(args[0]);
			String[] cmdarray;
			List<String> cmdlist = reward.getCommands();
			cmdarray = new String[cmdlist.size()];
			cmdarray = cmdlist.toArray(cmdarray);
			String[] message = {
					Messages.COLOR_INFO + "ID: " + reward.getId(),
					Messages.COLOR_INFO + "Cost: " + Integer.toString(reward.getCost()),
					Messages.COLOR_INFO + "Summary: " + reward.getSummary(),
					Messages.COLOR_INFO + "Commands: "
			};
			for (int i=0;i < cmdarray.length;i++) {
				cmdarray[i] = Messages.COLOR_INFO + "  " + cmdarray[i];
			}
			sender.sendMessage(message);
			sender.sendMessage(cmdarray);			
		} else {
			sender.sendMessage(Messages.NOT_A_REWARD);
		}		
		return true;
	}

}
