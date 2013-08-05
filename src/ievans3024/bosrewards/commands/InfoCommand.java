package ievans3024.bosrewards.commands;

import ievans3024.bosrewards.main.BOSRewards;
import ievans3024.bosrewards.main.Messages;
import ievans3024.bosrewards.main.Reward;

import java.util.Iterator;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


public final class InfoCommand extends SubCommand {

	public InfoCommand(BOSRewards plugin, RewardsCommand parent, String name,
			String permission, boolean allowConsole, int minArgs) {
		super(plugin, parent, name, permission, allowConsole, minArgs);
		String[] usage = {
			Messages.COLOR_SUCCESS + "/rewards info [id]" +
			Messages.COLOR_INFO + " - Displays attributes for the reward with ID of [id]."
		};
		this.setDescription("Displays attributes for rewards by id.")
		.setUsage(usage);
	}

	@Override
	public boolean run(CommandSender sender, Command command, String label,
			String[] args) {
		if (plugin.getDataController().getRewardExists(args[0])) {
			Reward reward = plugin.getDataController().getRewardById(args[0]);
			String[] message = {
					"",
					Messages.COLOR_INFO + "Reward Info:",
					Messages.COLOR_INFO + "  ID: " + reward.getId(),
					Messages.COLOR_INFO + "  Cost: " + Integer.toString(reward.getCost()),
					Messages.COLOR_INFO + "  Summary: " + reward.getSummary(),
			};
			
			List<String> cmdlist = reward.getCommands();
			Iterator<String> cmditer = cmdlist.iterator();
			String[] cmdarray;
			if (!cmdlist.isEmpty()){
				cmdarray = new String[cmdlist.size()];
				int i = 0;
				while (cmditer.hasNext()){
					cmdarray[i] = Messages.COLOR_INFO + "    " + i + " " + cmditer.next();
					i++;
				}
			} else {
				cmdarray = new String[1];
				cmdarray[0] = "    None";
			}
			
			List<String> permlist = reward.getPerms();
			Iterator<String> permiter = permlist.iterator();
			String[] permarray;
			if (!permlist.isEmpty()){
				permarray = new String[permlist.size()];
				int i = 0;
				while (permiter.hasNext()){
					permarray[i] = Messages.COLOR_INFO + "    " + i + " " + permiter.next();
					i++;
				}
			} else {
				permarray = new String[1];
				permarray[0] = "    None";
			}
			
			sender.sendMessage(message);
			sender.sendMessage(Messages.COLOR_INFO + "  Commands:");
			sender.sendMessage(cmdarray);	
			sender.sendMessage(Messages.COLOR_INFO + "  Permissions:");
			sender.sendMessage(permarray);
		} else {
			sender.sendMessage(Messages.NOT_A_REWARD);
		}		
		return true;
	}

}
