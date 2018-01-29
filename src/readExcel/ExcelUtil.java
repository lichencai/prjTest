package readExcel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import util.FileUtil;

public class ExcelUtil {
	
	private static int tableindex = 0;
	
	private static Map<String, String> map = new HashMap<String, String>();
	
	private static List<String> list = new ArrayList<String>();
	
	private static Map<String, String> sourceIdMap = new HashMap<String, String>();
	
	private static int flag = 0;
	
	private static String lineSeparate = "\r\n";
	
	private static String sqlDir = "D:\\temp\\gdsql";
	
	public static void main(String[] args) throws Exception{
		
		
		File resourceIdFile = new File("D:\\mydoc\\MG\\fujian\\res.xls");
		
		List<Code2Name> c2ns = getFileCatalog(resourceIdFile, "code");
		for(Code2Name c2n : c2ns){
			readFileWF(resourceIdFile, c2n.getCode(), c2n.getTableName(), c2n.getTableComment());
			createProp(resourceIdFile, c2n.getCode(), c2n.getTableName(), c2n.getTableComment());
			createRes(resourceIdFile, c2n.getCode(), c2n.getTableName(), c2n.getTableComment());
		}
		

		/*File resourceIdFile = new File("D:\\temp\\createTable");
		int i = 0;
		List<String> fileNames = new ArrayList<String>();
		for(File file : resourceIdFile.listFiles()){
			String fileName = file.getName().substring(0, file.getName().indexOf("."));
			try{
				fileNames.add(fileName);
				i++;
				readFileWF(file);
				System.out.println("处理文件: " + fileName + "成功");
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("处理文件: " + fileName + "失败");
			}
		}
		System.out.println(i);*/
		
/*		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\temp\\wfcodeMap.txt"), "utf-8"));
		i = 0;
		String str = br.readLine();
        for (String line = br.readLine(); line != null; line = br.readLine()) {
        	String[] kv = line.split("=");
        	if(kv.length != 2){
        		System.out.println(line);
        	}else{
        		for(String str1 : fileNames){
        			if(str1.contains(kv[0].toUpperCase()) || str1.equals(kv[0])){
        				System.out.println(str1+"="+kv[1]);
        				i++;
        			}
        		}
        	}
        }
        System.out.println(i);
        
        br.close();
*/		
		
		/*readResourceIdFile(resourceIdFile);
		
		File file = new File("D:\\Download\\shzy\\table_construct");
		
		for(String key : sourceIdMap.keySet()){
			String fileName = sourceIdMap.get(key);
			flag = 0;
			searchFile(file, fileName, key);
			if(flag == 0){
				System.out.println(fileName + " 资源找不到");
			}
		}*/
	}
	
