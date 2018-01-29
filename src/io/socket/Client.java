package io.socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket socket;
	public Client(String ip, int port) throws UnknownHostException, IOException{
		initSocket(ip, port);
	}
	private void initSocket(String ip, int port) throws UnknownHostException, IOException{
		socket = new Socket(ip, port);
	}
	public String readMsg() throws IOException{
		String result = null;
		InputStream is = socket.getInputStream();
//		DataInputStream dis = new DataInputStream(is);
//		byte[] b = new byte[dis.available()];
//		dis.read(b);
//		result = new String(b, "utf-8");
//		dis.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(is, "utf-8"));
		result = in.readLine();
		return result;
	}
	public void writeMsg(String msg) throws IOException{
		OutputStream os = socket.getOutputStream();
		DataOutputStream dos = new DataOutputStream(os);
		byte[] b = msg.getBytes("utf-8");
		dos.write(b);
		dos.close();
	}
	public static void main(String[] args) throws IOException {
		Client client = new Client("127.0.0.1", 11001);
		String result = client.readMsg();
		System.out.println(result);
	}
}
