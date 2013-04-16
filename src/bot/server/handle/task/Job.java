package bot.server.handle.task;

import org.apache.mina.core.session.IoSession;

public class Job{
	IoSession session;
	String message;
	public Job(IoSession session,String message){
		this.session = session;
		this.message = message;
	}
}
