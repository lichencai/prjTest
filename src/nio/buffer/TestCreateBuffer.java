package nio.buffer;

import java.nio.CharBuffer;

public class TestCreateBuffer {
	public static void main(String[] args) {
		CharBuffer buffer = CharBuffer.allocate(100);
		
		System.out.println(buffer.hasArray());
		
		
		char[] chars = buffer.array();
		
		chars[0] = 'a';
		
		buffer.position(1);
		
		buffer.put('b');
		
		buffer.flip();
		
		while(buffer.hasRemaining()){
			System.out.println(buffer.get());
		}
	}
}
