package zookeeper.charper4;

import java.nio.charset.Charset;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import zookeeper.ConnectionWatcher;

public class ActiveKeyValueStore extends ConnectionWatcher {
	
	private static final Charset CHARSET = Charset.forName("UTF-8");
	public void write(String path, String value) throws KeeperException, InterruptedException{
		Stat stat = zk.exists(path, true);
		if(stat == null){
			zk.create(path, value.getBytes(CHARSET), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}else{
			zk.setData(path, value.getBytes(CHARSET), -1);
		}
	}
	
	
	public String read(String path, Watcher watch) throws KeeperException, InterruptedException{
		byte[] data = zk.getData(path, watch, null);
		return new String(data, CHARSET);
	}
	

}
