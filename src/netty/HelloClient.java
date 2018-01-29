package netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

public class HelloClient {
	public static void main(String args[]) {  
        // Client����������  
        ClientBootstrap bootstrap = new ClientBootstrap(  
                new NioClientSocketChannelFactory(  
                        Executors.newCachedThreadPool(),  
                        Executors.newCachedThreadPool()));  
        // ����һ������������Ϣ�͸�����Ϣ�¼�����(Handler)  
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {  
            @Override  
            public ChannelPipeline getPipeline() throws Exception {  
                return Channels.pipeline(new HelloClientHandler());  
            }  
        });  
        // ���ӵ����ص�8000�˿ڵķ����  
        bootstrap.connect(new InetSocketAddress("127.0.0.1", 8000));  
    }  
  
    private static class HelloClientHandler extends SimpleChannelHandler {  
        /** 
         * ���󶨵�����˵�ʱ�򴥷�����ӡ"Hello world, I'm client." 
         */  
        @Override  
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {  
            System.out.println("Hello world, I'm client.");  
        }  
    }  
}
