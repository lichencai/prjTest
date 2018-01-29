package javase;

import java.util.List;

public class LineObj {
	private List<String> columns = null;
	private List<String> comments = null;
	private String tableName = null;
	private String tableComment = null;
	
	public String createTable(){
		StringBuffer sb = new StringBuffer();
		
		sb = sb.append("create table ").append(tableName).append("(").append(System.getProperty("line.separator"));
		
		for(int i = 0; i < columns.size(); i++){
			sb = sb.append(columns.get(i)).append(" VARCHAR2(1000)");
			if(i != columns.size() - 1)
				sb = sb.append(",");
			sb = sb.append(System.getProperty("line.separator"));
		}
		
		sb = sb.append(");").append(System.getProperty("line.separator"));
		
		sb = sb.append("comment on table ").append(tableName).append(" is '").append(tableComment).append("';").append(System.getProperty("line.separator"));
		
		for(int i = 0; i < comments.size(); i++){
			sb = sb.append("comment on column ").append(tableName).append(".").append(columns.get(i)).append(" is '").append(comments.get(i)).append("';").append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}
	public List<String> getColumns() {
		return columns;
	}
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	public List<String> getComments() {
		return comments;
	}
	public void setComments(List<String> comments) {
		this.comments = comments;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableComment() {
		return tableComment;
	}
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
}
