package proxy.cglib;

import net.sf.cglib.proxy.FixedValue;

/**
 * 表示锁定方法返回值，无论被代理类的方法返回什么值，回调方法都返回固定值。
 * 该值是一个int的类型
 */
public class ConcreteClassFixedValue implements FixedValue{

	@Override
	public Object loadObject() throws Exception {
		System.out.println("update方法永远返回该方法的值....");
		return 999;
	}

}
