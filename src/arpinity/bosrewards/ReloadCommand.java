package arpinity.bosrewards;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


public class ReloadCommand extends SubCommand {

	public ReloadCommand(BOSRewards plugin, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, name, permission, allowConsole, minArgs);
	}
	
	public boolean run(CommandSender sender, Command command, String label, String[] args) {
		this.getPlugin().reloadConfig();
		this.getPlugin().getDataController().reloadRewardsTable();
		this.getPlugin().getLogger().info("BOSRewards Reloaded!");
		return true;
	}

}
