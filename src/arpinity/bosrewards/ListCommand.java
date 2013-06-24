package arpinity.bosrewards;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ListCommand extends SubCommand {

	public ListCommand(BOSRewards plugin, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, name, permission, allowConsole, minArgs);
	}
	
	public boolean run(CommandSender sender, Command command, String label, String[] args){
		int pageNumber = 1;
		List<Reward> rewardsList = this.getPlugin().getDataController().getRewards();
		if (!rewardsList.isEmpty()){
			int maximumPages = rewardsList.size() / 5;
			if (rewardsList.size() % 5 != 0){
				maximumPages += 1;
			}
			if (args.length > 2){
				pageNumber = Integer.parseInt(args[2]);
				if (pageNumber > maximumPages){
					this.getPlugin().getLogger().info("There are not that many pages in the catalogue. There are " + maximumPages + ((maximumPages == 1) ? " page." : " pages.") );
				}
			}
			int listStart = (pageNumber * 5) - 5;
			int i = 0;
			this.getPlugin().getLogger().info("Rewards Catalogue - Page " + pageNumber + "/" + maximumPages);
			this.getPlugin().getLogger().info("-----------------------------");
			this.getPlugin().getLogger().info("ID    Summary    Cost");
			while (i < 5){
				Reward reward = rewardsList.get(listStart + i);
				this.getPlugin().getLogger().info(reward.getId() + "    " + reward.getSummary() + "    " + reward.getCost());
				i++;
				if (listStart + i > rewardsList.size() - 1){
					i = 5;
				}
			}
		}
		return true;
	}

}
