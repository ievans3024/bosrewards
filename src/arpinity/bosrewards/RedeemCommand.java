package arpinity.bosrewards;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class RedeemCommand extends SubCommand {

	public RedeemCommand(BOSRewards plugin, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, name, permission, allowConsole, minArgs);
	}

	@Override
	public boolean run(CommandSender sender, Command command, String label,
			String[] args) {
		if (args.length > 1){
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
					}
				}
			}
		}
		return true;
	}

}
