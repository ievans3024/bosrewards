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
}
