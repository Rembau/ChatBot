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
			logger.info("����ɻش��:"+a);
			answers.add(a);
		}
	}
	public void addNotAnswer(String a){
		if(notAnswer.length()==0){
			notAnswer=a;
			logger.info("����Unknow�ش�:"+a);
		}
	}
	public LinkedHashSet<String> getAnswers(){
		if(answers.size()==0){
			logger.info("û�пɻش�𰸣�����Unknow�ش�!");
			answers.add(this.notAnswer);
		}
		logger.info("�𰸼�:"+answers.size()+" "+this.answers);
		return this.answers;
	}
	public boolean isHave(){
		return this.isHaveNotAnwser;
	}
}
