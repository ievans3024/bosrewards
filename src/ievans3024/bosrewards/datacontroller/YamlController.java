package ievans3024.bosrewards.datacontroller;


import ievans3024.bosrewards.main.BOSRewards;
import ievans3024.bosrewards.main.Receipt;
import ievans3024.bosrewards.main.Reward;
import ievans3024.bosrewards.main.User;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


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
	public boolean getUserExists(String username){
		if (this.getUsersTable().getConfigurationSection(username) != null){
			return true;
		}
		return false;
	}
    
    @Override
	public User getUserByName(String username){
		if (!this.getUserExists(username)){
			return null;
		}
		//ConfigurationSection userSection = this.getUsersTable().getConfigurationSection(username);
		String usernode = username + ".";
		User user = new User(username)
		.setPoints(this.getUsersTable().getInt(usernode + "points"))
		.setLastOnline(this.getUsersTable().getString(usernode + "last-online"));
		List<Map<?,?>> rlist = this.getUsersTable().getMapList(usernode + "receipts");
		Iterator<Map<?,?>> iter = rlist.iterator();
		while (iter.hasNext()) {
			Map<?,?> r = iter.next();
			Receipt receipt = new Receipt((String) r.get("date"),(String) r.get("summary"),Integer.parseInt((String) r.get("cost")));
			user.addReceipt(receipt);			
		}
		return user;
	}
	
	@Override
	public int getUserPoints(String username){
		if (!this.getUserExists(username)){
			return -1;
		}
		return this.getUserByName(username).getPoints();
	}
	
	@Override
	public List<User> getUsersByPoints(int points) {
		Set<String> UsersSet = usersTable.getKeys(false);
		Iterator<String> iterator = UsersSet.iterator();
		List<User> UsersList = new ArrayList<User>();
		while (iterator.hasNext()){
			ConfigurationSection usersection = usersTable.getConfigurationSection(iterator.next());
			if (usersection.getInt("points") == points){
				UsersList.add(getUserByName(usersection.getName()));
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
		return new Reward()
		.setId(rewardSection.getName())
		.setSummary(rewardSection.getString("summary"))
		.setCost(rewardSection.getInt("cost"))
		.setCommands(rewardSection.getStringList("commands"))
		.setPermNodes(rewardSection.getStringList("permissions"));
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
			return -1;
		}
		return this.getRewardById(id).getCost();
	}
	
	@Override
	public List<String> getRewardCommands(String id){
		if (!this.getRewardExists(id)){
			List<String> emptyList = new ArrayList<String>();
			return emptyList;
		}
		return this.getRewardById(id).getCommands();
	}
	
	@Override
	public List<String> getRewardPermNodes(String id){
		if (!this.getRewardExists(id)){
			List<String> emptyList = new ArrayList<String>();
			return emptyList;
		}
		return this.getRewardById(id).getPerms();
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
		     plugin.saveResource("users.yml", false);
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
		if (plugin.getDataFolder() == null){
			plugin.getLogger().info("Plugin Data Folder is null");
		} else {
			plugin.getLogger().info("Plugin Data Folder is: " + plugin.getDataFolder().getName());
		}
		if (rewardsTableFile == null) {
		    rewardsTableFile = new File(plugin.getDataFolder(), "rewards.yml");
		}
		if (!rewardsTableFile.exists()) {            
		     plugin.saveResource("rewards.yml", false);
		}
	}
	
	// Users table setters
	
	@Override // writes individual user info to loaded table
	public void writeUser(User user){
		if (!getUserExists(user.getName())){
			this.getUsersTable().createSection(user.getName());
		}
		String usernode = user.getName() + ".";
		this.getUsersTable().set(usernode + "points", user.getPoints());
		this.getUsersTable().set(usernode + "last-online",user.getLastOnline());
		List<Map<String,String>> receiptlist = new ArrayList<Map<String,String>>();
		Iterator<Receipt> iter = user.getReceipts().iterator();
		while (iter.hasNext()){
			Receipt r = iter.next();
			HashMap<String,String> receipt = new HashMap<String,String>();
			receipt.put("date",r.getDate());
			receipt.put("summary", r.getSummary());
			receipt.put("cost", Integer.toString(r.getCost()));
			receiptlist.add(receipt);
		}
		this.getUsersTable().set(usernode + "receipts", receiptlist);
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
		String idnode = reward.getId() + ".";
		this.getRewardsTable().set(idnode + "summary", reward.getSummary());
		this.getRewardsTable().set(idnode + "cost",reward.getCost());
		this.getRewardsTable().set(idnode + "commands",reward.getCommands());
		this.getRewardsTable().set(idnode + "permissions", reward.getPerms());
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
