//Source file: D:\\PROGRAM FILES\\WORKSPACE\\NETWORKBOT\\client\\communication\\Communication.java

package client.communication;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import comm.Context;
import client.gui.ChatFrame;

public class Communication extends Thread {
	private static final Logger logger = Logger.getLogger(Communication.class);
	private ChatFrame cf;
	private ClientHandler handler;

	public Communication(ChatFrame cf) throws Exception {
		this.cf = cf;
	}

	public void run() {
		NioSocketConnector connector = new NioSocketConnector();
		connector.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),
						new LineDelimiter("nextLinenextLine"),new LineDelimiter("nextLinenextLine")))); // ���ñ��������
		connector.setConnectTimeoutMillis(30*1000);
		handler = new ClientHandler(cf);
		connector.setHandler(handler);// �����¼�������
		ConnectFuture cf = connector.connect(new InetSocketAddress("127.0.0.1",Context.CMD_S_PORT));// ��������
		cf.awaitUninterruptibly();
		logger.info("���ӷ������ɹ���");
	}
	public void sendCharMessage(){
		handler.sendCharMessage();
	}
	public void sendCharMessage(String message){
		handler.sendCharMessage(message);
	}
	public void sendUserInfomation(String user_num, String password) {
		handler.sendUserInfomation(user_num, password);
	}
}
