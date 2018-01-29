package aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeServerHandler implements Runnable{
	
	private int port ;
	CountDownLatch latch;
	AsynchronousServerSocketChannel asynchronousServerSocketChannel;
	
	public AsyncTimeServerHandler(int port){
		this.port = port;
		try{
			asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
			asynchronousServerSocketChannel.bind(new InetSocketAddress(this.port));
			System.out.println("The time server is start in port : " + port);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		latch = new CountDownLatch(1);
		doAccept();
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void doAccept(){
		//  ��һ�������ǻص�����Ҫ����ȥ�Ĳ���A��
		//  �ڶ����ǻص�ʵ���࣬��accept�����Ļص���Ҫ�Ļص���ʵ��<AsynchronousSocketChannel, ? super A>
		asynchronousServerSocketChannel.accept(this, new AcceptCompletionHandler());
	}
	
	
}
