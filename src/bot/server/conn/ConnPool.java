package bot.server.conn;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import bot.comm.Context;


public class ConnPool {
	private static final Logger logger = Logger.getLogger(ConnPool.class);
	private static LinkedBlockingQueue<Conn> pool = new LinkedBlockingQueue<Conn>();
	private static final int maxLimit =Context.connpool_num;
	private static volatile int nowNumOfCon=1;
	static {
		pool.add(new Conn());
	}
	public static Conn getConn() {
		try {
			logger.info("pool.size():"+pool.size());
			Conn conn = pool.poll(5, TimeUnit.SECONDS);
			if(conn==null && nowNumOfCon < maxLimit){
				conn = new Conn();
				nowNumOfCon++;
			}
			return conn;
		} catch (InterruptedException e) {
			return null;
		}
	}

	public static void setConn(Conn conn) {
		try {
			pool.put(conn);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
