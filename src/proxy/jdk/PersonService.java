package proxy.jdk;

public interface PersonService {
	public String getPersonName(Integer personId);  
    public void save(String name);  
    public void update(Integer personId, String name);
}
