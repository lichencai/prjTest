package other;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDate {
	public static void main(String[] args) throws Exception{
		//  1970-01-01
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date beginDate = sdf.parse("2016-06-30 23:59:59");
		
		System.out.println("beginDate : " + beginDate.getTime());
		
		
		
		
		long time = 1467129600000L;
		Date date = new Date(time);
		
		System.out.println(sdf.format(date));
		
		
		
		String[][] dataTable = new String[2][4];
		for(int i=0; i<dataTable.length; i++){
			for(int j=0; j < dataTable[i].length; j++){
				dataTable[i][j] = i + "," + j;
			}
		}
		
		for(int i=0; i<dataTable.length; i++){
			for(int j=0; j < dataTable[i].length; j++){
				System.out.println(dataTable[i][j]);
			}
		}
		
		
		
		
		
	}
}
