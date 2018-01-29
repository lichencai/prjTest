package jdbc.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MysqlUtils {
	public static final String password = "@@qweqwe123123@@";
	public static final String userName = "xmbike";
	public static final String url = "jdbc:mysql://gz-cdb-bolnp4zu.sql.tencentcdb.com:63570/dd_bike"
			+ "?tinyInt1isBit=false&amp;useUnicode=true&amp;characterEncoding=utf-8&amp;allowMultiQueries=true&amp;autoReconnect=true";
	
	static{
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws Exception{
		return DriverManager.getConnection(url, userName, password);
	}
	
	public static List<Map<String, Object>> queryTable(String table, TreeMap<String, Object> params) throws Exception{
		Connection conn = getConnection();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(table);
		
		Set<String> keys = params.keySet();
		boolean first = true;
		Iterator<String> iterator = keys.iterator();
		List<Object> sqlParams = new ArrayList<>();
		while(iterator.hasNext()) {
			String key = iterator.next();
			Object value = params.get(key);
			if(first) {
				sql.append(" where ").append(key).append(" = ?");
				first = false;
			}else {
				sql.append(" and ").append(key).append(" = ?");
			}
			sqlParams.add(value);
		}
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		for(int i = 0; i < sqlParams.size(); i++) {
			ps.setObject(i + 1, sqlParams.get(i));
		}
		ResultSet rs = ps.executeQuery();
		
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		List<Map<String, Object>> datas = new ArrayList<>();
		while(rs.next()) {
			Map<String, Object> map = new HashMap<>();
			for(int i = 1; i <= columnCount; i++) {
				String column = metaData.getColumnName(i);
				Object value = rs.getObject(i);
				map.put(column, value);
			}
			datas.add(map);
		}
		ps.close();
		conn.close();
		rs.close();
		return datas;
	}
}
