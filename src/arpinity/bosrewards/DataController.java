package arpinity.bosrewards;

//import java.util.ArrayList;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Data Controller abstract
 * Skeleton for data controller classes, provides standard methods for interacting with databases.
 * Extending classes should handle database specifics, interacting with the plugin through these methods.
 * @author arpinity3024
 *
 */
abstract class DataController { 
	
	public BOSRewards plugin;

	// Get Commands
	// Users
	public abstract boolean getUserExists(String user);
	public abstract int getUserPoints(String user);
	
	// Rewards
	public abstract boolean getRewardExists(String id);
	public abstract String getRewardSummary(String id);
	public abstract int getRewardCost(String id);
	//public abstract ArrayList<String> getRewardCommands(String id);
	
	
	// Set Commands
	
	// Load/Reload Commands
	public abstract FileConfiguration getRewards();
	public abstract FileConfiguration getUsers();
	public abstract void reloadRewardsTable();
	public abstract void reloadUsersTable();
	public abstract void initializeRewardsTable();
	public abstract void initializeUsersTable();
}
