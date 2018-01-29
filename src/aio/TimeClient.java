package aio;


public class TimeClient {
	public static void main(String[] args) {
		int port = 8080;
		try{
			port = Integer.parseInt(args[0]);
		}catch(Exception e){
		}
		new Thread(new AsyncTimeClientHandler("127.0.0.1", port)).start();
	}
}
