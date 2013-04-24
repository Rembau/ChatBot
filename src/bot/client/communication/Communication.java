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
						new LineDelimiter("nextLinenextLine"),new LineDelimiter("nextLinenextLine")))); // ���ñ��������
		connector.setConnectTimeoutMillis(30*1000);
		handler = new ClientHandler(cf);
		connector.setHandler(handler);// �����¼�������
		ConnectFuture cf = connector.connect(new InetSocketAddress(Context.servler_ip,Context.server_port));// ��������
		cf.awaitUninterruptibly();
		while(!cf.isDone()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(cf.isConnected()){
			logger.info("���ӷ������ɹ�...");
			String point = 
					  "1���������ַ���ǰ����ϡ�%�������Խ���������\n"
					+ "2������teacher����ѵ��ģʽ�����ȵ�¼����\n" 
					+ "3������end�˳�ѵ��ģʽ��";
			label.setText(point);
		} else{
			logger.info("���ӷ�����ʧ��...");
			label.setText("���ӷ�����ʧ��...");
			JOptionPane.showMessageDialog(null, "���ӷ�����ʧ�ܣ�");
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
