package nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SelectSocket {
	public static int PORT_NUMBER = 1235;
	private ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
	public static void main(String[] args) throws Exception{
		new SelectSocket().go(args);
	}
	public void go(String[] args) throws Exception{
		int port = PORT_NUMBER;
		if(args.length > 0){
			port = Integer.parseInt(args[0]);
		}
		System.out.println("Listening on port : " + port);
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.configureBlocking(false);
		Selector selector = Selector.open();
		ssc.bind(new InetSocketAddress(port));
		ssc.register(selector, SelectionKey.OP_ACCEPT);
		while(true){
			int n = selector.select();
			if(n == 0){
				continue;
			}
			Iterator it = selector.selectedKeys().iterator();
			while(it.hasNext()){
				SelectionKey key = (SelectionKey)it.next();
				if(key.isAcceptable()){
					ServerSocketChannel server = (ServerSocketChannel)key.channel();
					SocketChannel sc = server.accept();
					registerChannel(selector, sc, SelectionKey.OP_READ);
					sayHello(sc);
				}else if(key.isReadable()){
					readDataFromSocket(key);
				}
				it.remove();
			}
		}
	}
	
	private void registerChannel(Selector selector, SelectableChannel channel, int ops) throws Exception{
		if(channel == null){
			return ;
		}
		channel.configureBlocking(false);
		channel.register(selector, ops);
	}
	
	private void sayHello(SocketChannel channel) throws IOException{
		buffer.clear();
		buffer.put("server send to client : hi there\r\n".getBytes());
		buffer.flip();
		channel.write(buffer);
	}
	
	private void readDataFromSocket(SelectionKey key) throws IOException{
		SocketChannel channel = (SocketChannel)key.channel();
		int count = 0;
		buffer.clear();
		while((count = channel.read(buffer)) > 0){
			buffer.flip();
			while(buffer.hasRemaining()){
				channel.write(buffer);
			}
			buffer.clear();
		}
		if(count < 0){
			channel.close();
		}
	}
	
}
