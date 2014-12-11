import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class Program  {
	public static class EchoServerHandler extends SimpleChannelUpstreamHandler {

		ChannelBuffer chBuffer = ChannelBuffers.buffer(400);

		@Override
		public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
				throws Exception {
			ChannelBuffer buf = (ChannelBuffer) e.getMessage();
			while (buf.readable()) {
				char ch = (char) buf.readByte();
				chBuffer.writeChar(ch);
				if (ch == '\n' || ch == '\r') {
					Channel channel = e.getChannel();
					channel.write(chBuffer);
					chBuffer.clear();
				}
			}
		}
	}

	public static class NettyServer {

		public void startServer(int port) throws Exception {
			ChannelFactory factory = new NioServerSocketChannelFactory(
					Executors.newCachedThreadPool(),
					Executors.newCachedThreadPool());

			ServerBootstrap bootstrap = new ServerBootstrap(factory);
			bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
				public ChannelPipeline getPipeline() {
					return Channels.pipeline(new EchoServerHandler());
				}
			});
			bootstrap.bind(new InetSocketAddress("localhost", port));
		}
	}

	public static void main(String[] args) throws Exception {
		if(args.length != 1)
		{
			System.out.println("Please run program using 'Program port_number'");
			return;
		}
		
		try
		{
			int port = Integer.parseInt(args[0]);
			new NettyServer().startServer(port);
		}
		catch(Exception e)
		{
			System.out.println("Program failed to run , please verify correct port number");
		}
		
	}
}