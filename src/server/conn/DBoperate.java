package server.conn;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DBoperate {
	private static final Logger logger = Logger.getLogger(DBoperate.class);
	public static int delete(String sql){
		return update(sql);
	}
	public static int insert(String sql){
		return update(sql);
	}
	public static int update(String sql){
		Conn conn = ConnPool.getConn();
		try {
			int i = conn.getOldStmt().executeUpdate(sql);
			logger.info("操作成功！"+sql);
			return i;
		} catch (SQLException e) {
			logger.info("sql 异常，"+e.getMessage());
			if(!conn.isValid()){
				logger.info("Connection is unValid!");
				conn.setConn();
				try {
					int i = conn.getOldStmt().executeUpdate(sql);
					logger.info("操作成功！"+sql);
					return i;
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally{
			ConnPool.setConn(conn);
		}
		return -1;
	}
	
	public static ResultSet select(String sql){
		Conn conn = ConnPool.getConn();
		try {
			return conn.getNewStmt().executeQuery(sql);
		} catch (SQLException e) {
			logger.info("sql 异常，"+e.getMessage());
			if(!conn.isValid()){
				logger.info("Connection is unValid!");
				conn.setConn();
				try {
					return conn.getNewStmt().executeQuery(sql);
				} catch (SQLException e1) {
					logger.info("操作成功！"+sql);
					e1.printStackTrace();
				}
			}
		} finally{
			ConnPool.setConn(conn);
		}
		return null;
	}
	public static void main(String[] args) {
		new DBoperate();
		new Thread(){
			public void run(){
				new DBoperate();
			}
		}.start();
		new Thread(){
			public void run(){
				new DBoperate();
			}
		}.start();
		new Thread(){
			public void run(){
				new DBoperate();
			}
		}.start();
		new Thread(){
			public void run(){
				new DBoperate();
			}
		}.start();
		System.out.println("结束");
	}
}