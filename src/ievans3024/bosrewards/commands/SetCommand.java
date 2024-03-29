package ievans3024.bosrewards.commands;

import ievans3024.bosrewards.main.BOSRewards;
import ievans3024.bosrewards.main.Messages;
import ievans3024.bosrewards.main.ToolBox;
import ievans3024.bosrewards.main.User;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public final class SetCommand extends SubCommand {

	public SetCommand(BOSRewards plugin, RewardsCommand parent, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, parent, name, permission, allowConsole, minArgs);
		String[] usage = {
				Messages.COLOR_SUCCESS + "/rewards set [user] [number]" +
				Messages.COLOR_INFO + " - Sets a user's " + plugin.getPointWordPlural() + " to the number specified."
		};
		this.setDescription("Sets a users " + plugin.getPointWordPlural() + " to a particular value.")
		.setUsage(usage);
	}
	
	public boolean run(CommandSender sender, Command command, String label, String[] args) {
		if (plugin.getDataController().getUserExists(args[0])) {
			int pointsvalue;
			if (ToolBox.stringIsANumber(args[1])) {
				pointsvalue = Integer.parseInt(args[1]);
			} else {
				sender.sendMessage(Messages.INVALID_ARGUMENT + "\"" + args[1] + "\"" + " is not a number.");
				return true;
			}
			User user = plugin.getDataController().getUserByName(args[0]);
			String pointword = ((pointsvalue == 1) ? plugin.getPointWordSingle() : plugin.getPointWordPlural());
			if (pointsvalue >= 0) {
				user.setPoints(pointsvalue);
			} else {
				user.setPoints(0);
			}
			plugin.getDataController().writeUser(user);
			String message = args[0] + "'s "
					+ plugin.getPointWordPlural()
					+ " have been set to "
					+ args[1] + " "
					+ pointword;
			plugin.getLogger().info(message);
			sender.sendMessage(Messages.COLOR_SUCCESS + message);
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if (target != null) {
				target.sendMessage(Messages.COLOR_SUCCESS
						+ "Your "
						+ plugin.getPointWordPlural()
						+ " have been set to "
						+ args[1] + " "
						+ pointword);
			}
			return true;
		}
		sender.sendMessage(Messages.INVALID_ARGUMENT + "The user " + args[0] + " is not in the database.");
		return true;
	};

}
