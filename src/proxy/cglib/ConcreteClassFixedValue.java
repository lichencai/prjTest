package proxy.cglib;

import net.sf.cglib.proxy.FixedValue;

/**
 * ��ʾ������������ֵ�����۱�������ķ�������ʲôֵ���ص����������ع̶�ֵ��
 * ��ֵ��һ��int������
 */
public class ConcreteClassFixedValue implements FixedValue{

	@Override
	public Object loadObject() throws Exception {
		System.out.println("update������Զ���ظ÷�����ֵ....");
		return 999;
	}

}
