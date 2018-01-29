package javase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import readExcel.ExcelUtil;
import util.FileUtil;

public class ReadFile {
	
	
	private static String fileDir = "D:\\temp\\gd";
	private static String resDir = "D:\\temp\\gdres";
	
	private static String lineSparator = "\r\n";
	private static String value = "_NUM,_COLUMN,VARCHAR2,1000,Y,_COMMENT,,,Visible";
	private static StringBuffer allSb = new StringBuffer();
	
	public static void main(String[] args) throws Exception{
		
		File dir = new File(fileDir);
		for(File file : dir.listFiles()){
			try{
				System.out.println("处理文件: " + file.getPath());
				readFile(file);
				System.out.println("处理文件: " + file.getPath() + " 成功");
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("处理文件: " + file.getPath() + " 失败");
			}
		}
		
		String allStr = "[" + allSb.toString().substring(0, allSb.toString().length() - 1) + "]";
		FileUtil.writeFile(new File(resDir + "\\" + "all.res"), allStr);
		
	}
	
	public static void readFile(File file) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8")); 
		br.readLine();
		
		List<String> columns = new ArrayList<String>();
		List<String> comments = new ArrayList<String>();
		List<String> queryList = new ArrayList<String>();
		Map<String, String> queryMap = new TreeMap<String, String>();
		Map<String, String> column2CommnetMap = new TreeMap<String, String>();
		
		String line1 = br.readLine();
		String line2 = br.readLine();
		String line3 = br.readLine();
		
		String tableName = line1.substring(0, line1.indexOf(":"));
		String tableCNName = line1.substring(line1.indexOf(":") + 1, line1.length());
		
		comments.add("comment on table " + tableName + " is '" + tableCNName + "';");
		
		String[] queryArray = line2.split(",");
		if(queryArray == null || queryArray.length == 0) throw new Exception("没有设置请求参数");
		for(String str : queryArray){
			queryMap.put(str.substring(0, str.indexOf(":")).toUpperCase(), str.substring(str.indexOf(":") + 1, str.length()).toUpperCase());
			queryList.add(str.substring(0, str.indexOf(":")).toUpperCase());
		}
		
		String resourceView = tableName + "_VIEW";
		String resourceTable = tableName + "_TABLE";
		
		if(!line3.startsWith("S44")) throw new Exception("第四行不是服务编号");
		
