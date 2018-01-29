package proxy.cglib;

public class Test {
	public static void main(String[] args) {
		 /*BookServiceBean service = BookServiceFactory.getProxyInstance(new
		 MyCglibProxy("boss"));
		 service.create();
		 BookServiceBean service2 = BookServiceFactory.getProxyInstance(new
		 MyCglibProxy("john"));
		 service2.query();*/
		 
		BookServiceBean service = BookServiceFactory
				.getAuthInstanceByFilter(new MyCglibProxy("jhon"));
		service.create();
		BookServiceBean service2 = BookServiceFactory
				.getAuthInstanceByFilter(new MyCglibProxy("jhon"));
		service2.query();
		BookServiceBean service3 = BookServiceFactory
				.getAuthInstanceByFilter(new MyCglibProxy("jhon"));
		System.out.println(service3.update(1));
	}
}
