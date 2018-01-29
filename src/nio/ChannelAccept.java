package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * û��������accpet
 * ������������ʱ��,����ͨ��telnet localhost 1234��������
 **/
public class ChannelAccept {
	public static void main(String[] args) throws IOException, InterruptedException {
		String greeting = "Hello I must be going.\r\n";
		int port = 1234;
		ByteBuffer buffer = ByteBuffer.wrap(greeting.getBytes());
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.socket().bind(new InetSocketAddress(port));
		ssc.configureBlocking(false);
		while(true){
			System.out.println("Waiting for connections");
			SocketChannel sc = ssc.accept();
			if(sc == null){
				Thread.sleep(2000);
			}else{
				System.out.println("Incoming connection from : " + sc.socket().getRemoteSocketAddress());
				buffer.rewind();
				sc.write(buffer);
				sc.close();
			}
		}
	}
}
