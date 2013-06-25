package arpinity.bosrewards.main;

import org.bukkit.command.CommandSender;
import org.bukkit.help.HelpTopic;
import org.bukkit.plugin.java.JavaPlugin;

public final class BOSRewards extends JavaPlugin {
	
	// Database Controller
	protected DataController controller;
	public DataController getDataController() {
		return this.controller;
	}
	public void setDataController(DataController c) {
		controller = c;
	}	
	
	// SubCommand Permissions Checker
	private SubCmdPermsHandler checker;
	public SubCmdPermsHandler getPermsHandler() {
		return this.checker;
	}
	public void setPermsHandler(SubCmdPermsHandler p) {
		checker = p;
	}
	
	// Message Constants
	private MessageHandler messages;
	public MessageHandler getMessageHandler() {
		return this.messages;
	}
	public void setMessageHandler(MessageHandler m) {
		this.messages = m;
	}
	
	// Help Menu - testing
	public String test = "Test";	
	private class testHelpTopic extends HelpTopic {
		
		// TODO: help topic reference
		// https://github.com/rmichela/HelpDemo/blob/master/src/main/java/com/ryanmichela/helpdemo/HelpPlugin.java
		
		private testHelpTopic() {
			name = "test help";
			shortText = "This is to test if spaces in the name work";
			fullText = "If you saw this text, spaces in the name work.";
		}
		
		@Override
		public boolean canSee(CommandSender commandSender) {
			if (amendedPermission == null){
				return true;
			} else {
				return commandSender.hasPermission(amendedPermission);
			}
		}
	
	}	
	
    @Override
    public void onEnable(){
    	
    	// Configuration
    	this.saveDefaultConfig(); //create default config file if one doesn't exist
    	getConfig();
    	
    	// Database Controller Selection
    	if (getConfig().getString("database-type") != null) {
    		setDataController(new YamlController(this)); // TODO: write sql code
    	} else {
    		setDataController(new YamlController(this));
    	}
    	getDataController().openDatabase();
    	
    	// Message Constants
    	setMessageHandler(new MessageHandler(this));
    	
    	// Commands
    	setPermsHandler(new SubCmdPermsHandler(this));
    	RewardsCommand commandExecutor = new RewardsCommand(this);
    	getCommand("rewards").setExecutor(commandExecutor);
    	getCommand("rw").setExecutor(commandExecutor);
    	
    	// Help Topics
    	getServer().getHelpMap().addTopic(new testHelpTopic());
    	
    	getLogger().info("BOSRewards Enabled!");
    }
 
    @Override
    public void onDisable() {
    	getDataController().closeDatabase();
    	getLogger().info("BOSRewards Disabled!");
    }

}