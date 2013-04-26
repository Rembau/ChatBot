package bot.server.handle.task;

import org.apache.log4j.Logger;

public class Job{
	private static final Logger logger = Logger.getLogger(Job.class);
	private Session session;
	private String message;
	public Job(Session session,String message){
		this.session = session;
		this.message = message;
	}
	public void run(){
		long i =System.currentTimeMillis();
		session.handleMessage(message);
		logger.info("使用时间"+(System.currentTimeMillis()-i)+"ms");
	}
}
