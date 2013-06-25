package arpinity.bosrewards.datacontroller;

import java.util.List;

import arpinity.bosrewards.main.BOSRewards;
import arpinity.bosrewards.main.Reward;
import arpinity.bosrewards.main.User;

/**
 * Data Controller abstract
 * Skeleton for data controller classes, provides standard methods for interacting with databases.
 * Extending classes should handle database specifics, interacting with the plugin through these methods.
 * @author arpinity3024
 *
 */
public abstract class DataController {
	
	public final BOSRewards plugin;
	
	public DataController(BOSRewards plugin){
		this.plugin = plugin;
	}

	// Get Commands
	// Users
	public abstract boolean getUserExists(String user);
	public abstract int getUserPoints(String user);
	public abstract User getUserByName(String user);
	public abstract List<User> getUsersByPoints(int points);
	
	// Rewards
	public abstract boolean getRewardExists(String id);
	public abstract String getRewardSummary(String id);
	public abstract int getRewardCost(String id);
	public abstract Reward getRewardById(String id);
	public abstract List<String> getRewardCommands(String id);
	
	
	// Set Commands
	public abstract void writeUser(User user); // changes/creates individual user info, does not necessarily write to disk
	public abstract void writeUsers(); //writes whole user table to disk
	public abstract void writeReward(Reward reward); // changes/creates individual reward info, does not necessarily write to disk
	public abstract void writeRewards(); //writes whole rewards table to disk
	public abstract void removeRewardById(String id); //removes a reward from the rewards table
	
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