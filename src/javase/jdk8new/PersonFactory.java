package javase.jdk8new;

public interface PersonFactory<P extends Person> {
	P create(String firstName, String lastName);
}
