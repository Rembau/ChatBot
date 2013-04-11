package server.handle;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import comm.Context;

public class Dispatcher {
	private static final Logger logger = Logger.getLogger(Dispatcher.class);
	public Dispatcher(){
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),
						new LineDelimiter("nextLinenextLine"),new LineDelimiter("nextLinenextLine"))));
		ServerHandler sh = new ServerHandler();
		acceptor.setHandler(sh);
		try {
			acceptor.bind(new InetSocketAddress(Context.server_port));
			logger.info("服务器启动完成，端口："+Context.server_port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String agrs[]){
		new Dispatcher();
	}
}
