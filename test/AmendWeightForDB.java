package test;

import java.sql.ResultSet;
import java.sql.SQLException;

import server.conn.DBoperate_;
import server.handle.Record;

public class AmendWeightForDB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sql= "select * from b_teacherquestion";
		ResultSet rs = DBoperate_.select(sql);
		new Record();
		try {
			while(rs.next()){
				int assess=0;
				String userId=rs.getString("t_userId");
				if(!userId.equals("null")){
					//System.out.println(userId);
					assess += Record.userWeight.get(userId);
				}
				String answer = rs.getString("t_answer");
				if(answer.endsWith(".") || answer.endsWith("!") || answer.endsWith("?") || 
						answer.endsWith("¡£") || answer.endsWith("£¡") || answer.endsWith("£¿")){
					assess+=Record.pointForSign;
				}
				assess+=Record.weightHandle(answer);
				sql = "update b_teacherquestion set " +
						"t_assess='"+assess+"' where t_questionId = '"+rs.getString("t_questionId")+"'";
				DBoperate_.update(sql);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
