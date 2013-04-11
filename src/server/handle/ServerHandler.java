package server.handle;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import server.core.Bot;
import server.handle.message.ChatContent;
import server.handle.message.ReceiveMessage;
import server.handle.message.TrainMessage;
import server.handle.message.UserLoginResult;

public class ServerHandler extends IoHandlerAdapter{
	private static final Logger logger = Logger.getLogger(ServerHandler.class);
	
	public void sessionClosed(IoSession session){
		logger.info("客户端退出，"+session.getRemoteAddress());
	}
	public void messageReceived(IoSession session, Object message) {
		long i = System.currentTimeMillis();
		try {
			logger.info(session.getId()+":"+message);
			handleMessage(session,message.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("使用时间为："+(System.currentTimeMillis() - i));
	}
	public void handleMessage(IoSession session,String message) throws Exception{
		Bot bot = (Bot)session.getAttribute("bot");
		ReceiveMessage rm = new ReceiveMessage(message);
		String contents[] = rm.getContents();
		if(rm.getType()==1){
			String reply = bot.execute(contents);
			sendMessage(session,new ChatContent(new String[]{reply}).getMessageContent());
		} else if(rm.getType()==2){
			String r[] =new LoginCheck(bot).check(contents);
			sendMessage(session,new UserLoginResult(r).getMessageContent());
			if(r.length==2){
				sendMessage(session,new ChatContent(new String[]{r[1]+"欢迎你啊！"}).getMessageContent());
			}
		} else if(rm.getType()==3){
			String reply=bot.trainModel(contents);
			sendMessage(session,new TrainMessage(new String[]{Bot.getName(),reply}).getMessageContent());
		}
	}
	/**
	 * @param str 要发送的文字
	 * @param type 1，聊天 2，用户信息 3，训练信息
	 * @throws Exception
	 */
	public void sendMessage(IoSession session,String str) throws Exception{
		logger.info("send:"+str);
		Bot bot = (Bot)session.getAttribute("bot");
		bot.getMemory().initReplyTime();
		session.write(str);
	}
	public void sessionCreated(IoSession session){
		session.setAttribute("bot", new Bot());
	}
}
