package bot.server.handle.task;

import org.apache.log4j.Logger;

import bot.comm.BodyAndUUID;
import bot.server.core.Bot;
import bot.server.handle.LoginCheck;
import bot.server.handle.message.ChatContent;
import bot.server.handle.message.ReceiveMessage;
import bot.server.handle.message.TrainMessage;
import bot.server.handle.message.UserLoginResult;

public abstract class Session {
	private static final Logger logger = Logger.getLogger(Session.class);
	public String id;
	public String address;
	public int life_time_left=10;
	public Bot bot = new Bot();
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getLife_time_left() {
		return life_time_left;
	}
	public void setLife_time_left(int life_time_left) {
		this.life_time_left = life_time_left;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Bot getBot() {
		return bot;
	}
	public void setBot(Bot bot) {
		this.bot = bot;
	}
	public void handleMessage(String message){
		ReceiveMessage rm = new ReceiveMessage(message);
		String contents[] = rm.getContents();
		if(rm.getType()==1){
			String reply = bot.execute(contents);
			handleSendMessage(new ChatContent(new String[]{reply}).getMessageContent());
		} else if(rm.getType()==2){
			String r[] =new LoginCheck(bot).check(contents);
			handleSendMessage(new UserLoginResult(r).getMessageContent());
			if(r.length==2){
				handleSendMessage(new ChatContent(new String[]{r[1]+"»¶Ó­Äã°¡£¡"}).getMessageContent());
			}
		} else if(rm.getType()==3){
			String reply=bot.trainModel(contents);
			handleSendMessage(new TrainMessage(new String[]{Bot.getName(),reply}).getMessageContent());
		}
	}
	public void handleSendMessage(String str){
		logger.info("send:"+str);
		bot.getMemory().initReplyTime();
		str = BodyAndUUID.combine(id, str);
		sendMessage(str);
	}
	abstract void sendMessage(String str);
}
