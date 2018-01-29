package zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class ConnectionWatcher implements Watcher{

	private static final int SESSION_TIMEOUT = 50000;
	private CountDownLatch latch = new CountDownLatch(1);
	protected ZooKeeper zk = null;
	
	@Override
	public void process(WatchedEvent event) {
		KeeperState state = event.getState();
		System.out.println(String.format("process is trigger : %s", event.getType()));
		if(state == KeeperState.SyncConnected){
			latch.countDown();
		}
	}
	
	public void connection(String hosts) throws IOException, InterruptedException {
	    zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
	    latch.await();
	}
	
	public void close() throws InterruptedException {
	    if (null != zk) {
	      try {
	        zk.close();
	      } catch (InterruptedException e) {
	        throw e;
	      }finally{
	        zk = null;
	        System.gc();
	      }
	    }
	 }
	
}
