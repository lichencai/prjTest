package readExcel;

public class Code2Name {
	private String code;
	private String tableName;
	private String tableComment;
	public Code2Name(String code, String tableName, String tableComment) {
		super();
		this.code = code;
		this.tableName = tableName;
		this.tableComment = tableComment;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
