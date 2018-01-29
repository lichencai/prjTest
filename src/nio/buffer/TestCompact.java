package nio.buffer;

import java.nio.ByteBuffer;

public class TestCompact {
	public static void main(String[] args) {
		String str = "lichencai";
		ByteBuffer buffer = ByteBuffer.allocate(10);
		for(int i = 0; i < str.length(); i++){
			buffer.put((byte)str.charAt(i));
		}
		//  在调用get()方法之前,需要先翻转缓存区
		//  buffer.flip()相当于buffer.limit(buffer.position()).position(0);
		//  get()与put()都是根据position的位置所读取和设置的
		buffer.flip();
		byte b1 = buffer.get();
		byte b2 = buffer.get();
		System.out.println("b1:" + (char)b1 + ",b2:" + (char)b2);
		//  将未读取的字节移到buffer的开始处,position设置为移到字节的长度
		//  limit设置为capacity的值
		buffer.compact();		
		buffer.flip();
		while(buffer.hasRemaining()){
			System.out.println((char)buffer.get());
		}
	}
}
