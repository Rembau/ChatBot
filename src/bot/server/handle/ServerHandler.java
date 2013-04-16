package bot.server.handle;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import bot.server.core.Bot;
import bot.server.handle.task.Job;
import bot.server.handle.task.Jobs;


public class ServerHandler extends IoHandlerAdapter{
	private static final Logger logger = Logger.getLogger(ServerHandler.class);
	
	public void sessionClosed(IoSession session){
		logger.info("客户端退出，"+session.getRemoteAddress());
	}
	public void messageReceived(IoSession session, Object message) {
		long i = System.currentTimeMillis();
		try {
			logger.info(session.getId()+":"+message);
			Jobs.putJob(new Job(session,message.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		logger.info("使用时间为："+(System.currentTimeMillis() - i));
	}
	
	public void sessionCreated(IoSession session){
		session.setAttribute("bot", new Bot());
	}
}
