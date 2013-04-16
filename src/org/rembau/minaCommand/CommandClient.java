package org.rembau.minaCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.rembau.Context;


public class CommandClient extends Thread{
	private String cmd="";
	private IoSession session=null;
	public CommandClient(){}
	public CommandClient(String str){
		this.cmd=str;
	}
	public void run(){
		try {
			NioSocketConnector connector = new NioSocketConnector();
			connector.getFilterChain().addLast(
					"codec",
					new ProtocolCodecFilter(new TextLineCodecFactory(Charset
							.forName("UTF-8")))); // 设置编码过滤器
			connector.setConnectTimeoutMillis(30*1000);
			connector.setHandler(new ClientHandler());// 设置事件处理器
			ConnectFuture cf = connector.connect(new InetSocketAddress("127.0.0.1",Context.CMD_S_PORT));// 建立连接
			cf.awaitUninterruptibly();
			session = cf.getSession();
		} catch (Exception e) {
			if(e.getMessage().indexOf("Connection refused")!=-1){
				System.out.println("Server is stoped,please run \"classname start\",startup the programe.");
			}
			System.out.println(e.getMessage());
			System.exit(0);
		}
		if(cmd.length()==0){
			loop();
		} else {
			session.write(cmd);
		}
	}
	public void loop(){
		BufferedReader readFromSystem=null;
		try {
			readFromSystem = new BufferedReader(new InputStreamReader(System.in));
			String cmd=readFromSystem.readLine();
			while(true){
				cmd=cmd.trim();
				if(!cmd.equals("")){
					session.write(cmd);
				} else {
					System.out.print(">");
				}
				cmd =readFromSystem.readLine();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{

		}
	}
}
