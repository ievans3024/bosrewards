package ievans3024.bosrewards.main;

public final class PagedArray {
	private final String[] content;
	private final int maxpages;
	
	public PagedArray(String[] c) {
		this.content = c;
		int maximum = c.length / 6;
		if (c.length % 6 != 0) {
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
		int length = 6;
		int index = (page * 6) - 6;
		if (this.content.length < 6) {
			length = this.content.length;
		} else if (this.content.length - index < 6) {
			length = this.content.length - index;
		}
		newarray = new String[length + 1];
		int i = 0;
		while (i < length) {
			newarray[i] = this.content[index + i];
			i++;
		}
		newarray[length] = "";
		return newarray;
	}
	
}
