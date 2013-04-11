package server.aboutBot;

import java.util.LinkedList;
import java.util.Random;

public class Constants {
	public static final String cmd_tra_teacher = "teacher";
	public static final String cmd_tra_end = "end";
	public static final String cmd_tra_train = "train";
	
	public static final String cmd_tra_answer = "answer";
	
	public static final String answer_none = "none";
	public static final String answer_cannot1[] = new String[]{"�Ҳ�֪����������˵ʲô�ء�"};
	public static final String answer_cannot2[] = new String[]{"��̫���ˣ�������������˼����˵�ļ򵥵�ɡ�"};
	public static final String answer_request_teach[] = new String[]{
		"�������Ը�������ô�ش���ظ�'y'��"
	}; 
	public static final String answer_doubt_say[] = new String[]{
		"����˵ʲô�أ�"
	};
	public static final String answer_doubt_ask[] = new String[]{
		"������ʲô�أ�"
	};
	public static final String answer_forRepeat_say[] = new String[]{
		"�ظ�˵ͬһ�仰������˼��"
	};
	public static final String answer_forRepeat_ask[] = new String[]{
		"�ظ���ͬһ�仰������˼��"
	};
	public static final String answer_forLongNOAction[] = new String[]{
		"��ô��ʱ��Żظ�������˵��ʵ�������ڸ�ʲô��"
	};
	public static final String answer_forTypeSlow[] = new String[]{
		"��Ĵ����ٶ�̫���˰ɣ��ǲ���û��ר�ĺ������죿"
	};
	public static final String answer_thankTeacher[] = new String[]{
		"����ѧ���¶����ˣ�лл�㡣"
	};
	public static final String answer_teachNOteach[] = new String[]{
		"˵�ý��ң��ֲ����ң��㲻���š������Լ���ѵ��,����'n'������ѵ����"
	};
	public static final String answer_doNotTeach[] = new String[]{
		"��Ȼ����ô��Ը����ң��Ǿ����ˡ�","�����˰ɡ�"
	};
	public static final String answer_teachForm2 = "��������Ӧ�ûش�Ĵ𰸣����磺answer�� ����answer��ͷ��";
	public static final String answer_teachForm1 = "���Ѿ�����ѵ��ģʽ���밴��ѵ���������ѵ��\n" +
	"Q:[����]\n" +
	"A:[��Ӧ�Ļش�]";
	public static final String answer_thanks[] = new String[]{
		"лл�㣡","Thank you."
	};
	public static final String answer_oneTrainEnd[] = new String[]{
		"���Ѿ����μ�ס��˵�Ļ��ˣ�����Լ���ѵ����Ҳ��������end����ѵ����"
	};
	public static final String answer_unhappy[] = new String[]{
		"��Ҫ�����ˡ�","�������ġ�","һ����˼��û�а���"
	};
	String str="";
	private static LinkedList<String> reply_yes=new LinkedList<String>(){
		private static final long serialVersionUID = 1L;
		{
			add("y");add("Y");add("��");add("����");
		}
	};
	private static LinkedList<String> reply_no=new LinkedList<String>(){
		private static final long serialVersionUID = 1L;
		{
			add("n");add("N");add("��");
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
