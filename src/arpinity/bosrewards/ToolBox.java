package arpinity.bosrewards;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ToolBox {
	public static String arrayToString(String[] array, String delimiter){
		List<String> arrayList = Arrays.asList(array);
		Iterator<String> iterator = arrayList.iterator();
		String string = null;
		while (iterator.hasNext()){
			string += iterator.next();
			string += delimiter;
		}
		return string;
	}
}
