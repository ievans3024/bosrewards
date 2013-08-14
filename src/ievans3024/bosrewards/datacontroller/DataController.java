package ievans3024.bosrewards.datacontroller;

import ievans3024.bosrewards.main.BOSRewards;
import ievans3024.bosrewards.main.Reward;
import ievans3024.bosrewards.main.User;

import java.util.List;


/**
 * Data Controller abstract
 * 
 * Extending classes should handle database specifics, interacting with the plugin through these methods.
 * 
 * @author ievans3024
 */
public abstract class DataController {
	
	public final BOSRewards plugin;
	
	public DataController(BOSRewards plugin){
		this.plugin = plugin;
	}

	// ***** USER TABLE METHODS ***** //
	
	/**
	 * getUserExists
	 * 
	 * Finds if a user exists in the Users table.
	 * 
	 * @param username of a player on the server or in the Users table.
	 * @return true if the user exists, otherwise false.
	 */
	public abstract boolean getUserExists(String user);
	
	/**
	 * getUserPoints
	 * 
	 * Fetches the number of points a user has.
	 * 
	 * @param user username of a player on the server or in the Users table.
	 * @return less than zero if the user does not exist in the Users table, otherwise, the number of points the user has.
	 */
	public abstract int getUserPoints(String user);
	
	/**
	 * getUserByName
	 * 
	 * Fetches an object containing all rewards information for a particular user from the Users table.
	 * 
	 * @param user username of a player on the server or in the Users tables
	 * @return User object, see {@link #User}
	 */
	public abstract User getUserByName(String user);
	
	/**
	 * getUsersByPoints
	 * 
	 * An as-yet unused method which returns a List of User objects that have a certain number of points.
	 * 
	 * @param points the number of points to look for
	 * @return List<User>
	 */
	public abstract List<User> getUsersByPoints(int points);
	
	/**
	 * writeUser
	 * 
	 * Writes an individual user's information to the Users table.
	 * 
	 * Depending on the type of database being used, this method may or may not write to disk immediately. 
	 * 
	 * This method should overwrite existing user information with the same username.
	 * 
	 * @param user User object to write to the table.
	 */
	public abstract void writeUser(User user);
	
	/**
	 * writeUsers
	 * 
	 * Writes entire user table to disk.
	 * 
	 * Depending on the type of database being used, this method may be redundant or useless.
	 */
	public abstract void writeUsers(); 
	
	
	
	// ***** REWARDS TABLE METHODS ***** //
	
	/**
	 * getRewardExists
	 * 
	 * Finds out if a reward with the specified id exists.
	 * 
	 * @param id the id of a reward in the Rewards table.
	 * @return true if the reward exists, otherwise false.
	 */
	public abstract boolean getRewardExists(String id);
	
	/**
	 * getRewardSummary
	 * 
	 * Fetches the summary of a reward from the Rewards table.
	 * 
	 * @param id the id of the reward in question.
	 * @return summary text if the reward exists, otherwise null.
	 */
	public abstract String getRewardSummary(String id);
	
	/**
	 * getRewardCost
	 * 
	 * Fetches the cost of a reward from the Rewards table.
	 * 
	 * @param id the id the reward in question
	 * @return -1 if reward does not exist, otherwise, the cost of the reward.
	 */
	public abstract int getRewardCost(String id);
	
	/**
	 * getRewardById
	 * 
	 * Fetches an object containing all the information for a particular reward from the Rewards table.
	 * 
	 * @param id the id of a reward in the Rewards table.
	 * @return Reward, see {@link #Reward}
	 */
	public abstract Reward getRewardById(String id);
	
	/**
	 * Finds a reward entry in the Rewards table and removes it, if it exists.
	 * @param id the id of the reward to remove.
	 */
	public abstract void removeRewardById(String id); //removes a reward from the rewards table
	
	/**
	 * getRewardCommands
	 * 
	 * Fetches the command strings that will be executed when a user purchases a particular reward.
	 * 
	 * @param id the id of the reward in question.
	 * @return A List of Strings containing the command strings
	 */
	public abstract List<String> getRewardCommands(String id);
	
	/**
	 * Fetches list of permission nodes for a reward by id.
	 * @param id the id of the reward in question.
	 * @return A list of permission node strings
	 */
	public abstract List<String> getRewardPermNodes(String id);
	
	/**
	 * writeReward
	 * 
	 * Writes an individual reward to the Rewards table
	 * 
	 * Depending on the type of database being used, this method may or may not save changes to disk immediately.
	 * 
	 * This method should overwrite any existing rewards with the same id.
	 * 
	 * @param reward the Reward object to write to the Rewards Table
	 */
	public abstract void writeReward(Reward reward);
	
	/**
	 * writeRewards
	 * 
	 * Writes the whole Rewards table to disk.
	 * 
	 * Depending on the type of database being used, this method may be redundant or useless.
	 */
	public abstract void writeRewards(); //writes whole rewards table to disk

	
	// Load/Reload Commands
	public abstract void openDatabase();
	public abstract void closeDatabase();
	public abstract List<Reward> getRewards();
	public abstract List<User> getUsers();
	public abstract void reloadRewardsTable();
	public abstract void reloadUsersTable();
	public abstract void initializeRewardsTable();
	public abstract void initializeUsersTable();
}
