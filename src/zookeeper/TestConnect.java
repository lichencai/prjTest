package zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class TestConnect {
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		ZooKeeper zk = new ZooKeeper("192.168.118.128:2181", 500000, new Watcher() {
	         // 监控所有被触发的事件
             public void process(WatchedEvent event) {
            	 System.out.println(event.getPath());
             }
	    });
		
		//创建一个节点root，数据是mydata,不进行ACL权限控制，节点为永久性的(即客户端shutdown了也不会消失)
	    zk.create("/root", "mydata".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
	    zk.create("/root/childone","childone".getBytes(), Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
	    
	    zk.getChildren("/root",true);
	    
	    zk.getData("/root/childone", true, null);
	    
	    zk.setData("/root/childone","childonemodify".getBytes(), -1);
	    
	    zk.delete("/root/childone", -1);
	    
	    zk.close();
	}
}
