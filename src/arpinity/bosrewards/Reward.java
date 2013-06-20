package arpinity.bosrewards;

import java.util.List;

public class Reward {
	private String id;
	private String summary;
	private int cost;
	private List<String> commands;
	
	
	// Getters

	public final String getId(){
		return id;
	}
	public final String getSummary(){
		return summary;
	}
	public final int getCost(){
		return cost;
	}
	public final List<String> getCommands(){
		return commands;
	
	}
	
	//Setters
	
	public final void setId(String id){
		this.id = id;
	}
	public final void setSummary(String summary){
		this.summary = summary;
	}
	public final void setCost(int cost){
		this.cost = cost;
	}
	public final void setFirstCommand(String command){
		this.commands.set(0,command);
	}
	public final void setCommands(List<String> commands){
		this.commands = commands;
	}
	public final void addCommands(String command){
		this.commands.add(command);
	}
}
