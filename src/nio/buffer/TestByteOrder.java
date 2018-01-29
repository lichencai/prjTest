package nio.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;

public class TestByteOrder {
	public static void main(String[] args) {
		
		//  JVM 的默认字节顺序
		System.out.println(ByteOrder.nativeOrder());
		
		ByteBuffer buffer = ByteBuffer.allocate(100);
		//  byteBuffer 的默认字节顺序
		System.out.println(buffer.order());
		
		//  除了ByteBuffer 其他buffer通过分配或者包装一个数组所创建的buffer的字节顺序与ByteOrder.nativeOrder()相同
		CharBuffer charBuffer = CharBuffer.allocate(100);
		System.out.println(charBuffer.order());
	}
}
