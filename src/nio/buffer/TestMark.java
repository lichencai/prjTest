package nio.buffer;

import java.nio.ByteBuffer;

public class TestMark {
	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(10);
		
		buffer.put((byte)'A');
		buffer.put((byte)'B');
		
		buffer.mark();
		
		buffer.put((byte)'C');
		buffer.put((byte)'D');
		
		buffer.position(1);
		
		buffer.flip();
		
		while(buffer.hasRemaining()){
			System.out.println(buffer.get());
		}
		
		buffer.reset();
		
		while(buffer.hasRemaining()){
			System.out.println(buffer.get());
		}
	}
}
