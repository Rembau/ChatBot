package server.handle.message;

import comm.Message;
import comm.MessageCombine;

public class TrainMessage  extends MessageCombine implements Message{
	/**
	 * @param s �����һ��Ϊ
	 * 1���û�����ѵ��ʱ����ʾ
	 */
	public TrainMessage(String s[]){
		this.s=s;
		this.type=3;
	}
}
