package bot.server.core.analyzer;

public class ResultAnswer {
	private String question;
	private String answer;
	private String userId;
	private String questionId;
	public String getQuestionId(){
		return this.questionId;
	}
	public String getQuestion(){
		return this.question;
	}
	public void setQuestion(String question){
		this.question=question;
	}
	public String getAnswer(){
		return this.answer;
	}
	public void setAnswer(String answer){
		this.answer=answer;
	}public String getUserId(){
		return this.userId;
	}
	public void setUserId(String userId){
		this.userId=userId;
	}
}