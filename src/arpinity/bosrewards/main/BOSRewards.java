package arpinity.bosrewards.main;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.plugin.java.JavaPlugin;

import arpinity.bosrewards.commands.RewardsCommand;
import arpinity.bosrewards.datacontroller.DataController;
import arpinity.bosrewards.datacontroller.YamlController;


public final class BOSRewards extends JavaPlugin {
	
	// Calendar
	private Calendar calendar;
	public Calendar getCalendar() {
		return this.calendar;
	}
	public void createCalendar() {
		this.calendar = Calendar.getInstance();
	}
	private SimpleDateFormat dateFormat;
	public SimpleDateFormat getDateFormat() {
		return this.dateFormat;
	}
	public void setDateFormat(String format) {
		this.dateFormat = new SimpleDateFormat(format);
	}
	
	// Database Controller
	private DataController controller;
	private int dataSaveTaskId;
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
    	
    	// Calendar
    	this.createCalendar();
    	this.setDateFormat("yyyy/MM/dd");
    	
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
    	
    	// Scheduled Tasks
    	SaveTask dataSaveTask = new SaveTask(this);
    	this.dataSaveTaskId = getServer().getScheduler().scheduleSyncRepeatingTask(this, dataSaveTask, 20L, 2000L);
    	
    	getLogger().info("BOSRewards Enabled!");
    }
 
    @Override
    public void onDisable() {
    	getServer().getScheduler().cancelTask(this.dataSaveTaskId);
    	getDataController().closeDatabase();
    	getLogger().info("BOSRewards Disabled!");
    }

}