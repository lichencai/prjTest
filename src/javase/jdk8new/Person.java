package javase.jdk8new;

public class Person {
	String firstName;
    String lastName;
    Person() {}
    Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public void saySomething(String something) {
    	System.out.println(firstName + lastName + " say : " + something);
    }
    
    public static void main(String[] args) {
    	//  这里一定要是一个接口方法
    	PersonFactory<Person> personFactory = Person::new;
    	Person person = personFactory.create("li", "chencai");
    	person.saySomething("hello");
	}
    
}
