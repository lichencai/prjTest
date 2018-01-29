package other;

import java.io.File;
import java.util.UUID;

public class CreateThread {
	final static String dir = "D:\\temp\\jzd\\";
	public static void main(String[] args) {
		Thread[] thread = new Thread[10];
		for(int i = 0; i < 10; i++){
			thread[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					for(int i = 0; i < 100; i++){
						try{
							String fileName = UUID.randomUUID().toString() + ".xml";
							File file = new File(dir + fileName);
							file.createNewFile();
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			});
			thread[i].start();
		}
	}
}
