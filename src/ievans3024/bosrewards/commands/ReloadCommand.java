package ievans3024.bosrewards.commands;

import ievans3024.bosrewards.main.BOSRewards;
import ievans3024.bosrewards.main.Messages;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public final class ReloadCommand extends SubCommand {

	public ReloadCommand(BOSRewards plugin, RewardsCommand parent, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, parent, name, permission, allowConsole, minArgs);
		String[] usage = {
			Messages.COLOR_SUCCESS + "/rewards reload",
			Messages.COLOR_INFO + "Reloads the plugin configuration and data tables."
		};
		this.setDescription("Reloads the plugin config and data")
		.setUsage(usage);
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
