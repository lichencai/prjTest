package proxy.jdk;

public class PersonServiceBean implements PersonService {

	public String user = null; 
	public PersonServiceBean(){};  
    public PersonServiceBean(String user){  
        this.user = user;  
    }
	@Override
	public String getPersonName(Integer personId) {
		System.out.println("这是find方法");  
        return this.user;
	}

	@Override
	public void save(String name) {
		System.out.println("这是save方法"); 
	}

	@Override
	public void update(Integer personId, String name) {
		System.out.println("这是update方法"); 
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}
