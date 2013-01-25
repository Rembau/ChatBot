package server.aboutBot;

import java.util.LinkedHashSet;

public class Answers {
	private boolean isHaveNotAnwser=false;   //
	private LinkedHashSet<String> answers =new LinkedHashSet<String>();
	private String notAnswer="";
	
	public void addCanAnswer(String a){
		if(a.length()!=0)
		answers.add(a);
	}
	public void addNotAnswer(String a){
		if(notAnswer.length()==0)
			notAnswer=a;
	}
	public LinkedHashSet<String> getAnswers(){
		if(answers.size()==0){
			answers.add(this.notAnswer);
		}
		System.out.println("´ð°¸¼¯£º"+this.answers);
		return this.answers;
	}
	public boolean isHave(){
		return this.isHaveNotAnwser;
	}
}
