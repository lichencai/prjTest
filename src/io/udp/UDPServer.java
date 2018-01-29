package io.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {
	private DatagramSocket serverSocket = null;
	private DatagramSocket socket = null;
	
	public UDPServer() throws SocketException{
		serverSocket = new DatagramSocket(11003);	//  监听的端口
		socket = new DatagramSocket();
	}
	
	public void sendPacket() throws IOException{
		byte[] datas = "李陈财server".getBytes();
		DatagramPacket packet = new DatagramPacket(datas, datas.length, InetAddress.getLocalHost(), 11002);
		socket.send(packet);
	}
	
	public void receivePacket() throws IOException{
		byte[] buffer = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
		serverSocket.receive(receivePacket);
		String msg = new String(receivePacket.getData());
		System.out.println(msg);
	}
	
	
	public static void main(String[] args) throws IOException{
		UDPServer server = new UDPServer();
		server.sendPacket();
	}
	
}
