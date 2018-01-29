package nio;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DatagramClient {
	public static void main(String[] args) throws Exception {
		DatagramChannel channel = DatagramChannel.open();
		//channel.bind(new InetSocketAddress("127.0.0.1", 1091));
//		if(channel.isConnected()){
//			System.out.println("connect...");
//		}
		String str = "lichencai";
		ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
		channel.send(buffer, new InetSocketAddress(InetAddress.getLocalHost(), 1098));
	}
}
