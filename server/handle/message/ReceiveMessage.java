package server.handle.message;

/**
 * @author Administrator
 *	客户端接受信息处理
 */
public class ReceiveMessage {
	private String message;
	private int type;
	private String separate="::::";
	private String content[];
	public ReceiveMessage(String str){
		this.message=str;
		handle();
	}
	public String[] getContents(){
		return this.content;
	}
	/**
	 * @return 1：聊天信息 2：用户信息 3:用户输入的命令
	 */
	public int getType(){
		return this.type;
	}
	void handle(){
		String typeStr=message.substring(0,message.indexOf("_"));
		type=Integer.valueOf(typeStr);
		
		String contents=message.substring(message.indexOf("_")+1);
		content=contents.split(separate);
	}
	public static void main(String args[]){
		
	}
}
