//Source file: D:\\PROGRAM FILES\\WORKSPACE\\NETWORKBOT\\client\\communication\\message\\ReceiveMessage.java

package client.communication.message;


/**
 * @author Administrator
 * 客户端接受信息处理
 */
public class ReceiveMessage 
{
   private String message;
   private int type;
   private String separate = "::::";
   private String content[];
   
   /**
    * @param str
    * @roseuid 50187B520072
    */
   public ReceiveMessage(String str) 
   {
		this.message=str;
		handle();    
   }
   
   /**
    * @return java.lang.String[]
    * @roseuid 50187B52007D
    */
   public String[] getContents() 
   {
		return this.content;    
   }
   
   /**
    * @return int
    * @roseuid 50187B52007E
    */
   public int getType() 
   {
		return this.type;    
   }
   
   /**
    * @roseuid 50187B52007F
    */
   void handle() 
   {
		String typeStr=message.substring(0,message.indexOf("_"));
		type=Integer.valueOf(typeStr);
		
		String contents=message.substring(message.indexOf("_")+1);
		content=contents.split(separate);    
   }
   
   /**
    * @param args[]
    * @roseuid 50187B520080
    */
   public static void main(String args[]) 
   {
    
   }
}
