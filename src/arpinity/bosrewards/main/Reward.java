package arpinity.bosrewards.main;

import java.util.List;

public final class Reward {
	private String id;
	private String summary;
	private int cost = -1;
	private List<String> commands;
	
	
	// Getters

	public final String getId(){
		return this.id;
	}
	public final String getSummary(){
		return this.summary;
	}
	public final int getCost(){
		return this.cost;
	}
	public final List<String> getCommands(){
		return this.commands;
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
		if (this.commands.size() > 0) {
			this.commands.clear();
		}
		this.commands.add(command);
	}
	public final void setCommands(List<String> commands){
		this.commands = commands;
	}
	public final void addCommands(String command){
		this.commands.add(command);
	}
}
