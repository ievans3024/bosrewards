package ievans3024.bosrewards.commands;

import ievans3024.bosrewards.main.BOSRewards;
import ievans3024.bosrewards.main.Messages;
import ievans3024.bosrewards.main.Reward;
import ievans3024.bosrewards.main.ToolBox;

import java.util.Map;
import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;



public final class EditCommand extends SubCommand {

	private Map<String,EditFlag> editFlags = new HashMap<String,EditFlag>();
	
	public EditCommand(BOSRewards plugin, RewardsCommand parent, String name, String permission,
			boolean allowConsole, int minArgs) {
		super(plugin, parent, name, permission, allowConsole, minArgs);
		this.editFlags.put("cost", new CostFlag());
		this.editFlags.put("commands", new CommandsFlag());
		this.editFlags.put("cmds", new CommandsFlag());
		this.editFlags.put("+commands", new AddCommandsFlag());
		this.editFlags.put("+cmds", new AddCommandsFlag());
		this.editFlags.put("summary", new SummaryFlag());
		this.editFlags.put("-commands", new RemoveCommandsFlag());
		this.editFlags.put("-cmds", new RemoveCommandsFlag());
		this.editFlags.put("permission", new PermissionFlag());
		this.editFlags.put("perm", new PermissionFlag());
		
		String[] usage = {
			Messages.COLOR_SUCCESS + "/rewards edit [id] [flag] [value]" +
			Messages.COLOR_INFO + " - Edits the properties of the reward with id of [id].",
			Messages.COLOR_INFO + "Flags:" +
			Messages.COLOR_SUCCESS + " cost, commands, +commands, -commands, summary, permission",
			Messages.COLOR_INFO + "Consult the BOSRewards documentation for more info."
		};
		this.setDescription("Edits the properties of a reward.")
		.setUsage(usage);
	}
	
	private interface EditFlag {
		public void change(String[] args, Reward reward);
	}
	
	private final class CostFlag implements EditFlag {
		public void change(String[] args, Reward reward) {
			int cost;
			try {
				cost = Integer.parseInt(args[2]);
			} catch (NumberFormatException ex) {
				throw ex;
			}
			reward.setCost(cost);			
		}
	}
	
	private final class CommandsFlag implements EditFlag {
		public void change(String[] args, Reward reward) {
			reward.setFirstCommand(ToolBox.arrayToString(args, 2, args.length));
		}
	}

	private final class AddCommandsFlag implements EditFlag {
		public void change(String[] args, Reward reward) {
			reward.addCommands(ToolBox.arrayToString(args, 2, args.length));
		}
	}
	
	private final class RemoveCommandsFlag implements EditFlag {
		public void change(String[]args, Reward reward) {
			int index;
			try {
				index = Integer.parseInt(args[2]);
			} catch (NumberFormatException ex) {
				throw ex;
			}
			try {
				reward.removeCommands(index);
			} catch (IndexOutOfBoundsException ex) {
				throw ex;
			}			
		}
	}
	
	private final class SummaryFlag implements EditFlag {
		public void change(String[] args, Reward reward){
			reward.setSummary(ToolBox.arrayToString(args, 2, args.length));
		}
	}
	
	private final class PermissionFlag implements EditFlag {
		public void change(String[] args, Reward reward){
			reward.setPermNode(args[2]);
		}
	}
	
	public boolean run(CommandSender sender, Command command, String label, String[] args){
		if (args.length > 2){
			if (this.editFlags.containsKey(args[1])){
				if (plugin.getDataController().getRewardExists(args[0])){
					Reward reward = plugin.getDataController().getRewardById(args[0]);
					
					try {
						this.editFlags.get(args[1]).change(args,reward);
					} catch (NumberFormatException ex) {
						sender.sendMessage(Messages.INVALID_ARGUMENT + "\"" + args[2] + "\"" + " is not a number.");
						return true;
					} catch (IndexOutOfBoundsException ex) {
						sender.sendMessage(Messages.INVALID_ARGUMENT + "\"" + args[2] + "\"" + " does not correspond to a reward.");
						return true;
					}
					
					plugin.getDataController().writeReward(reward);
					sender.sendMessage(Messages.SUCCESS_EDIT + reward.getId());
					return true;
				}
				sender.sendMessage(Messages.INVALID_ARGUMENT + "the reward " + args[0] + " does not exist.");
				return true;
			}
			sender.sendMessage(Messages.INVALID_ARGUMENT + "invalid flag.");
			return true;
		}
		sender.sendMessage(Messages.NOT_ENOUGH_ARGS);
		return true;
	}

}
