package server.core;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import server.core.RepeatEntity;
import server.core.NowQuestionAnswers.NowMemory;
import server.tools.RepeatCompare;

public class Memory {
	private static final Logger logger = Logger.getLogger(Memory.class);
	private People talker=new People();
	private LinkedList<String> allListForPeo=new LinkedList<String>(); 	//��¼�����¼
	private LinkedList<String> allListForBot=new LinkedList<String>(); 	//��¼�����¼
	private LinkedList<TreeSet<String>> keyList=new LinkedList<TreeSet<String>>();	//��˳���¼��������ݹؼ��ֶԷ�
	private NowQuestionAnswers nowAnswersAndQuestion = new NowQuestionAnswers();   //��ǰ����Ŀɻش��
	private PeopleState peopleState = new PeopleState();  //�û����뵱ǰ�����һЩ״̬
	private long replyTime=0;
	/**
	 * ��¼�ظ�������
	 */
	private RepeatQuestions repeatQuation = new RepeatQuestions();
	/**
	 * ѵ�������˱�־
	 * 1����֤�Է��Ƿ�ͬ��ѵ����������յ�Yͬ�⣬markConsult=2,�������Է��Ļ�����
	 * 2����֤�Է���ѵ���Ƿ���Ϲ��򣬷��� ��¼�������Ͻ���
	 */
	private int markConsult=0;
	/**
	 * �����Լ����Ա�־
	 * 1���ϴ�̸���������Լ��������й�
	 */
	private int markAboutSelf=0;
	
	/**
	 * ��־�Լ���һ���ش��Ƿ����ʾ�
	 */
	private boolean isQuestionOfMyself =false;
	/**
	 * ��־��һ��������Ƿ��ܻش������
	 */
	public Memory(){
		Calendar calendar=Calendar.getInstance();
		this.replyTime=calendar.getTimeInMillis()/1000;
	}
	public void endInit(){
		isQuestionOfMyself = nowAnswersAndQuestion.getNowMemory().isQuestion();
	}
	/**
	 * �жϻ�������һ���Ƿ����ʾ�
	 * @return
	 */
	public boolean isQuestion(){
		return this.isQuestionOfMyself;
	}
	
	public void setMarkAboutSelf(int i){
		this.markAboutSelf=i;
	}
	public int getMarkAboutSelf(){
		return this.markAboutSelf;
	}
	public void setMarkConsult(int i){
		markConsult=i;
	}
	public int getMarkConsult(){
		return this.markConsult;
	}
	public void addChatRecordSelf(String content){
		allListForBot.add(content);
	}
	public void addChatRecordSide(String content){
		allListForPeo.add(content);
	}
	public void addKeyRecord(TreeSet<String> key){
		if(nowAnswersAndQuestion.getNowMemory().isHaveHim()){
			key.addAll(keyList.getLast());
		}
		keyList.add(key);
	}
	public LinkedList<TreeSet<String>> getKeyRecord(){
		return this.keyList;
	}
	public People getPeople(){
		return this.talker;
	}
	public void initReplyTime(){
		Calendar calendar=Calendar.getInstance();
		this.replyTime=calendar.getTimeInMillis()/1000;
	}
	/**
	 * �����ظ����
	 * 
	 */
	public int handleRepeatNum(){
		int num=0;
		RepeatEntity repeatEntity;
		NowMemory nowMemory = nowAnswersAndQuestion.getNowMemory();
		if(isRepeat()){
			if((repeatEntity = repeatQuation.isExist(nowMemory.getRecordNow())) != null){
				repeatEntity.setRepeatStr(nowMemory.getRecordNow());
				repeatEntity.recordRepeatNum();  //�����ظ��ظ�������һ
				logger.info("�ظ�����2��");
			}
			else {
				repeatQuation.add((repeatEntity=new RepeatEntity(nowMemory.getRecordNow())));
				logger.info("�ظ�");
			}
			num=repeatEntity.getRepeatNum();
			nowMemory.setRepeatNow(true);   //���õ�ǰ���ظ���
		} else if((repeatEntity = repeatQuation.isExist(nowMemory.getRecordNow())) != null){
			repeatEntity.setRepeatStr(nowMemory.getRecordNow());
			num=repeatEntity.getRepeatNum();
			nowMemory.setRepeatNow(false);  //��ǰ�����ظ��ģ����ǲ��ظ���
			logger.info("�ظ�����2��");
		} else {
			nowAnswersAndQuestion.initAnswers();  //�����ظ��Ļش��б��ʼ��
			nowMemory.setRepeatNow(false);  //ͬ��
		}
		repeatQuation.forgerHandle(repeatEntity);  //��������
		return num;
	}
	
	/**
	 * �ж��Ƿ��ظ�
	 * ����1��ȡ���ʣ�ȡ���ʣ�ȡ���� ��ȡ���� ȥ���ظ��ģ�����һ��ĶԱȣ�����䣩
	 * ����2��������λ�������ͬ��ֱ�ӱȽϡ�������Գ����Ƕμ���
	 * �����õ��Ƿ���2
	 * @return �ظ�����true�����ظ�����false
	 */
	public boolean isRepeat(){
		String record=null;
		if(allListForPeo.size()==0){
			return false;
		}
		record=nowAnswersAndQuestion.getNowMemory().getRecordLast();
		record=record.replaceAll(" ", "");
		
		NowMemory nowMemory = nowAnswersAndQuestion.getNowMemory();
		String recordNow=nowMemory.getRecordNow();
		return RepeatCompare.isRepeat(record,recordNow);
	}
	/**
	 * ����
	 */
	public void anger(){
		talker.upTypeRank();
	}
	/**
	 * �����ָ�
	 */
	public void recoverMood(){
		talker.downTypeRank();
	}
	public boolean isRepeatNow(){
		return nowAnswersAndQuestion.getNowMemory().isRepeatNow();
	}
	public NowQuestionAnswers getNowAnswers(){
		return this.nowAnswersAndQuestion;
	}
	public NowMemory getNowMemory(){
		return this.nowAnswersAndQuestion.getNowMemory();
	}
	
	/**
	 * ��ʼ���û����뵱ǰ���ӵ�״̬�������ַ��ٶȣ����ʹ�ô�����
	 * @param str
	 */
	public void initPeopleState(String str[]){
		this.peopleState.irascible=Integer.valueOf(str[0]);
		this.peopleState.proficient=Integer.valueOf(str[1]);
		this.peopleState.normal=Integer.valueOf(str[2]);
		Calendar calendar=Calendar.getInstance();
		long l=calendar.getTimeInMillis()/1000;
		this.peopleState.useTime = l-this.replyTime;
		logger.info(str[0]+" "+str[1]+" "+str[2]+" "+this.peopleState.useTime);
	}
	public boolean isEnableRecord(){
		if(this.peopleState.irascible == 0 && 
				this.peopleState.normal == 0 &&
				this.peopleState.proficient == 0)
		return true;
		return false;
	}
	public int getPeopleStateI(){
		return this.peopleState.irascible;
	}
	public int getPeopleStatep(){
		return this.peopleState.proficient;
	}
	public int getPeopleStaten(){
		return this.peopleState.normal;
	}
	public long getPeopleStateUseTime(){
		return this.peopleState.useTime;
	}
	class PeopleState{
		int irascible; //0,ƽ�� ��1��ƽ��
		int proficient; //-1 �Ҵ�0,���� ��1������ ��2��ר��
		int normal; //0,���� ��1������
		long useTime;  //�����ҷ���Ϣ��ȥ ��ʱ
	}
}