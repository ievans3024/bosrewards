package ievans3024.bosrewards.main;

public final class Receipt {
	
	private final String date;
	private final String summary;
	private final int cost;

	public Receipt(String date, String summary, int cost) {
		this.date = date;
		this.summary = summary;
		this.cost = cost;
	}
	
	public final String getDate() {
		return this.date;
	}
	
	public final String getSummary() {
		return this.summary;
	}
	
	public final int getCost() {
		return this.cost;
	}
	

}
