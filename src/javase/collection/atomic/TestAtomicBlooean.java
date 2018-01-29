package javase.collection.atomic;

import java.util.concurrent.atomic.AtomicBoolean;

public class TestAtomicBlooean {
	
	private AtomicBoolean inited = new AtomicBoolean(false);
	
	public static void main(String[] args) {
		
		TestAtomicBlooean obj = new TestAtomicBlooean();
		Task task = new Task(obj);
		for(int i = 0; i < 10; i++){
			Thread thread = new Thread(task);
			thread.start();
		}
		
	}
	
	public void init(){
		if(inited.compareAndSet(false, true)){
			System.out.println("编写初始化代码...");
			
			
			
			
			return ;
		}
		
		System.out.println("编写其他代码...");
	}
}

class Task implements Runnable{

	private TestAtomicBlooean obj = null;
	
	public Task(TestAtomicBlooean obj){
		this.obj = obj;
	}
	
	@Override
	public void run() {
		obj.init();
	}
	
}

