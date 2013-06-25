package arpinity.bosrewards.main;

import java.util.List;

public final class User {
	private String name;
	private int points;
	private List<String> receipts;
	
	//Getters
	
	public final String getName(){
		return this.name;
	}
	public final int getPoints(){
		return this.points;
	}
	public final List<String> getReceipts(){
		return this.receipts;
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
	public final void addReceipt(String receipt){
		this.receipts.add(receipt);
	}
}