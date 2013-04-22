package bot.server.handle.task;

import org.apache.log4j.Logger;

public class Executor extends Thread{
	private static final Logger logger = Logger.getLogger(Executor.class);
	public void run(){
		while(true){
			Job job;
			try {
				job = Jobs.getJob();
				job.run();
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}
}
