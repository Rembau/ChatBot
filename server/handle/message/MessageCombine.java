package server.handle.message;

public class MessageCombine {
	public int type=0;
	public String separate="::::";
	public String s[];
	public String getMessageContent(){
		String str="";
		for(int i=0;i<s.length-1;i++){
			str+=s[i]+""+separate;
		}
		str+=s[s.length-1];
		return this.combine(str);
	}
	public String combine(String str){
		return type+"_"+str;
	}
}
