package io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class TestFileInputStream {
	
	public static void main(String[] args) throws Exception{
		
		
	}
	
	/**
	 * 以二进制的形式读取文件
	 */
	public void readByteFile() throws Exception{
		DataInputStream dis = new DataInputStream(new FileInputStream("D:\\temp\\temp1.txt"));
		byte[] b = new byte[1024];
		int size = dis.read(b);
		System.out.println("size:" + size);
		int length = Math.min(size, 1024);
		// 下面是无法进行将b转成string的，文件中存放的是一个byte字节，其值是20
		//System.out.println(new String(b,0,length));
		//  这样就可以实现将一个byte类型的值显示出来
		System.out.println(b[0]);
		dis.close();
	}
	
	
	/**
	 * 以二进制的形式写文件
	 */
	public void writeByteFile() throws Exception{
		//  如果文件不存在，则会创建一个文件，以二进制的形式进行写文件
		DataOutputStream dos = new DataOutputStream(new FileOutputStream("D:\\temp\\temp1.txt"));
		dos.write(20);
		dos.close();
	}
	
	
	public void fileInputOutputStream() throws Exception{
		File input = new File("D:\\temp\\temp.txt");
		File output = new File("D:\\temp\\temp_copy.txt");
		if(!output.exists()){
			if(!output.createNewFile())
				throw new Exception("创建文件D:\\temp\\temp_copy.txt失败");
		}
		FileInputStream fis = new FileInputStream(input);
		FileOutputStream fos = new FileOutputStream(output);
		
		byte[] buffer = new byte[1024];
		int length = 0;
		while((length = fis.read(buffer)) > 0){
			int size = Math.min(length, 1024);
			fos.write(buffer, 0, size);
		}
		
		fis.close();
		fos.close();
	}
	
}
