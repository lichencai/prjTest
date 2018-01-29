package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestConnect {
	private static String userName = "sqs";
	private static String password = "sqs";
	private static String url = "jdbc:oracle:thin:@10.10.2.6:1521:db3";
	public static void main(String[] args) throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection(url, userName, password);
		//Statement stat = conn.createStatement();
		
		PreparedStatement prepared = conn.prepareStatement("select host_name from v$instance");
		ResultSet set = prepared.executeQuery();
		while(set.next()){
			System.out.println(set.getString("host_name"));
		}
		
		conn.close();
		//ResultSet result = stat.executeQuery("select host_name from v$instance");
		//System.out.println(result.getString(1));
		//System.out.println(result);
	}
}
