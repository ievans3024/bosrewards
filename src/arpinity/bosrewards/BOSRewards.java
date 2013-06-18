package arpinity.bosrewards;

import org.bukkit.plugin.java.JavaPlugin;

public final class BOSRewards extends JavaPlugin {
	
	private DataController controller;
	
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
    		setDataController(new YamlController()); // TODO: write sql code
    	} else {
    		setDataController(new YamlController());
    	}
    	
    	controller.openDatabase();
    	
    	//Commands
    	getCommand("rewards").setExecutor(new RewardsCommand(this));
    	getCommand("rw").setExecutor(new RewardsCommand(this));
    	
    	getLogger().info("BOSRewards Enabled!");
    }
 
    @Override
    public void onDisable() {
    	getLogger().info("BOSRewards Disabled!");
    }

}