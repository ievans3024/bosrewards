package arpinity.bosrewards.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import arpinity.bosrewards.main.BOSRewards;
import arpinity.bosrewards.main.Messages;
import arpinity.bosrewards.main.ToolBox;

public final class HelpCommand extends SubCommand {

	public HelpCommand(BOSRewards plugin, RewardsCommand parent, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, parent, name, permission, allowConsole, minArgs);
	}

	@Override
	public boolean run(CommandSender sender, Command command, String label,
			String[] args) {
		String[] message;
		if (args.length > 0) {
			if (this.parent.getSubCmdExists(args[1])) {
				if (sender.hasPermission(this.parent.getSubCmdPermNode(args[1]))) {
					message = this.parent.getSubCmdUsage(args[1]);
				}
				message = ToolBox.stringToArray(Messages.NO_PERMISSION);
			}
			message = Messages.NOT_A_SUBCMD;
		} else {
			// TODO: create logic to list subcommands available and their short description strings
			message = ToolBox.stringToArray("This functionality is not there yet, blame arpinity.");
		}
		sender.sendMessage(message);
		return true;
	}

}
