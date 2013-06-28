package arpinity.bosrewards.commands;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import arpinity.bosrewards.main.BOSRewards;
import arpinity.bosrewards.main.Messages;
import arpinity.bosrewards.main.PagedArray;
import arpinity.bosrewards.main.ToolBox;

public final class HelpCommand extends SubCommand {

	public HelpCommand(BOSRewards plugin, RewardsCommand parent, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, parent, name, permission, allowConsole, minArgs);
		String[] usage = {
				Messages.COLOR_SUCCESS + "/rewards help <page>"
						+ Messages.COLOR_INFO + " - lists all subcommands and their descriptions",
				Messages.COLOR_SUCCESS + "/rewards help <subcommand>"
						+ Messages.COLOR_INFO + " - provides usage info for <subcommand>"
		};
		this.setDescription("Help menu for BOSRewards")
		.setUsage(usage);
	}

	@Override
	public boolean run(CommandSender sender, Command command, String label,
			String[] args) {
		String[] message;
		ArrayList<String> catalogue = new ArrayList<String>();
		int pageNumber = 1;
		Set<String> subCmdSet = this.parent.getMapKeys();
		while (subCmdSet.iterator().hasNext()) {
			String cmd = subCmdSet.iterator().next();
			if (sender instanceof ConsoleCommandSender || sender.hasPermission(this.parent.getSubCmdPermNode(cmd))) {
				catalogue.add(Messages.COLOR_INFO + cmd + " - " + this.parent.getSubCmdDescription(cmd));
			}
		}
		String[] catArray = new String[catalogue.size()];
		PagedArray reply = new PagedArray(catArray);	
		
		if (args.length > 0) {
			if (this.parent.getSubCmdExists(args[0])) {
				if (sender instanceof ConsoleCommandSender || sender.hasPermission(this.parent.getSubCmdPermNode(args[0]))) {
					message = this.parent.getSubCmdUsage(args[0]);
				} else {
					message = ToolBox.stringToArray(Messages.NO_PERMISSION);
				}
			} else if (Integer.parseInt(args[0]) > 0) {
				pageNumber = Integer.parseInt(args[0]);
				message = reply.getPage(pageNumber);
			} else {
				message = Messages.NOT_A_SUBCMD;
			}
		} else {
			message = reply.getPage(pageNumber);
		}
		sender.sendMessage(message);
		return true;
	}

}
