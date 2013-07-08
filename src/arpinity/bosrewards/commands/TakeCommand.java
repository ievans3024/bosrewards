package arpinity.bosrewards.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import arpinity.bosrewards.main.BOSRewards;
import arpinity.bosrewards.main.Messages;
import arpinity.bosrewards.main.ToolBox;
import arpinity.bosrewards.main.User;

public final class TakeCommand extends SubCommand {

	public TakeCommand(BOSRewards plugin, RewardsCommand parent, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, parent, name, permission, allowConsole, minArgs);
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
			User user = plugin.getDataController().getUserByName(args[0]);
			if (user.getPoints() >= pointsvalue) {
				user.subtractPoints(pointsvalue);
			} else {
				user.setPoints(0);
				pointsvalue = user.getPoints();
			}
			plugin.getDataController().writeUser(user);
			String pointword = ((pointsvalue == 1) ? plugin.getPointWordSingle() : plugin.getPointWordPlural());
			String havehas = ((pointsvalue == 1) ? "has" : "have");
			String pointstring = Integer.toString(pointsvalue);
			sender.sendMessage(Messages.COLOR_SUCCESS 
					+ pointstring + " "
					+ pointword 
					+ " " + havehas 
					+ " been taken from " 
					+ args[0]);
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if (target != null) {
				target.sendMessage(Messages.COLOR_SUCCESS
						+ pointstring + " "
						+ pointword + " "
						+ havehas + " been subtracted from your balance.");
			}
			return true;
		}
		sender.sendMessage(Messages.INVALID_ARGUMENT + "The user " + args[0] + " is not in the database.");
		return true;
	}

}
