package server.conn;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class DBoperate {
	public DBoperate(){
		//System.out.println("DBoperate_");
	}
	public static boolean delete(String sql){
		Conn conn = ConnPool.getConn();
		try {
			conn.getOldStmt().executeUpdate(sql);
			return true;
		} catch(MySQLIntegrityConstraintViolationException e1){
			return false;
		} catch (SQLException e) {
			//System.out.println(sql+"³ö´í");
			e.printStackTrace();
		} finally{
			ConnPool.setConn(conn);
		}
		return true;
	}
	public static boolean insert(String sql){
		Conn conn = ConnPool.getConn();
		try {
			conn.getOldStmt().executeUpdate(sql);
			return true;
			}catch(MySQLIntegrityConstraintViolationException e1){
			return false;
		} catch (SQLException e) {
			conn.setConn();
			try {
				conn.getOldStmt().executeUpdate(sql);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally{
			ConnPool.setConn(conn);
		}
		return true;
	}
	public static void update(String sql){
		Conn conn = ConnPool.getConn();
		try {
			conn.getOldStmt().executeUpdate(sql);
		} catch(MySQLIntegrityConstraintViolationException e1){
			
		} catch (SQLException e) {
			conn.setConn();
			try {
				conn.getOldStmt().executeUpdate(sql);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally{
			ConnPool.setConn(conn);
		}
	}
	public static ResultSet select(String sql){
		Conn conn = ConnPool.getConn();
		try {
			return conn.getNewStmt().executeQuery(sql);
		} catch (SQLException e) {
			conn.setConn();
			try {
				conn.getNewStmt().executeQuery(sql);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return null;
		} finally{
			ConnPool.setConn(conn);
		}
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
		System.out.println("½áÊø");
	}
}