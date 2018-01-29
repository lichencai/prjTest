package io.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

//  ¶à²¥´«Êä
public class Client {
	
	private MulticastSocket client = null;
	
	public Client(){
		
	}
	
	public void joinGroup() throws IOException{
		InetAddress groupAddress = InetAddress.getByName("224.1.1.1");
		client = new MulticastSocket(11005);
		client.joinGroup(groupAddress);
	}
	
	
	public void recevicePacket() throws Exception{
		byte[] buffer = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
		client.receive(receivePacket);
		String msg = new String(receivePacket.getData());
		System.out.println(msg);
	}
	
	
	public static void main(String[] args) {
		for(int i = 0; i < 10; i++){
			Thread threads = new Thread(){
				public void run(){
					try{
						Client client = new Client();
						client.joinGroup();
						client.recevicePacket();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			};
			
			threads.start();
		}
	}
}
