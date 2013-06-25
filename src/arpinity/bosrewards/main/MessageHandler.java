package arpinity.bosrewards.main;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public final class MessageHandler {
	
	private final BOSRewards plugin;
	
	// private final String[] messages
	private final String[] noconsole = {
			ChatColor.RED + "Console can't use this subcommand"
	};
	private final String[] noperms = {
			ChatColor.RED + "You do not have permission to use this"
	};
	private final String[] nosubcmd = {
			ChatColor.AQUA + "That subcommand does not exist",
			ChatColor.AQUA + "Try /help rewards for a list of subcommands"
	};
	
	Map<String,String[]> messageMap = new HashMap<String,String[]>();
	
	public MessageHandler(BOSRewards plugin) {
		this.plugin = plugin;
		this.messageMap.put("noconsole", noconsole);
		this.messageMap.put("noperms", noperms);
		this.messageMap.put("nosubcmd", nosubcmd);
	}
	
	public BOSRewards getPlugin() {
		return this.plugin;
	}
	
	public void sendMessage(CommandSender sender, String label) {
		sender.sendMessage(this.messageMap.get(label));
	}
}
