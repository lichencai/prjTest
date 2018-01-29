package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	
	public static void writeFile(File file, String content) throws Exception{
		if(file.exists()) throw new Exception(file.getPath() + " ´æÔÚ");
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
		osw.write(content);
		osw.close();
		fos.close();
	}
	
	
	public static void copyFile(File src, File des) throws Exception{
		int byteread = 0;
		InputStream is = new FileInputStream(src);
		FileOutputStream fos = new FileOutputStream(des);
		byte[] buffer = new byte[1024];
		while((byteread = is.read(buffer)) != -1){
			fos.write(buffer, 0, byteread);
		}
		is.close();
		fos.close();
	}
	
	public static void writeFile(File file, String line, boolean append) throws Exception{
		FileWriter fw = new FileWriter(file, append);
		fw.write(line);
		fw.close();
	}
	
	public static List<String> readFileLine(File file) throws Exception{
		List<String> result = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			result.add(line);
		}
		br.close();
		return result;
	}
}
