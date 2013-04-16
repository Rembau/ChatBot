package bot.server.conn;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import bot.server.tools.ReadInfo;


public class Conn {
	private static final Logger logger = Logger.getLogger(Conn.class);
	private Connection con;
	private Statement stmt;
	private String dbName;
	private String ip;
	private String port;
	private String user;
	private String password;

	public Conn() {
		init();
		setConn();
	}

	public Conn(String configuration) {
		init(configuration);
		setConn();
	}

	void init() {
		ReadInfo.load(new File("conf" + File.separator + "sqldata.properties"));
		dbName = ReadInfo.getString("dbName");
		ip = ReadInfo.getString("ip");
		port = ReadInfo.getString("port");
		user = ReadInfo.getString("user");
		password = ReadInfo.getString("password");
	}

	void init(String configuration) {
		ReadInfo.load(new File("conf" + File.separator + configuration));
		dbName = ReadInfo.getString("dbName");
		ip = ReadInfo.getString("ip");
		port = ReadInfo.getString("port");
		user = ReadInfo.getString("user");
		password = ReadInfo.getString("password");
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.print("找不到类");
			// System.exit(0);
		}
	}

	public Connection setConn() {
		try {
			// logger.info(this.ip+""+ this.port+""+ this.dbName+""+
			// this.user+""+ this.password);
			con = DriverManager.getConnection("jdbc:mysql://" + this.ip + ":"
					+ this.port + "/" + this.dbName, this.user, this.password);
			logger.info(ip + "数据库连接成功!");
			stmt = con.createStatement();
		} catch (SQLException e) {
			logger.info("连接不成功" + e.getMessage());
			// e.printStackTrace();
		}
		return con;
	}

	public void close() {
		try {
			logger.info(this.ip + "数据库连接关闭!");
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
		}
	}

	public Statement getNewStmt() throws SQLException {
		Statement stmt = null;
		try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			con = setConn();
			try {
				stmt = con.createStatement();
			} catch (SQLException e1) {
				throw e1;
			}
		}
		return stmt;
	}

	public Statement getOldStmt() {
		if (stmt == null) {
			try {
				stmt = con.createStatement();
			} catch (SQLException e) {
				try {
					setConn().createStatement();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		}// end if
		return stmt;
	}
	public boolean isValid(){
		try {
			return con.isValid(5);
		} catch (SQLException e) {
			return false;
		}
	}
	public PreparedStatement getPStmt(String sql) {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pstmt;
	}

	public static void main(String[] args) {
		Conn conn = new Conn();
		try {
			ResultSet rs = conn.stmt.executeQuery("show databases");
			while (rs.next()) {
				logger.info(rs.getString(1));
			}
			conn.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}