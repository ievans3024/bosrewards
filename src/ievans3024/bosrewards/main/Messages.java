package ievans3024.bosrewards.main;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public final class Messages {
	
	// colors
	public static final String COLOR_BAD = ChatColor.RED + "" + ChatColor.ITALIC;
	public static final String COLOR_SYNTAX_ERROR = ChatColor.DARK_AQUA + "" + ChatColor.ITALIC;
	public static final String COLOR_SUCCESS = ChatColor.RESET + "" + ChatColor.GREEN;
	public static final String COLOR_INFO = ChatColor.RESET + "" + ChatColor.GRAY;
	
	
	// "bad" messages
	public static final String NO_CONSOLE = COLOR_BAD + "Console can't use this subcommand";
	public static final String NO_PERMISSION = COLOR_BAD + "You do not have permission to use this";
	public static void sendNoPermsError(CommandSender sender) {
		if (sender instanceof ConsoleCommandSender) {
			sender.sendMessage(NO_CONSOLE);
		} else if (sender instanceof Player){
			sender.sendMessage(NO_PERMISSION);
		}
	}

	
	// "syntax error" messages
	public static final String[] NOT_A_SUBCMD = {
		COLOR_SYNTAX_ERROR + "That subcommand does not exist",
		COLOR_SYNTAX_ERROR + "Try /rewards help for a list of subcommands"
	};
	public static final String[] NOT_A_REWARD = {
		COLOR_SYNTAX_ERROR + "That reward does not exist!",
		COLOR_SYNTAX_ERROR + "Find the reward ID by typing /rewards list"
	};
	public static final String[] NOT_ENOUGH_ARGS = {
		COLOR_SYNTAX_ERROR + "Not enough arguments for that command",
		COLOR_SYNTAX_ERROR + "Try /rewards help <command> for more info"
	};
	public static final String INVALID_ARGUMENT = COLOR_SYNTAX_ERROR + "Invalid argument: ";
	
	
	// "success" messages
	public static final String SUCCESS_ADD = COLOR_SUCCESS + "Added reward ";
	public static final String SUCCESS_REMOVE = COLOR_SUCCESS + "Removed reward ";
	public static final String SUCCESS_EDIT = COLOR_SUCCESS + "Edited reward ";
	
	// "fail" messages
	public static final String FAIL_REDEEM_NEGCOST = COLOR_BAD + "This reward is disabled.";
	public static final String FAIL_REDEEM_NOPERM = COLOR_BAD + "You do not have permission to get this reward.";
	
}
