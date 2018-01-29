package proxy.jdk;

public class PersonTest {
	public static void main(String[] args) {
		JDKProxyFactory factory = new JDKProxyFactory();
		
        PersonService bean = (PersonService) factory  
                .createProxyInstance(new PersonServiceBean("lucy"));  
        //用户为lucy，有权限  
        bean.save("abc");  
        PersonService bean2 = (PersonService) factory  
            .createProxyInstance(new PersonServiceBean());  
        //用户为null，没有权限，不输出  
        bean2.save("abc");
	}
}
