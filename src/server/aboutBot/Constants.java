package server.aboutBot;

import java.util.LinkedList;
import java.util.Random;

public class Constants {
	public static final String cmd_tra_teacher = "teacher";
	public static final String cmd_tra_end = "end";
	public static final String cmd_tra_train = "train";
	
	public static final String cmd_tra_answer = "answer";
	
	public static final String answer_none = "none";
	public static final String answer_cannot1[] = new String[]{"我不知道你想让我说什么呢。"};
	public static final String answer_cannot2[] = new String[]{"我太笨了，竟不理解你的意思。请说的简单点吧。"};
	public static final String answer_request_teach[] = new String[]{
		"（如果你愿意教我怎么回答，请回复'y'）"
	}; 
	public static final String answer_doubt_say[] = new String[]{
		"你想说什么呢？"
	};
	public static final String answer_doubt_ask[] = new String[]{
		"你想问什么呢？"
	};
	public static final String answer_forRepeat_say[] = new String[]{
		"重复说同一句话，有意思吗？"
	};
	public static final String answer_forRepeat_ask[] = new String[]{
		"重复问同一句话，有意思吗？"
	};
	public static final String answer_forLongNOAction[] = new String[]{
		"这么长时间才回复，跟我说老实话，你在干什么？"
	};
	public static final String answer_forTypeSlow[] = new String[]{
		"你的打字速度太慢了吧，是不是没有专心和我聊天？"
	};
	public static final String answer_thankTeacher[] = new String[]{
		"我又学到新东西了，谢谢你。"
	};
	public static final String answer_teachNOteach[] = new String[]{
		"说好教我，又不教我，你不诚信。（可以继续训练,输入'n'，结束训练）"
	};
	public static final String answer_doNotTeach[] = new String[]{
		"既然你这么不愿意教我，那就算了。","那算了吧。"
	};
	public static final String answer_teachForm2 = "请输入我应该回答的答案！形如：answer答案 必以answer开头。";
	public static final String answer_teachForm1 = "你已经进入训练模式，请按照训练规则进行训练\n" +
	"Q:[问题]\n" +
	"A:[对应的回答]";
	public static final String answer_thanks[] = new String[]{
		"谢谢你！","Thank you."
	};
	public static final String answer_oneTrainEnd[] = new String[]{
		"我已经牢牢记住你说的话了，你可以继续训练，也可以输入end结束训练！"
	};
	public static final String answer_unhappy[] = new String[]{
		"我要生气了。","你真无聊。","一点意思都没有啊。"
	};
	String str="";
	private static LinkedList<String> reply_yes=new LinkedList<String>(){
		private static final long serialVersionUID = 1L;
		{
			add("y");add("Y");add("好");add("可以");
		}
	};
	private static LinkedList<String> reply_no=new LinkedList<String>(){
		private static final long serialVersionUID = 1L;
		{
			add("n");add("N");add("不");
		}
	};
	public static boolean isPositive(String str){
		for (String str_ : reply_yes) {
			if(str.startsWith(str_)){
				return true;
			}
		}
		return false;
	}
	public static boolean isNegative(String str){
		for (String str_ : reply_no) {
			if(str.startsWith(str_)){
				return true;
			}
		}
		return false;
	}
	public static String getAnswerCannot1() {
		return answer_cannot1[random(answer_cannot1.length)];
	}
	public static String getAnswerCannot2() {
		return answer_cannot2[random(answer_cannot2.length)];
	}
	private static int random(int limit){
		return new Random().nextInt(limit);
	}
	public static String getAnswerRequestTeach() {
		return answer_request_teach[random(answer_request_teach.length)];
	}
	public static String getAnswerDoubtSay() {
		return answer_doubt_say[random(answer_doubt_say.length)];
	}
	public static String getAnswerDoubtAsk() {
		return answer_doubt_ask[random(answer_doubt_ask.length)];
	}
	public static String getAnwerForrepeatSay() {
		return answer_forRepeat_say[random(answer_forRepeat_say.length)];
	}
	public static String getAnwerForrepeatAsk() {
		return answer_forRepeat_ask[random(answer_forRepeat_ask.length)];
	}
	public static String getAnwerForlongnoaction() {
		return answer_forLongNOAction[random(answer_forLongNOAction.length)];
	}
	public static String getAnswerFortypeslow() {
		return answer_forTypeSlow[random(answer_forTypeSlow.length)];
	}
	public static String getAnswerThankteacher() {
		return answer_thankTeacher[random(answer_thankTeacher.length)];
	}
	public static String getAnswerTeachnoteach() {
		return answer_teachNOteach[random(answer_teachNOteach.length)];
	}
	public static String getAnswerDoNotteach() {
		return answer_doNotTeach[random(answer_doNotTeach.length)];
	}
	public static String getAnswerThanks() {
		return answer_thanks[random(answer_thanks.length)];
	}
	public static String getAnswerOnetrainend() {
		return answer_oneTrainEnd[random(answer_oneTrainEnd.length)];
	}
	public static String getAnswerUnhappy() {
		return answer_unhappy[random(answer_unhappy.length)];
	}
}
