package ievans3024.bosrewards.commands;

import ievans3024.bosrewards.main.BOSRewards;
import ievans3024.bosrewards.main.Messages;
import ievans3024.bosrewards.main.User;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public final class GiveCommand extends SubCommand {

	public GiveCommand(BOSRewards plugin, RewardsCommand parent, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, parent, name, permission, allowConsole, minArgs);
		String[] usage = {
				Messages.COLOR_SUCCESS + "/rewards give [user] [number]" +
				Messages.COLOR_INFO + " - Adds a number of " + plugin.getPointWordPlural() + " to [user]'s balance."
		};
		this.setDescription("Adds some " + plugin.getPointWordPlural() + " to a user's balance.")
		.setUsage(usage);
	}
	
	public boolean run(CommandSender sender, Command command, String label, String[] args){
		if (plugin.getDataController().getUserExists(args[0])){
			User user = plugin.getDataController().getUserByName(args[0]);
			int pointsvalue = Integer.parseInt(args[1]);
			if (pointsvalue > 0) {
				if (user.getPoints() + pointsvalue < 0) {
					sender.sendMessage(Messages.COLOR_BAD + user.getName() + " has reached the maximum points allowable.");
					return true;
				}
				String pointword = ((pointsvalue == 1) ? plugin.getPointWordSingle() : plugin.getPointWordPlural());
				String havehas = ((pointsvalue == 1) ? "has" : "have");
				if (user.getPoints() < 0) {
					user.setPoints(0);
				}
				user.addPoints(pointsvalue);
				plugin.getDataController().writeUser(user);
				String message = args[1] + " "
						+ pointword 
						+ " " + havehas 
						+ " been given to " 
						+ args[0];
				sender.sendMessage(Messages.COLOR_SUCCESS + message);
				plugin.getLogger().info(message);
				Player target = Bukkit.getServer().getPlayer(args[0]);
				if (target != null) {
					target.sendMessage(Messages.COLOR_SUCCESS
							+ "You have been awarded "
							+ args[1] + " "
							+ pointword);
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
