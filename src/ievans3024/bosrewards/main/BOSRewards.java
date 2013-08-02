package ievans3024.bosrewards.main;

import ievans3024.bosrewards.commands.RewardsCommand;
import ievans3024.bosrewards.datacontroller.DataController;
import ievans3024.bosrewards.datacontroller.YamlController;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.plugin.java.JavaPlugin;


/**
 * BOSRewards prize catalogue plugin developed on the Bukkit API
 * and made for the Brotherhood of Slaughter Minecraft server.
 * Visit our website at www.bhslaughter.com!
 * 
 * @version 0.0.8.8
 * @author ievans3024
 */

public final class BOSRewards extends JavaPlugin {
	
	// Calendar
	private Calendar calendar;
	private SimpleDateFormat dateFormat;
	public Calendar getCalendar() {
		return this.calendar;
	}
	public SimpleDateFormat getDateFormat() {
		return this.dateFormat;
	}
	
	// Database Controller
	private DataController controller;
	private int dataSaveTaskId;
	public DataController getDataController() {
		return this.controller;
	}
	
	public final String getPointWordSingle() {
		return getConfig().getString("point-name");
	}
	public final String getPointWordPlural() {
		return getConfig().getString("point-name-plural");
	}
	
	@Override public void onLoad(){
		// Load configuration & write README
		saveDefaultConfig();
		saveResource("README.txt",true);
;		
    	// Create calendar
    	this.calendar = Calendar.getInstance();
    	this.dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    	
    	// Select database controller based on configuration
    	if (getConfig().getString("database-type") != null) {
    		this.controller = new YamlController(this); // TODO: write sql code
    	} else {
    		this.controller = new YamlController(this);
    	}
    	getDataController().openDatabase();
    	
	}
	
    @Override
    public void onEnable(){  
    	
    	// Register commands
    	RewardsCommand commandExecutor = new RewardsCommand(this);
    	getCommand("rewards").setExecutor(commandExecutor);
    	getCommand("rw").setExecutor(commandExecutor);
    	
    	// Register event listeners
    	getServer().getPluginManager().registerEvents(new LoginListener(this), this);
    	
    	// Schedule tasks
    	SaveTask dataSaveTask = new SaveTask(this);
    	this.dataSaveTaskId = getServer().getScheduler().scheduleSyncRepeatingTask(this, dataSaveTask, 20L, 2000L);
    	
    	// Log completed startup
    	getLogger().info("BOSRewards Enabled!");
    	
    }
 
    @Override
    public void onDisable() {
    	
    	// Cancel scheduled tasks
    	getServer().getScheduler().cancelTask(this.dataSaveTaskId);
    	
    	// Write any loose data and close database connection
    	getDataController().closeDatabase();
    	
    	// Log completed shutdown
    	getLogger().info("BOSRewards Disabled!");
    }

}