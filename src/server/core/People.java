package server.core;

public class People {
	private String userNum;
	private String name="none";  //����
	private int typeRank=1;  //ƽ�ȣ��ºͣ�������Ȩ�������������ѣ������գ���Ǹ����������ŭ�𣩡���𣨿�����
	String sex;  //�У�Ů
	String like;  //ϲ��
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
	 * ��ȡ�Է�������
	 * @return ��ʾ���͵�����
	 */
	public void setRankOfPeople(int rank){
		this.typeRank=rank;
	}
	public int getRankOfPeople(){
		return this.typeRank;
	}
}
