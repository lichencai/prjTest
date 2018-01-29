package jdbc.oracle;

import java.util.ArrayList;
import java.util.List;

public class DataObject {
	private List<String> columns;
	private List<List<Object>> objs;
	
	public DataObject(){
		columns = new ArrayList<String>();
		objs = new ArrayList<List<Object>>();
	}
	
	public List<String> getColumns() {
		return columns;
	}
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	public List<List<Object>> getObjs() {
		return objs;
	}

	public void setObjs(List<List<Object>> objs) {
		this.objs = objs;
	}

	/**
	 * 加入数据表中表的头
	 */
	public void addColumnValue(int index, String value){
		columns.add(index, value);
	}
	/**
	 * 加入数据值
	 */
	public void addObj(List<Object> list){
		objs.add(list);
	}
	
	/**
	 * 获取某一行某一列中的单元值
	 */
	public Object getObj(int line, String columnName) throws Exception{
		int index = columns.indexOf(columnName);
		if(-1 == index) throw new Exception(String.format("%s is not found", columnName));
		return objs.get(line).get(index);
	}
	
	/**
	 * 获取某一列全部的数据
	 */
	public List<Object> getColumnsObj(String columnName){
		int index = columns.indexOf(columnName);
		List<Object> columnsObj = new ArrayList<Object>();
		for(int i = 0; i < objs.size(); i++){
			columnsObj.add(objs.get(i).get(index));
		}
		return columnsObj;
	}
	
}