		FileUtil.writeFile(new File("D:\\temp\\codeMap.txt"), resourceTable + "=" + line3 + lineSparator, true);
		
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String column = line.substring(0, line.indexOf(":")).toUpperCase();
			String comment = line.substring(line.indexOf(":") + 1, line.length());
			column2CommnetMap.put(column, comment);
			columns.add(column + " VARCHAR2(1000),");
			comments.add("comment on column " + tableName + "." + column + " is '" + comment + "';");
		}
		
		br.close();
		//  创建表的sql语句
		ExcelUtil.createSql(columns, comments, tableName, tableName + "-" + tableCNName +".sql");
		//  创建表资源
		createTableResource(queryList, queryMap, column2CommnetMap, resourceTable, tableName, tableCNName);
		//  创建视图资源
		createViewResource(queryMap, column2CommnetMap, resourceTable, resourceView, tableName, tableCNName);
	}
	
	public static void createViewResource(Map<String, String> queryMap, Map<String, String> column2CommnetMap, String resourceTableId, String resourceViewId, String tableName, String tableCNName) throws Exception{
		
		List<Map<String, String>> metaList = new ArrayList<Map<String, String>>();
		
		Map<String, String> otherMeta = new TreeMap<String, String>();
		otherMeta.put("Key", "Desc");
		otherMeta.put("Value", tableCNName);
		
		Map<String, String> otherMeta1 = new TreeMap<String, String>();
		otherMeta1.put("Key", "Elements");
		otherMeta1.put("Value", "");
		
		Map<String, String> otherMeta2 = new TreeMap<String, String>();
		otherMeta2.put("Key", "Resources");
		otherMeta2.put("Value", resourceTableId);
		
		Map<String, String> otherMeta3 = new TreeMap<String, String>();
		otherMeta3.put("Key", "ViewName");
		otherMeta3.put("Value", tableName);
		
		Map<String, String> otherMeta4 = new TreeMap<String, String>();
		otherMeta4.put("Key", "ResourceName");
		otherMeta4.put("Value", tableCNName);
		
		StringBuffer v2t = new StringBuffer();

		int index = 1;
		for(String str : column2CommnetMap.keySet()){
			
			String comment = column2CommnetMap.get(str);
			
			if(queryMap.get(str) != null){
				String temp = queryMap.get(str);
				v2t.append(temp).append(":").append(str).append(",");
				str = temp;
			}else{
				v2t.append(str).append(":").append(str).append(",");
			}
			
			String val = value.replace("_NUM", index+"").replace("_COLUMN", str).replace("_COMMENT", comment);
			Map<String, String> meta = new TreeMap<String, String>();
			meta.put("Key", "Column_" + str);
			meta.put("Value", val);
			metaList.add(meta);
			index++;
			
		}
		
		Map<String, String> otherMeta5 = new TreeMap<String, String>();
		otherMeta5.put("Key", "ViewColMap@" + resourceTableId);
		otherMeta5.put("Value", v2t.toString().substring(0, v2t.toString().length() - 1));
		
		metaList.add(otherMeta);
		metaList.add(otherMeta1);
		metaList.add(otherMeta2);
		metaList.add(otherMeta3);
		metaList.add(otherMeta4);
		metaList.add(otherMeta5);
		
		Map<String, Object> metaData = new TreeMap<String, Object>();
		metaData.put("metaData", metaList);

		String metaStr = JSONArray.fromObject(metaData).toString();
		
		Map<String, String> otherMap = new TreeMap<String, String>();
		otherMap.put("resourceAddress", "HMS.HcpDataMatrixService:tcp -h 127.0.0.1 -p  11000");
		otherMap.put("resourceID", resourceViewId);
		otherMap.put("resourceState", "");
		otherMap.put("resourceType", "MatrixView");
		
		String otherMapStr = JSONObject.fromObject(otherMap).toString();
		
		String res = metaStr.substring(0, metaStr.length() - 2) + "," + otherMapStr.substring(1, otherMapStr.length() - 1) + "}]";
		allSb.append(res.substring(1, res.length()-1)).append(",");
		FileUtil.writeFile(new File(resDir + "\\" + resourceViewId + ".res"), res);
	}
	
	
	public static void createTableResource(List<String> queryList, Map<String, String> queryMap, Map<String, String> column2CommnetMap, String resourceId, String tableName, String tableCNName) throws Exception{
		
		List<Map<String, String>> metaList = new ArrayList<Map<String, String>>();
		
		Map<String, String> otherMeta = new TreeMap<String, String>();
		otherMeta.put("Key", "Desc");
		otherMeta.put("Value", tableCNName);
		
		Map<String, String> otherMeta1 = new TreeMap<String, String>();
		String resource_QueryField = "";
		for(String str : queryList){
			resource_QueryField = resource_QueryField + str + ",";
		}
		resource_QueryField = resource_QueryField.substring(0, resource_QueryField.length() - 1);
		otherMeta1.put("Key", "Resource_QueryField");
		otherMeta1.put("Value", resource_QueryField);
		
		Map<String, String> otherMeta2 = new TreeMap<String, String>();
		otherMeta2.put("Key", "DataReadOnly");
		otherMeta2.put("Value", "1");
		
		Map<String, String> otherMeta3 = new TreeMap<String, String>();
		otherMeta3.put("Key", "TableName");
		otherMeta3.put("Value", tableName);
		
		Map<String, String> otherMeta4 = new TreeMap<String, String>();
		otherMeta4.put("Key", "RemoteMQS");
		otherMeta4.put("Value", "MatrixMessageServiceI4Gadl_KXC");
		
		Map<String, String> otherMeta5 = new TreeMap<String, String>();
		otherMeta5.put("Key", "DBName");
		otherMeta5.put("Value", "OracleDB_MGYSUCKLUATJTHRF");
		
		Map<String, String> otherMeta6 = new TreeMap<String, String>();
		otherMeta6.put("Key", "ResourceName");
		otherMeta6.put("Value", tableCNName);
		
		metaList.add(otherMeta);
		metaList.add(otherMeta1);
		metaList.add(otherMeta2);
		metaList.add(otherMeta3);
		metaList.add(otherMeta4);
		metaList.add(otherMeta5);
		metaList.add(otherMeta6);

		for(String str : queryList){
			Map<String, String> colMaps = new TreeMap<String, String>();
			colMaps.put("Key", "ColMap_" + queryMap.get(str));
			colMaps.put("Value", str);
			metaList.add(colMaps);
		}
		
		int index = 1;
		for(String str : column2CommnetMap.keySet()){
			String val = value.replace("_NUM", index+"").replace("_COLUMN", str).replace("_COMMENT", column2CommnetMap.get(str));
			Map<String, String> meta = new TreeMap<String, String>();
			meta.put("Key", "Column_"+str);
			meta.put("Value", val);
			metaList.add(meta);
			index++;
		}
		
		Map<String, Object> metaData = new TreeMap<String, Object>();
		metaData.put("metaData", metaList);

		String metaStr = JSONArray.fromObject(metaData).toString();
		
		Map<String, String> otherMap = new TreeMap<String, String>();
		otherMap.put("resourceAddress", "HMS.HcpDataMatrixService:tcp -h 127.0.0.1 -p  11000");
		otherMap.put("resourceID", resourceId);
		otherMap.put("resourceState", "");
		otherMap.put("resourceType", "HcpTable");
		
		String otherMapStr = JSONObject.fromObject(otherMap).toString();
		
		String res = metaStr.substring(0, metaStr.length() - 2) + "," + otherMapStr.substring(1, otherMapStr.length() - 1) + "}]";
		
		allSb.append(res.substring(1, res.length()-1)).append(",");
		
		FileUtil.writeFile(new File(resDir + "\\" + resourceId + ".res"), res);
	}
	
	
	
	public static void readFile1() throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(  
                new FileInputStream("D:\\temp\\sql.txt")));  
  
        for (String line = br.readLine(); line != null; line = br.readLine()) {
        	
        	if(line.startsWith("create")){
        		line = line.replace("create", "alter") + " modify";
        	}
        	
        	if(line.contains("VARCHAR2") || line.contains("CHAR")){
        		int begin = line.indexOf("(");
        		int end = line.indexOf(")");
        		String number = line.substring(begin+1, end);
        		int value = Integer.parseInt(number);
        		if(line.contains("VARCHAR2") && value * 2 < 4000){
        			line = line.replace(number, value * 2 + "");
        		}
        		if(line.contains("CHAR") && value * 2 < 2000){
        			line = line.replace(number, value * 2 + "");
        		}
        	}
        	
        	if(line.contains(" not null")){
    			line = line.replace(" not null", "");
    		}
        	
        	if(line.equals(")")){
        		line = line + ";";
        	}
        	
        	write(line);
        }  
        br.close();
	}
	
	
	
	public static Map<String, String> read1() throws Exception{
		
		Map<String, String> map = new TreeMap<String, String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\temp\\value.txt"), "utf-8"));  
        for (String line = br.readLine(); line != null; line = br.readLine()) {
        	Pattern p = Pattern.compile("\\s+");
        	Matcher m = p.matcher(line);
        	line = m.replaceAll(",");
        	String[] words = line.split(",");
        	map.put(words[0].toLowerCase(), words[1]);
        }
        br.close();
        
        return map;
	}
	
	public static List<String> readFile(String path) throws Exception{
		List<String> columns = new ArrayList<String>();
		List<String> comments = new ArrayList<String>();
		List<String> temp = new ArrayList<String>();
		
		
		LineObj obj = new LineObj();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf-8"));  
		int flag = 1;
        for (String line = br.readLine(); line != null; line = br.readLine()) {
        	String[] lines = line.split(":");
        	String column = lines[0] + " VARCHAR2(200),";
        	String comment = "comment on column GAGD_KXC_HZ_CZWRYXXB." + lines[0] + " is '" + lines[1] + "';";
        	columns.add(column);
        	comments.add(comment);
        	temp.add(lines[0]);
        }
        
        for(String s : columns){
        	System.out.println(s);
        }
        
        for(String s : comments){
        	System.out.println(s);
        }
        
        StringBuffer sb = new StringBuffer();
        for(String str : temp){
        	sb.append(str).append(",");
        }
        
        System.out.println(sb.toString());
        br.close();
        return null;
	}
	
	public static final void write(String content) throws IOException {  
		FileOutputStream fos=new FileOutputStream(new File("D:\\temp\\result.txt"), true);
        OutputStreamWriter osw=new OutputStreamWriter(fos, "UTF-8");
        BufferedWriter bw=new BufferedWriter(osw);
        bw.write(content+"\r\n");
        bw.close();
        osw.close();
        fos.close();
    }
}
