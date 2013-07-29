package ievans3024.bosrewards.commands;

import ievans3024.bosrewards.main.BOSRewards;
import ievans3024.bosrewards.main.Messages;
import ievans3024.bosrewards.main.PagedArray;
import ievans3024.bosrewards.main.ToolBox;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;


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
		Iterator<String> subCmdIter = subCmdSet.iterator();
		while (subCmdIter.hasNext()) {
			String cmd = subCmdIter.next();
			if (sender instanceof ConsoleCommandSender || sender.hasPermission(this.parent.getSubCmdPermNode(cmd))) {
				catalogue.add(Messages.COLOR_INFO + "/rewards " + cmd + " - " + this.parent.getSubCmdDescription(cmd));
			}
		}
		String[] catArray = new String[catalogue.size()];
		PagedArray reply = new PagedArray(catalogue.toArray(catArray));	
		
		if (args.length > 0) {
			if (this.parent.getSubCmdExists(args[0])) {
				if (sender instanceof ConsoleCommandSender || sender.hasPermission(this.parent.getSubCmdPermNode(args[0]))) {
					message = this.parent.getSubCmdUsage(args[0]);
				} else {
					message = ToolBox.stringToArray(Messages.NO_PERMISSION);
				}
			} else if (ToolBox.stringIsANumber(args[0])) {
				pageNumber = Integer.parseInt(args[0]);
				if (pageNumber > reply.getMaxPages()) {
					message = ToolBox.stringToArray(Messages.COLOR_SYNTAX_ERROR	
								+ "Invalid page number. Expecting number 1 through "
								+ reply.getMaxPages());
					sender.sendMessage(message);
					return true;
				} else {
					message = reply.getPage(pageNumber);
				}
			} else {
				message = Messages.NOT_A_SUBCMD;
			}
		} else {
			message = reply.getPage(pageNumber);
		}
		String[] header = {
			"",
			Messages.COLOR_INFO + "BOSRewards Help - Page "
					+ Messages.COLOR_SYNTAX_ERROR + pageNumber
					+ Messages.COLOR_INFO + "/"
					+ Messages.COLOR_SYNTAX_ERROR + reply.getMaxPages(),
			Messages.COLOR_INFO + "---------------------------"
		};
		sender.sendMessage(header);
		sender.sendMessage(message);
		return true;
	}

}
