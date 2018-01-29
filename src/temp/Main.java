package temp;

import java.util.Random;

public class Main {
    public static StringBuffer encode(Integer id) {
        Integer uid = (id * 397) ^ 1136593273;
        System.out.println(uid);
        StringBuffer sb = new StringBuffer(uid.toString());
//        for (int i = 1, j = sb.length() - 1; i < sb.length() >>> 1; i++, j--) {
//            char temp = sb.charAt(i);
//            sb.setCharAt(i, sb.charAt(j));
//            sb.setCharAt(j, temp);
//        }
        
        
        return sb;
    }
    
    
    
    public static void main(String[] args) {
//    	encode(2);
    	
    	for(int i = 0; i < 100000; i++) {
        	int k = (int)((Math.random()*9+1)*100000);
        	
        	String s = Integer.toString(k);
        	System.out.println(s);
        	if(s.length() < 6) {
        		System.out.println(s);
        	}
        	
        	
        }
    	
    	
    }
    
    
}
