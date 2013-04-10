package server.aboutBot;

import java.util.Random;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import server.tools.CheckQuestion;

public class NowQuestionAnswers {
	private static final Logger logger = Logger.getLogger(NowQuestionAnswers.class);
	private TreeMap<Integer,QuestionAnswer> answers;
	private NowMemory nowMemory = new NowMemory();   //当前问题的一些信息
	public NowQuestionAnswers(){
		initAnswers();
	}
	public void initAnswers(){
		this.answers = new TreeMap<Integer,QuestionAnswer>(); 
	}
	public void addAnswer(String ans,
			int wei,
			int id,
			int y,
			int h,
			int m,
			int l,
			String question,
			int isQ,
			int haveNo,
			int keyNum){
		QuestionAnswer qa = new QuestionAnswer(ans,wei,id,y,h,m,l,question,isQ,haveNo,keyNum);
		//logger.info("========="+qa.answer);
		int point=qa.amountWeight();
		while(answers.containsKey(point)){
			point--;
		}
		logger.info(point);
		answers.put(point,qa);
	}
	public String getRandomAnswer(){
		if(answers.size()==0){
			return "none";
		}
		if(answers.size()==1){
			return getAnswer(0);
		}
		Random rd = new Random();
		int index = rd.nextInt(answers.size()-1);
		//logger.info("NowQuestionAnswers.getRandowAnswer(),随机数："+index+" 共"+answers.size());
		return getAnswer(index);
	}
	public String getAnswer(int r){
		int i=0;
		for(QuestionAnswer qa:answers.values()){
			if(i++==r){
				return qa.answer;
			}
		}
		return null;
	}
	public String getBestAnswer(){
		if(answers.size()==0){
			return "none";
		}
		return this.answers.get(answers.lastKey()).answer;
	}
	public NowMemory getNowMemory(){
		return this.nowMemory;
	}
	class QuestionAnswer{
		int id;   //数据库中id
		String answer;
		int weight;
		int haveYou=0;
		int haveHim=0;
		int haveMe=0;
		int questionLength=0;
		String question;
		int isQuestion=0;
		int haveNo=0;
		int keyNum=0;
		int points=0;
		public QuestionAnswer(String ans,
				int wei,
				int id,
				int y,
				int h,
				int m,
				int l,
				String question,
				int isQ,
				int haveNo,
				int keyNum){
			this.answer = ans;
			this.weight=wei;
			this.id=id;
			this.haveHim=h;
			this.haveMe=m;
			this.haveYou=y;
			this.questionLength=l;
			this.question=question;
			this.isQuestion=isQ;
			this.haveNo=haveNo;
			this.keyNum = keyNum;
		}
		public int amountWeight(){
			points+=weight;
			if((haveYou==1 && nowMemory.isHaveYou)){
				points+=10;
			} else if(nowMemory.isHaveYou || haveYou==1){
				points-=5;
			}
			if(haveMe==1 && nowMemory.isHaveMe){
				points+=10;
			} else if(haveMe == 1 || nowMemory.isHaveMe){
				points-=5;
			}
			if(haveHim==1 && nowMemory.isHaveHim){
				points+=10;
			} else if(haveHim == 1 && nowMemory.isHaveHim){
				points-=5;
			}
			if(haveNo==1 && nowMemory.isNegative){
				points+=10;
			} else if(haveNo==1 || nowMemory.isNegative){
				points-=5;
			}
			if(isQuestion==1 && nowMemory.isQuestion){
				points+=10;
			} else if(isQuestion==1 || nowMemory.isQuestion){
				points-=5;
			}
			points -= Math.abs(keyNum-nowMemory.keyNum)*5;
			return points;
		}
	}
	/**
	 * 保存当前对方问题的一些信息
	 * @author Administrator
	 *
	 */
	class NowMemory {
		private boolean isQuestion=false;
		/**
		 * 本句话是否是重复的
		 */
		private boolean isRepeatNow=false;
		/**
		 * 有‘你’
		 */
		private boolean isHaveYou=false;
		/**
		 * 有‘我’
		 */
		private boolean isHaveMe=false;
		/**
		 * 有‘ta’
		 */
		private boolean isHaveHim=false;
		private int keyNum=0;
		/**
		 * 当前对方的问题
		 */
		private String recordNow;
		private String recordLast;
		/**
		 * 是否是否定句型
		 */
		private boolean isNegative =false;
		public NowMemory(){}
		public boolean isRepeatNow(){
			return this.isRepeatNow;
		}
		public void setRepeatNow(boolean b){
			this.isRepeatNow=b;
		}
		public boolean isQuestion(){
			return this.isQuestion;
		}
		public boolean isNegative(){
			return this.isNegative;
		}
		public boolean isHaveYou(){
			return this.isHaveYou;
		}
		public boolean isHaveMe(){
			return this.isHaveMe;
		}
		public boolean isHaveHim(){
			return this.isHaveHim;
		}
		public int getKeyNum(){
			return this.keyNum;
		}
		public void setKeyNum(int n){
			this.keyNum = n;
		}
		/**
		 * 设置当前对方的问题，并判断问题中是否有指示代词
		 * @param content
		 */
		public void init(String content){
			setRecordNow(content);
			this.isHaveYou=CheckQuestion.isHaveYou(content);
			this.isHaveMe=CheckQuestion.isHaveMe(content);
			this.isHaveHim=CheckQuestion.isHaveHim(content);
			this.isNegative = CheckQuestion.isNegative(content);
			this.isQuestion = CheckQuestion.isQuestion(content);
			
		}
		/**
		 * 设置对方的当前问题
		 * @param r
		 */
		public void setRecordNow(String r){
			this.recordLast=recordNow;
			r=r.replaceAll(" ", "");
			this.recordNow=r;
		}
		/**
		 * 获取对方的当前问题
		 * @return 
		 */
		public String getRecordNow(){
			return this.recordNow;
		}
		public String getRecordLast(){
			return this.recordLast;
		}
	}
}
