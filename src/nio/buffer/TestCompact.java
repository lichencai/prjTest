package nio.buffer;

import java.nio.ByteBuffer;

public class TestCompact {
	public static void main(String[] args) {
		String str = "lichencai";
		ByteBuffer buffer = ByteBuffer.allocate(10);
		for(int i = 0; i < str.length(); i++){
			buffer.put((byte)str.charAt(i));
		}
		//  �ڵ���get()����֮ǰ,��Ҫ�ȷ�ת������
		//  buffer.flip()�൱��buffer.limit(buffer.position()).position(0);
		//  get()��put()���Ǹ���position��λ������ȡ�����õ�
		buffer.flip();
		byte b1 = buffer.get();
		byte b2 = buffer.get();
		System.out.println("b1:" + (char)b1 + ",b2:" + (char)b2);
		//  ��δ��ȡ���ֽ��Ƶ�buffer�Ŀ�ʼ��,position����Ϊ�Ƶ��ֽڵĳ���
		//  limit����Ϊcapacity��ֵ
		buffer.compact();		
		buffer.flip();
		while(buffer.hasRemaining()){
			System.out.println((char)buffer.get());
		}
	}
}
