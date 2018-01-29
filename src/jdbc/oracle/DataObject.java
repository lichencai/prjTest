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
	 * �������ݱ��б��ͷ
	 */
	public void addColumnValue(int index, String value){
		columns.add(index, value);
	}
	/**
	 * ��������ֵ
	 */
	public void addObj(List<Object> list){
		objs.add(list);
	}
	
	/**
	 * ��ȡĳһ��ĳһ���еĵ�Ԫֵ
	 */
	public Object getObj(int line, String columnName) throws Exception{
		int index = columns.indexOf(columnName);
		if(-1 == index) throw new Exception(String.format("%s is not found", columnName));
		return objs.get(line).get(index);
	}
	
	/**
	 * ��ȡĳһ��ȫ��������
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
