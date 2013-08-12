package ievans3024.bosrewards.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class User {
	private final String name;
	private int points;
	private List<Receipt> receipts;
	private String lastOnline;
	
	public User(String name) {
		this.name = name;
		this.receipts = new ArrayList<Receipt>();
	}
	
	public User(String name, int points, List<Receipt> receipts, String date){
		this.name = name;
		this.points = points;
		this.receipts = receipts;
		this.lastOnline = date;
	}
	
	//Getters
	
	public String getName() {
		return this.name;
	}
	public int getPoints() {
		return this.points;
	}
	public List<Receipt> getReceipts() {
		return this.receipts;
	}
	public List<String> getReceiptsList() {
		List<String> receiptlist = new ArrayList<String>();
		Iterator<Receipt> iterator = this.receipts.iterator();
		while (iterator.hasNext()) {
			Receipt receipt = iterator.next();
			receiptlist.add(Messages.COLOR_INFO + receipt.getDate());
			receiptlist.add(Messages.COLOR_INFO + receipt.getSummary() + "    " + receipt.getCost());
			receiptlist.add("");
		}			
		return receiptlist;
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
	public User addReceipt(Receipt receipt) {
		this.receipts.add(receipt);
		return this;
	}
	public User setLastOnline(String date) {
		this.lastOnline = date;
		return this;
	}
}