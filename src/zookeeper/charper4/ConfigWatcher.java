package zookeeper.charper4;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;

public class ConfigWatcher implements Watcher{

	private ActiveKeyValueStore store;
	
	@Override
	public void process(WatchedEvent event) {
		if(event.getType() == EventType.NodeDataChanged){
            try{
                dispalyConfig();
            }catch(InterruptedException e){
                System.err.println("Interrupted. exiting. ");
                Thread.currentThread().interrupt();
            }catch(KeeperException e){
                System.out.printf("KeeperExceptions. Exiting.\n", e);
            }
        }
	}
	
	public ConfigWatcher(String hosts) throws IOException, InterruptedException {
        store = new ActiveKeyValueStore();
        store.connection(hosts);
    }

	public void dispalyConfig() throws KeeperException, InterruptedException{
        String value = store.read(ConfigUpdater.PATH, this);
        System.out.printf("Read %s as %s\n",ConfigUpdater.PATH,value);
    }
	
	public static void main(String[] args) throws Exception{
		String hosts = "192.168.118.130:2181";
		ConfigWatcher watcher = new ConfigWatcher(hosts);
		watcher.dispalyConfig();
	    Thread.sleep(Long.MAX_VALUE);
	}
	
	
}
