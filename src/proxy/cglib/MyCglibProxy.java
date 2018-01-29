package proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * �������ʵ�֣�����intercept���ڶ��󷽷�����֮ǰ���Ѿ���������
 */
public class MyCglibProxy implements MethodInterceptor {

	public Enhancer enhancer = new Enhancer();
	private String name;

	public MyCglibProxy(String name) {
		this.name = name;
	}

	@Override
	public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		System.out.println("���õķ����ǣ�" + method.getName());
		// �û������ж�
		if (!"boss".equals(name)) {
			System.out.println("��û��Ȩ�ޣ�");
			return null;
		}
		Object result = methodProxy.invokeSuper(object, args);
		return result;
	}

	@SuppressWarnings("rawtypes")
	public Object getDaoBean(Class cls) {
		enhancer.setSuperclass(cls);
		enhancer.setCallback(this);
		return enhancer.create();
	}

}