package arpinity.bosrewards.subcommands;

import java.util.Map;
import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import arpinity.bosrewards.main.BOSRewards;
import arpinity.bosrewards.main.Messages;
import arpinity.bosrewards.main.Reward;
import arpinity.bosrewards.main.ToolBox;


public final class EditCommand extends SubCommand {

	private Map<String,EditFlag> editFlags = new HashMap<String,EditFlag>();
	
	public EditCommand(BOSRewards plugin, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, name, permission, allowConsole, minArgs);
		this.editFlags.put("cost", new CostFlag());
		this.editFlags.put("commands", new CommandsFlag());
		this.editFlags.put("cmds", new CommandsFlag());
		this.editFlags.put("+commands", new AddCommandsFlag());
		this.editFlags.put("+cmds", new AddCommandsFlag());
		this.editFlags.put("summary", new SummaryFlag());
	}
	
	private interface EditFlag {
		public void change(String[] args, Reward reward);
	}
	
	private class CostFlag implements EditFlag {
		public void change(String[] args, Reward reward) {
			reward.setCost(Integer.parseInt(args[2]));
		}
	}
	
	private class CommandsFlag implements EditFlag {
		public void change(String[] args, Reward reward) {
			reward.setFirstCommand(ToolBox.arrayToString(args, 2, args.length));
		}
	}

	private class AddCommandsFlag implements EditFlag {
		public void change(String[] args, Reward reward) {
			reward.addCommands(ToolBox.arrayToString(args, 2, args.length));
		}
	}
	
	private class SummaryFlag implements EditFlag {
		public void change(String[] args, Reward reward){
			reward.setSummary(ToolBox.arrayToString(args, 2, args.length));
		}
	}
	
	public boolean run(CommandSender sender, Command command, String label, String[] args){
		sender.sendMessage(args);
		if (args.length > 2
				&& this.editFlags.containsKey(args[1])){
			if (this.getPlugin().getDataController().getRewardExists(args[0])){
				Reward reward = this.getPlugin().getDataController().getRewardById(args[0]);
				this.editFlags.get(args[1]).change(args,reward);
				this.getPlugin().getDataController().writeReward(reward);
				sender.sendMessage(Messages.SUCCESS_EDIT + reward.getId());
				return true;
			}
			sender.sendMessage(Messages.INVALID_ARGUMENT + "the reward " + args[0] + "does not exist.");
			return true;
		}
		sender.sendMessage(Messages.NOT_ENOUGH_ARGS);
		return true;
	};

}
