//Source file: D:\\PROGRAM FILES\\WORKSPACE\\NETWORKBOT\\client\\communication\\message\\ChatContent.java

package client.communication.message;

import comm.Message;
import comm.MessageCombine;


public class ChatContent extends MessageCombine implements Message 
{
   
   /**
    * @param s[]
    * @roseuid 50187B520028
    */
   public ChatContent(String s[]) 
   {
		this.s=s;
		this.type=1;    
   }
}
