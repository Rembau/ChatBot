package bot.server.core;

import java.util.LinkedList;

import bot.server.tools.RepeatCompare;

public class RepeatQuestions {
	private LinkedList<RepeatEntity> repeatEntitys= new LinkedList<RepeatEntity>();
	/**
	 * 遗忘时间
	 */
	static int maxRepeatMemory=5;
	public void add(RepeatEntity r){
		repeatEntitys.addFirst(r);
	}
	/**
	 * 判断当前问题是否是重复次数大于1的问题，如果是返回实体，并把实体的重复字符重置
	 * @param str 当前问题
	 * @return 如果重复，返回重复记录的实体，否则返回null
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
	 * 把重复实体中的所有 遗忘度量减一，如果度量为1则删除此重复
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
				//System.out.println("遗忘处理："+r.getRepeatStr()+" "+r.getRepeatNum());
				r.setRepeatMemoryLevel();
				//System.out.println(r.getRepeatNum());
			}
		}
		repeatEntitys.removeAll(removed);
	}
}
class RepeatEntity{
	private int repeatNum=1; //记录重复次数
	private String repeatStr;
	/**
	 * 遗忘度量
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
	 * 重置实体的重复字符，并初始化遗忘度量
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
	 * 记录重复，重复次数加一
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
	 * 清空重复记录
	 */
	public void clearRepeatNum(){
		repeatNum=1;
	}
	/**
	 * 获取问题的重复次数
	 * @return 问题重复次数
	 */
	public int getRepeatNum(){
		return this.repeatNum;
	}
}
