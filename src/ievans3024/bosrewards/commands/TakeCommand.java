package ievans3024.bosrewards.commands;

import ievans3024.bosrewards.main.BOSRewards;
import ievans3024.bosrewards.main.Messages;
import ievans3024.bosrewards.main.ToolBox;
import ievans3024.bosrewards.main.User;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public final class TakeCommand extends SubCommand {

	public TakeCommand(BOSRewards plugin, RewardsCommand parent, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, parent, name, permission, allowConsole, minArgs);
		String[] usage = {
				Messages.COLOR_SUCCESS + "/rewards take [user] [number]" +
				Messages.COLOR_INFO + " - Removes a number of " + plugin.getPointWordPlural() + " from [user]'s balance."
		};
		this.setDescription("Removes some " + plugin.getPointWordPlural() + " from a user's balance.")
		.setUsage(usage);
	}
	
	public boolean run(CommandSender sender, Command command, String label, String[] args){
		if (plugin.getDataController().getUserExists(args[0])){
			int pointsvalue;
			if (ToolBox.stringIsANumber(args[1])) {
				pointsvalue = Integer.parseInt(args[1]);
			} else {
				sender.sendMessage(Messages.INVALID_ARGUMENT + "\"" + args[1] + "\"" + " is not a number.");
				return true;
			}
			if (pointsvalue > 0) {
				User user = plugin.getDataController().getUserByName(args[0]);
				if (user.getPoints() >= 0 && user.getPoints() >= pointsvalue) {
					user.subtractPoints(pointsvalue);
				} else {
					pointsvalue = user.getPoints();
					user.setPoints(0);
				}
				plugin.getDataController().writeUser(user);
				String pointword = ((pointsvalue == 1) ? plugin.getPointWordSingle() : plugin.getPointWordPlural());
				String havehas = ((pointsvalue == 1) ? "has" : "have");
				String pointstring = Integer.toString(pointsvalue);
				String message = pointstring + " "
						+ pointword 
						+ " " + havehas 
						+ " been taken from " 
						+ args[0];
				plugin.getLogger().info(message);
				sender.sendMessage(Messages.COLOR_SUCCESS + message);
				Player target = Bukkit.getServer().getPlayer(args[0]);
				if (target != null) {
					target.sendMessage(Messages.COLOR_SUCCESS
							+ pointstring + " "
							+ pointword + " "
							+ havehas + " been subtracted from your balance.");
				}
			} else {
				sender.sendMessage(Messages.COLOR_BAD + "Please provide a positive number greater than zero.");
			}
			return true;
		}
		sender.sendMessage(Messages.INVALID_ARGUMENT + "The user " + args[0] + " is not in the database.");
		return true;
	}

}
