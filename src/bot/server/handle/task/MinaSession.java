package bot.server.handle.task;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import bot.comm.BodyAndUUID;
import bot.server.core.Bot;
import bot.server.handle.LoginCheck;
import bot.server.handle.message.ChatContent;
import bot.server.handle.message.ReceiveMessage;
import bot.server.handle.message.TrainMessage;
import bot.server.handle.message.UserLoginResult;

public class MinaSession extends Session {
	private static final Logger logger = Logger.getLogger(MinaSession.class);
	private IoSession session;
	public MinaSession(IoSession session){
		this.session = session;
	}
	public void handleMessage(String message){
		ReceiveMessage rm = new ReceiveMessage(message);
		String contents[] = rm.getContents();
		if(rm.getType()==1){
			String reply = bot.execute(contents);
			sendMessage(new ChatContent(new String[]{reply}).getMessageContent());
		} else if(rm.getType()==2){
			String r[] =new LoginCheck(bot).check(contents);
			sendMessage(new UserLoginResult(r).getMessageContent());
			if(r.length==2){
				sendMessage(new ChatContent(new String[]{r[1]+"��ӭ�㰡��"}).getMessageContent());
			}
		} else if(rm.getType()==3){
			String reply=bot.trainModel(contents);
			sendMessage(new TrainMessage(new String[]{Bot.getName(),reply}).getMessageContent());
		}
	}
	/**
	 * @param str Ҫ���͵�����
	 * @param type 1������ 2���û���Ϣ 3��ѵ����Ϣ
	 */
	public void sendMessage(String str){
		logger.info("send:"+str);
		bot.getMemory().initReplyTime();
		str = BodyAndUUID.combine(id, str);
		session.write(str);
	}
}