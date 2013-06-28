package arpinity.bosrewards.commands;

import java.util.List;
import java.util.regex.Matcher;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import arpinity.bosrewards.main.BOSRewards;
import arpinity.bosrewards.main.Messages;
import arpinity.bosrewards.main.Reward;
import arpinity.bosrewards.main.User;

public class RedeemCommand extends SubCommand {

	public RedeemCommand(BOSRewards plugin, RewardsCommand parent, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, parent, name, permission, allowConsole, minArgs);
	}

	@Override
	public boolean run(CommandSender sender, Command command, String label,
			String[] args) {
		boolean hasByPass = (sender.hasPermission("BOSRewards.admin.bypass"));
		User user = plugin.getDataController().getUserByName(sender.getName());
		if (plugin.getDataController().getRewardExists(args[1])){
			Reward reward = plugin.getDataController().getRewardById(args[1]);
			if (hasByPass || reward.getCost() >= 0){
				if (hasByPass || user.getPoints() >= reward.getCost()){
					String date = plugin.getDateFormat().format(plugin.getCalendar().getTime());
					String receiptString = date + " " + reward.getSummary() + " " + reward.getCost();
					user.subtractPoints(reward.getCost());
					user.addReceipt(receiptString);
					plugin.getLogger().info(user.getName() + receiptString);
					List<String> commands = reward.getCommands();
					while (commands.iterator().hasNext()){
						String cmd = commands.iterator().next();
						
						//find unescaped keywords
						cmd = cmd.replaceAll("\\Q${user}\\E",user.getName())
						
						//clear escape sequences
						.replaceAll("\\{", Matcher.quoteReplacement("{"))
						.replaceAll("\\}", Matcher.quoteReplacement("}"))
						.replaceAll("\\$", Matcher.quoteReplacement("$"));
						
						plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
					}
					sender.sendMessage(Messages.COLOR_SUCCESS
							+ "You redeemed "
							+ Messages.COLOR_INFO + reward.getCost() + " "
							+ Messages.COLOR_SUCCESS + ((reward.getCost() == 1) ? this.pointSingular : this.pointPlural)
							+ " for " + reward.getSummary());
				}
				sender.sendMessage(Messages.COLOR_BAD
						+ "You don't have enough "
						+ this.pointPlural
						+ " for that reward");
				return true;
			}
			sender.sendMessage(Messages.FAIL_REDEEM_NEGCOST);
			return true;
		}
		sender.sendMessage(Messages.NOT_A_REWARD);
		return true;
	}

}
