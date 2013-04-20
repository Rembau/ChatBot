package bot.server.handle.task;

import bot.comm.Context;

public class Executors {
	public static int pool_num=Context.threadpool_num;
	public void init(){
		for (int i = 0; i < pool_num; i++) {
			Executor excu = new Executor();
			excu.start();
		}
	}
}
