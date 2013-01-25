package server.tools;

public class CheckQuestion {
	public static boolean isHaveYou(String content){
		if(content.indexOf("你")==-1){
			return false;
		} else {
			return true;
		}
	}
	public static boolean isHaveMe(String content){
		if(content.indexOf("我")==-1){
			return false;
		} else {
			return true;
		}
	}
	public static boolean isHaveHim(String content){
		if(content.indexOf("他")==-1 && content.indexOf("她")==-1 && content.indexOf("它")==-1){
			return false;
		} else {
			return true;
		}
	}
	public static boolean isQuestion(String content){
		if(content.endsWith("?") || content.endsWith("？") || content.indexOf("什么")!=-1 || 
				content.indexOf("怎么")!=-1 || content.indexOf("谁")!=-1 || content.indexOf("哪")!=-1){
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 是否是否定句
	 * @param content
	 * @return
	 */
	public static boolean isNegative(String content){
		boolean isNegative=false;
		int j=0;
		if((j=content.indexOf("不"))!=-1 ){
			if(j-1>=0 && j+1<content.length() && content.charAt(j-1) == content.charAt(j+1))
				return false;
			if(j-2>=0 && j+2<content.length() && content.charAt(j-1) ==content.charAt(j+2) && 
					content.charAt(j-2) == content.charAt(j+1))
				return false;
		}
		content = content.replace("?", "");
		content = content.replace(".", "");
		content = content.replace("？", "");
		content = content.replace("。", "");
		char[] ch = content.toCharArray();
		
		for(int i=0;i<ch.length-1;i++){
			if(ch[i]=='不' || ch[i]=='没'){
				isNegative=!isNegative;
			}
		}
		return isNegative;
	}
	public static void main(String args[]){
		System.out.println(CheckQuestion.isNegative("好不好？"));
	}
}
