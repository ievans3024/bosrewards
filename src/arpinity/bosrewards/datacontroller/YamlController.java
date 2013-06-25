package arpinity.bosrewards.datacontroller;


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

import arpinity.bosrewards.main.BOSRewards;
import arpinity.bosrewards.main.Reward;
import arpinity.bosrewards.main.User;

public final class YamlController extends DataController {
	
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
		userByName.setName(userSection.getName());
		userByName.setPoints(userSection.getInt("points"));
		return userByName;		
	}
	
	@Override
	public int getUserPoints(String user){
		if (!this.getUserExists(user)){
			return 0; // -1 for non-existent users?
		}
		return this.getUserByName(user).getPoints();
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
		rewardById.setId(rewardSection.getName());
		rewardById.setSummary(rewardSection.getString("summary"));
		rewardById.setCost(rewardSection.getInt("cost"));
		rewardById.setCommands(rewardSection.getStringList("commands"));
		return rewardById;
	}
	
	@Override
	public String getRewardSummary(String id){
		if (!this.getRewardExists(id)){
			return null;
		}
		return this.getRewardById(id).getSummary();
	}
	
	@Override
	public int getRewardCost(String id){
		if (!this.getRewardExists(id)){
			return 0; // -1 for non-existent rewards?
		}
		Reward rewardById = this.getRewardById(id);
		return rewardById.getCost();
	}
	
	@Override
	public List<String> getRewardCommands(String id){
		if (!this.getRewardExists(id)){
			List<String> emptyList = new ArrayList<String>();
			return emptyList;
		}
		Reward rewardById = this.getRewardById(id);
		return rewardById.getCommands();
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
		if (!getUserExists(user.getName())){
			this.getUsersTable().createSection(user.getName());
		}
		ConfigurationSection userSection = this.getUsersTable().getConfigurationSection(user.getName());
		userSection.set("points", user.getPoints());
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
	
	@Override
	public void removeRewardById(String id){
		if (this.getRewardExists(id)){
			this.getRewardsTable().set(id,null);
		}
	}
	
	@Override // writes individual reward info to loaded table
	public void writeReward(Reward reward){
		if (!getRewardExists(reward.getId())){
			this.getRewardsTable().createSection(reward.getId());
		}
		ConfigurationSection rewardSection = getRewardsTable().getConfigurationSection(reward.getId());
		rewardSection.set("summary", reward.getSummary());
		rewardSection.set("cost",reward.getCost());
		rewardSection.set("commands",reward.getCommands());
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
