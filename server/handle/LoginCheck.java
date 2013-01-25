package server.handle;

import java.sql.ResultSet;
import java.sql.SQLException;

import server.aboutBot.Bot;
import server.conn.DBoperate_;

public class LoginCheck {
	private Bot bot;
	public LoginCheck(Bot bot){
		this.bot=bot;
	}
	public String[] check(String infor[]){
		String reply[]=new String[2];
		String user = infor[0];
		String password = infor[1];
		String sql="select * from b_users where user_id='"+user+"' and user_password='"+password+"'";
		ResultSet rs=DBoperate_.select(sql);
		try {
			if(rs.next()){
				reply[0]=rs.getString("user_weight");
				reply[1]=rs.getString("user_name");
				handlePeopleInfo(rs.getString("user_name"),rs.getString("user_id"),rs.getDouble("user_weight"),
						rs.getString("user_sex"));
			} else{
				return new String[]{"none"};
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				rs.getStatement().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return reply;
	}
	/**
	 * 设置当前用户 用户名
	 * @param userNum 用户名
	 */
	public void handlePeopleInfo(String name,String userNum,double weight,String sex){
		bot.getMemory().getPeople().setName(name);
		bot.getMemory().getPeople().setUserNum(userNum);
		bot.getMemory().getPeople().setWeight(weight);
		bot.getMemory().getPeople().setSex(sex);
	}
}
