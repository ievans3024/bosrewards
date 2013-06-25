package arpinity.bosrewards.subcommands;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import arpinity.bosrewards.main.BOSRewards;
import arpinity.bosrewards.main.Messages;
import arpinity.bosrewards.main.Reward;
import arpinity.bosrewards.main.User;

public class RedeemCommand extends SubCommand {

	public RedeemCommand(BOSRewards plugin, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, name, permission, allowConsole, minArgs);
	}

	@Override
	public boolean run(CommandSender sender, Command command, String label,
			String[] args) {
		User user = this.getPlugin().getDataController().getUserByName(sender.getName());
		if (this.getPlugin().getDataController().getRewardExists(args[1])){
			Reward reward = this.getPlugin().getDataController().getRewardById(args[1]);
			if (reward.getCost() >= 0){
				if (user.getPoints() >= reward.getCost()){
					Calendar calendar = Calendar.getInstance();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					String date = dateFormat.format(calendar.getTime());
					String receiptString = date + " " + reward.getSummary() + " " + reward.getCost();
					user.subtractPoints(reward.getCost());
					user.addReceipt(receiptString);
					this.getPlugin().getLogger().info(user.getName() + receiptString);
					List<String> commands = reward.getCommands();
					while (commands.iterator().hasNext()){
						this.getPlugin().getServer().dispatchCommand(Bukkit.getConsoleSender(), commands.iterator().next());
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
