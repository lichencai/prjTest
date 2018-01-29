package other;

import java.util.ArrayList;
import java.util.Iterator;

public class TestUnicode {
	public static void main(String[] args) {
		String unicode = "\\u9875\\u9762\\u7a97\\u53e3\\u7684\\u6253";
		String[] hex = unicode.split("\\\\u");
		StringBuffer sb = new StringBuffer();
		for(int i = 1; i < hex.length; i++){
			int data = Integer.parseInt(hex[i], 16);
			sb.append((char)data);
		}
		System.out.println(sb.toString());
		
		System.out.println("================================================================================");
		
		ArrayList<String> array = new ArrayList<String>();
		array.add("1");
		array.add("2");
		array.add("3");
		array.add("4");
		array.add("5");
		Iterator<String> iter = array.iterator();
		while(iter.hasNext()){
			String str = iter.next();
			if("3".equals(str)){
				iter.remove();
			}
		}
		for(String str : array){
			System.out.println(str);
		}
	}
}
