package util;

public class PrintUtil {
	
	public static void printf(Object obj) {
		if(obj instanceof java.util.List) {
			for(Object each : (java.util.List)obj)
				System.out.print(each);
		}else {
			System.out.println(obj);
		}
	}
}
