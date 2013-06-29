package arpinity.bosrewards.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import arpinity.bosrewards.main.BOSRewards;
import arpinity.bosrewards.main.Messages;
import arpinity.bosrewards.main.Reward;
import arpinity.bosrewards.main.PagedArray;

public final class ListCommand extends SubCommand {

	public ListCommand(BOSRewards plugin, RewardsCommand parent, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, parent, name, permission, allowConsole, minArgs);
	}
	
	public boolean run(CommandSender sender, Command command, String label, String[] args){
		int pageNumber = 1;
		List<Reward> rewardsList = plugin.getDataController().getRewards();
		Iterator<Reward> iterator = rewardsList.iterator();
		if (!rewardsList.isEmpty()) {
			ArrayList<String> catalogue = new ArrayList<String>();
			int[] tablepad = {0,0};
			for (int i=0;i<rewardsList.size();i++) {
				int idlength = rewardsList.get(i).getId().length() + 2;
				int sumlength = rewardsList.get(i).getSummary().length() + 2;
				if (idlength > tablepad[0]) {
					tablepad[0] = idlength;
				}
				if (sumlength > tablepad[1]) {
					tablepad[1] = sumlength;
				}
			}
			while (iterator.hasNext()) {
				Reward reward = iterator.next();
				if (sender.hasPermission("BOSRewards.admin.bypass") || reward.getCost() >= 0) {
					String id = String.format("%1$-" + tablepad[0] + "s", reward.getId());
					String summary = String.format("%1$-" + tablepad[1] + "s", reward.getSummary());
					catalogue.add(Messages.COLOR_INFO + id + summary + reward.getCost());
				}
			}
			if (catalogue.isEmpty()) {
				sender.sendMessage(Messages.COLOR_INFO + "No rewards are available for purchase!");
				return true;
			}
			String[] catArray = new String[catalogue.size()];
			PagedArray reply = new PagedArray(catalogue.toArray(catArray));
			
			if (args.length > 0){
				pageNumber = Integer.parseInt(args[0]);
				if (pageNumber > reply.getMaxPages()){
					sender.sendMessage(Messages.COLOR_SYNTAX_ERROR + "Invalid page number. Expecting number 1 through " + reply.getMaxPages());
					return true;
				}
			}
			
			String[] header = {
					"",
					Messages.COLOR_INFO + "Rewards Catalogue - " + "Page "
							+ Messages.COLOR_SYNTAX_ERROR + pageNumber
							+ Messages.COLOR_INFO + "/" 
							+ Messages.COLOR_SYNTAX_ERROR + reply.getMaxPages(),
					Messages.COLOR_INFO + String.format("%1$" + catalogue.get(0).length() + "s","-").replace(' ','-'),
					Messages.COLOR_SYNTAX_ERROR 
							+ String.format("%1$-" + tablepad[0] + "s","ID")
							+ String.format("%1$-" + tablepad[1] + "s","Summary")
							+ "Cost"
			};
			
			sender.sendMessage(header);
			sender.sendMessage(reply.getPage(pageNumber));
			return true;
		}
		sender.sendMessage(Messages.COLOR_INFO + "No rewards are available for purchase!");
		return true;
	}

}
