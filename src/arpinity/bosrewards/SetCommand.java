package arpinity.bosrewards;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class SetCommand extends SubCommand {

	public SetCommand(BOSRewards plugin, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, name, permission, allowConsole, minArgs);
	}
	
	public boolean run(CommandSender sender, Command command, String label, String[] args) {
		if (args.length > 3) {
			if (this.getPlugin().getDataController().getUserExists(args[1])) {
				User user = this.getPlugin().getDataController().getUserByName(args[1]);
				user.setPoints(Integer.parseInt(args[2]));
				this.getPlugin().getDataController().writeUser(user);
			}
		}
		return true;
	};

}
