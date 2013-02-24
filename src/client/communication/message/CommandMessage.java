//Source file: D:\\PROGRAM FILES\\WORKSPACE\\NETWORKBOT\\client\\communication\\message\\CommandMessage.java

package client.communication.message;


public class CommandMessage extends MessageCombine implements Message 
{
   
   /**
    * @param s[]
    * @roseuid 50187B52008E
    */
   public CommandMessage(String s[]) 
   {
		this.s=s;
		this.type=3;    
   }
}
