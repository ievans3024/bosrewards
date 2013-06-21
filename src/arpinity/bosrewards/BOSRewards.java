package arpinity.bosrewards;

import org.bukkit.plugin.java.JavaPlugin;

public final class BOSRewards extends JavaPlugin {
	
	private DataController controller;
	
	public String test = "Test";
	
	public DataController getDataController(){
		return controller;
	}
	
	public void setDataController(DataController c){
		controller = c;
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
    	
    	//Commands
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