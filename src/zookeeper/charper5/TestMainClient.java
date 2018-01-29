package zookeeper.charper5;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class TestMainClient implements Watcher{

	protected static ZooKeeper zk = null;
    protected static Integer mutex;
    int sessionTimeout = 10000;
    protected String root;
    
    public TestMainClient(String connectStr){
    	if(zk == null){
    		try{
    			//String configFile = this.getClass().getResource("/").getPath()+"org/zk/leader/election/log4j.xml";
                //DOMConfigurator.configure(configFile);
                //System.out.println("创建一个新的连接:");
                zk = new ZooKeeper(connectStr, sessionTimeout, this);
                mutex = new Integer(-1);
    		}catch(IOException e) {
                zk = null;
            }
    	}
    }
    
    
	@Override
	public void process(WatchedEvent event) {
		synchronized (mutex){
            mutex.notify();
        }
	}
	
}
