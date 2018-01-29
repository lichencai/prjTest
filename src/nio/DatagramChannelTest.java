package nio;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.DatagramChannel;
import java.nio.channels.WritableByteChannel;

public class DatagramChannelTest {
	public static void main(String[] args) throws IOException, InterruptedException {
		WritableByteChannel wbc = Channels.newChannel(System.out);
		DatagramChannel channel = DatagramChannel.open();
		channel.configureBlocking(false);
		DatagramSocket socket = channel.socket();
		socket.bind(new InetSocketAddress(1098));
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		while(true){
			SocketAddress sa = channel.receive(buffer);
			if(sa == null){
				Thread.sleep(2000);
				continue ;
			}
			System.out.println("sa is " + sa);
			buffer.flip();
			System.out.println("buffer size : " + buffer.limit());
			wbc.write(buffer);
//			while(buffer.hasRemaining()){
//				System.out.println((char)buffer.get());
//			}
		}
	}
}
