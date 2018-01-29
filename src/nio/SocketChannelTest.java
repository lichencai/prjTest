package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelTest {
	
	public static void main(String[] args) throws Exception{
		test();
	}
	
	public static void test() throws Exception{
		SocketChannel channel = SocketChannel.open();
		channel.connect(new InetSocketAddress("127.0.0.1", 1234));
		
		ByteBuffer buf = ByteBuffer.allocate(48);
		int bytesRead = channel.read(buf);
		System.out.println("bytesRead : " + bytesRead + " content : " + new String(buf.array()));
		buf.flip();
		while(bytesRead != -1){
			while(buf.hasRemaining()){
				System.out.println((char)buf.get());
			}
		}
		channel.close();
		System.out.println("end...");
	}
	
}
