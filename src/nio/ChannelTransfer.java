package nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class ChannelTransfer {
	public static void main(String[] args) throws IOException {
		WritableByteChannel wbc = Channels.newChannel(System.out);
		String[] files = {"D:\\temp\\temp.txt"};
		for(int i = 0; i < files.length; i++){
			FileInputStream fis = new FileInputStream(files[i]);
			FileChannel channel = fis.getChannel();
			channel.transferTo(0, channel.size(), wbc);
			channel.close();
			fis.close();
		}
	}
}
