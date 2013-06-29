package arpinity.bosrewards.main;

import java.util.List;

public final class User {
	private final String name;
	private int points;
	private List<String> receipts;
	private String lastOnline;
	
	public User(String name) {
		this.name = name;
	}
	
	//Getters
	
	public String getName() {
		return this.name;
	}
	public int getPoints() {
		return this.points;
	}
	public List<String> getReceipts() {
		return this.receipts;
	}
	public String getLastOnline() {
		return this.lastOnline;
	}
	
	//Setters
	
	public User setPoints(int points) {
		this.points = points;
		return this;
	}
	public User addPoints(int points) {
		this.points += points;
		return this;
	}
	public User subtractPoints(int points) {
		this.points -= points;
		return this;
	}
	public User addReceipt(String receipt) {
		this.receipts.add(receipt);
		return this;
	}
	public User setLastOnline(String date) {
		this.lastOnline = date;
		return this;
	}
}