package javase.collection.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TestArrayBlockingQueue {
	public static void main(String[] args) throws InterruptedException {
		ArrayBlockingQueue<QueueObj> queue = new ArrayBlockingQueue<QueueObj>(10);
		
		
		LinkedBlockingQueue<QueueObj> link = new LinkedBlockingQueue<QueueObj>();
		
		for(int i = 0; i < 20; i++){
			QueueObj obj = new QueueObj();
			link.offer(obj, 1000, TimeUnit.MILLISECONDS);
			System.out.println(i);
		}
	}
}
