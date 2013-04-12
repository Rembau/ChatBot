package server.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import server.conn.DBoperate;
import server.core.analyzer.Fenci;
import server.handle.Record;
import server.tools.RepeatCompare;

/**
 * @version java 1.06
 * @author ľľ��ï��
 *
 */
public class Bot {
	private static final Logger logger = Logger.getLogger(Bot.class);
	
	/**
	 * �����˵�һЩ�̶�����
	 */
	private static Specialty specialty = new Specialty();

	private Memory memory;   //����ģ�� 
	private Answers answers;
	/**
	 * ��־�Ƿ�������һ���Ӿ���Իش�
	 */
	//private boolean isCanAnswer=false;
	//private boolean isHaveNotAnwser=false;   //
	//private boolean isNotAnswerNow=false;    //��־�Ѿ��в�֪���Ļش��ˡ�
	/**
	 * ���������йؼ���ƥ�� ��С����
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
		if(contents[0].equalsIgnoreCase(Constants.cmd_tra_teacher)){
			reply=getMemory().getPeople().getName()+"," +
					Constants.answer_teachForm1;
		} else if(contents[0].equalsIgnoreCase(Constants.cmd_tra_end)){
			reply=getMemory().getPeople().getName()+","+Constants.getAnswerThanks();
		} else if(contents[0].equalsIgnoreCase(Constants.cmd_tra_train)){
			String question=contents[1].substring(2);
			String answer=contents[2].substring(2);
			//logger.info(question.trim()+" "+answer.trim());
			Record.recordToDBForTrain(memory.getPeople().getUserNum(), question, answer,memory.getPeople().getIp());
			return memory.getPeople().getName()+","+Constants.getAnswerOnetrainend();
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
		reply = resolve(content);
		memory.addChatRecordSide(content);		//����Է��������¼
		memory.addChatRecordSelf(reply);		//�����Լ��������¼
		
		memory.endInit();
		return reply;
	};
	
	/**
	 * ��������д�����ѵ��
	 * @param content
	 * @return
	 */
	public String trainRT(String content){
		String reply="none";
		if(memory.getMarkConsult()==1){
			if(Constants.isPositive(content)){
				logger.info("ͬ�����ѵ��ģʽ��");
				memory.setMarkConsult(2);
				return Constants.answer_teachForm2;
			} else if(Constants.isNegative(content)){
				logger.info("��ͬ�����ѵ��ģʽ��");
				memory.setMarkConsult(0);
				Record.recordToDBForStudy(memory.getNowMemory().getRecordNow(),memory.getPeople().getIp());
				return Constants.getAnswerDoNotteach();
			} else{
				logger.info("û��ͬ��Ҳû�в�ͬ���Ƿ����ѵ��ģʽ��");
				Record.recordToDBForStudy(memory.getNowMemory().getRecordNow(),memory.getPeople().getIp());
			}
			memory.setMarkConsult(0);
		} else if(memory.getMarkConsult()>1){
			if(content.startsWith(Constants.cmd_tra_answer)){
				String content_=content.substring(6);
				Record.recordToDBForTrain(memory.getPeople().getUserNum(),memory.getNowMemory().getRecordNow(),content_,memory.getPeople().getIp());
				memory.setMarkConsult(0);
				return Constants.getAnswerThankteacher();
			} else if(memory.getMarkConsult()<4 && !Constants.isNegative(content)){
				memory.setMarkConsult(memory.getMarkConsult()+1);
				return Constants.getAnswerTeachnoteach();
			} else if(Constants.isNegative(content)){
				memory.setMarkConsult(0);
				Record.recordToDBForStudy(memory.getNowMemory().getRecordNow(),memory.getPeople().getIp());
				return Constants.getAnswerDoNotteach();
			}
		}
		return reply;
	}
	
	/**
	 * �ѶԷ�������ֽ�ɶ��������
	 * @param content
	 * @return ������ĸ�����
	 */
	public String resolve(String content){
		//isCanAnswer=false;
		String reply="";
		Pattern p=Pattern.compile("[^.��\\?��]+([.��\\?��])",Pattern.CASE_INSENSITIVE);
		   //�����ⰴ���ţ���ţ��ʺŷֳɶ������
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
			logger.info(m.group()+" "+m.group(1));
		}
		
