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
	
	public BOSRewards getPlugin() {
		return this.plugin;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getPermNode() {
		return this.permissionNode;
	}
	
	public boolean isConsoleAllowed() {
		return this.allowConsole;
	}
	
	public int getMinArgs() {
		return this.minArgs;
	}
	
	public String getUsage() {
		return this.usage;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public boolean getCanUseSubCommand(CommandSender sender) {
		if (this.isConsoleAllowed() && sender instanceof ConsoleCommandSender) {
			return true;
		} else if (sender.hasPermission(this.getPermNode())){
			return true;
		}
		return false;
	}
	
	
	// Setters
	
	public SubCommand setPermission(String permission) {
		this.permissionNode = permission;
		return this;
	}	
	
	public SubCommand allowConsole(boolean bool) {
		this.allowConsole = bool;
		return this;
	}
	
	public SubCommand setMinArgs(int minimum) {
		this.minArgs = minimum;
		return this;
	}
	
	public SubCommand setUsage(String usage) {
		this.usage = usage;
		return this;
	}
	
	public SubCommand setDescription(String description) {
		this.description = description;
		return this;
	}

}
