package zookeeper.charper4;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.KeeperException;

public class ConfigUpdater {
	public static final String PATH = "/config";
	private ActiveKeyValueStore store;
	private Random random = new Random();
	private String lineSeparator = System.getProperty("line.separator");
	
	public ConfigUpdater(String hosts) throws IOException, InterruptedException {
        store = new ActiveKeyValueStore();
        store.connection(hosts);
    }
	
	public void run() throws KeeperException, InterruptedException{
		for(int i = 0; i < 10; i++){
			String value = random.nextInt(100) + "";
			store.write(PATH, value);
			System.out.printf("set %s to %s %s", PATH, value, lineSeparator);
			TimeUnit.SECONDS.sleep(random.nextInt(10));
		}
	}
	
	public static void main(String[] args) throws Exception{
		String hosts = "192.168.118.130:2181";
		ConfigUpdater updater = new ConfigUpdater(hosts);
		updater.run();
		Thread.sleep(Long.MAX_VALUE);
	}
	
	
}