		if(!content.substring(i).trim().equals("")){		
			handle(content.substring(i).trim(),"");  //����
			//logger.info(content.substring(i).trim());
		}
		for(String str:answers.getAnswers()){
			reply+=str;
		}
		
		if(!this.memory.getPeople().getName().equals("none")){
			if(reply.indexOf("��")!=-1){
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
		logger.info(a);
		replys.add(a);
	}*/
	/**
	 * ��ÿһ�����ӽ��д���
	 * @param content �������ӵ�����
	 * @param sign �þ��ӵĽ������ţ�������Ƕ��ţ���Ż��ʺţ���Ĭ������Ϊ���
	 * @return �ش�
	 */
	public void handle(String content,String sign){

		TreeSet<String> wordList_more= Fenci.IKAnalysis(content,false);
		logger.info("�ִʽ��:"+wordList_more);
		/*
		 * ��߾������ȼ���׼ȷ��100%
		 */
		logger.info("start aboutMemory");
		if(aboutMemoryHandle(content,sign)){ 
			//isCanAnswer=true;
			logger.info("aboutMemory access");
			return;
		}
		/*
		 * �θ߾������ȼ���ר�ҿ⣬׼ȷ�� 95%���ϣ�����
		 */
		logger.info("start aboutMyself");
		if(aboutMyself(wordList_more,sign)){
			//isCanAnswer=true;
			logger.info("aboutMyself access");
			return;
		}
		/*
		 * ���ƶ�ƥ�䣬�ȵ�
		 */
		logger.info("start aboutQuestion");
		if(aboutQuestion(content)){
			//isCanAnswer=true;
			logger.info("aboutQuestion access");
			return;
		}
		logger.info("start aboutUnknow");
		aboutUnknow(content);
		logger.info("aboutUnknow access");
	}
	/**
	 * �йؼ���Ĵ�����ǿ���ԵĻظ�
	 * @param content �Է�����������
	 * @return �ظ� 1��none��ʾû�кͼ����йصĻظ� 2��ǿ���ԵĻظ��������Է������⣩
	 */
	public boolean aboutMemoryHandle(String content,String sign){
		boolean result=false;
		String reply="";
		if(memory.getPeopleStateUseTime()>=60){
			Random rd = new Random();
			int time = (int)memory.getPeopleStateUseTime();
			if(time > 160){
				reply=Constants.getAnwerForlongnoaction();
				answers.addNotAnswer(reply);
				result=true;
				return result;
			} else if(time > 100 ){
				int index = rd.nextInt(10);
				if(index<9){
					reply=Constants.getAnwerForlongnoaction();
					answers.addNotAnswer(reply);
					result=true;
					return result;
				}
			} else if(time > 80){
				int index = rd.nextInt(5);
				if(index<4){
					reply=Constants.getAnwerForlongnoaction();
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
				reply=Constants.getAnswerFortypeslow();
				answers.addNotAnswer(reply);
				result=true;
				return result;
			}
		}
		memory.getNowMemory().init(content);
		int repeatNum=memory.handleRepeatNum();
		logger.info("��ǰ�ظ�������"+repeatNum+" ��ǰ�Ƿ��ظ���"+memory.isRepeatNow());
		if(repeatNum>Bot.endureReplyNum && memory.isRepeat()){
			if(sign.equals("?")){
				reply=Constants.getAnwerForrepeatAsk();
			} else{
				reply=Constants.getAnwerForrepeatSay();
			} 
			if(repeatNum==Bot.endureReplyNum+2){
				reply+=Constants.getAnswerUnhappy();
			}
			if(repeatNum>Bot.endureReplyNum+2){
				memory.anger();
			}
			result=true;
		} else if(repeatNum>Bot.endureReplyNum){
			reply="������I ���� you";
			result=true;
		} else if(memory.isRepeatNow()){
			reply=memory.getNowAnswers().getRandomAnswer();
			if(!reply.equals("none")){
				result=true;
			}
		}
		if(result)
			answers.addCanAnswer(reply);
		return result;
	}
	/**
	 * �ж��Ƿ����Լ��ĳ��������Ƿ��й�
	 * @param content �Է�����������
	 * @return �ظ� 1��none��ʾ���Լ����޹� 2�����Լ��йص�Ӧ�õĻظ�
	 */
	public boolean aboutMyself(TreeSet<String> wordList,String sign){
		boolean result = false;
		String reply="";
		if(memory.getMarkAboutSelf()==1){
			if(wordList.contains("����") || wordList.contains("����")){
				result=true;
				reply="���治��˵������";
			}
			else if(wordList.contains("����") || wordList.contains("��")){
				result=true;
				reply="���ǣ�лл����";
			}
		}
		memory.setMarkAboutSelf(0);  //�ظ����
		if(sign.equals("?") || sign.equals("��") || wordList.contains("ʲô") || wordList.contains("��ô")){
			//logger.info(memory.isHaveYou() +" "+ memory.isHaveMe());
			if(memory.getNowMemory().isHaveYou() && !memory.getNowMemory().isHaveMe()){
				String spkey=specialty.findSpKey(wordList);
				logger.info("�ҵ��Ĺؼ���"+spkey);
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
	 * �������� �����ݿ�ȡ�Ļش�
	 * @return
	 */
	public boolean aboutQuestion(String content){
		boolean result =false;
		String reply="";
		/*if(content.indexOf("���")!=-1){
			return "��ð����ܸ��˼����㡣";
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
				reply= Constants.getAnswerDoubtAsk();
			} else {
				reply= Constants.getAnswerDoubtSay();
			}
			result=true;
			answers.addNotAnswer(reply);
		} else {
			memory.addKeyRecord(wordList);//�ѹؼ����б�����������
			getAnswers();
			reply=memory.getNowAnswers().getBestAnswer();
			if(reply.equals("none") && wordList.size()==1 && 
					!memory.getNowMemory().isHaveYou() && !memory.getNowMemory().isHaveHim() &&
					!memory.getNowMemory().isHaveMe() && memory.getNowMemory().getRecordNow().length()<4){
				reply = "ʲô"+content+" ˵����ϸ��ɡ�";
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
	 * ͨ������ģ���еĵ�ǰ�Է����Ļ��еĹؼ��ּ����������ݿ��еļ�¼
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
			logger.info(sql);
			ResultSet rs = DBoperate.select(sql);
			try {
				if(!rs.next()){
					min--;
					count++;
					continue;
				}
				rs.last();
				logger.info("���ҵ� "+rs.getRow()+" ����¼��");
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
					logger.info("�����ǣ�"+rs.getString("t_questionAll").trim()+";���ǣ�"+rs.getString("t_answer").trim()+";" +
							"ȨֵΪ��"+rs.getInt("t_assess"));
				}
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
	 * ��û�л�ȡ����ʱ
	 * @param content
	 * @return
	 */
	public void aboutUnknow(String content){
		String reply="";
		if(memory.getNowMemory().isHaveYou()){
			reply=Constants.getAnswerCannot1();
		} else{
			//reply=specialty.getUnknowAnswer();
			reply=Constants.getAnswerCannot2();
		}
		if(memory.getPeople().getName().equals("none")){
			if(memory.isEnableRecord() && content.length()>1 && content.length()<=25 &&
					(content.endsWith("?")||content.endsWith("��")/*||content.endsWith(".")||content.endsWith("��")*/)){
				reply+=Constants.getAnswerRequestTeach();
				memory.setMarkConsult(1);
			}
		} else {
			if(memory.isEnableRecord() && content.length()>1 && content.length()<=25){
				reply+=Constants.getAnswerRequestTeach();
				memory.setMarkConsult(1);
			}
		}
		answers.addNotAnswer(reply);
	}
	/**
	 * ��ȡ�����˵�����
	 * @return �����˵�����
	 */
	public static String getName(){
		return Specialty.getName();
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