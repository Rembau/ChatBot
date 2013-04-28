package bot.server.handle.task;

import org.apache.mina.core.session.IoSession;


public class MinaSession extends Session {
	private IoSession session;
	public MinaSession(IoSession session){
		this.session = session;
	}
	/**
	 * @param str Ҫ���͵�����
	 * @param type 1������ 2���û���Ϣ 3��ѵ����Ϣ
	 */
	public void sendMessage(String str){
		session.write(str);
	}
}
