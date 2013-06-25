package arpinity.bosrewards.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import arpinity.bosrewards.main.BOSRewards;
import arpinity.bosrewards.main.User;

public final class BalanceCommand extends SubCommand {

	public BalanceCommand(BOSRewards plugin, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, name, permission, allowConsole, minArgs);
	}

	@Override
	public boolean run(CommandSender sender, Command command, String label,
			String[] args) {
		User user;
		String sentencePrefix;
		String pointSingular = this.getPlugin().getConfig().getString("point-name");
		String pointPlural = this.getPlugin().getConfig().getString("point-name-plural");
		if (args.length > 1){
			user = this.getPlugin().getDataController().getUserByName(args[1]);
			sentencePrefix = user.getName() + " has ";
		} else {
			if (sender instanceof Player){
				user = this.getPlugin().getDataController().getUserByName(sender.getName());
				sentencePrefix = "You have ";
			} else {
				this.getPlugin().getLogger().info("Console never gets points. Console will never be rewarded.");
				return true;
			}
		}
		if (sender instanceof Player){
			sender.sendMessage(sentencePrefix
					+ user.getPoints()
					+ ((user.getPoints() == 1) ? pointSingular : pointPlural));
		} else {
			this.getPlugin().getLogger().info(sentencePrefix
					+ user.getPoints()
					+ ((user.getPoints() == 1) ? pointSingular : pointPlural));
		}
		return true;
	}

}
