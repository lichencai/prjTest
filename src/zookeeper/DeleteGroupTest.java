package zookeeper;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

public class DeleteGroupTest {
	private static final String HOSTS = "192.168.118.128:2181,192.168.118.128:2182,192.168.118.128:2183";
	  private static final String groupName = "licc";
	  
	  private DeleteGroup deleteGroup = null;
	  
	  public void init() throws IOException, InterruptedException {
	    deleteGroup = new DeleteGroup();
	    deleteGroup.connection(HOSTS);
	  }
	  
	  public void testDelete() throws IOException, InterruptedException, KeeperException {
	    deleteGroup.delete(groupName);
	  }
	  
	  public void destroy() throws InterruptedException {
	    if(null != deleteGroup){
	      try {
	        deleteGroup.close();
	      } catch (InterruptedException e) {
	        throw e;
	      }finally{
	        deleteGroup = null;
	        System.gc();
	      }
	    }
	  }
	  
	  public static void main(String[] args) throws Exception{
		  DeleteGroupTest test = new DeleteGroupTest();
		  test.init();
		  test.testDelete();
		  test.destroy();
	  }
	  
	  
}
