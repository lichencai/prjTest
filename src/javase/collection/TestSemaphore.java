package javase.collection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
/**
 * ���Ʋ������߳�����
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
					System.out.println("�߳�" + Thread.currentThread().getName() + "����,����" + (3 - sp.availablePermits()) + "����");
					
					try {
						Thread.sleep((long)(Math.random() * 3000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					//System.out.println("�߳�" + Thread.currentThread().getName() + "�뿪");
					sp.release();
					System.out.println("�߳�" + Thread.currentThread().getName() + "�뿪,����" + (3 - sp.availablePermits()) + "����");
					
				}
			};
			
			
			pool.execute(runnable);
		}
		
	}
}
