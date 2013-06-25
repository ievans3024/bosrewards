package arpinity.bosrewards.main;

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
	
	@Override public void onLoad(){
    	
	}
	
    @Override
    public void onEnable(){    	
    	// Configuration
    	this.saveDefaultConfig(); //create default config file if one doesn't exist
    	
    	// Database Controller Selection
    	if (getConfig().getString("database-type") != null) {
    		setDataController(new YamlController(this)); // TODO: write sql code
    	} else {
    		setDataController(new YamlController(this));
    	}
    	getDataController().openDatabase();
    	
    	// Commands
    	setPermsHandler(new SubCmdPermsHandler(this));
    	RewardsCommand commandExecutor = new RewardsCommand(this);
    	getCommand("rewards").setExecutor(commandExecutor);
    	getCommand("rw").setExecutor(commandExecutor);
    	
    	getLogger().info("BOSRewards Enabled!");
    }
 
    @Override
    public void onDisable() {
    	getDataController().closeDatabase();
    	getLogger().info("BOSRewards Disabled!");
    }

}