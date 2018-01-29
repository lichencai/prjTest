package javase.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

public class TestTreeMap {
	public static void main(String[] args) {
		testBeanComparator();
		testBeanComparable();
		testSortList();
		testTreeMap();
	}
	
	
	public static void testTreeMap(){
		//  
		TreeMap<Object, Object> map = new TreeMap<Object, Object>();
		map.put(new Object(), new Object());
	}
	
	
	public static void testBeanComparator(){
		System.out.println("==========================testBeanComparator=========================");
		SortBean sortBean = new SortBean();
		TreeMap<BeanComparator, String> tmap = new TreeMap<BeanComparator, String>(sortBean);
		tmap.put(new BeanComparator(-1), "-1");
		tmap.put(new BeanComparator(-3), "-3");
		tmap.put(new BeanComparator(7), "7");
		for(BeanComparator each : tmap.keySet()){
			System.out.println(tmap.get(each));
		}
	}
	
	
	/**
	 * 测试TreeMap中key的对象实现Comparable接口
	 */
	public static void testBeanComparable(){
		System.out.println("==========================testBeanComparable=========================");
		TreeMap<BeanComparable, String> tmap = new TreeMap<BeanComparable, String>(); 
		tmap.put(new BeanComparable(1), "1");
		tmap.put(new BeanComparable(2), "2");
		tmap.put(new BeanComparable(3), "3");
		tmap.put(new BeanComparable(3), "4");
		tmap.put(new BeanComparable(-1), "-1");
		
		Entry<BeanComparable, String> entrys = tmap.higherEntry(new BeanComparable(1));
		System.out.println(entrys.getKey().getPrice());
		
		System.out.println("===================================================");
		
		for(BeanComparable each : tmap.keySet()){
			System.out.println(tmap.get(each));
		}
	}
	
	/**
	 * 对象实现Comparable接口，通过Collections.sort实现排序
	 */
	public static void testSortList(){
		System.out.println("==========================testSortList=========================");
		List<BeanComparable> comparableList = new ArrayList<BeanComparable>();
		comparableList.add(new BeanComparable(-1));
		comparableList.add(new BeanComparable(-2));
		comparableList.add(new BeanComparable(1));
		Collections.sort(comparableList);
		for(BeanComparable each : comparableList){
			System.out.println(each.getPrice());
		}
		
		System.out.println("===================================================");
		
		
		List<BeanComparator> comparatorList = new ArrayList<BeanComparator>();
		comparatorList.add(new BeanComparator(-1));
		comparatorList.add(new BeanComparator(-2));
		comparatorList.add(new BeanComparator(-2));
		comparatorList.add(new BeanComparator(1));
		Collections.sort(comparatorList, new SortBean());	//  设置Comparator<?>来设置排序的顺序
		for(BeanComparator each : comparatorList){
			System.out.println(each.getPrice());
		}
		
	}
	
	
	
	
	
}
