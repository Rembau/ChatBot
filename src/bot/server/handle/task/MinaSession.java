package bot.server.handle.task;

import org.apache.mina.core.session.IoSession;


public class MinaSession extends Session {
	private IoSession session;
	public MinaSession(IoSession session){
		this.session = session;
	}
	/**
	 * @param str 要发送的文字
	 * @param type 1，聊天 2，用户信息 3，训练信息
	 */
	public void sendMessage(String str){
		session.write(str);
	}
}
