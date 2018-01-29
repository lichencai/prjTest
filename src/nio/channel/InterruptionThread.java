package nio.channel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class InterruptionThread extends Thread{

	private FileChannel channel = null;
	
	public InterruptionThread(FileChannel channel){
		this.channel = channel;
	}
	
	@Override
	public void run() {
		while(!this.isInterrupted()){
			ByteBuffer dst = ByteBuffer.allocate(1024);
			try {
				int size = 0;
				while((size = channel.read(dst)) > 0){
					System.out.println("size:" + size);
					byte[] b = new byte[size];
					dst.flip();
					dst.get(b, 0, dst.remaining());
					String content = new String(b, "utf-8");
					System.out.println(content);
					dst.clear();
					for(int i = 0; i < Integer.MAX_VALUE; i++){
						for(int j = 0; j < Integer.MAX_VALUE; j++){
							
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		FileInputStream fis = new FileInputStream("D:\\temp\\ResJZGJThread.java");
		FileChannel channel = fis.getChannel();
		InterruptionThread thread = new InterruptionThread(channel);
		thread.start();
		Thread.sleep(2000);
		thread.interrupt();
	}
}
