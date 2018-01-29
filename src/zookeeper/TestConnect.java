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
	         // ������б��������¼�
             public void process(WatchedEvent event) {
            	 System.out.println(event.getPath());
             }
	    });
		
		//����һ���ڵ�root��������mydata,������ACLȨ�޿��ƣ��ڵ�Ϊ�����Ե�(���ͻ���shutdown��Ҳ������ʧ)
	    zk.create("/root", "mydata".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
	    zk.create("/root/childone","childone".getBytes(), Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
	    
	    zk.getChildren("/root",true);
	    
	    zk.getData("/root/childone", true, null);
	    
	    zk.setData("/root/childone","childonemodify".getBytes(), -1);
	    
	    zk.delete("/root/childone", -1);
	    
	    zk.close();
	}
}
