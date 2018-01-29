package proxy.jdk;

public class PersonTest {
	public static void main(String[] args) {
		JDKProxyFactory factory = new JDKProxyFactory();
		
        PersonService bean = (PersonService) factory  
                .createProxyInstance(new PersonServiceBean("lucy"));  
        //�û�Ϊlucy����Ȩ��  
        bean.save("abc");  
        PersonService bean2 = (PersonService) factory  
            .createProxyInstance(new PersonServiceBean());  
        //�û�Ϊnull��û��Ȩ�ޣ������  
        bean2.save("abc");
	}
}
