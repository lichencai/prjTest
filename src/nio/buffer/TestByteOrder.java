package nio.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;

public class TestByteOrder {
	public static void main(String[] args) {
		
		//  JVM ��Ĭ���ֽ�˳��
		System.out.println(ByteOrder.nativeOrder());
		
		ByteBuffer buffer = ByteBuffer.allocate(100);
		//  byteBuffer ��Ĭ���ֽ�˳��
		System.out.println(buffer.order());
		
		//  ����ByteBuffer ����bufferͨ��������߰�װһ��������������buffer���ֽ�˳����ByteOrder.nativeOrder()��ͬ
		CharBuffer charBuffer = CharBuffer.allocate(100);
		System.out.println(charBuffer.order());
	}
}
