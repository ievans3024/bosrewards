package arpinity.bosrewards;

import java.util.List;

public class User {
	private String name;
	private int points;
	private List<String> reciepts;
	
	//Getters
	
	public final String getName(){
		return this.name;
	}
	public final int getPoints(){
		return this.points;
	}
	public final List<String> getReciepts(){
		return this.reciepts;
	}
	
	//Setters
	
	public final void setName(String name){
		this.name = name;
	}
	public final void setPoints(int points){
		this.points = points;
	}
	public final void addPoints(int points){
		this.points += points;
	}
	public final void subtractPoints(int points){
		this.points -= points;
	}
	public final void addReciept(String reciept){
		this.reciepts.add(reciept);
	}
}