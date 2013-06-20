package arpinity.bosrewards;

public class User {
	private String name;
	private int points;
	
	//Getters
	
	public final String getName(){
		return this.name;
	}
	public final int getPoints(){
		return this.points;
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
}