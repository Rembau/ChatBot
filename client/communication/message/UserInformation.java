//Source file: D:\\PROGRAM FILES\\WORKSPACE\\NETWORKBOT\\client\\communication\\message\\UserInformation.java

package client.communication.message;


public class UserInformation extends MessageCombine implements Message 
{
   
   /**
    * @param s[]
    * @roseuid 50187B520040
    */
   public UserInformation(String s[]) 
   {
		this.s=s;
		this.type=2;    
   }
}
