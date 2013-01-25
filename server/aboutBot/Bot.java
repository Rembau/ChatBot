package server.aboutBot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import server.aboutBot.analyzer.Fenci;
import server.conn.DBoperate_;
import server.handle.Record;
import server.tools.RepeatCompare;

/**
 * @version java 1.06
 * @author 木木（茂）
 *
 */
public class Bot {
	private static String name="木木";
	/**
	 * 机器人的一些固定属性
	 */
	private static Specialty specialty = new Specialty();

	private Memory memory;   //记忆模块 
	private Answers answers;
	/**
	 * 标志是否至少有一个子句可以回答
	 */
	//private boolean isCanAnswer=false;
	//private boolean isHaveNotAnwser=false;   //
	//private boolean isNotAnswerNow=false;    //标志已经有不知道的回答了。
	/**
	 * 问题中所有关键字匹配 最小数量
	 */
	private static int findTime = 2;
	private static int endureReplyNum=3;
	/*private boolean isTrainModel=false;*/
	public Bot(){
		init();
	}
	public String filter(){
		return "";
	}
	public String trainModel(String contents[]){
		String reply=null;
		if(contents[0].equals("teacher")){
			reply=getMemory().getPeople().getName()+"" +
					"你已经进入训练模式，请按照训练规则进行训练\n" +
					"Q:[问题]\n" +
					"A:[对应的回答]";
		} else if(contents[0].equals("end")){
			reply=getMemory().getPeople().getName()+"谢谢你！";
		} else if(contents[0].equals("train")){
			String question=contents[1].substring(2);
			String answer=contents[2].substring(2);
			//System.out.println(question.trim()+" "+answer.trim());
			Record.recordToDBForTrain(memory.getPeople().getUserNum(), question, answer,memory.getPeople().getIp());
			return memory.getPeople().getName()+"我已经牢牢记住你说的话了，你可以继续训练，也可以输入end结束训练！";
		}
		return reply;
	}
	/**
	 * 聊天过程中触发的训练
	 * @param content
	 * @return
	 */
	public String trainRT(String content){
		String reply="none";
		if(memory.getMarkConsult()==1){
			if(content.startsWith("y") || content.startsWith("Y")|| content.equals("好")){
				memory.setMarkConsult(2);
				return "请输入我应该回答的答案！形如：answer答案 必以answer开头。";
			} else if(content.startsWith("n") || content.startsWith("N") || content.equals("不")){
				memory.setMarkConsult(0);
				Record.recordToDBForStudy(memory.getNowMemory().getRecordNow(),memory.getPeople().getIp());
				return "那算了。";
			} else{
				Record.recordToDBForStudy(memory.getNowMemory().getRecordNow(),memory.getPeople().getIp());
			}
			memory.setMarkConsult(0);
		} else if(memory.getMarkConsult()>1){
			if(content.startsWith("answer")){
				String content_=content.substring(6);
				Record.recordToDBForTrain(memory.getPeople().getUserNum(),memory.getNowMemory().getRecordNow(),content_,memory.getPeople().getIp());
				memory.setMarkConsult(0);
				return "我又学到新东西了，谢谢你。";
			} else if(memory.getMarkConsult()<4 && !content.equals("N") && !content.equals("n")){
				memory.setMarkConsult(memory.getMarkConsult()+1);
				return "说好教我，又不教我，你不诚信。（可以继续训练,输入'n'，结束训练）";
			} else if(content.equals("n") || content.equals("N")){
				memory.setMarkConsult(0);
				Record.recordToDBForStudy(memory.getNowMemory().getRecordNow(),memory.getPeople().getIp());
				return "既然你这么不愿意教我，那就算了。";
			}
		}
		return reply;
	}
	public String execute(String[] contentAll){
		String content = contentAll[0];
		
		content=content.trim();
		String reply="";
		if(!(reply=trainRT(content)).equals("none")){
			return reply;
		}
		answers = new Answers();
		//isHaveNotAnwser = false;
		String peopleState[]= new String[3];
		peopleState[0]=contentAll[1];
		peopleState[1]=contentAll[2];
		peopleState[2]=contentAll[3];
		
		memory.initPeopleState(peopleState);
		reply =resolve(content);
		memory.addChatRecordSide(content);		//加入对方的聊天记录
		memory.addChatRecordSelf(reply);		//加入自己的聊天记录
		
		memory.endInit();
		return reply;
	};
	
