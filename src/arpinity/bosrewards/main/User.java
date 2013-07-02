package arpinity.bosrewards.main;

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
		int[] tablepad = {12,0};
		for (int i=(this.receipts.size() - 1);i>=0;i--) {
			int sumlength = this.receipts.get(i).getSummary().length() + 2;
			if (sumlength > tablepad[1]) {
				tablepad[1] = sumlength;
			}
		}
		Iterator<Receipt> iterator = this.receipts.iterator();
		while (iterator.hasNext()) {
			Receipt receipt = iterator.next();
			String date = String.format("%1$-" + tablepad[0] + "s", receipt.getDate());
			String summary = String.format("%1$-" + tablepad[1] + "s", receipt.getSummary());
			receiptlist.add(Messages.COLOR_INFO + date + summary + receipt.getCost());
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