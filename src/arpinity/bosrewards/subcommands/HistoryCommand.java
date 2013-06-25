package arpinity.bosrewards.subcommands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import arpinity.bosrewards.main.BOSRewards;
import arpinity.bosrewards.main.Messages;
import arpinity.bosrewards.main.User;

public final class HistoryCommand extends SubCommand {

	public HistoryCommand(BOSRewards plugin, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, name, permission, allowConsole, minArgs);
	}

	@Override
	public final boolean run(CommandSender sender, Command command, String label,
			String[] args) {
		User user;
		String header;
		int pageNumber = 1;
		if (args.length > 1){
			if (this.getPlugin().getDataController().getUserExists(args[1])){
				user = this.getPlugin().getDataController().getUserByName(args[1]);
				header =  Messages.COLOR_INFO + "Redemption history for " + user.getName();
				if (args.length > 2){
					pageNumber = Integer.parseInt(args[2]);
				}
			} else if (sender instanceof Player){
				user = this.getPlugin().getDataController().getUserByName(sender.getName());
				header = Messages.COLOR_INFO + "Your redemption history: ";
				pageNumber = Integer.parseInt(args[1]);
			} else {
				Messages.sendNoPermsError(sender);
				return true;
			}
		} else {
			if (sender instanceof Player){
				user = this.getPlugin().getDataController().getUserByName(sender.getName());
				header = Messages.COLOR_INFO + "Your redemption history: ";
			} else {
				Messages.sendNoPermsError(sender);
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
				String message = Messages.COLOR_SYNTAX_ERROR + "There are not that many pages. There are " + maximumPages + ((maximumPages == 1) ? " page." : " pages.");
				sender.sendMessage(message);
				return true;
			}				
			int listStart = (pageNumber * 5) - 5;
			String[] message = new String[7];
			message[0] = header;
			message[1] = "--------------------------";
			int i = 0;
			int m = 2;
			while (m < 7){
				message[m] = Messages.COLOR_INFO + userReceipts.get(listStart + i);
				m++;
				i++;
				if ((listStart + i) > (userReceipts.size() - 1)){
					m = 7;
				}
			}
			sender.sendMessage(message);
		}
		return true;
	}

}
