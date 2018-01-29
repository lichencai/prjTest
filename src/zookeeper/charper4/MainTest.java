package zookeeper.charper4;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

public class MainTest {
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		String hosts = "192.168.118.130:2181";
		ConfigUpdater updater = new ConfigUpdater(hosts);
		updater.run();
		Thread.sleep(Long.MAX_VALUE);
	}
}
