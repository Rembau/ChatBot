package server.core;

public class People {
	private String userNum;
	private String name="none";  //名字
	private int typeRank=1;  //平等（温和）、导向（权威）、撒娇（谎）、引诱（道歉）、生气（怒火）、告别（开场）
	String sex;  //男，女
	String like;  //喜好
	double weight=10;
	String ip="";
	public void setIp(String ip){
		this.ip=ip;
	}
	public String getIp(){
		return this.ip;
	}
	public void setSex(String sex){
		this.sex = sex;
	}
	public String getSex(){
		return this.sex;
	}
	public void setWeight(double w){
		this.weight=w;
	}
	public double getWeight(){
		return this.weight;
	}
	public String getUserNum(){
		return this.userNum;
	}
	public void setUserNum(String userNum){
		this.userNum=userNum;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return this.name;
	}
	public void upTypeRank(){
		if(typeRank<6){
			typeRank++;
		}
	}
	public void downTypeRank(){
		if(typeRank>1){
			typeRank--;
		}
	}
	/**
	 * 获取对方的类型
	 * @return 表示类型的数字
	 */
	public void setRankOfPeople(int rank){
		this.typeRank=rank;
	}
	public int getRankOfPeople(){
		return this.typeRank;
	}
}
