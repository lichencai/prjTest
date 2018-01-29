package zookeeper.charper5;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

public class Locks extends TestMainClient{
	
	public static final Logger logger = Logger.getLogger(Locks.class);
	String myZnode;
	
	
	public Locks(String connectStr, String root) {
		super(connectStr);
		this.root = root;
		if (zk != null) {
            try {
                Stat s = zk.exists(root, false);
                if (s == null) {
                    zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
            } catch (KeeperException e) {
                logger.error(e);
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
	}
	
	void getLock() throws KeeperException, InterruptedException{
        List<String> list = zk.getChildren(root, false);
        String[] nodes = list.toArray(new String[list.size()]);
        Arrays.sort(nodes);
        if(myZnode.equals(root+"/"+nodes[0])){
            doAction();
        }else{
            waitForLock(nodes[0]);
        }
    }
	private void doAction(){
        System.out.println(myZnode + " 同步队列已经得到同步，可以开始执行后面的任务了");
    }
	
	void waitForLock(String lower) throws InterruptedException, KeeperException {
		System.out.println(myZnode + " wait for " + root + "/" + lower);
        Stat stat = zk.exists(root + "/" + lower,true);
        if(stat != null){
        	synchronized (mutex) {
        		mutex.wait();
			}
        }else{
            getLock();
        }
    }
	
	@Override
    public void process(WatchedEvent event) {
		System.out.println(myZnode + " " + event.getType());
        if(event.getType() == Event.EventType.NodeDeleted){
            System.out.println(myZnode + " 得到通知");
            super.process(event);
            try {
				getLock();
			} catch (KeeperException | InterruptedException e) {
				e.printStackTrace();
			}
        }
    }
	
	void check() throws InterruptedException, KeeperException {
        myZnode = zk.create(root + "/lock_" , new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
        getLock();
    }
	
	public static void main(String[] args) {
        final String connectString = "192.168.118.130:2181";
        
        Thread[] threads = new Thread[10];
        for(int i = 0; i < threads.length; i++){
        	threads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					Locks lk = new Locks(connectString, "/locks");
			        try {
			            lk.check();
			        } catch (InterruptedException e) {
			            logger.error(e);
			        } catch (KeeperException e) {
			            logger.error(e);
			        }
				}
			});
        }
        
        for(int i = 0; i < threads.length; i++){
        	threads[i].start();
        }
        
        
        
        
        
    }

}
