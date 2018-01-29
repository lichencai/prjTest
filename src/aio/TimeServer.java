package aio;

public class TimeServer {
	public static void main(String[] args) {
		int port = 8080;
		try{
			port = Integer.parseInt(args[0]);
		}catch(Exception e){
		}
		new Thread(new AsyncTimeServerHandler(port)).start();
	}
}
