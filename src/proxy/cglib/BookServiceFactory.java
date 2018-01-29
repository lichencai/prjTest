package proxy.cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

public class BookServiceFactory {
	private static BookServiceBean service = new BookServiceBean();  
	 private BookServiceFactory() {  
	 }  
	 public static BookServiceBean getInstance() {  
	  return service;  
	 }  
	 /**
	  * �������������Ҫ������еĴ�����
	  */
	 public static BookServiceBean getProxyInstance(MyCglibProxy myProxy){    
	     Enhancer en = new Enhancer();     
	     //���д���     
	     en.setSuperclass(BookServiceBean.class);     
	     en.setCallback(myProxy);     
	     //���ɴ���ʵ��     
	     return (BookServiceBean)en.create();     
	 }
	 
	 public static BookServiceBean getAuthInstanceByFilter(MyCglibProxy myProxy){    
	     Enhancer en = new Enhancer();     
	     en.setSuperclass(BookServiceBean.class);     
	     en.setCallbacks(new Callback[]{myProxy, NoOp.INSTANCE, new ConcreteClassFixedValue()});     
	     en.setCallbackFilter(new MyProxyFilter());     
	     return (BookServiceBean)en.create();     
	 }
}
