package server.handle.message;

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
