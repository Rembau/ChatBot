package server.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import server.tools.ReadInfo;

public class Conn
{
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
  public Conn(String configuration){
	  init(configuration);
	  setConn();
  }
  void init(){
	  ReadInfo.load(this.getClass().getResource("sqldata.properties"));
	  dbName=ReadInfo.getString("dbName");
	  ip=ReadInfo.getString("ip");
	  port=ReadInfo.getString("port");
	  user=ReadInfo.getString("user");
	  password=ReadInfo.getString("password");
  }
  void init(String configuration){
	  ReadInfo.load(this.getClass().getResource(configuration));
	  dbName=ReadInfo.getString("dbName");
	  ip=ReadInfo.getString("ip");
	  port=ReadInfo.getString("port");
	  user=ReadInfo.getString("user");
	  password=ReadInfo.getString("password");
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
	    }
	    catch (ClassNotFoundException e)
	    {
	      System.out.print("�Ҳ�����");
	      //System.exit(0);
	    }
  }
  public Connection setConn() {
    try { 
    	//System.out.println(this.ip+""+  this.port+""+   this.dbName+""+  this.user+""+ this.password);
    	con = DriverManager.getConnection("jdbc:mysql://" + this.ip + ":" + this.port + "/" + this.dbName, this.user, this.password);        	
    	System.out.println(ip+"���ݿ����ӳɹ�!");
    	stmt = con.createStatement();
    } catch (SQLException e){
      System.out.println("���Ӳ��ɹ�"+e.getMessage());
      //e.printStackTrace();
      }
    return con;
  }
  public void close(){
	  try {
		System.out.println(this.ip+"���ݿ����ӹر�!");
		con.close();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.println(e.getMessage());
	}
  }
  public Statement getNewStmt(){
	  Statement stmt=null;
    try {
    	stmt = con.createStatement();
	} catch (SQLException e) {
		con=setConn();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	return stmt;
  }
  public Statement getOldStmt(){
	  if(stmt==null){
		  try {
			stmt=con.createStatement();
		} catch (SQLException e) {
			try {
				setConn().createStatement();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
	  }//end if
	return stmt;
  }
  public PreparedStatement getPStmt(String sql){
	  PreparedStatement pstmt=null;
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
	      while (rs.next())
	      {
	        System.out.println(rs.getString(1));
	      }
	      conn.con.close();
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }
  }
}