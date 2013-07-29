package ievans3024.bosrewards.main;

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
	
	public Reward setId(String id){
		this.id = id;
		return this;
	}
	public Reward setSummary(String summary){
		this.summary = summary;
		return this;
	}
	public Reward setCost(int cost){
		this.cost = cost;
		return this;
	}
	public Reward setFirstCommand(String command){
		if (this.commands.size() > 0) {
			this.commands.clear();
		}
		this.commands.add(command);
		return this;
	}
	public Reward setCommands(List<String> commands){
		this.commands = commands;
		return this;
	}
	public Reward addCommands(String command){
		this.commands.add(command);
		return this;
	}
	public Reward removeCommands(int cmdindex){
		this.commands.remove(cmdindex);
		return this;
	}
}
