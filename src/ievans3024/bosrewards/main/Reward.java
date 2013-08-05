package ievans3024.bosrewards.main;

import java.util.Iterator;
import java.util.List;

import org.bukkit.command.CommandSender;

public final class Reward {
	private String id;
	private String summary;
	private int cost = -1;
	private List<String> commands;
	private List<String> permissions;
	
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
	public List<String> getPerms(){
		return this.permissions;
	}
	public boolean hasPerms(CommandSender sender){
		boolean hasPermission = false;
		if (this.permissions.isEmpty()){
			hasPermission = true;
		} else {
			Iterator<String> permiter = this.permissions.iterator();
			while (permiter.hasNext()){
				if (sender.hasPermission(permiter.next())){
					hasPermission = true;
				}
			}	
		}
		return hasPermission;
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
		if (this.commands.size() > 0){
			this.commands.clear();
		}
		this.commands.add(command);
		return this;
	}
	public Reward setCommands(List<String> commands){
		this.commands = commands;
		return this;
	}
	public Reward addCommand(String command){
		this.commands.add(command);
		return this;
	}
	public Reward removeCommand(int cmdindex){
		this.commands.remove(cmdindex);
		return this;
	}
	public Reward setPermNodes(List<String> perms){
		this.permissions = perms;
		return this;
	}
	public Reward setFirstPermNode(String perm){
		if (this.permissions.size() > 0){
			this.permissions.clear();
		}
		this.permissions.add(perm);
		return this;
	}
	public Reward addPermNode(String perm){
		this.permissions.add(perm);
		return this;
	}
	public Reward remPermNode(int permindex){
		this.permissions.remove(permindex);
		return this;
	}
}
