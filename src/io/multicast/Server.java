package io.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Server {
	private MulticastSocket server = null;
	
	public Server(){
		
	}
	
	public void joinGroup() throws IOException{
		InetAddress groupAddress = InetAddress.getByName("224.1.1.1");
		server = new MulticastSocket(11005);
		server.joinGroup(groupAddress);
	}
	
	public void sendPacket() throws IOException{
		byte[] datas = "李陈财server".getBytes();
		//  多播技术的发送消息 无需点对点的发送 即发送地址需要改成组地址的ip InetAddress.getByName("224.1.1.1")
		DatagramPacket packet = new DatagramPacket(datas, datas.length, InetAddress.getByName("224.1.1.1"), 11005);
		server.send(packet);
	}
	
	
	public static void main(String[] args) throws Exception{
		Server server = new Server();
		server.joinGroup();
		server.sendPacket();
	}
	
	
}
