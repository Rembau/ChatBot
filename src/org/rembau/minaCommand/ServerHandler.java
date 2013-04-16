package org.rembau.minaCommand;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.rembau.Context;

public class ServerHandler extends IoHandlerAdapter {
	private static final Logger logger = Logger.getLogger(ServerHandler.class);
	private CommandHandler ch;
	private IoSession session;
	public void exceptionCaught(IoSession session, Throwable cause) {
		//System.out.println(cause);
	}
	public void sessionClosed(IoSession session){
		if(this.session==session){
			Context.NUMOFCLIENT--;
			logger.info("client has been disconnected");
		} else {
			logger.info("A extra client has been disconnected");
		}
		session.close(true);
	}
	public void sessionCreated(IoSession session){
		if(Context.NUMOFCLIENT==0){
			Context.NUMOFCLIENT++;
			this.session=session;
			session.write("Connect accessed!");
			logger.info("a user Connection successful!");
		} else{
			session.write("A user is connecting already!Remote Ip is "+session.getRemoteAddress()+".\n");
			session.close(true);
		}
	}
	public void messageReceived(IoSession session, Object message) {
		String command = message.toString();
		if(command.trim().length()==0){
			return;
		}
		logger.info("command of user:"+command);
		if(command.equals(Context.CMD_STOP)){
			session.write("server's message:server is stoped!"+"\n");
			logger.info("server is stoped!");
			System.exit(0);
		} else if(command.equals(Context.CMD_EXIT)){
			session.write("user leaved!\n");
			logger.info("user leaved!");
		} else {
			if(ch!=null)
				ch.excute(command);
		}
		session.write("command of user:"+command+"\n");
	}
	public CommandHandler getCh() {
		return ch;
	}
	public void setCh(CommandHandler ch) {
		this.ch = ch;
	}
}