	/**
	 * 把对方的问题分解成多个子问题
	 * @param content
	 * @return 子问题的各个答案
	 */
	public String resolve(String content){
		//isCanAnswer=false;
		String reply="";
		Pattern p=Pattern.compile("[^.。\\?？]+([.。\\?？])",Pattern.CASE_INSENSITIVE);
		   //把问题按逗号，句号，问号分成多个句子
		Matcher m;
		m=p.matcher(content);
		LinkedList<String> questionList = new LinkedList<String>();
		//HashSet<String> replys =  new HashSet<String>();
		int i=0;
		while(m.find()){
			String section;
			if(m.group(1)==null){
				section=m.group();
				if(!RepeatCompare.isRepeat(questionList, section)){
					handle(section,"");
					questionList.add(section);
				};
			} else {
				section=m.group();
				if(!RepeatCompare.isRepeat(questionList, section)){
					handle(section,m.group(1));
					questionList.add(section);
				};
			}
			i=m.end();
			System.out.println(m.group()+" "+m.group(1));
		}
		
		if(!content.substring(i).trim().equals("")){		
			handle(content.substring(i).trim(),"");  //处理
			//System.out.println(content.substring(i).trim());
		}
		for(String str:answers.getAnswers()){
			reply+=str;
		}
		
		if(!this.memory.getPeople().getName().equals("none")){
			if(reply.indexOf("你")!=-1){
				Random rd = new Random();
				int index = rd.nextInt(12);
				if(index<2){
					reply=this.memory.getPeople().getName()+","+reply;
				}
			} else {
				Random rd = new Random();
				int index = rd.nextInt(12);
				if(index<1){
					reply=this.memory.getPeople().getName()+","+reply;
				}
			}
		}
		return reply;
	}
	/*public void judgeAndAddAns(HashSet<String> replys,String content,String sign){
		String a=handle(content,sign);
		if(isNotAnswerNow){
			if(isHaveNotAnwser){
				return;
			} else {
				isHaveNotAnwser=true;
			}
		}
		System.out.println(a);
		replys.add(a);
	}*/
	/**
	 * 对每一个句子进行处理
	 * @param content 单个句子的内容
	 * @param sign 该句子的结束符号，如果不是逗号，句号或问号，则默认设置为句号
	 * @return 回答
	 */
	public void handle(String content,String sign){

		TreeSet<String> wordList_more= Fenci.IKAnalysis(content,false);
		/*
		 * 最高绝对优先级，准确度100%
		 */
		if(aboutMemoryHandle(content,sign)){ 
			//isCanAnswer=true;
			System.out.println("aboutMemory");
			return;
		}
		/*
		 * 次高绝对优先级，专家库，准确度 95%以上（待）
		 */
		if(aboutMyself(wordList_more,sign)){
			//isCanAnswer=true;
			System.out.println("aboutMyself");
			return;
		}
		/*
		 * 相似度匹配，等等
		 */
		if(aboutQuestion(content)){
			//isCanAnswer=true;
			System.out.println("aboutQuestion");
			return;
		}
		aboutUnknow(content);
		System.out.println("aboutUnknow");
	}
	/**
	 * 有关记忆的处理，和强制性的回复
	 * @param content 对方的聊天内容
	 * @return 回复 1）none表示没有和记忆有关的回复 2）强制性的回复（丢弃对方的问题）
	 */
	public boolean aboutMemoryHandle(String content,String sign){
		boolean result=false;
		String reply="";
		if(memory.getPeopleStateUseTime()>=60){
			Random rd = new Random();
			int time = (int)memory.getPeopleStateUseTime();
			if(time > 160){
				reply="这么长时间才回复，跟我说老实话，你在干什么？";
				answers.addNotAnswer(reply);
				result=true;
				return result;
			} else if(time > 100 ){
				int index = rd.nextInt(10);
				if(index<9){
					reply="这么长时间才回复，跟我说老实话，你在干什么？";
					answers.addNotAnswer(reply);
					result=true;
					return result;
				}
			} else if(time > 80){
				int index = rd.nextInt(5);
				if(index<4){
					reply="这么长时间才回复，跟我说老实话，你在干什么？";
					answers.addNotAnswer(reply);
					result=true;
					return result;
				}
			}
		} 
		if(memory.getPeopleStatep()==2){
			Random rd = new Random();
			int index = rd.nextInt(10);
			if(index%4==0){
				reply="你的打字速度太慢了吧，是不是没有专心和我聊天？";
				answers.addNotAnswer(reply);
				result=true;
				return result;
			}
		}
		memory.getNowMemory().init(content);
		int repeatNum=memory.handleRepeatNum();
		System.out.println(repeatNum+" "+memory.isRepeatNow());
		if(repeatNum>Bot.endureReplyNum && memory.isRepeat()){
			if(sign.equals("?")){
				reply="重复问同一句话，有意思吗？";
			} else{
				reply="重复说同一句话，有意思吗？";
			} 
			if(repeatNum==Bot.endureReplyNum+2){
				reply+="我要生气了！";
			}
			if(repeatNum>Bot.endureReplyNum+2){
				memory.anger();
			}
			result=true;
		} else if(repeatNum>Bot.endureReplyNum){
			reply="又来？I 服了 you";
			result=true;
		} else if(memory.isRepeatNow()){
			reply=memory.getNowAnswers().getRandomAnswer();
			if(!reply.equals("none")){
				result=true;
			}
		}
		answers.addCanAnswer(reply);
		return result;
	}
	/**
	 * 判断是否与自己的常用属性是否有关
	 * @param content 对方的聊天内容
	 * @return 回复 1）none表示与自己的无关 2）与自己有关的应该的回复
	 */
	public boolean aboutMyself(TreeSet<String> wordList,String sign){
		boolean result = false;
		String reply="";
		if(memory.getMarkAboutSelf()==1){
			if(wordList.contains("不好") || wordList.contains("不行")){
				result=true;
				reply="你真不会说话啊。";
			}
			else if(wordList.contains("不错") || wordList.contains("真")){
				result=true;
				reply="那是，谢谢啊。";
			}
		}
		memory.setMarkAboutSelf(0);  //回复标记
		if(sign.equals("?") || sign.equals("？") || wordList.contains("什么") || wordList.contains("怎么")){
			//System.out.println(memory.isHaveYou() +" "+ memory.isHaveMe());
			if(memory.getNowMemory().isHaveYou() && !memory.getNowMemory().isHaveMe()){
				String spkey=specialty.findSpKey(wordList);
				System.out.println("找到的关键字"+spkey);
				if(!spkey.equals("none")){
					reply=specialty.findSpReply(spkey, memory.getPeople().getRankOfPeople());
					memory.setMarkAboutSelf(1);
					result=true;
				} //end if
			} 
		}  //end if
		answers.addCanAnswer(reply);
		return result;
	}
	/**
	 * 根据问题 从数据库取的回答
	 * @return
	 */
	public boolean aboutQuestion(String content){
		boolean result =false;
		String reply="";
		/*if(content.indexOf("你好")!=-1){
			return "你好啊，很高兴见到你。";
		}*/
		if(content.length()>15){
			content=content.substring(0,14);
		}
		String content_=specialty.removeStopWordFuci(content);
		TreeSet<String> wordList= Fenci.IKAnalysis(content_,true);
		specialty.removeStopWords(wordList);
		memory.getNowMemory().setKeyNum(wordList.size());
		
		if(wordList.size()==0 && !memory.getNowMemory().isHaveYou() && 
				!memory.getNowMemory().isHaveHim() && !memory.getNowMemory().isHaveMe()){
			if(memory.getNowMemory().isQuestion()){
				reply= "你想问什么呢？";
			} else {
				reply="你想说什么呢？";
			}
			result=true;
			answers.addNotAnswer(reply);
		} else {
			memory.addKeyRecord(wordList);//把关键字列表加入记忆链表
			getAnswers();
			reply=memory.getNowAnswers().getBestAnswer();
			if(reply.equals("none") && wordList.size()==1 && 
					!memory.getNowMemory().isHaveYou() && !memory.getNowMemory().isHaveHim() &&
					!memory.getNowMemory().isHaveMe() && memory.getNowMemory().getRecordNow().length()<4){
				reply = "什么"+content+",说的详细点吧。";
				answers.addNotAnswer(reply);
				result=true;
			} else if(!reply.equals("none")){
				answers.addCanAnswer(reply);
				result=true;
			}
		}
		return result;
	}
	public void getAnswers(String str){
		//wordList.add(str);
		getAnswers();
	}
	/**
	 * 通过记忆模块中的当前对方的文化中的关键字集，查找数据库中的记录
	 */
	public void getAnswers(){
		TreeSet<String> wordList = memory.getKeyRecord().getLast();

		String reg1 ="'(";
		for(String str:wordList){
			reg1+="-="+str+"=-|";
		}
		reg1 =reg1.substring(0,reg1.length()-1);
		int min = wordList.size();
		if(min>5){
			min=5;
		}
		reg1+="){"; 
		String reg2="}";
		int count=0;
		while(min>0 && count<Bot.findTime){
			String sql="select * from b_teacherquestion where t_question2 regexp "+reg1+""+min+""+reg2+"'";
			System.out.println(sql);
			ResultSet rs = DBoperate_.select(sql);
			try {
				if(!rs.next()){
					min--;
					count++;
					continue;
				}
				rs.beforeFirst();
				while(rs.next()){
					memory.getNowAnswers().addAnswer(
							rs.getString("t_answer"), 
							rs.getInt("t_assess"), 
							rs.getInt("t_questionId"),
							rs.getInt("t_haveYou"),
							rs.getInt("t_haveHim"),
							rs.getInt("t_haveMe"),
							rs.getInt("t_length"),
							rs.getString("t_questionAll"),
							rs.getInt("t_isQuestion"),
							rs.getInt("t_haveNo"),
							rs.getInt("t_question2KeyNum"));
					System.out.print(rs.getString("t_answer")+":"+rs.getString("t_questionAll")+",");
				}
				System.out.println();
				break;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				try {
					rs.getStatement().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 当没有获取到答案时
	 * @param content
	 * @return
	 */
	public void aboutUnknow(String content){
		String reply="";
		if(memory.getNowMemory().isHaveYou()){
			reply="我不知道你想让我说什么呢。";
		} else{
			//reply=specialty.getUnknowAnswer();
			reply="我太笨了，竟不理解你的意思。请说的简单点吧。";
		}
		if(memory.getPeople().getName().equals("none")){
			if(memory.isEnableRecord() && content.length()>1 && content.length()<=25 &&
					(content.endsWith("?")||content.endsWith("？")/*||content.endsWith(".")||content.endsWith("。")*/)){
				reply+="（如果你愿意教我怎么回答，请回复'y'）";
				memory.setMarkConsult(1);
			}
		} else {
			if(memory.isEnableRecord() && content.length()>1 && content.length()<=25){
				reply+="（如果你愿意教我怎么回答，请回复'y'）";
				memory.setMarkConsult(1);
			}
		}
		answers.addNotAnswer(reply);
	}
	/**
	 * 获取机器人的名字
	 * @return 机器人的名字
	 */
	public static String getName(){
		return name;
	};
	public void init(){
		memory = new Memory();
	};
	public Memory getMemory(){
		return this.memory;
	}
	public static Specialty getSpecialty(){
		return specialty;
	}
	public static void main(String[] args) {

	}
}