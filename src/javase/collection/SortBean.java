package javase.collection;

import java.util.Comparator;

/**
 * ��������
 * ���ӿں;����ʵ�ַ��룬���ǲ���ģʽ������ӿڽ��б��
 * �������нӿڣ����ӿ�ͨ��set�������뵽������ȥ
 */
public class SortBean implements Comparator<BeanComparator>{
	@Override
	public int compare(BeanComparator o1, BeanComparator o2) {
		return - (o1.getPrice() - o2.getPrice());
	}

}
