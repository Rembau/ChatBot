package org.rembau.minaCommand;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ClientHandler extends IoHandlerAdapter {
	public void sessionClosed(IoSession session){
		System.out.println("Connection has been disconnected,client is exiting.");
		System.exit(0);
	}
	public void messageReceived(IoSession session, Object message)throws Exception {
		String str = message.toString();
		if(str.trim().length()==0){
			return;
		}
		System.out.println("message:"+str);
		if(str.equals("user leaved!") || str.equals("server's message:server is stoped!")){
			System.exit(0);
		}
		System.out.print(">");
	}
}
