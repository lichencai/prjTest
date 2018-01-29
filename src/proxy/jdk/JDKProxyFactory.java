package proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxyFactory implements InvocationHandler{  
	private Object proxyObject; //目标对象
	/** 
     * 绑定委托对象并返回一个代理类  
     * @param proxyObject 
     * @return 
     */  
    public Object createProxyInstance(Object proxyObject) {  
        this.proxyObject = proxyObject;  
          
        //生成代理类的字节码加载器   
        ClassLoader classLoader = proxyObject.getClass().getClassLoader();  
        //需要代理的接口，被代理类实现的多个接口都必须在这里定义  (这是一个缺陷，cglib弥补了这一缺陷)    
        Class<?>[] proxyInterface = proxyObject.getClass().getInterfaces();//new Class[]{};   
          
        //织入器，织入代码并生成代理类     
        return Proxy.newProxyInstance(classLoader, proxyInterface, this);  
  
    }
    @Override  
    public Object invoke(Object proxy, Method method, Object[] args)  
            throws Throwable {  
        PersonServiceBean bean = (PersonServiceBean)this.proxyObject;  
        Object result = null;  
        //控制哪些用户执行切入逻辑  
        if(bean.getUser() != null) {  
            //执行原有逻辑     
            result = method.invoke(this.proxyObject, args);
            System.out.println("方法执行后进行处理...");
        }
        return result;  
    }
}
