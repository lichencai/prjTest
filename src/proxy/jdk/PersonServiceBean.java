package proxy.jdk;

public class PersonServiceBean implements PersonService {

	public String user = null; 
	public PersonServiceBean(){};  
    public PersonServiceBean(String user){  
        this.user = user;  
    }
	@Override
	public String getPersonName(Integer personId) {
		System.out.println("����find����");  
        return this.user;
	}

	@Override
	public void save(String name) {
		System.out.println("����save����"); 
	}

	@Override
	public void update(Integer personId, String name) {
		System.out.println("����update����"); 
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}
