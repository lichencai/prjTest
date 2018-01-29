package javase.collection.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TestThreadPoolExecutor {
	
	//final BlockingQueue<Runnable> queue = new SynchronousQueue<Runnable>();
	final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(1);
	
	final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 4, 10, TimeUnit.SECONDS, queue, 
			Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
	
	final AtomicInteger completeTask = new AtomicInteger(0);
	final AtomicInteger rejectedTask = new AtomicInteger(0);
	
	static long beginTime;
	
	final int count = 10;
	
	public static void main(String[] args) {
		
		beginTime = System.currentTimeMillis();
		
		TestThreadPoolExecutor test = new TestThreadPoolExecutor();
		
		test.start();
		
	}
	
	
	public void start(){
		CountDownLatch latch = new CountDownLatch(count);
		CyclicBarrier barrier = new CyclicBarrier(count);
		
		for(int i = 0; i < count; i++){
			new Thread(new TestThread(latch, barrier)).start();
		}
		
		try {
			latch.await();
			executor.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	class TestThread implements Runnable{

		private CountDownLatch latch = null;
		private CyclicBarrier barrier = null;
		
		public TestThread(CountDownLatch latch, CyclicBarrier barrier){
			this.latch = latch;
			this.barrier = barrier;
		}
		
		@Override
		public void run() {
			try {
				barrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			try{
				executor.execute(new Task(latch));
			} catch(RejectedExecutionException e){
				latch.countDown();
				System.out.println("被拒绝的任务数:" + rejectedTask.incrementAndGet());
			}
		}
		
	}

	class Task implements Runnable{

		private CountDownLatch latch = null;
		
		public Task(CountDownLatch latch){
			this.latch = latch;
		}
		
		@Override
		public void run() {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("执行的任务数为：" + completeTask.incrementAndGet());
			System.out.println("任务耗时：" + (System.currentTimeMillis() - beginTime) + " ms");
			latch.countDown();
		}
		
	}
}









