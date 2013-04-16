package bot.server.handle.task;

import java.util.concurrent.LinkedBlockingQueue;

public class Jobs {
	private static LinkedBlockingQueue<Job> jobs = new LinkedBlockingQueue<Job>();
	public static Job getJob() throws Exception{
		return jobs.take();
	}
	public static void putJob(Job job) throws Exception{
		jobs.put(job);
	}
}
