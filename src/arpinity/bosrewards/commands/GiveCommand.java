package arpinity.bosrewards.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import arpinity.bosrewards.main.BOSRewards;
import arpinity.bosrewards.main.Messages;
import arpinity.bosrewards.main.User;

public final class GiveCommand extends SubCommand {

	public GiveCommand(BOSRewards plugin, RewardsCommand parent, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, parent, name, permission, allowConsole, minArgs);
	}
	
	public boolean run(CommandSender sender, Command command, String label, String[] args){
		if (plugin.getDataController().getUserExists(args[0])){
			User user = plugin.getDataController().getUserByName(args[0]);
			int pointsvalue = Integer.parseInt(args[1]);
			String pointword = ((pointsvalue == 1) ? this.pointSingular : this.pointPlural);
			String havehas = ((pointsvalue == 1) ? "has" : "have");
			user.addPoints(pointsvalue);
			plugin.getDataController().writeUser(user);
			sender.sendMessage(Messages.COLOR_SUCCESS 
					+ args[1] + " "
					+ pointword 
					+ " " + havehas 
					+ " been given to " 
					+ args[0]);
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if (target != null) {
				target.sendMessage(Messages.COLOR_SUCCESS
						+ "You have been awarded "
						+ args[1] + " "
						+ pointword);
			}
			return true;
		}
		sender.sendMessage(Messages.INVALID_ARGUMENT + "The user " + args[0] + " is not in the database.");
		return true;
	}

}