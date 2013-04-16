package org.rembau.minaCommand;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.rembau.Context;


public class CommandServer extends Thread{ 
	private static final Logger logger = Logger.getLogger(CommandServer.class);
	private ServerHandler sh=null;
	public void run(){
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset
						.forName("UTF-8"))));
		sh = new ServerHandler();
		acceptor.setHandler(sh);
		try {
			acceptor.bind(new InetSocketAddress(Context.CMD_S_PORT));
			logger.info("Server is started!");
		} catch (IOException e) {
			if(e.getMessage().indexOf("Address already in use")!=-1){
				String str ="port already in use.\n" +
						"Other program take up the port,or this program is started many time!\n" +
						"run the command \"classname status\"check up.\n" +
						"if you want run the program many time,please change the conf/extra.properties.port's value.";
				System.out.println(str);
				logger.info(str);
				System.exit(0);
			}else {
				System.out.println(e.getMessage());
				logger.info(e.getMessage());
			}
			return;
		}
	}
	/**
	 * if you want to configuration own commands,you must inherit the Class CommandHandle and register.
	 */
	public void registerCommandHandle(CommandHandler ch){
		sh.setCh(ch);
	}
}

