package nio.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class SelectSocketClient {
	public static void main(String[] args) throws Exception{
		SocketChannel sc = SocketChannel.open();
		sc.connect(new InetSocketAddress("127.0.0.1", 1235));
		sc.configureBlocking(false);
		if(!sc.isConnected()){
			throw new Exception("没有连接成功");
		}
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		WritableByteChannel write = Channels.newChannel(System.out);
		while(true){
			buffer.clear();
			int n = sc.read(buffer);
			if(n == 0){
				continue;
			}
			buffer.flip();
			write.write(buffer);
			
			buffer.clear();
			buffer.put("client say : recevie".getBytes());
			buffer.flip();
			int wn = sc.write(buffer);
			System.out.println("wn : " + wn);
			Thread.sleep(10000);
		}
	}
}
