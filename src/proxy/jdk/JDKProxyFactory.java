package proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxyFactory implements InvocationHandler{  
	private Object proxyObject; //Ŀ�����
	/** 
     * ��ί�ж��󲢷���һ��������  
     * @param proxyObject 
     * @return 
     */  
    public Object createProxyInstance(Object proxyObject) {  
        this.proxyObject = proxyObject;  
          
        //���ɴ�������ֽ��������   
        ClassLoader classLoader = proxyObject.getClass().getClassLoader();  
        //��Ҫ����Ľӿڣ���������ʵ�ֵĶ���ӿڶ����������ﶨ��  (����һ��ȱ�ݣ�cglib�ֲ�����һȱ��)    
        Class<?>[] proxyInterface = proxyObject.getClass().getInterfaces();//new Class[]{};   
          
        //֯������֯����벢���ɴ�����     
        return Proxy.newProxyInstance(classLoader, proxyInterface, this);  
  
    }
    @Override  
    public Object invoke(Object proxy, Method method, Object[] args)  
            throws Throwable {  
        PersonServiceBean bean = (PersonServiceBean)this.proxyObject;  
        Object result = null;  
        //������Щ�û�ִ�������߼�  
        if(bean.getUser() != null) {  
            //ִ��ԭ���߼�     
            result = method.invoke(this.proxyObject, args);
            System.out.println("����ִ�к���д���...");
        }
        return result;  
    }
}
