package server.tools;

import java.util.LinkedList;

/**
 * �ж������ַ����Ƿ��� ���ظ������⡱
 * @author linmap
 *
 */
public class RepeatCompare {
	/**
	 * ���ܴ������С����
	 */
	private static int minLengthAccept=1;
	public static boolean isRepeat(String record,String recordNow){
		int length_record=record.length();
		if(length_record<minLengthAccept){
			return false;
		}
		int length_recordNow=recordNow.length();
		if(length_recordNow<minLengthAccept){
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