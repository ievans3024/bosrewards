package arpinity.bosrewards;


import java.io.File;
import java.io.InputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class yamlController extends DataController {
	
	yamlController() {
		super();
		// TODO Auto-generated constructor stub
	}

    private FileConfiguration rewardsTable = null;
    private File rewardsTableFile = null;
    private FileConfiguration usersTable = null;
    private File usersTableFile = null;	
	
	// Get, load/reload, initialize database tables
    public FileConfiguration getRewards() {
        if (rewardsTable == null) {
            this.reloadRewardsTable();
        }
        return rewardsTable;
    }
    
    public FileConfiguration getUsers() {
        if (usersTable == null) {
            this.reloadUsersTable();
        }
        return usersTable;
    }
	
	public void reloadRewardsTable(){
	    if (rewardsTableFile == null) {
	    	rewardsTableFile = new File(plugin.getDataFolder(), "rewards.yml");
        }
        rewardsTable = YamlConfiguration.loadConfiguration(rewardsTableFile);
     
        // Look for defaults in the jar
        InputStream defConfigStream = plugin.getResource("rewards.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            rewardsTable.setDefaults(defConfig);
        }  
	}
	
	public void reloadUsersTable(){
	    if (usersTableFile == null) {
	    	usersTableFile = new File(plugin.getDataFolder(), "users.yml");
        }
        usersTable = YamlConfiguration.loadConfiguration(usersTableFile);
     
        // Look for defaults in the jar
        InputStream defConfigStream = plugin.getResource("users.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            usersTable.setDefaults(defConfig);
        } 
	}
	
	public void initializeRewardsTable(){
		if (rewardsTableFile == null) {
		    rewardsTableFile = new File(plugin.getDataFolder(), "rewards.yml");
		}
		if (!rewardsTableFile.exists()) {            
		     this.plugin.saveResource("rewards.yml", false);
		}
	}
	
	public void initializeUsersTable(){
		if (usersTableFile == null) {
		    usersTableFile = new File(plugin.getDataFolder(), "users.yml");
		}
		if (!usersTableFile.exists()) {            
		     this.plugin.saveResource("users.yml", false);
		}
	}
	
	// Get user information
	public boolean getUserExists(String user){
		if (getUsers().getConfigurationSection(user) != null){
			return true;
		}
		return false;
	}
	
	public int getUserPoints(String user){
		return getUsers().getInt(user + ".points");
	}
	
	// Get reward information
	public boolean getRewardExists(String id){
		if (getRewards().getConfigurationSection(id) != null){
			return true;
		}
		return false;
	}
	
	public String getRewardSummary(String id){
		return getRewards().getString(id + ".summary");
	}
	
	public int getRewardCost(String id){
		return getRewards().getInt(id + ".cost");
	}
}
