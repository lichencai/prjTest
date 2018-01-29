package javase.collection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
/**
 * 控制并发的线程数量
 */
public class TestSemaphore {
	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();
		
		final Semaphore sp = new Semaphore(3, true);
		
		for(int i = 0; i < 10; i++){
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() {
					try {
						sp.acquire();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//System.out.println(sp.availablePermits());
					System.out.println("线程" + Thread.currentThread().getName() + "进入,已有" + (3 - sp.availablePermits()) + "并发");
					
					try {
						Thread.sleep((long)(Math.random() * 3000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					//System.out.println("线程" + Thread.currentThread().getName() + "离开");
					sp.release();
					System.out.println("线程" + Thread.currentThread().getName() + "离开,已有" + (3 - sp.availablePermits()) + "并发");
					
				}
			};
			
			
			pool.execute(runnable);
		}
		
	}
}
