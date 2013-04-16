package bot.server.handle.task;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import bot.server.core.Bot;
import bot.server.handle.LoginCheck;
import bot.server.handle.message.ChatContent;
import bot.server.handle.message.ReceiveMessage;
import bot.server.handle.message.TrainMessage;
import bot.server.handle.message.UserLoginResult;


public class Excutor extends Thread{
	private static final Logger logger = Logger.getLogger(Excutor.class);
	public void run(){
		while(true){
			Job job;
			try {
				job = Jobs.getJob();
				handle(job);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}
	private void handle(Job job) throws Exception{
		handleMessage(job.session,job.message);
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
				sendMessage(session,new ChatContent(new String[]{r[1]+"��ӭ�㰡��"}).getMessageContent());
			}
		} else if(rm.getType()==3){
			String reply=bot.trainModel(contents);
			sendMessage(session,new TrainMessage(new String[]{Bot.getName(),reply}).getMessageContent());
		}
	}
	/**
	 * @param str Ҫ���͵�����
	 * @param type 1������ 2���û���Ϣ 3��ѵ����Ϣ
	 * @throws Exception
	 */
	public void sendMessage(IoSession session,String str) throws Exception{
		logger.info("send:"+str);
		Bot bot = (Bot)session.getAttribute("bot");
		bot.getMemory().initReplyTime();
		session.write(str);
	}
}
