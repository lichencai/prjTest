package proxy.cglib;

public class BookServiceBean {
	public void create(){     
        System.out.println("create() is running !");     
    }     
    public void query(){     
        System.out.println("query() is running !");     
    }     
    public int update(int n){     
        System.out.println("update() is running !");
        return n;
    }     
    public void delete(){     
        System.out.println("delete() is running !");     
    }
}
