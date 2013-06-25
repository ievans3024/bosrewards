package arpinity.bosrewards.subcommands;

import java.util.Map;
import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import arpinity.bosrewards.main.BOSRewards;
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
			reward.setCost(Integer.parseInt(args[3]));
		}
	}
	
	private class CommandsFlag implements EditFlag {
		public void change(String[] args, Reward reward) {
			reward.setFirstCommand(ToolBox.arrayToString(args, 3, args.length));
		}
	}

	private class AddCommandsFlag implements EditFlag {
		public void change(String[] args, Reward reward) {
			reward.addCommands(ToolBox.arrayToString(args, 3, args.length));
		}
	}
	
	private class SummaryFlag implements EditFlag {
		public void change(String[] args, Reward reward){
			reward.setSummary(ToolBox.arrayToString(args, 3, args.length));
		}
	}
	
	public boolean run(CommandSender sender, Command command, String label, String[] args){
		if (args.length > 4
				&& this.editFlags.keySet().contains(args[2])){
			if (this.getPlugin().getDataController().getRewardExists(args[1])){
				Reward reward = this.getPlugin().getDataController().getRewardById(args[1]);
				this.editFlags.get(args[2]).change(args,reward);
				this.getPlugin().getDataController().writeReward(reward);
			}
			return true;
		}
		return false;
	};

}
