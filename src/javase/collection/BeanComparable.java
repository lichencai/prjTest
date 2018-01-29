package javase.collection;

/**
 * ��Ȼ����
 * ʹ��TreeMapʱ��key����ʵ��Comparable�ӿڣ�����ʵ������������ʹ�õĺ����
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
