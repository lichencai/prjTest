package javase.collection;

import java.util.Comparator;

/**
 * 定制排序
 * 将接口和具体的实现分离，即是策略模式，面向接口进行编程
 * 环境持有接口，将接口通过set方法传入到环境中去
 */
public class SortBean implements Comparator<BeanComparator>{
	@Override
	public int compare(BeanComparator o1, BeanComparator o2) {
		return - (o1.getPrice() - o2.getPrice());
	}

}
