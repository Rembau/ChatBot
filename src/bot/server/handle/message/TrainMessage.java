package bot.server.handle.message;

import bot.comm.Message;
import bot.comm.MessageCombine;

public class TrainMessage  extends MessageCombine implements Message{
	/**
	 * @param s 数组第一个为
	 * 1：用户进入训练时的提示
	 */
	public TrainMessage(String s[]){
		this.s=s;
		this.type=3;
	}
}
