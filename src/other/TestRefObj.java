package other;

import java.util.List;

public class TestRefObj {
	public TestRefObj(){
		
	}
	
	
	public String testRefMethod(List<String> param, String param1){
		System.out.println("param:" + param.get(0) + ",param1:" + param1);
		return "success";
	}
	
	
}
