//Source file: D:\\PROGRAM FILES\\WORKSPACE\\NETWORKBOT\\client\\communication\\Communication.java

package bot.client.communication;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import bot.client.gui.ChatFrame;
import bot.comm.Context;


public class Communication extends Thread {
	private static final Logger logger = Logger.getLogger(Communication.class);
	private ChatFrame cf;
	private ClientHandler handler;
	private JTextPane label;

	public Communication(ChatFrame cf,JTextPane label){
		this.cf = cf;
		this.label = label;
	}

	public void run(){
		NioSocketConnector connector = new NioSocketConnector();
		connector.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),
						new LineDelimiter("nextLinenextLine"),new LineDelimiter("nextLinenextLine")))); // 设置编码过滤器
		connector.setConnectTimeoutMillis(30*1000);
		handler = new ClientHandler(cf);
		connector.setHandler(handler);// 设置事件处理器
		ConnectFuture cf = connector.connect(new InetSocketAddress(Context.servler_ip,Context.server_port));// 建立连接
		cf.awaitUninterruptibly();
		while(!cf.isDone()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(cf.isConnected()){
			logger.info("连接服务器成功...");
			String point = 
					  "1：在输入字符串前面加上’%’，可以进行搜索。\n"
					+ "2：输入teacher进入训练模式（须先登录）。\n" 
					+ "3：输入end退出训练模式。";
			label.setText(point);
		} else{
			logger.info("连接服务器失败...");
			label.setText("连接服务器失败...");
			JOptionPane.showMessageDialog(null, "连接服务器失败！");
		}
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
