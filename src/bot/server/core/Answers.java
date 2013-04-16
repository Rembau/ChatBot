package bot.server.core;

import java.util.LinkedHashSet;

import org.apache.log4j.Logger;

public class Answers {
	private static final Logger logger = Logger.getLogger(Answers.class);
	private boolean isHaveNotAnwser=false;   //
	private LinkedHashSet<String> answers =new LinkedHashSet<String>();
	private String notAnswer="";
	
	public void addCanAnswer(String a){
		if(a.length()!=0){
			logger.info("插入可回答答案:"+a);
			answers.add(a);
		}
	}
	public void addNotAnswer(String a){
		if(notAnswer.length()==0){
			notAnswer=a;
			logger.info("插入Unknow回答:"+a);
		}
	}
	public LinkedHashSet<String> getAnswers(){
		if(answers.size()==0){
			logger.info("没有可回答答案，插入Unknow回答!");
			answers.add(this.notAnswer);
		}
		logger.info("答案集:"+answers.size()+" "+this.answers);
		return this.answers;
	}
	public boolean isHave(){
		return this.isHaveNotAnwser;
	}
}
