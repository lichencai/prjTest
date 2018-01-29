package javase.jdk8new;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import util.PrintUtil;

public class LambdaTest {
	
	public void test1() {
		List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
		Collections.sort(names, (a, b) -> b.compareTo(a));
		PrintUtil.printf(names);
	}
	
	
	public void test2(String a) {
		System.out.println(a);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void filter(List<String> names, Predicate condition) {
	    names.stream().filter((name) -> condition.test(name)).forEach((name) -> {
	        System.out.println(name + " ");
	    });
	}
	
	public static void main(String[] args) {
		LambdaTest test = new LambdaTest();
		//test.test1();
		//  这个变量会变成final类型的
		int i = 0;
		Arrays.asList( "a", "b", "d" ).forEach( e -> {
			System.out.println(e + "," + i);
//			i++;	//这样是无法进行编译的
		});
		
		Arrays.asList( "a", "d", "b" ).sort( ( e1, e2 ) -> e1.compareTo( e2 ) );
		
		List<String> sortList = Arrays.asList( "a", "d", "b", "a" );
		sortList.sort((e1, e2) -> e1.compareTo(e2));
		sortList.forEach(e1 -> System.out.println(e1));
		
		
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.forEach(a -> test.test2(a));
		
		
		
		new Thread(() -> System.out.println("123"));
		
		
		Collections.sort(sortList, (e1,e2) -> {
			return -1;
		});
		
		
		new Functional() {
			@Override
			public String sayFunctional(String name) {
				return null;
			}
		};
		
		
		Functional functional = new Functional() {
			@Override
			public String sayFunctional(String name) {
				return null;
			}
		};
		functional.sayFunctional("12");
		
		Functional functional1 = (s) -> {
			return s;
		};
		System.out.println(functional1.sayFunctional("123"));
		
		
		List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
		System.out.println("Languages which starts with J :");
	    filter(languages, (str) -> ((String) str).startsWith("J"));
	    
	    
	    
	    
	    
	    Predicate<String> startsWithJ = (n) -> n.startsWith("J");
	    Predicate<String> fourLetterLong = (n) -> n.length() == 4;
	    languages.stream()
	        .filter(startsWithJ.and(fourLetterLong))
	        .forEach((n) -> System.out.print("nName, which starts with 'J' and four letter long is : " + n));
	    
	    
	    
	    List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
		Object bill = costBeforeTax.stream().map((cost) -> cost + .12 * cost).reduce((sum, cost) -> sum + cost).get();
	    System.out.println("Total : " + bill);
	    
	    
	    
	    
	    Optional<String> firstName = Optional.of("Tom");
	    
	    
	    Function<String, Integer> toInteger = Integer::valueOf;
	    Function<String, String> backToString = toInteger.andThen(e -> {
	    	System.out.println(e.getClass());
	    	return "licc" + e;
	    });
	    
	    Function<String, Integer> toInteger1 = e -> {
	    	int e1 = Integer.valueOf(e);
	    	return e1 * e1;
	    };
	    
	    toInteger.apply("123");
	    String backToStr = backToString.apply("123123");
	    System.out.println(backToStr);
	}
}
