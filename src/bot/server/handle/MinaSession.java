package bot.server.handle;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import bot.server.core.Bot;
import bot.server.handle.message.ChatContent;
import bot.server.handle.message.ReceiveMessage;
import bot.server.handle.message.TrainMessage;
import bot.server.handle.message.UserLoginResult;
import bot.server.handle.task.Session;

public class MinaSession implements Session {
	private static final Logger logger = Logger.getLogger(MinaSession.class);
	private IoSession session;
	public MinaSession(IoSession session){
		this.session = session;
	}
	public void handleMessage(String message){
		Bot bot = (Bot)session.getAttribute("bot");
		ReceiveMessage rm = new ReceiveMessage(message);
		String contents[] = rm.getContents();
		if(rm.getType()==1){
			String reply = bot.execute(contents);
			sendMessage(new ChatContent(new String[]{reply}).getMessageContent());
		} else if(rm.getType()==2){
			String r[] =new LoginCheck(bot).check(contents);
			sendMessage(new UserLoginResult(r).getMessageContent());
			if(r.length==2){
				sendMessage(new ChatContent(new String[]{r[1]+"欢迎你啊！"}).getMessageContent());
			}
		} else if(rm.getType()==3){
			String reply=bot.trainModel(contents);
			sendMessage(new TrainMessage(new String[]{Bot.getName(),reply}).getMessageContent());
		}
	}
	/**
	 * @param str 要发送的文字
	 * @param type 1，聊天 2，用户信息 3，训练信息
	 */
	public void sendMessage(String str){
		logger.info("send:"+str);
		Bot bot = (Bot)session.getAttribute("bot");
		bot.getMemory().initReplyTime();
		session.write(str);
	}
}
