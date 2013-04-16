package bot.server.handle.task;

import bot.comm.Context;

public class Exceutors {
	public static int pool_num=Context.threadpool_num;
	public void init(){
		for (int i = 0; i < pool_num; i++) {
			Excutor excu = new Excutor();
			excu.start();
		}
	}
}
