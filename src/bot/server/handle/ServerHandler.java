package bot.server.handle;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import bot.comm.BodyAndUUID;
import bot.server.handle.task.Job;
import bot.server.handle.task.Jobs;
import bot.server.handle.task.MinaSession;
import bot.server.handle.task.Session;
import bot.server.handle.task.Sessions;


public class ServerHandler extends IoHandlerAdapter{
	private static final Logger logger = Logger.getLogger(ServerHandler.class);
	
	public void sessionClosed(IoSession session){
		logger.info("客户端退出，"+session.getRemoteAddress());
	}
	public void messageReceived(IoSession session, Object message) {
		long i = System.currentTimeMillis();
		try {
			logger.info(session.getId()+":"+message);
			String context[] = BodyAndUUID.getContext(message.toString());
			String id =context[0];
			Session se = Sessions.getSe(id);
			if(se == null){
				se = new MinaSession(session);
				se.setId(id);
				Sessions.addSession(id, se);
			}
			Jobs.putJob(new Job(se,context[1]));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		logger.info("使用时间为："+(System.currentTimeMillis() - i));
	}
	public void sessionCreated(IoSession session){
		
	}
}
