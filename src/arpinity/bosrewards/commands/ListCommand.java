package arpinity.bosrewards.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import arpinity.bosrewards.main.BOSRewards;
import arpinity.bosrewards.main.Messages;
import arpinity.bosrewards.main.Reward;

public final class ListCommand extends SubCommand {

	public ListCommand(BOSRewards plugin, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, name, permission, allowConsole, minArgs);
	}
	
	public boolean run(CommandSender sender, Command command, String label, String[] args){
		int pageNumber = 1;
		List<Reward> rewardsList = plugin.getDataController().getRewards();
		if (!rewardsList.isEmpty()){
			int maximumPages = rewardsList.size() / 5;
			if (rewardsList.size() % 5 != 0){
				maximumPages += 1;
			}
			if (args.length > 2){
				pageNumber = Integer.parseInt(args[2]);
				if (pageNumber > maximumPages){
					sender.sendMessage(Messages.COLOR_SYNTAX_ERROR + "There are not that many pages in the catalogue. There are " + maximumPages + ((maximumPages == 1) ? " page." : " pages.") );
					return true;
				}
			}
			int listStart = (pageNumber * 5) - 5;
			String[] message = new String[8];
			message[0] = Messages.COLOR_INFO + "Rewards Catalogue - "
							+ Messages.COLOR_SYNTAX_ERROR + "Page "
							+ Messages.COLOR_INFO + pageNumber
							+ Messages.COLOR_SYNTAX_ERROR + "/" 
							+ Messages.COLOR_INFO + maximumPages;
			message[1] = Messages.COLOR_INFO + "-----------------------------";
			message[2] = Messages.COLOR_INFO + "ID        Summary        Cost";
			
			// i is to iterate through the list based on page number
			// m is to count the number of rewards that don't have a negative cost.
			int i = 0;
			int m = 3;
			while (m < 8){
				Reward reward = rewardsList.get(listStart + i);
				if (reward.getCost() >= 0) {
					message[m] = reward.getId() + "        " + reward.getSummary() + "        " + reward.getCost();
					m++;
				}
				i++;
				if (listStart + i > rewardsList.size() - 1){
					m = 8;
				}
			}
			sender.sendMessage(message);
			return true;
		}
		sender.sendMessage(Messages.COLOR_INFO + "No rewards have been set up to purchase yet!");
		return true;
	}

}
