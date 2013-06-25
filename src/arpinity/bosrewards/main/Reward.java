package arpinity.bosrewards.main;

import java.util.List;

public final class Reward {
	private String id;
	private String summary;
	private int cost = -1;
	private List<String> commands;
	
	
	// Getters

	public String getId(){
		return this.id;
	}
	public String getSummary(){
		return this.summary;
	}
	public int getCost(){
		return this.cost;
	}
	public List<String> getCommands(){
		return this.commands;
	}
	
	//Setters
	
	public void setId(String id){
		this.id = id;
	}
	public void setSummary(String summary){
		this.summary = summary;
	}
	public void setCost(int cost){
		this.cost = cost;
	}
	public void setFirstCommand(String command){
		if (this.commands.size() > 0) {
			this.commands.clear();
		}
		this.commands.add(command);
	}
	public void setCommands(List<String> commands){
		this.commands = commands;
	}
	public void addCommands(String command){
		this.commands.add(command);
	}
}
