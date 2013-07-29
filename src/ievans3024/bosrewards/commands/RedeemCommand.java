package ievans3024.bosrewards.commands;

import ievans3024.bosrewards.main.BOSRewards;
import ievans3024.bosrewards.main.Messages;
import ievans3024.bosrewards.main.Receipt;
import ievans3024.bosrewards.main.Reward;
import ievans3024.bosrewards.main.User;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


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
		if (plugin.getDataController().getRewardExists(args[0])){
			Reward reward = plugin.getDataController().getRewardById(args[0]);
			if (hasByPass || reward.getCost() >= 0){
				if (hasByPass || user.getPoints() >= reward.getCost()){
					String date = plugin.getDateFormat().format(plugin.getCalendar().getTime());
					String receiptString = date + "  " + reward.getSummary() + "  " + reward.getCost() + " " + ((reward.getCost() == 1) ? plugin.getPointWordSingle() : plugin.getPointWordPlural());
					if (!hasByPass) {
						user.subtractPoints(reward.getCost());
						user.addReceipt(new Receipt(date,reward.getSummary(),reward.getCost()));
						plugin.getDataController().writeUser(user);
					}
					plugin.getLogger().info("Receipt: " + user.getName() + " " + receiptString);
					List<String> commands = reward.getCommands();
					Iterator<String> cmdIter = commands.iterator();
					while (cmdIter.hasNext()){
						String cmd = cmdIter.next();
						
						//find unescaped keywords
						cmd = cmd.replaceAll("\\Q${user}\\E",user.getName())
						
						//clear escape sequences
						.replaceAll("\\Q\\{\\E", "{")
						.replaceAll("\\Q\\}\\E", "}")
						.replaceAll("\\Q\\$\\E", "\\$");
						
						plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
					}
					sender.sendMessage(Messages.COLOR_SUCCESS
							+ "You redeemed "
							+ Messages.COLOR_INFO + reward.getCost() + " "
							+ Messages.COLOR_SUCCESS + ((reward.getCost() == 1) ? plugin.getPointWordSingle() : plugin.getPointWordPlural())
							+ " for " + reward.getSummary());
					return true;
				}
				sender.sendMessage(Messages.COLOR_BAD
						+ "You don't have enough "
						+ plugin.getPointWordPlural()
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
