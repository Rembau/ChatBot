package bot.server.handle.message;

import bot.comm.Message;
import bot.comm.MessageCombine;

public class UserLoginResult  extends MessageCombine implements Message{
	public UserLoginResult(String s[]){
		this.s=s;
		type=2;
	}
}
