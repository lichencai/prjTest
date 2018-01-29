package javase.collection.testThreadFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class TestThreadFactory implements ThreadFactory{

	private static AtomicInteger threadCount = new AtomicInteger(0); 
	
	@Override
	public Thread newThread(Runnable r) {
		threadCount.incrementAndGet();
		Thread thread = new Thread(r);
		thread.setName("TestThreadFactory-" + threadCount.get());
		return thread;
	}
	
	public static void main(String[] args) {
	}
}
