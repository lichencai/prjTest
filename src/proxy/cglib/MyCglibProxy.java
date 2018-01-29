package proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 代理类的实现，方法intercept是在对象方法调用之前就已经被调用了
 */
public class MyCglibProxy implements MethodInterceptor {

	public Enhancer enhancer = new Enhancer();
	private String name;

	public MyCglibProxy(String name) {
		this.name = name;
	}

	@Override
	public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		System.out.println("调用的方法是：" + method.getName());
		// 用户进行判断
		if (!"boss".equals(name)) {
			System.out.println("你没有权限！");
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