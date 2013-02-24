//Source file: D:\\PROGRAM FILES\\WORKSPACE\\NETWORKBOT\\client\\communication\\message\\MessageCombine.java

package client.communication.message;


public class MessageCombine 
{
   public int type = 0;
   public String separate = "::::";
   public String s[];
   
   /**
    * @return java.lang.String
    * @roseuid 50187B52005D
    */
   public String getMessageContent() 
   {
		String str="";
		for(int i=0;i<s.length-1;i++){
			str+=s[i]+""+separate;
		}
		str+=s[s.length-1];
		return this.combine(str);    
   }
   
   /**
    * @param str
    * @return java.lang.String
    * @roseuid 50187B52005E
    */
   public String combine(String str) 
   {
		return type+"_"+str;    
   }
   
   /**
    * @return int
    * @roseuid 50187B520060
    */
   public int getType() 
   {
		return this.type;    
   }
}
