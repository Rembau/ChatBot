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
	private LinkedList<String> allListForPeo=new LinkedList<String>(); 	//记录聊天记录
	private LinkedList<String> allListForBot=new LinkedList<String>(); 	//记录聊天记录
	private LinkedList<TreeSet<String>> keyList=new LinkedList<TreeSet<String>>();	//按顺序记录聊天的内容关键字对方
	private NowQuestionAnswers nowAnswersAndQuestion = new NowQuestionAnswers();   //当前问题的可回答答案
	private PeopleState peopleState = new PeopleState();  //用户输入当前问题的一些状态
	private long replyTime=0;
	/**
	 * 记录重复的问题
	 */
	private RepeatQuestions repeatQuation = new RepeatQuestions();
	/**
	 * 训练机器人标志
	 * 1：验证对方是否同意训练，如果接收到Y同意，markConsult=2,否则丢弃对方的话结束
	 * 2：验证对方的训练是否符合规则，符合 记录，不符合结束
	 */
	private int markConsult=0;
	/**
	 * 讨论自己属性标志
	 * 1：上次谈话内容与自己的属性有关
	 */
	private int markAboutSelf=0;
	
	/**
	 * 标志自己上一个回答是否是问句
	 */
	private boolean isQuestionOfMyself =false;
	/**
	 * 标志上一句机器人是否能回答出来。
	 */
	public Memory(){
		Calendar calendar=Calendar.getInstance();
		this.replyTime=calendar.getTimeInMillis()/1000;
	}
	public void endInit(){
		isQuestionOfMyself = nowAnswersAndQuestion.getNowMemory().isQuestion();
	}
	/**
	 * 判断机器人上一句是否是问句
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
	 * 处理重复相关
	 * 
	 */
	public int handleRepeatNum(){
		int num=0;
		RepeatEntity repeatEntity;
		NowMemory nowMemory = nowAnswersAndQuestion.getNowMemory();
		if(isRepeat()){
			if((repeatEntity = repeatQuation.isExist(nowMemory.getRecordNow())) != null){
				repeatEntity.setRepeatStr(nowMemory.getRecordNow());
				repeatEntity.recordRepeatNum();  //连续重复重复次数加一
				logger.info("重复大于2次");
			}
			else {
				repeatQuation.add((repeatEntity=new RepeatEntity(nowMemory.getRecordNow())));
				logger.info("重复");
			}
			num=repeatEntity.getRepeatNum();
			nowMemory.setRepeatNow(true);   //设置当前是重复的
		} else if((repeatEntity = repeatQuation.isExist(nowMemory.getRecordNow())) != null){
			repeatEntity.setRepeatStr(nowMemory.getRecordNow());
			num=repeatEntity.getRepeatNum();
			nowMemory.setRepeatNow(false);  //当前不是重复的，就是不重复的
			logger.info("重复大于2次");
		} else {
			nowAnswersAndQuestion.initAnswers();  //不是重复的回答列表初始化
			nowMemory.setRepeatNow(false);  //同上
		}
		repeatQuation.forgerHandle(repeatEntity);  //遗忘处理
		return num;
	}
	
	/**
	 * 判断是否重复
	 * 方法1：取代词，取动词，取名词 ，取助词 去掉重复的，与上一句的对比（两句间）
	 * 方法2：如果两段话长度相同，直接比较。否则把稍长的那段剪短
	 * 这里用的是方法2
	 * @return 重复返回true，不重复返回false
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
	 * 生气
	 */
	public void anger(){
		talker.upTypeRank();
	}
	/**
	 * 情绪恢复
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
	 * 初始化用户输入当前句子的状态，输入字符速度，鼠标使用次数等
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
		int irascible; //0,平和 ，1不平和
		int proficient; //-1 乱打，0,熟练 ，1不熟练 ，2不专心
		int normal; //0,正常 ，1火星人
		long useTime;  //距离我发信息过去 用时
	}
}