	public static void searchFile(File file, String str, String sourceId) throws Exception{
		try{
			if(file.isFile()){
				String name = file.getName();
				if(name.startsWith(str)){
					if(flag == 1) return ;
					flag = 1;
					File des = new File("D:\\mydoc\\socicalAgent\\doc\\socialAgent\\河北\\石家庄1\\" + file.getName());
					if(des.exists()) throw new Exception(des.getName() + " 文件存在过...");
					FileUtil.copyFile(file, des);
				}
			}else if(file.isDirectory()){
				for(File each : file.listFiles()){
					searchFile(each, str, sourceId);
				}
			}else{
				throw new Exception("查找不到文件的类型...");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static List<Code2Name> getFileCatalog(File file, String sheetName) throws Exception{
		InputStream input = new FileInputStream(file);
		Workbook wb = new HSSFWorkbook(input);
		Sheet sheet = wb.getSheet(sheetName);
		Iterator<Row> rows = sheet.iterator();
		List<Code2Name> c2ns = new ArrayList<Code2Name>();
		while(rows.hasNext()){
			Row row = rows.next();
			Code2Name c2n = new Code2Name(row.getCell(0).toString(), row.getCell(1).toString(), row.getCell(2).toString());
			c2ns.add(c2n);
		}
		return c2ns;
	}
	
	
	public static void createRes(File file, String sheetName, String table, String tableName) throws Exception{
		InputStream input = new FileInputStream(file);
		Workbook wb = new HSSFWorkbook(input);
		Sheet sheet = wb.getSheet(sheetName);
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("[{\"metaData\":[").append("{\"Key\":\"RemoteMQS\",\"Value\":\"PosePost_Info_Test\"},")
			.append("{\"Key\":\"TableType\",\"Value\":\"PostTrans\"},").append("{\"Key\":\"DBName\",\"Value\":\"OracleDB_PHNZCURHPEIJXQAD\"},")
			.append("{\"Key\":\"ResourceName\",\"Value\":\"").append(tableName).append("\"},")
			.append("{\"Key\":\"Desc\",\"Value\":\"").append(tableName).append("\"},")
			.append("{\"Key\":\"TableName\",\"Value\":\"").append(table).append("\"},")
			.append("{\"Key\":\"SourceId\",\"Value\":\"").append(sheetName).append("\"},");
		
		
		Iterator<Row> rows = sheet.iterator();
		int i = 1;
		while(rows.hasNext()){
			Row row = rows.next();
			
			String index = row.getCell(0).toString();
			if(StringUtils.isEmpty(index.trim())) continue; 
			
			StringBuffer columnSb = new StringBuffer();
			columnSb.append("{\"Key\":\"Column_").append(row.getCell(3)).append("\",\"Value\":\"").append(i).append(",").append(row.getCell(3)).append(",VARCHAR2,200,Y,").append(row.getCell(2)).append(",,,Visible\"},");
			
			sb.append(columnSb.toString());
			
			i++;
		}
		
		
		StringBuffer tailSb = new StringBuffer();
		tailSb.append("],").append("\"resourceAddress\":\"HMS.HcpDataMatrixService:tcp -h 12.5.4.24 -p  11000\",")
			.append("\"resourceID\":\"PostTrans_").append(table).append("\",")
			.append("\"resourceState\":\"\",")
			.append("\"resourceType\":\"HcpTable\"}]");
		
		
		write(sb.substring(0, sb.length() - 1) + tailSb.toString(),sqlDir + "\\" + table + "-" + tableName + ".res");
	}
	
	
	
	public static void createProp(File file, String sheetName, String table, String tableName) throws Exception{
		InputStream input = new FileInputStream(file);
		Workbook wb = new HSSFWorkbook(input);
		Sheet sheet = wb.getSheet(sheetName);
		
		StringBuffer sb = new StringBuffer();
		Iterator<Row> rows = sheet.iterator();
		while(rows.hasNext()){
			Row row = rows.next();
			

			String index = row.getCell(0).toString();
			if(StringUtils.isEmpty(index.trim())) continue;
			
			
			sb.append(row.getCell(1)).append(" = ").append(row.getCell(3)).append(lineSeparate);
		}
		
		write(sb.toString(), sqlDir + "\\" + sheetName + ".properties");
		
	}
	
	
	public static void readFileWF(File file, String sheetName, String table, String tableName) throws Exception{
		InputStream input = new FileInputStream(file);
		Workbook wb = new HSSFWorkbook(input);
		Sheet sheet = wb.getSheet(sheetName);
		
		List<String> columns = new ArrayList<String>();
		List<String> comments = new ArrayList<String>();
		
		comments.add("comment on table " + table + " is '" + tableName + "';");
		
		Iterator<Row> rows = sheet.iterator();
		while(rows.hasNext()){
			Row row = rows.next();
			

			String index = row.getCell(0).toString();
			if(StringUtils.isEmpty(index.trim())) continue;
			
			columns.add(row.getCell(3) + " VARCHAR2(200),");
			String columnCommnet = row.getCell(2).toString();
			comments.add("comment on column " + table + "." + row.getCell(3) + " is '" + columnCommnet + "';");
		}
		createSql(columns, comments, table, table + "-" + tableName +".sql");
	}
	
	
	
	public static void readFileWF(File file) throws Exception{
		InputStream input = new FileInputStream(file);
		Workbook wb = new HSSFWorkbook(input);
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> rows = sheet.iterator();
		String fileName = file.getName().replace(".xls", "");
		
		String table = fileName.substring(0, fileName.indexOf("-"));
		table = "GAZY_" + table;
		table = table.toUpperCase();
		
		String tableName = fileName.substring(fileName.indexOf("-") + 1, fileName.length());
		
		List<String> columns = new ArrayList<String>();
		List<String> comments = new ArrayList<String>();
		comments.add("comment on table " + table + " is '" + tableName + "';");
		while(rows.hasNext()){
			Row row = rows.next();
				
			String length = row.getCell(1).toString();
			if(length.indexOf(".") != -1){
				length = length.substring(0, length.indexOf("."));
			}
			
			columns.add(row.getCell(3) + " VARCHAR2(4000),");
			
			String columnCommnet = row.getCell(2).toString();
			comments.add("comment on column " + table + "." + row.getCell(3) + " is '" + columnCommnet + "';");
		}
		
		createSql(columns, comments, table, table + "-" + tableName +".sql");
	}
	
	
	public static void createSql(List<String> columns, List<String> comments, String table, String sqlFile) throws Exception{
		
		if(columns.size() == 0 || comments.size() == 0){
			throw new Exception("不存在字段");
		}
		
		StringBuffer sqlSb = new StringBuffer();
		
		sqlSb.append("create table ").append(table).append(lineSeparate).append("(").append(lineSeparate);
		
		String column = columns.get(columns.size() - 1);
		columns.remove(columns.size() - 1);
		columns.add(column.substring(0, column.length() - 1));
		
		for(String str : columns){
			sqlSb.append(str).append(lineSeparate);
		}
		
		sqlSb.append(");").append(lineSeparate);
		
		for(String str : comments){
			sqlSb.append(str).append(lineSeparate);
		}
		
		write(sqlSb.toString(), sqlDir + "\\" + sqlFile);
	}
	
	public static final void write(String content, String filePath) throws IOException {
		File file = new File(filePath);
		if(!file.exists()) 
			file.createNewFile();
		FileOutputStream fos=new FileOutputStream(file);
        OutputStreamWriter osw=new OutputStreamWriter(fos, "UTF-8");
        BufferedWriter bw=new BufferedWriter(osw);
        bw.write(content+"\r\n");
        bw.close();
        osw.close();
        fos.close();
    }
	
	public static void readResourceIdFile(File file) throws Exception{
		InputStream input = new FileInputStream(file);
		Workbook wb = new HSSFWorkbook(input);
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> rows = sheet.iterator();
		rows.next();rows.next();rows.next();
		while(rows.hasNext()){
			Row row = rows.next();
			String resourceName = row.getCell(1).getStringCellValue().replace("_技术侦查", "").trim();
			String resourceId = row.getCell(0).getStringCellValue();
			sourceIdMap.put(resourceId, resourceName);
		}
	}
	
	public static void searchFile(File file) throws Exception{
		try{
			if(file.isFile()){
				readFile(file);
			}else if(file.isDirectory()){
				for(File each : file.listFiles()){
					searchFile(each);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put(file.getPath(), e.getMessage());
		}
	}
	
	public static void readFile(File file) throws Exception{
		
		System.out.println("读取文件: " + file.getPath());
		
		tableindex++;
		
		InputStream input = new FileInputStream(file);
		Workbook wb = new HSSFWorkbook(input);
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> rows = sheet.iterator();
		Row titleRow = rows.next();
		Iterator<Cell> titleCell = titleRow.cellIterator();
		String tableNameCN = titleCell.next().getStringCellValue();
		String tableName = "SJZ_TABLE" + tableindex;
		rows.next();
		
		File resultFile = new File(file.getParent() + "\\" + file.getName().substring(0, file.getName().indexOf('.')) + ".sql");
		
		StringBuffer sb = new StringBuffer();
		StringBuffer commentSb = new StringBuffer();
		String lineSep = "\r\n";
		sb.append("create table ").append(tableName).append(lineSep);
		sb.append("(").append(lineSep);
		commentSb.append("comment on table ").append(tableName).append(" is '").append(tableNameCN).append("';").append(lineSep);
		while(rows.hasNext()){
			Row row = rows.next();
			String type = row.getCell(3).getStringCellValue();
			if(!type.startsWith("VARCHAR")){
				type = "VARCHAR(100)";
			}
			sb.append(row.getCell(1).getStringCellValue()).append(" ")
				.append(type).append(",").append(lineSep);
			commentSb.append("comment on column ").append(tableName).append(".").append(row.getCell(1).getStringCellValue())
				.append(" is '").append(row.getCell(2).getStringCellValue()).append("';").append(lineSep);
		}
		String tableSql = sb.substring(0, sb.length() - 3) + lineSep + ");" + lineSep;
		FileUtil.writeFile(resultFile, tableSql + commentSb.toString());
		
		System.out.println("文件 " + file.getParent() + "\\" + file.getName().substring(0, file.getName().indexOf('.')) + ".sql 创建成功");
	}
}
