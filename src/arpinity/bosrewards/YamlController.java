package arpinity.bosrewards;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class YamlController extends DataController {
	
	public YamlController(BOSRewards plugin) {
		super(plugin);
	}
	
    private FileConfiguration rewardsTable = null;
    private File rewardsTableFile = null;
    private FileConfiguration usersTable = null;
    private File usersTableFile = null;	
    
    private FileConfiguration getRewardsTable(){
    	if (rewardsTable == null) {
    		this.reloadRewardsTable();
    	}
    	return rewardsTable;
    }
    
    private FileConfiguration getUsersTable() {
        if (usersTable == null) {
            this.reloadUsersTable();
        }
        return usersTable;
    }
	
    
    // Users table getters
    
    @Override
	public boolean getUserExists(String user){
		if (this.getUsersTable().getConfigurationSection(user) != null){
			return true;
		}
		return false;
	}
    
    @Override
	public User getUserByName(String user){
		if (!this.getUserExists(user)){
			return null;
		}
		ConfigurationSection userSection = getUsersTable().getConfigurationSection(user);
		User userByName = new User();
		userByName.name = userSection.getName();
		userByName.points = userSection.getInt("points");
		return userByName;		
	}
	
	@Override
	public int getUserPoints(String user){
		if (!this.getUserExists(user)){
			return 0; // -1 for non-existent users?
		}
		User userByName = this.getUserByName(user);
		return userByName.points;
	}
	
	@Override
	public List<User> getUsersByPoints(int points) {
		Set<String> UsersSet = usersTable.getKeys(false);
		Iterator<String> iterator = UsersSet.iterator();
		List<User> UsersList = new ArrayList<User>();
		while (iterator.hasNext()){
			if (usersTable.getConfigurationSection(iterator.next()).getInt("points") == points){
				UsersList.add(getUserByName(iterator.next()));
			}
		}
		return UsersList;		
	}
    
	// Rewards table getters
	
	@Override
	public boolean getRewardExists(String id){
		if (this.getRewardsTable().getConfigurationSection(id) != null){
			return true;
		}
		return false;
	}
	
	@Override
	public Reward getRewardById(String id){
		if (!getRewardExists(id)){
			return null;
		}
		ConfigurationSection rewardSection = getRewardsTable().getConfigurationSection(id);
		Reward rewardById = new Reward();
		rewardById.id = rewardSection.getName();
		rewardById.summary = rewardSection.getString("summary");
		rewardById.cost = rewardSection.getInt("cost");
		rewardById.commands = rewardSection.getStringList("commands");
		return rewardById;
	}
	
	@Override
	public String getRewardSummary(String id){
		if (!this.getRewardExists(id)){
			return null;
		}
		Reward rewardById = this.getRewardById(id);
		return rewardById.summary;
	}
	
	@Override
	public int getRewardCost(String id){
		if (!this.getRewardExists(id)){
			return 0; // -1 for non-existent rewards?
		}
		Reward rewardById = this.getRewardById(id);
		return rewardById.cost;
	}
	
    // Users table load, reload, create
    
    @Override
    public List<User> getUsers() {
        if (usersTable == null) {
            this.reloadUsersTable();
        }
        Set<String> UsersSet = usersTable.getKeys(false);
        Iterator<String> iterator = UsersSet.iterator();
        List<User> UsersList = new ArrayList<User>();
        while (iterator.hasNext()){
        	UsersList.add(getUserByName(iterator.next()));
        }
        return UsersList;        
    }
    
	@Override
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
	
	@Override
	public void initializeUsersTable(){
		if (usersTableFile == null) {
		    usersTableFile = new File(plugin.getDataFolder(), "users.yml");
		}
		if (!usersTableFile.exists()) {            
		     this.plugin.saveResource("users.yml", false);
		}
	}
	
	// Rewards table load, reload, create
	
	@Override
    public List<Reward> getRewards() {
        if (rewardsTable == null) {
            this.reloadRewardsTable();
        }
        Set<String> rewardsSet = rewardsTable.getKeys(false);
        Iterator<String> iterator = rewardsSet.iterator();
        List<Reward> rewardsList = new ArrayList<Reward>();
        while (iterator.hasNext()){
        	rewardsList.add(getRewardById(iterator.next()));
        }
        return rewardsList;        
    }
	
    @Override
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
	
	@Override
	public void initializeRewardsTable(){
		if (this.plugin.getDataFolder() == null){
			this.plugin.getLogger().info("Plugin Data Folder is null");
		} else {
			this.plugin.getLogger().info("Plugin Data Folder is: " + this.plugin.getDataFolder().getName());
		}
		if (rewardsTableFile == null) {
		    rewardsTableFile = new File(plugin.getDataFolder(), "rewards.yml");
		}
		if (!rewardsTableFile.exists()) {            
		     this.plugin.saveResource("rewards.yml", false);
		}
	}
	
	// Users table setters
	
	@Override // writes individual user info to loaded table
	public void writeUser(User user){
		if (!getUserExists(user.name)){
			this.getUsersTable().createSection(user.name);
		}
		ConfigurationSection userSection = this.getUsersTable().getConfigurationSection(user.name);
		userSection.set("points", user.points);
	}
	
	@Override // writes loaded users table to disk
	public void writeUsers(){
		if (usersTable == null || usersTableFile == null) {
		    return;
		    }
		    try {
		        this.getUsersTable().save(usersTableFile);
		    } catch (IOException ex) {
		        plugin.getLogger().log(Level.SEVERE, "Could not save config to " + usersTableFile, ex);
		    }
	}
	
	
	// Reward table setters
	
	@Override // writes individual reward info to loaded table
	public void writeReward(Reward reward){
		if (!getRewardExists(reward.id)){
			this.getRewardsTable().createSection(reward.id);
		}
		ConfigurationSection rewardSection = getRewardsTable().getConfigurationSection(reward.id);
		rewardSection.set("summary", reward.summary);
		rewardSection.set("cost",reward.cost);
		rewardSection.set("commands",reward.commands);
	}
	
	@Override // writes loaded rewards table to disk
	public void writeRewards(){
		if (rewardsTable == null || rewardsTableFile == null) {
		    return;
		    }
		    try {
		        this.getRewardsTable().save(rewardsTableFile);
		    } catch (IOException ex) {
		        plugin.getLogger().log(Level.SEVERE, "Could not save config to " + rewardsTableFile, ex);
		    }
	}
	
	@Override
	public void openDatabase(){
		this.initializeRewardsTable();
		this.initializeUsersTable();
		this.reloadRewardsTable();
		this.reloadUsersTable();
	}
	
	@Override
	public void closeDatabase(){
		this.writeRewards();
		this.writeUsers();
	}
}
