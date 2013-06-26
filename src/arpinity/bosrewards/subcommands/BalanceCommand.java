package arpinity.bosrewards.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import arpinity.bosrewards.main.BOSRewards;
import arpinity.bosrewards.main.Messages;
import arpinity.bosrewards.main.User;

public final class BalanceCommand extends SubCommand {

	public BalanceCommand(BOSRewards plugin, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, name, permission, allowConsole, minArgs);
	}

	@Override
	public boolean run(CommandSender sender, Command command, String label,
			String[] args) {
		
		User user;
		String sentencePrefix;
		
		if (args.length > 0) {
			if (sender instanceof ConsoleCommandSender || sender.hasPermission("BOSRewards.admin.seebalance")) {
				if (this.getPlugin().getDataController().getUserExists(args[0])) {
					user = this.getPlugin().getDataController().getUserByName(args[0]);
					sentencePrefix = Messages.COLOR_INFO + user.getName() + " has ";
				} else {
					sender.sendMessage(Messages.INVALID_ARGUMENT + args[0] + " not in rewards database");
					return true;
				}
			} else {
				Messages.sendNoPermsError(sender);
				return true;
			}
		} else {
			if (sender instanceof Player){
				user = this.getPlugin().getDataController().getUserByName(sender.getName());
				sentencePrefix = Messages.COLOR_INFO + "You have ";
			} else {
				Messages.sendNoPermsError(sender);
				return true;
			}
		}
		sender.sendMessage(sentencePrefix
					+ user.getPoints()
					+ ((user.getPoints() == 1) ? this.pointSingular : this.pointPlural));
		return true;
	}

}
