package ievans3024.bosrewards.commands;

import ievans3024.bosrewards.main.BOSRewards;
import ievans3024.bosrewards.main.Messages;
import ievans3024.bosrewards.main.PagedArray;
import ievans3024.bosrewards.main.ToolBox;
import ievans3024.bosrewards.main.User;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
//import org.bukkit.entity.Player;


public final class HistoryCommand extends SubCommand {

	public HistoryCommand(BOSRewards plugin, RewardsCommand parent, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, parent, name, permission, allowConsole, minArgs);
		String[] usage = {
			Messages.COLOR_SUCCESS + "/rewards history <page>" +
			Messages.COLOR_INFO + " - Displays a paged history of your rewards redemptions.",
			Messages.COLOR_SUCCESS + "/rewards history <user> <page>" +
			Messages.COLOR_INFO + " - Displays a paged history of <user>'s rewards redemptions."
		};
		this.setDescription("Displays a paged history of rewards redemptions.")
		.setUsage(usage);
	}

	@Override
	public final boolean run(CommandSender sender, Command command, String label,
			String[] args) {
		User user;
		String[] header = new String[3];
		header[0] = "";
		int pageNumber = 1;
		if (args.length > 0){
			if (ToolBox.stringIsANumber(args[0])){
				if (!(sender instanceof ConsoleCommandSender)) {
					user = plugin.getDataController().getUserByName(sender.getName());
					header[1] = Messages.COLOR_INFO + "Your redemption history - Page ";
					pageNumber = Integer.parseInt(args[0]);
				} else {
					Messages.sendNoPermsError(sender);
					return true;
				}
			} else {
				if (sender instanceof ConsoleCommandSender || sender.hasPermission("BOSRewards.admin.seehistory")) {
					if (plugin.getDataController().getUserExists(args[0])){
						user = plugin.getDataController().getUserByName(args[0]);
						header[1] = Messages.COLOR_INFO + "Redemption history for " + user.getName() + " - Page ";
						if (args.length > 1) {
							if (ToolBox.stringIsANumber(args[1])){
								pageNumber = Integer.parseInt(args[1]);
							} else {
								sender.sendMessage(Messages.INVALID_ARGUMENT + "\"" + args[1] + "\"" + " is not a page number.");
								return true;
							}
						}
					} else {
						sender.sendMessage(Messages.INVALID_ARGUMENT + " " + args[0] + " is not in the database.");
						return true;
					}
				} else if (sender.getName().equalsIgnoreCase(args[0])){
						user = plugin.getDataController().getUserByName(sender.getName());
						header[1] = Messages.COLOR_INFO + "Your redemption history - Page ";
				} else {
					Messages.sendNoPermsError(sender);
					return true;
				}
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
		
		String[] userReceipts = new String[user.getReceiptsList().size()];
		userReceipts = user.getReceiptsList().toArray(userReceipts);
		if (userReceipts.length > 0){
			PagedArray reply = new PagedArray(userReceipts);
			if (pageNumber < 1 || pageNumber > reply.getMaxPages()){
				String[] message = {
						Messages.COLOR_SYNTAX_ERROR
							+ "Invalid page number. Expecting number 1 through "
							+ reply.getMaxPages()
				};
				sender.sendMessage(message);
				return true;
			} else {
				header[1] += Messages.COLOR_SUCCESS 
								+ Integer.toString(pageNumber) 
								+ Messages.COLOR_INFO + "/" 
								+ Messages.COLOR_SUCCESS + Integer.toString(reply.getMaxPages());
				header[2] = Messages.COLOR_INFO + String.format("%1$" + header[1].length() + "s","-").replace(' ','-');
				sender.sendMessage(header);
				sender.sendMessage(reply.getPage(pageNumber));
				return true;
			}
		} else {
			sender.sendMessage(Messages.COLOR_INFO + "No rewards history yet!");
			return true;
		}
	}

}
