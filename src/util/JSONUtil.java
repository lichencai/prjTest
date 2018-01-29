package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONUtil {
	
	public static String map2JsonStr(Map<String, String> map){
		return JSONObject.fromObject(map).toString();
	}
	
	@SuppressWarnings("rawtypes")
	public static String list2JsonStr(List list){
		return JSONArray.fromObject(list).toString();
	}
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("123", "123");
		map.put("321", "321");
		System.out.println(map2JsonStr(map));
		
		
		
		List<Object> list = new ArrayList<Object>();
		list.add(map);
		System.out.println(list2JsonStr(list));
	}
	
	
	
	
	
	
}
