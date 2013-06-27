package arpinity.bosrewards.main;

public final class ToolBox {
	public static String arrayToString(String[] array, int start, int finish){
		StringBuilder sb = new StringBuilder(array[start]);
		if (start >= array.length || finish < start){
			return "";
		}
		if (finish > array.length){
			finish = array.length;
		}
		for(int i=start+1; i<finish; i++){
			sb.append(" ").append(array[i]);
		}
		String s = sb.toString();
		return s;
	}
	
	public static String[] stringToArray(String string) {
		String[] array = {string};
		return array;
	}
	
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
}
