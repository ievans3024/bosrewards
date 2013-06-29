package arpinity.bosrewards.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
//import org.bukkit.entity.Player;

import arpinity.bosrewards.main.BOSRewards;
import arpinity.bosrewards.main.Messages;
import arpinity.bosrewards.main.PagedArray;
import arpinity.bosrewards.main.User;

public final class HistoryCommand extends SubCommand {

	public HistoryCommand(BOSRewards plugin, RewardsCommand parent, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, parent, name, permission, allowConsole, minArgs);
	}

	@Override
	public final boolean run(CommandSender sender, Command command, String label,
			String[] args) {
		User user;
		String[] header = new String[3];
		header[0] = "";
		int pageNumber = 1;
		if (args.length > 0){
			if (plugin.getDataController().getUserExists(args[0])) {
				if (sender instanceof ConsoleCommandSender || sender.hasPermission("BOSRewards.admin.seehistory")) {
					user = plugin.getDataController().getUserByName(args[0]);
					header[1] = Messages.COLOR_INFO + "Redemption history for " + user.getName() + " - ";
					if (args.length > 2){
						pageNumber = Integer.parseInt(args[1]);
					}
				} else {
					Messages.sendNoPermsError(sender);
					return true;
				}
			} else if (!(sender instanceof ConsoleCommandSender)) {
				user = plugin.getDataController().getUserByName(sender.getName());
				header[1] = Messages.COLOR_INFO + "Your redemption history - ";
				pageNumber = Integer.parseInt(args[0]);
			} else {
				Messages.sendNoPermsError(sender);
				return true;
			}
		} else {
			if (!(sender instanceof ConsoleCommandSender)) {
				user = plugin.getDataController().getUserByName(sender.getName());
				header[1] = Messages.COLOR_INFO + "Your redemption history - Page ";
			} else {
				Messages.sendNoPermsError(sender);
				return true;
			}
		}
		
		String[] userReceipts = new String[user.getReceipts().size()];
		userReceipts = user.getReceipts().toArray(userReceipts);
		if (userReceipts.length > 0){
			PagedArray reply = new PagedArray(userReceipts);
			if (pageNumber < 0 || pageNumber > reply.getMaxPages()){
				String[] message = {
						Messages.COLOR_SYNTAX_ERROR
							+ "Invalid page number. Expecting number 1 through "
							+ reply.getMaxPages()
				};
				sender.sendMessage(message);
				return true;
			} else {
				header[0] += Messages.COLOR_SUCCESS 
								+ Integer.toString(pageNumber) 
								+ Messages.COLOR_INFO + "/" 
								+ Messages.COLOR_SUCCESS + Integer.toString(reply.getMaxPages());
				sender.sendMessage(header);
				sender.sendMessage(reply.getPage(pageNumber));
				return true;
			}
		} else {
			String[] message = {
					Messages.COLOR_INFO + "You have not purchased any rewards yet!"
			};
			sender.sendMessage(message);
		}
		return true;
	}

}
