package arpinity.bosrewards.main;

import org.bukkit.ChatColor;

public final class Messages {
	
	// colors
	
	public static final ChatColor COLOR_BAD = ChatColor.RED;
	public static final ChatColor COLOR_SYNTAX_ERROR = ChatColor.AQUA;
	public static final ChatColor COLOR_SUCCESS = ChatColor.GREEN;
	public static final ChatColor COLOR_INFO = ChatColor.WHITE;
	
	
	// "bad" messages
	public static final String NO_CONSOLE = COLOR_BAD + "Console can't use this subcommand";
	public static final String NO_PERMISSION = COLOR_BAD + "You do not have permission to use this";

	
	// "syntax error" messages
	public static final String[] NOT_A_SUBCMD = {
		COLOR_SYNTAX_ERROR + "That subcommand does not exist",
		COLOR_SYNTAX_ERROR + "Try /help rewards for a list of subcommands"
	};
	public static final String[] NOT_A_REWARD = {
		COLOR_SYNTAX_ERROR + "That reward does not exist!",
		COLOR_SYNTAX_ERROR + "Find the reward ID by typing /rewards list"
	};
	public static final String[] NOT_ENOUGH_ARGS = {
		COLOR_SYNTAX_ERROR + "Not enough arguments for that command",
		COLOR_SYNTAX_ERROR + "Try /help rewards <command> for more info"
	};
	public static final String INVALID_ARGUMENT = COLOR_SYNTAX_ERROR + "Invalid argument: ";
	
	
	// "success" messages
	public static final String SUCCESS_ADD = COLOR_SUCCESS + "Added reward ";
	public static final String SUCCESS_REMOVE = COLOR_SUCCESS + "Removed reward ";
	public static final String SUCCESS_EDIT = COLOR_SUCCESS + "Edit reward ";
	
	// "fail" messages
	public static final String FAIL_REDEEM_NEGCOST = COLOR_BAD + "This reward is disabled.";
	
}
