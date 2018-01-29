package javase.collection;

/**
 * 自然排序
 * 使用TreeMap时，key必须实现Comparable接口，即可实现排序，排序所使用的红黑树
 */
public class BeanComparable implements Comparable<BeanComparable>{
	
	private int price = 0;

	public BeanComparable(int price){
		this.price = price;
	}
	
	@Override
	public int compareTo(BeanComparable o) {
		return this.price - o.price;
	}

	public int getPrice() {
		return price;
	}
	
}
