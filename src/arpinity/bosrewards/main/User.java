package arpinity.bosrewards.main;

import java.util.List;

public final class User {
	private String name;
	private int points;
	private List<String> receipts;
	
	//Getters
	
	public String getName(){
		return this.name;
	}
	public int getPoints(){
		return this.points;
	}
	public List<String> getReceipts(){
		return this.receipts;
	}
	
	//Setters
	
	public void setName(String name){
		this.name = name;
	}
	public void setPoints(int points){
		this.points = points;
	}
	public void addPoints(int points){
		this.points += points;
	}
	public void subtractPoints(int points){
		this.points -= points;
	}
	public void addReceipt(String receipt){
		this.receipts.add(receipt);
	}
}