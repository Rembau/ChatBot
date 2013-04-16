//Source file: D:\\PROGRAM FILES\\WORKSPACE\\NETWORKBOT\\client\\communication\\message\\ChatContent.java

package bot.client.communication.message;

import bot.comm.Message;
import bot.comm.MessageCombine;


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
