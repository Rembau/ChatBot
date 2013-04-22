package bot.server.handle.task;

public class Job{
	Session session;
	String message;
	public Job(Session session,String message){
		this.session = session;
		this.message = message;
	}
	public void run(){
		session.handleMessage(message);
	}
}
