package arpinity.bosrewards.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import arpinity.bosrewards.main.BOSRewards;

public abstract class SubCommand {
	
	// strings set in config file
	protected final String pointSingular;
	protected final String pointPlural;
	
	//TODO: use abstracts for everything except plugin
	
	private final BOSRewards plugin;
	private final String name;
	private String permissionNode;
	private boolean allowConsole = false;
	private int minArgs = 0;
	private String description;
	private String usage;
	
	public SubCommand(BOSRewards plugin,String name, String permission, boolean allowConsole, int minArgs) {
		this.plugin = plugin;
		this.name = name;
		this.permissionNode = permission;
		this.allowConsole = allowConsole;
		this.minArgs = minArgs;
		this.pointSingular = this.getPlugin().getConfig().getString("point-name");
		this.pointPlural = this.getPlugin().getConfig().getString("point-name-plural");
	}
	
	public abstract boolean run(CommandSender sender, Command command, String label, String[] args);
	
	// Getters
	
	public final BOSRewards getPlugin() {
		return this.plugin;
	}
	
	public final String getName() {
		return this.name;
	}
	
	public final String getPermNode() {
		return this.permissionNode;
	}
	
	public final boolean isConsoleAllowed() {
		return this.allowConsole;
	}
	
	public final int getMinArgs() {
		return this.minArgs;
	}
	
	public final String getUsage() {
		return this.usage;
	}
	
	public final String getDescription() {
		return this.description;
	}
	
	public final boolean getCanUseSubCommand(CommandSender sender) {
		if (this.isConsoleAllowed() && sender instanceof ConsoleCommandSender) {
			return true;
		} else if (sender.hasPermission(this.getPermNode())){
			return true;
		}
		return false;
	}
	
	
	// Setters
	
	public final SubCommand setPermission(String permission) {
		this.permissionNode = permission;
		return this;
	}	
	
	public final SubCommand allowConsole(boolean bool) {
		this.allowConsole = bool;
		return this;
	}
	
	public final SubCommand setMinArgs(int minimum) {
		this.minArgs = minimum;
		return this;
	}
	
	public final SubCommand setUsage(String usage) {
		this.usage = usage;
		return this;
	}
	
	public final SubCommand setDescription(String description) {
		this.description = description;
		return this;
	}

}
