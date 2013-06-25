package arpinity.bosrewards.main;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import arpinity.bosrewards.subcommands.SubCommand;

public final class SubCmdPermsHandler {
	
	private final BOSRewards plugin;
	
	public SubCmdPermsHandler(BOSRewards plugin) {
		this.plugin = plugin;
	}
	
	public BOSRewards getPlugin() {
		return this.plugin;
	}
	
	public boolean getCanUseSubCommand(CommandSender sender,SubCommand command) {
		if (command.isConsoleAllowed() && sender instanceof ConsoleCommandSender) {
			return true;
		} else if (sender.hasPermission(command.getPermNode())){
			return true;
		}
		return false;
	}
	
	public void sendNoPermsError(CommandSender sender) {
		if (sender instanceof ConsoleCommandSender) {
			sender.sendMessage(Messages.NO_CONSOLE);
		} else {
			sender.sendMessage(Messages.NO_PERMISSION);
		}
	}
}
