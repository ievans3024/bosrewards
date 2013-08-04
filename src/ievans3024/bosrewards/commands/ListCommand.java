package ievans3024.bosrewards.commands;

import ievans3024.bosrewards.main.BOSRewards;
import ievans3024.bosrewards.main.Messages;
import ievans3024.bosrewards.main.PagedArray;
import ievans3024.bosrewards.main.Reward;
import ievans3024.bosrewards.main.ToolBox;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


public final class ListCommand extends SubCommand {

	public ListCommand(BOSRewards plugin, RewardsCommand parent, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, parent, name, permission, allowConsole, minArgs);
		String[] usage = {
			Messages.COLOR_SUCCESS + "/rewards list <page>" +
			Messages.COLOR_INFO + " - Lists all available rewards, paged if necessary."
		};
		this.setDescription("Lists all available rewards.")
		.setUsage(usage);
	}
	
	public boolean run(CommandSender sender, Command command, String label, String[] args){
		int pageNumber = 1;
		List<Reward> rewardsList = plugin.getDataController().getRewards();
		Iterator<Reward> iterator = rewardsList.iterator();
		if (!rewardsList.isEmpty()) {
			ArrayList<String> catalogue = new ArrayList<String>();
			int[] tablepad = {0,0};
			for (int i=0;i<rewardsList.size();i++) {
				int idlength = rewardsList.get(i).getId().length() + 3;
				int sumlength = rewardsList.get(i).getSummary().length() + 3;
				if (idlength > tablepad[0]) {
					tablepad[0] = idlength;
				}
				if (sumlength > tablepad[1]) {
					tablepad[1] = sumlength;
				}
			}
			while (iterator.hasNext()) {
				Reward reward = iterator.next();
				boolean hasBypass = sender.hasPermission("BOSRewards.admin.bypass");
				boolean hasPermission = (reward.getPermNode() == null) ? true : sender.hasPermission(reward.getPermNode());			
				if (hasBypass || hasPermission){
					if (hasBypass || reward.getCost() >= 0) {
						String id = String.format("%1$-" + tablepad[0] + "s", reward.getId());
						String summary = String.format("%1$-" + tablepad[1] + "s", reward.getSummary());
						catalogue.add(Messages.COLOR_INFO + id + summary + reward.getCost());
					}
				}
			}
			if (catalogue.isEmpty()) {
				sender.sendMessage(Messages.COLOR_INFO + "No rewards are available for purchase!");
				return true;
			}
			String[] catArray = new String[catalogue.size()];
			PagedArray reply = new PagedArray(catalogue.toArray(catArray));
			
			if (args.length > 0){
				if (ToolBox.stringIsANumber(args[0])) {
					pageNumber = Integer.parseInt(args[0]);
				} else {
					sender.sendMessage(Messages.INVALID_ARGUMENT + "\"" + args[0] + "\"" + " is not a page number.");
					return true;
				}
				if (pageNumber < 1 || pageNumber > reply.getMaxPages()){
					sender.sendMessage(Messages.COLOR_SYNTAX_ERROR + "Invalid page number. Expecting number 1 through " + reply.getMaxPages());
					return true;
				}
			}
			
			String[] header = {
					"",
					Messages.COLOR_INFO + "Rewards Catalogue - " + "Page "
							+ Messages.COLOR_SUCCESS + pageNumber
							+ Messages.COLOR_INFO + "/" 
							+ Messages.COLOR_SUCCESS + reply.getMaxPages(),
					Messages.COLOR_INFO + String.format("%1$" + catalogue.get(0).length() + "s","-").replace(' ','-'),
					Messages.COLOR_SUCCESS 
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
