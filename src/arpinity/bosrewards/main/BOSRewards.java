package arpinity.bosrewards.main;

import org.bukkit.plugin.java.JavaPlugin;

import arpinity.bosrewards.datacontroller.DataController;
import arpinity.bosrewards.datacontroller.YamlController;


public final class BOSRewards extends JavaPlugin {
	
	// Database Controller
	private DataController controller;
	public DataController getDataController() {
		return this.controller;
	}
	public void setDataController(DataController c) {
		this.controller = c;
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
    	RewardsCommand commandExecutor = new RewardsCommand(this);
    	getCommand("rewards").setExecutor(commandExecutor);
    	getCommand("rw").setExecutor(commandExecutor);
    	
    	// Event Listeners
    	getServer().getPluginManager().registerEvents(new LoginListener(this), this);
    	
    	getLogger().info("BOSRewards Enabled!");
    }
 
    @Override
    public void onDisable() {
    	getDataController().closeDatabase();
    	getLogger().info("BOSRewards Disabled!");
    }

}