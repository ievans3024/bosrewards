package arpinity.bosrewards;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HistoryCommand extends SubCommand {

	public HistoryCommand(BOSRewards plugin, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, name, permission, allowConsole, minArgs);
	}

	@Override
	public boolean run(CommandSender sender, Command command, String label,
			String[] args) {
		User user;
		String header;
		int pageNumber = 1;
		if (args.length > 1){
			if (this.getPlugin().getDataController().getUserExists(args[1])){
				user = this.getPlugin().getDataController().getUserByName(args[1]);
				header = "Redemption history for " + this.getPlugin().getDataController().getUserByName(args[1]).getName();
				if (args.length > 2){
					pageNumber = Integer.parseInt(args[2]);
				}
			} else if (sender instanceof Player){
				user = this.getPlugin().getDataController().getUserByName(sender.getName());
				header = "Your redemption history: ";
				pageNumber = Integer.parseInt(args[1]);
			} else {
				this.getPlugin().getLogger().info("Corrupt to its core, Console has never and will never have a redemption history. Try specifying a player.");
				return true;
			}
		} else {
			if (sender instanceof Player){
				user = this.getPlugin().getDataController().getUserByName(sender.getName());
				header = "Your redemption history: ";
			} else {
				this.getPlugin().getLogger().info("Corrupt to its core, Console has never and will never have a redemption history. Try specifying a player.");
				return true;
			}
		}
		List<String> userReceipts = user.getReceipts();
		if (!userReceipts.isEmpty()){
			int maximumPages = userReceipts.size() / 5;
			if (maximumPages % 5 != 0){
				maximumPages += 1;
			}
			if (pageNumber > maximumPages){
				String message = "There are not that many pages. There are " + maximumPages + ((maximumPages == 1) ? " page." : " pages.");
				if (sender instanceof Player){
					sender.sendMessage(message);
				} else {
					this.getPlugin().getLogger().info(message);						
				}
			}				
			int i = 0;
			int listStart = (pageNumber * 5) - 5;
			if (sender instanceof Player){
				sender.sendMessage(header);
			} else {
				this.getPlugin().getLogger().info(header);
			}
			while (i < 5){
				if (sender instanceof Player){
					sender.sendMessage(userReceipts.get(listStart + i));
				} else {
					this.getPlugin().getLogger().info(userReceipts.get(listStart + i));
				}
				i++;
				if ((listStart + i) > (userReceipts.size() - 1)){
					i = 5;
				}
			}
		}
		return true;
	}

}
