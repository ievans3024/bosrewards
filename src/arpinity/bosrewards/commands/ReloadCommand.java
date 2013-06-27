package arpinity.bosrewards.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import arpinity.bosrewards.main.BOSRewards;
import arpinity.bosrewards.main.Messages;


public final class ReloadCommand extends SubCommand {

	public ReloadCommand(BOSRewards plugin, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, name, permission, allowConsole, minArgs);
	}
	
	public boolean run(CommandSender sender, Command command, String label, String[] args) {
		plugin.reloadConfig();
		plugin.getDataController().reloadRewardsTable();
		sender.sendMessage(Messages.COLOR_INFO + "BOSRewards Reloaded!");
		if (sender instanceof Player) {
			plugin.getLogger().info("BOSRewards Reloaded!");
		}
		return true;
	}

}
