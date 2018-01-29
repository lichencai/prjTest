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
	 * �Զ����Ƶ���ʽ��ȡ�ļ�
	 */
	public void readByteFile() throws Exception{
		DataInputStream dis = new DataInputStream(new FileInputStream("D:\\temp\\temp1.txt"));
		byte[] b = new byte[1024];
		int size = dis.read(b);
		System.out.println("size:" + size);
		int length = Math.min(size, 1024);
		// �������޷����н�bת��string�ģ��ļ��д�ŵ���һ��byte�ֽڣ���ֵ��20
		//System.out.println(new String(b,0,length));
		//  �����Ϳ���ʵ�ֽ�һ��byte���͵�ֵ��ʾ����
		System.out.println(b[0]);
		dis.close();
	}
	
	
	/**
	 * �Զ����Ƶ���ʽд�ļ�
	 */
	public void writeByteFile() throws Exception{
		//  ����ļ������ڣ���ᴴ��һ���ļ����Զ����Ƶ���ʽ����д�ļ�
		DataOutputStream dos = new DataOutputStream(new FileOutputStream("D:\\temp\\temp1.txt"));
		dos.write(20);
		dos.close();
	}
	
	
	public void fileInputOutputStream() throws Exception{
		File input = new File("D:\\temp\\temp.txt");
		File output = new File("D:\\temp\\temp_copy.txt");
		if(!output.exists()){
			if(!output.createNewFile())
				throw new Exception("�����ļ�D:\\temp\\temp_copy.txtʧ��");
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
