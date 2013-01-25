package server.tools;

public class CheckQuestion {
	public static boolean isHaveYou(String content){
		if(content.indexOf("��")==-1){
			return false;
		} else {
			return true;
		}
	}
	public static boolean isHaveMe(String content){
		if(content.indexOf("��")==-1){
			return false;
		} else {
			return true;
		}
	}
	public static boolean isHaveHim(String content){
		if(content.indexOf("��")==-1 && content.indexOf("��")==-1 && content.indexOf("��")==-1){
			return false;
		} else {
			return true;
		}
	}
	public static boolean isQuestion(String content){
		if(content.endsWith("?") || content.endsWith("��") || content.indexOf("ʲô")!=-1 || 
				content.indexOf("��ô")!=-1 || content.indexOf("˭")!=-1 || content.indexOf("��")!=-1){
			return true;
		} else {
			return false;
		}
	}
	/**
	 * �Ƿ��Ƿ񶨾�
	 * @param content
	 * @return
	 */
	public static boolean isNegative(String content){
		boolean isNegative=false;
		int j=0;
		if((j=content.indexOf("��"))!=-1 ){
			if(j-1>=0 && j+1<content.length() && content.charAt(j-1) == content.charAt(j+1))
				return false;
			if(j-2>=0 && j+2<content.length() && content.charAt(j-1) ==content.charAt(j+2) && 
					content.charAt(j-2) == content.charAt(j+1))
				return false;
		}
		content = content.replace("?", "");
		content = content.replace(".", "");
		content = content.replace("��", "");
		content = content.replace("��", "");
		char[] ch = content.toCharArray();
		
		for(int i=0;i<ch.length-1;i++){
			if(ch[i]=='��' || ch[i]=='û'){
				isNegative=!isNegative;
			}
		}
		return isNegative;
	}
	public static void main(String args[]){
		System.out.println(CheckQuestion.isNegative("�ò��ã�"));
	}
}
