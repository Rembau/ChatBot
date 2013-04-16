package bot.server.tools;

import java.util.LinkedList;

import bot.comm.Context;


/**
 * 判断两个字符串是否是 “重复的问题”
 * @author linmao
 *
 */
public class RepeatCompare {
	public static boolean isRepeat(String record,String recordNow){
		int length_record=record.length();
		if(length_record<Context.minLengthAccept){
			return false;
		}
		int length_recordNow=recordNow.length();
		if(length_recordNow<Context.minLengthAccept){
			return false;
		}
		double ratio = length_record/(length_recordNow*1.0);
		if(ratio > 2 || ratio <0.5){
			return false;
		}
		if(length_recordNow<length_record){
			return record.subSequence(0, length_recordNow).equals(recordNow);
		}
		else {
			return recordNow.substring(0,length_record).equals(record);
		}
	}
	public static boolean isRepeat(LinkedList<String> questionList,String recordNow){
		for(String str:questionList){
			if(isRepeat(str,recordNow)){
				return true;
			}
		}
		return false;
	}
}