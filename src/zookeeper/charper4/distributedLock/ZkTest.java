package zookeeper.charper4.distributedLock;

import zookeeper.charper4.distributedLock.ConcurrentTest.ConcurrentTask;

public class ZkTest {
	public static void main(String[] args) {
		
		/*
        Runnable task1 = new Runnable(){
            public void run() {
                DistributedLock lock = null;
                try {
                    lock = new DistributedLock("192.168.118.130:2181", "test1");
                    lock.lock();
                    Thread.sleep(3000);
                    System.out.println("===Thread " + Thread.currentThread().getId() + " running");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    if(lock != null)
                        lock.unlock();
                }
            }
        };
        
        new Thread(task1).start();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        */
        
        ConcurrentTask[] tasks = new ConcurrentTask[6];
        
        for(int i=0;i<tasks.length;i++){
            
        	ConcurrentTask task3 = new ConcurrentTask(){
            	
                public void run() {
                    DistributedLock lock = null;
                    try {
                        lock = new DistributedLock("192.168.118.130:2183","test2");
                        lock.lock();
                        System.out.println("Thread " + Thread.currentThread().getId() + " running");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        lock.unlock();
                    }
                }
                
            };
            tasks[i] = task3;
        }
        
        new ConcurrentTest(tasks);
        
    }
}
