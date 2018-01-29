package zookeeper.charper5;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class Synchronizing extends TestMainClient {
	
	int size;
    String name;
    public static final Logger logger = Logger.getLogger(Synchronizing.class);
    
    
	public Synchronizing(String connectStr, String root, int size) {
		super(connectStr);
		this.root = root;
        this.size = size;
 
        if (zk != null) {
            try {
                Stat s = zk.exists(root, false);
                if (s == null) {
                    zk.create(root, new byte[0], Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
                }
            } catch (KeeperException e) {
                logger.error(e);
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
        try {
            name = new String(InetAddress.getLocalHost().getCanonicalHostName().toString());
        } catch (UnknownHostException e) {
            logger.error(e);
        }
	}
	
	void addQueue() throws KeeperException, InterruptedException{
        zk.exists(root + "/start",true);
        zk.create(root + "/" + name, new byte[0], Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("addQueue");
        synchronized (mutex) {
            List<String> list = zk.getChildren(root, false);
            System.out.println("===");
            if (list.size() < size) {
                mutex.wait();
            } else {
                zk.create(root + "/start", new byte[0], Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            }
        }
    }
 
    @Override
    public void process(WatchedEvent event) {
        if(event.getPath().equals(root + "/start") && event.getType() == Event.EventType.NodeCreated){
            System.out.println("得到通知");
            super.process(event);
            doAction();
        }
    }
 
    private void doAction(){
        System.out.println("同步队列已经得到同步，可以开始执行后面的任务了");
    }
    
    public static void main(String args[]) {
        String connectString = "192.168.118.130:2181";
        int size = 2;
        final Synchronizing b = new Synchronizing(connectString, "/synchronizing", size);
        try{
        	
        	new Thread(new Runnable(){

				@Override
				public void run() {
					try {
						b.addQueue();
					} catch (KeeperException | InterruptedException e) {
						e.printStackTrace();
					}
				}
        		
        	}).start();
        	
        	Thread.sleep(2000);
        	
        	new Thread(new Runnable(){

				@Override
				public void run() {
					try {
						b.addQueue();
					} catch (KeeperException | InterruptedException e) {
						e.printStackTrace();
					}
				}
        		
        	}).start();
        	
        } catch (Exception e){
            logger.error(e);
        }
    }

}
