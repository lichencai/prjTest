package io.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient {
	
	private DatagramSocket serverSocket = null;
	private DatagramSocket socket = null;
	
	public UDPClient() throws SocketException{
		serverSocket = new DatagramSocket(11002);
		socket = new DatagramSocket();
	}
	
	public void sendPacket() throws IOException{
		byte[] datas = "Àî³Â²Æ".getBytes();
		DatagramPacket packet = new DatagramPacket(datas, datas.length, InetAddress.getLocalHost(),11003);
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
		UDPClient client = new UDPClient();
		client.receivePacket();
		System.out.println("client end...");
	}
	
}
