package ievans3024.bosrewards.commands;

import ievans3024.bosrewards.main.BOSRewards;
import ievans3024.bosrewards.main.Messages;
import ievans3024.bosrewards.main.User;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
//import org.bukkit.entity.Player;


public final class BalanceCommand extends SubCommand {

	public BalanceCommand(BOSRewards plugin, RewardsCommand parent, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, parent, name, permission, allowConsole, minArgs);
		String[] usage = {
				Messages.COLOR_SUCCESS + "/rewards balance"
					+ Messages.COLOR_INFO + " - Displays your " + plugin.getPointWordPlural() + " balance.",
				Messages.COLOR_SUCCESS + "/rewards balance [user]"
					+ Messages.COLOR_INFO + " - Displays the " + plugin.getPointWordPlural() + " balance for [user]"
		};
		this.setDescription("Displays " + plugin.getPointWordPlural() + " balance")
		.setUsage(usage);
	}

	@Override
	public boolean run(CommandSender sender, Command command, String label,
			String[] args) {
		
		User user;
		String sentencePrefix;
		
		if (args.length > 0) {
			if (sender instanceof ConsoleCommandSender || sender.hasPermission("BOSRewards.admin.seebalance")) {
				if (plugin.getDataController().getUserExists(args[0])) {
					user = plugin.getDataController().getUserByName(args[0]);
					sentencePrefix = Messages.COLOR_INFO + user.getName() + " has ";
				} else {
					sender.sendMessage(Messages.INVALID_ARGUMENT + args[0] + " not in rewards database");
					return true;
				}
			} else if (sender.getName().equalsIgnoreCase(args[0])) {
				user = plugin.getDataController().getUserByName(sender.getName());
				sentencePrefix = Messages.COLOR_INFO + "You have ";
			} else {
				Messages.sendNoPermsError(sender);
				return true;
			}
		} else {
			if (!(sender instanceof ConsoleCommandSender)){
				user = plugin.getDataController().getUserByName(sender.getName());
				sentencePrefix = Messages.COLOR_INFO + "You have ";
			} else {
				Messages.sendNoPermsError(sender);
				return true;
			}
		}
		sender.sendMessage(sentencePrefix
					+ user.getPoints() + " "
					+ ((user.getPoints() == 1) ? plugin.getPointWordSingle() : plugin.getPointWordPlural()));
		return true;
	}

}
