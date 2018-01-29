package other;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRef {
	public static void main(String[] args) throws Exception{
		TestRefObj obj = new TestRefObj();
		Class clazz = obj.getClass();
		Method m1 = clazz.getDeclaredMethod("testRefMethod", java.util.List.class, String.class);
		List<String> list = new ArrayList<String>();
		list.add("paramList");
		String result = (String)m1.invoke(obj, list, "param2");
		System.out.println(result);
	}
}
