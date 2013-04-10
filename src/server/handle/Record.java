package server.handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.TreeSet;

import server.aboutBot.Bot;
import server.aboutBot.analyzer.Fenci;
import server.conn.DBoperate;
import server.tools.CheckQuestion;

public class Record {
	public static int pointForSign=10;
	public static Hashtable<String,Integer> userWeight= new Hashtable<String,Integer>();
	public Record(){}
	 static {
		ResultSet rs = DBoperate.select("select * from b_users");
		try {
			while(rs.next()){
				userWeight.put(rs.getString("user_id"), rs.getInt("user_weight"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	 
	public static void recordToDBForTrain(String userNum,String question,String answer,String ip){
		answer = answer.trim();
		int length=weightHandle(answer);
		int assess=length;
		if(userNum!=null){
			//System.out.println(userNum);
			assess += userWeight.get(userNum);
		}
		int flag=0;
		if(answer.endsWith(".") || answer.endsWith("!") || answer.endsWith("?") || 
				answer.endsWith("。") || answer.endsWith("！") || answer.endsWith("？")){
			assess+=pointForSign;
			flag=1;
		}
		if(answer.startsWith("你")){
			if(flag == 1){
				answer = answer.substring(0,answer.length()-1);
			}
			answer = answer+"?";
		}
		Record.recordToDBForTrain(userNum, question, answer, assess,length,ip);
	}
	public static void recordToDBForTrain(String userNum,String question,String answer,int assess,int length,String ip){
		int y=0;
		int h=0;
		int m=0;
		int n=0;
		int q=0;
		if(CheckQuestion.isQuestion(question)){
			q=1;
		}
		if(CheckQuestion.isHaveYou(question)){
			y=1;
		}
		if(CheckQuestion.isHaveHim(question)){
			h=1;
		}
		if(CheckQuestion.isHaveMe(question)){
			m=1;
		}
		if(CheckQuestion.isNegative(question)){
			n=1;
		}
		String handledQuestion=Bot.getSpecialty().removeStopWord(question);   
		if(question.length()<1){   //去掉助词后问题长度小于1则不入库
			return;
		}
		String[] obj = Record.getQuestion2(question);
		String question2=obj[0];
		String question2KeyNum = obj[1];
		if(question2.length()<1)
			return;
		String sql="insert into b_teacherQuestion(t_questionAll,t_question1,t_question2,t_question2KeyNum,t_answer,t_userId,t_assess," +
				"t_haveYou,t_haveMe,t_haveHim,t_length,t_haveNo,t_isQuestion,t_ipAddr)" +
				"values('"+question+"','"+handledQuestion+"','"+question2+"','"+question2KeyNum+"','"+answer+"','"+userNum+"'," +
						"'"+assess+"','"+y+"','"+m+"','"+h+"','"+length+"','"+n+"','"+q+"','"+ip+"')";
		System.out.println(sql);
		DBoperate.insert(sql);
	}
	public static int weightHandle(String content){
		int weight=content.length();
		return weight>15?15:weight;
	}
	public static String[] getQuestion2(String question){
		String[] obj=new String[2];
		String question2="";
		String content_=Bot.getSpecialty().removeStopWordFuci(question);
		TreeSet<String> wordList= Fenci.IKAnalysis(content_,true);
		Bot.getSpecialty().removeStopWords(wordList);
		obj[1]=String.valueOf(wordList.size());
		for(String question2_:wordList){
			question2 +="-="+question2_+"=-"; 
		}
		obj[0]=question2;
		return obj;
	}
	public static void recordToDBForStudy(String question,String ip){
		String sql = "insert into b_teacherQuestion_notAnswer(t_question,t_length,t_ipAddr) " +
				"values('"+question+"','"+question.length()+"','"+ip+"')";
		System.out.println(sql);
		DBoperate.insert(sql);
	}
	public static void main(String args[]){
		String sql= "select * from b_teacherquestion";
		ResultSet rs = DBoperate.select(sql);
		new Record();
		try {
			while(rs.next()){
				int assess=0;
				String userId=rs.getString("t_userId");
				if(!userId.equals("null")){
					//System.out.println(userId);
					assess += userWeight.get(userId);
				}
				String answer = rs.getString("t_answer");
				if(answer.endsWith(".") || answer.endsWith("!") || answer.endsWith("?") || 
						answer.endsWith("。") || answer.endsWith("！") || answer.endsWith("？")){
					assess+=pointForSign;
				}
				assess+=weightHandle(answer);
				sql = "update b_teacherquestion set " +
						"t_assess='"+assess+"' where t_questionId = '"+rs.getString("t_questionId")+"'";
				DBoperate.update(sql);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
