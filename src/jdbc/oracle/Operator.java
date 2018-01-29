package jdbc.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oracle.sql.CLOB;

public class Operator {
	
	private static String url = "jdbc:oracle:thin:@100.100.100.1:1521:smart";
	private static String user = "matrix";
	private static String password = "matrix";
	
	static{
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static Connection getConnection(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void excuteInsert(String sql, List<Object[]> objs) throws Exception{
		Connection conn = getConnection();
		if(conn == null) throw new Exception("获取不到连接");
		PreparedStatement pre = conn.prepareStatement(sql);
		int maxSize = 500;
		int count = 0;
		for(Object[] obj : objs){
			count ++;
			for(int i = 0; i < obj.length; i++){
				pre.setObject((i + 1), obj[i]);
			}
			pre.addBatch();
			if(count % maxSize == 0){
				pre.executeBatch();
			}
		}
		pre.executeBatch();
		pre.close();
		conn.close();
	}
	
	
	private static void testExcuteInsert() throws Exception{
		String sql = "insert into tb_test_data(name, address, info, age) values (?,?,?,?)";
		List<Object[]> objs = new ArrayList<Object[]>();
		for(int i = 0; i < 100000; i++){
			Object[] obj = new Object[4];
			obj[0] = "name" + (i + 1) / 10000;
			obj[1] = "address" + (i + 1) / 1000;
			obj[2] = "info" + (i + 1) / 25000;
			obj[3] = (i + 1) / 5000;
			objs.add(obj);
		}
		excuteInsert(sql, objs);
	}
	
	private static DataObject selectSql(String sql) throws Exception{
		DataObject dataObj = new DataObject();
		Connection conn = getConnection();
		if(conn == null) throw new Exception("获取不到连接");
		PreparedStatement pre = conn.prepareStatement(sql);
		ResultSet result = pre.executeQuery();
		ResultSetMetaData metaData = result.getMetaData();
		int columnCount = metaData.getColumnCount();
		for(int i = 1; i <= columnCount; i++){
			String columnName = metaData.getColumnName(i);
			dataObj.addColumnValue(i-1, columnName);
		}
		while(result.next()){
			List<Object> list = new ArrayList<Object>();
			for(int i = 1; i <= columnCount; i++){
				Object obj = result.getObject(i);
				list.add(i - 1, obj);
			}
			dataObj.addObj(list);
		}
		return dataObj;
	}
	
	
	public static void main(String[] args) throws Exception{
//		String sql = "select * from tb_c_resourceinfo_header t where not exists "
//				+ "(select 1 from tb_c_resourceinfo_metadata t1 where t1.tag = 'resultset' and t.resourceid = t1.resourceid) and t.type = 'HcpTable'";
//		
//		DataObject data = selectSql(sql);
//		
//		List<Object> resourceIds = data.getColumnsObj("RESOURCEID");
//		
//		for(Object resourceId : resourceIds){
//			
//			sql = "select * from tb_c_resourceinfo_metadata t where t.resourceid='" + resourceId.toString() + "'";
//			DataObject metaData = selectSql(sql);
//			List<Object> tags = metaData.getColumnsObj("TAG");
//			Object resourceName = metaData.getObj(tags.indexOf("ResourceName"), "VALUE");
//			String resourceNameValue = getStringValue(resourceName);
//			System.out.println(resourceNameValue + "(" + resourceId + ")");
//			List<List<Object>> objs = metaData.getObjs();
//			for(int i = 0; i < objs.size(); i++){
//				Object valueObj = metaData.getObj(i, "VALUE");
//				if(valueObj == null) continue;
//				String value = getStringValue(valueObj);
//				if(isEmpty(value)) continue;
//				String[] vals = value.split(",");
//				if(vals.length < 5) continue;
//				System.out.println(vals[1] + " " + vals[5]);
//			}
//		}
//		
//		
//		System.out.println(resourceIds.size());
		testExcuteInsert();
	}
	
	
	public static boolean isEmpty(String value){
		if(value == null || "".equals(value))
			return true;
		return false;
	}
	
	public static String getStringValue(Object obj) throws Exception{
		if(obj instanceof CLOB){
			CLOB clob = (CLOB)obj;
			return clob.getSubString((long)1, (int)clob.length());
		}
		return obj.toString();
	}
	
	
}
