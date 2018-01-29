package poi;

import java.io.FileInputStream;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelUtil {
	public static void main(String[] args) throws Exception{
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("d:/test.xls"));
	}
}
