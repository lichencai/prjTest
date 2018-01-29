package io.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	private ServerSocket serverSocket = null;
	List<Socket> sockets = new ArrayList<Socket>();
	public Server(int port) throws IOException{
		serverSocket = new ServerSocket(port);
	}
	public Socket getSocket() throws IOException{
		Socket socket = serverSocket.accept();
		System.out.println(socket.getInetAddress().getHostAddress() + " is connected");
		sockets.add(socket);
		return socket;
	}
	public void writeMsg(String msg) throws IOException{
		System.out.println("server write msg...");
		byte[] b = msg.getBytes("utf-8");
		for(Socket each : sockets){
			OutputStream os = each.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.write(b);
			dos.close();
		}
	}
	
	public static void main(String[] args) throws Exception {
		Server server = new Server(11001);
		server.getSocket();
		
		//server.writeMsg("Àî³Â²Æ");
		Thread.sleep(100000);
	}
	
}
