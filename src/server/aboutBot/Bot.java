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
 * @author ľľ��ï��
 *
 */
public class Bot {
	private static String name="ľľ";
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
		if(contents[0].equals("teacher")){
			reply=getMemory().getPeople().getName()+"" +
					"���Ѿ�����ѵ��ģʽ���밴��ѵ���������ѵ��\n" +
					"Q:[����]\n" +
					"A:[��Ӧ�Ļش�]";
		} else if(contents[0].equals("end")){
			reply=getMemory().getPeople().getName()+"лл�㣡";
		} else if(contents[0].equals("train")){
			String question=contents[1].substring(2);
			String answer=contents[2].substring(2);
			//System.out.println(question.trim()+" "+answer.trim());
			Record.recordToDBForTrain(memory.getPeople().getUserNum(), question, answer,memory.getPeople().getIp());
			return memory.getPeople().getName()+"���Ѿ����μ�ס��˵�Ļ��ˣ�����Լ���ѵ����Ҳ��������end����ѵ����";
		}
		return reply;
	}
	/**
	 * ��������д�����ѵ��
	 * @param content
	 * @return
	 */
	public String trainRT(String content){
		String reply="none";
		if(memory.getMarkConsult()==1){
			if(content.startsWith("y") || content.startsWith("Y")|| content.equals("��")){
				memory.setMarkConsult(2);
				return "��������Ӧ�ûش�Ĵ𰸣����磺answer�� ����answer��ͷ��";
			} else if(content.startsWith("n") || content.startsWith("N") || content.equals("��")){
				memory.setMarkConsult(0);
				Record.recordToDBForStudy(memory.getNowMemory().getRecordNow(),memory.getPeople().getIp());
				return "�����ˡ�";
			} else{
				Record.recordToDBForStudy(memory.getNowMemory().getRecordNow(),memory.getPeople().getIp());
			}
			memory.setMarkConsult(0);
		} else if(memory.getMarkConsult()>1){
			if(content.startsWith("answer")){
				String content_=content.substring(6);
				Record.recordToDBForTrain(memory.getPeople().getUserNum(),memory.getNowMemory().getRecordNow(),content_,memory.getPeople().getIp());
				memory.setMarkConsult(0);
				return "����ѧ���¶����ˣ�лл�㡣";
			} else if(memory.getMarkConsult()<4 && !content.equals("N") && !content.equals("n")){
				memory.setMarkConsult(memory.getMarkConsult()+1);
				return "˵�ý��ң��ֲ����ң��㲻���š������Լ���ѵ��,����'n'������ѵ����";
			} else if(content.equals("n") || content.equals("N")){
				memory.setMarkConsult(0);
				Record.recordToDBForStudy(memory.getNowMemory().getRecordNow(),memory.getPeople().getIp());
				return "��Ȼ����ô��Ը����ң��Ǿ����ˡ�";
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
		memory.addChatRecordSide(content);		//����Է��������¼
		memory.addChatRecordSelf(reply);		//�����Լ��������¼
		
		memory.endInit();
		return reply;
	};
	
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
			System.out.println(m.group()+" "+m.group(1));
		}
		
		if(!content.substring(i).trim().equals("")){		
			handle(content.substring(i).trim(),"");  //����
			//System.out.println(content.substring(i).trim());
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
		System.out.println(a);
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
		/*
		 * ��߾������ȼ���׼ȷ��100%
		 */
		if(aboutMemoryHandle(content,sign)){ 
			//isCanAnswer=true;
			System.out.println("aboutMemory");
			return;
		}
		/*
		 * �θ߾������ȼ���ר�ҿ⣬׼ȷ�� 95%���ϣ�����
		 */
		if(aboutMyself(wordList_more,sign)){
			//isCanAnswer=true;
			System.out.println("aboutMyself");
			return;
		}
		/*
		 * ���ƶ�ƥ�䣬�ȵ�
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
				reply="��ô��ʱ��Żظ�������˵��ʵ�������ڸ�ʲô��";
				answers.addNotAnswer(reply);
				result=true;
				return result;
			} else if(time > 100 ){
				int index = rd.nextInt(10);
				if(index<9){
					reply="��ô��ʱ��Żظ�������˵��ʵ�������ڸ�ʲô��";
					answers.addNotAnswer(reply);
					result=true;
					return result;
				}
			} else if(time > 80){
				int index = rd.nextInt(5);
				if(index<4){
					reply="��ô��ʱ��Żظ�������˵��ʵ�������ڸ�ʲô��";
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
				reply="��Ĵ����ٶ�̫���˰ɣ��ǲ���û��ר�ĺ������죿";
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
				reply="�ظ���ͬһ�仰������˼��";
			} else{
				reply="�ظ�˵ͬһ�仰������˼��";
			} 
			if(repeatNum==Bot.endureReplyNum+2){
				reply+="��Ҫ�����ˣ�";
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
			//System.out.println(memory.isHaveYou() +" "+ memory.isHaveMe());
			if(memory.getNowMemory().isHaveYou() && !memory.getNowMemory().isHaveMe()){
				String spkey=specialty.findSpKey(wordList);
				System.out.println("�ҵ��Ĺؼ���"+spkey);
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
				reply= "������ʲô�أ�";
			} else {
				reply="����˵ʲô�أ�";
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
				reply = "ʲô"+content+",˵����ϸ��ɡ�";
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
	 * ��û�л�ȡ����ʱ
	 * @param content
	 * @return
	 */
	public void aboutUnknow(String content){
		String reply="";
		if(memory.getNowMemory().isHaveYou()){
			reply="�Ҳ�֪����������˵ʲô�ء�";
		} else{
			//reply=specialty.getUnknowAnswer();
			reply="��̫���ˣ�������������˼����˵�ļ򵥵�ɡ�";
		}
		if(memory.getPeople().getName().equals("none")){
			if(memory.isEnableRecord() && content.length()>1 && content.length()<=25 &&
					(content.endsWith("?")||content.endsWith("��")/*||content.endsWith(".")||content.endsWith("��")*/)){
				reply+="�������Ը�������ô�ش���ظ�'y'��";
				memory.setMarkConsult(1);
			}
		} else {
			if(memory.isEnableRecord() && content.length()>1 && content.length()<=25){
				reply+="�������Ը�������ô�ش���ظ�'y'��";
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