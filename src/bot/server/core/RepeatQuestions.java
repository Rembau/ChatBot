package bot.server.core;

import java.util.LinkedList;

import bot.server.tools.RepeatCompare;

public class RepeatQuestions {
	private LinkedList<RepeatEntity> repeatEntitys= new LinkedList<RepeatEntity>();
	/**
	 * ����ʱ��
	 */
	static int maxRepeatMemory=5;
	public void add(RepeatEntity r){
		repeatEntitys.addFirst(r);
	}
	/**
	 * �жϵ�ǰ�����Ƿ����ظ���������1�����⣬����Ƿ���ʵ�壬����ʵ����ظ��ַ�����
	 * @param str ��ǰ����
	 * @return ����ظ��������ظ���¼��ʵ�壬���򷵻�null
	 */
	public RepeatEntity isExist(String str){
		for(RepeatEntity r:repeatEntitys){
			String record=r.getRepeatStr();
			if(RepeatCompare.isRepeat(record,str)){
				return r;
			}
		}
		return null;
	}
	/**
	 * ���ظ�ʵ���е����� ����������һ���������Ϊ1��ɾ�����ظ�
	 */
	public void forgerHandle(RepeatEntity repeatEntity){
		LinkedList<RepeatEntity> removed= new LinkedList<RepeatEntity>();
		for(RepeatEntity r:repeatEntitys){
			if(r.getRepeatMemoryLevel()==1){
				removed.add(r);
				continue;
			}
			else if(r==repeatEntity){
				continue;
			}
			else {
				//System.out.println("��������"+r.getRepeatStr()+" "+r.getRepeatNum());
				r.setRepeatMemoryLevel();
				//System.out.println(r.getRepeatNum());
			}
		}
		repeatEntitys.removeAll(removed);
	}
}
class RepeatEntity{
	private int repeatNum=1; //��¼�ظ�����
	private String repeatStr;
	/**
	 * ��������
	 */
	private int repeatMemoryLevel=RepeatQuestions.maxRepeatMemory;
	public RepeatEntity(String r){
		this.repeatStr=r;
	}
	public void setRepeatMemoryLevel(){
		this.repeatMemoryLevel--;
	}
	public int getRepeatMemoryLevel(){
		return this.repeatMemoryLevel;
	}
	/**
	 * ����ʵ����ظ��ַ�������ʼ����������
	 * @param str
	 */
	public void setRepeatStr(String str){
		this.repeatStr=str;
		this.repeatMemoryLevel=RepeatQuestions.maxRepeatMemory;
	}
	public String getRepeatStr(){
		return this.repeatStr;
	}
	/**
	 * ��¼�ظ����ظ�������һ
	 */
	public void recordRepeatNum(){
		if(repeatNum<RepeatQuestions.maxRepeatMemory){
			repeatNum++;
		}
	}
	public void recordRepeatNum(int n){
		if(repeatNum<RepeatQuestions.maxRepeatMemory){
			repeatNum+=n;
		}
		if(repeatNum>RepeatQuestions.maxRepeatMemory){
			repeatNum=RepeatQuestions.maxRepeatMemory;
		}
	}
	/**
	 * ����ظ���¼
	 */
	public void clearRepeatNum(){
		repeatNum=1;
	}
	/**
	 * ��ȡ������ظ�����
	 * @return �����ظ�����
	 */
	public int getRepeatNum(){
		return this.repeatNum;
	}
}
