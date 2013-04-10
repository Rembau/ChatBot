package server.conn;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class ConnPool {
	private static final Logger logger = Logger.getLogger(ConnPool.class);
	private static LinkedBlockingQueue<Conn> pool = new LinkedBlockingQueue<Conn>();
	private static final int maxLimit =10;
	public static Conn getConn() {
		try {
			logger.info("pool.size():"+pool.size());
			Conn conn = pool.poll(5, TimeUnit.SECONDS);
			if(conn==null && pool.size() < maxLimit){
				conn = new Conn();
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
