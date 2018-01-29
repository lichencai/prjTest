package nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferOperate {
	
	private static String path = "D:\\temp\\codeMap.txt";
	
	
	public static void main(String[] args) throws Exception{
		test("D:\\temp\\temp.txt");
	}
	
	public static void test(String path) throws Exception{
		RandomAccessFile aFile = new RandomAccessFile(path, "rw");
		FileChannel inChannel = aFile.getChannel();
		ByteBuffer buf = ByteBuffer.allocate(48);
		String newData = "New String to write to file..." + System.currentTimeMillis();

		buf.clear();
		buf.put(newData.getBytes());
		
		buf.flip();
		
		while(buf.hasRemaining()){
			inChannel.write(buf);
		}
		aFile.close();
		inChannel.close();
	}
	
	
	public static void test() throws Exception{
		RandomAccessFile aFile = new RandomAccessFile(path, "rw");
		FileChannel inChannel = aFile.getChannel();
		ByteBuffer buf = ByteBuffer.allocate(48);
		int bytesRead = inChannel.read(buf);
		while(bytesRead != -1){
			buf.flip();
			while(buf.hasRemaining()){
				System.out.println((char)buf.get());
				//  或者使用channel的write方法进行读取buffer中的数据
			}
			buf.clear();
			bytesRead = inChannel.read(buf);
		}
		aFile.close();
	}
	
	
	
	
}
