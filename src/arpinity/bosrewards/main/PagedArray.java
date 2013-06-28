package arpinity.bosrewards.main;

public final class PagedArray {
	private final String[] content;
	private final int maxpages;
	
	public PagedArray(String[] c) {
		this.content = c;
		int maximum = c.length / 5;
		if (c.length % 5 != 0) {
			maximum++;
		}
		this.maxpages = maximum;
	}
	
	public int getMaxPages() {
		return this.maxpages;
	}
	
	public String[] getPage(int page) {
		if (page < 1 || page > this.maxpages) {
			return null;
		}
		String[] newarray;
		newarray = new String[5];
		int index = (page * 5) - 5;
		for (int i=0;i < 5;i++) {
			newarray[i] = this.content[index + i];
		}
		return newarray;
	}
	
}
