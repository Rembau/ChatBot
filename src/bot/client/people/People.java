//Source file: D:\\PROGRAM FILES\\WORKSPACE\\NETWORKBOT\\client\\people\\People.java

package bot.client.people;


public class People 
{
   private int id = 0;
   
   /**
    * 0：游客   1：用户
    */
   private String status;
   private String name = "";
   private String userId = "";
   private PeopleAttributeNow pa = new PeopleAttributeNow ();
   
   /**
    * @return java.lang.String
    * @roseuid 50187B5200EF
    */
   public String getName() 
   {
		return name;    
   }
   
   /**
    * @param id
    * @roseuid 50187B5200F0
    */
   public void setUserId(String id) 
   {
		this.userId=id;    
   }
   
   /**
    * @return java.lang.String
    * @roseuid 50187B5200F2
    */
   public String getUserId() 
   {
		return userId;    
   }
   
   /**
    * @param name
    * @roseuid 50187B5200F3
    */
   public void setName(String name) 
   {
		this.name=name;
		changeId();    
   }
   
   /**
    * @roseuid 50187B5200FA
    */
   public void changeId() 
   {
		id=(++id)%2;    
   }
   
   /**
    * @return int
    * @roseuid 50187B5200FB
    */
   public int getId() 
   {
		return id;    
   }
   
   /**
    * @return java.lang.String
    * @roseuid 50187B5200FC
    */
   public String getStatus() 
   {
		return status;    
   }
   
   /**
    * @param st
    * @roseuid 50187B5200FD
    */
   public void setStatus(String st) 
   {
		status=st;    
   }
   
   /**
    * @return client.people.PeopleAttributeNow
    * @roseuid 50187B5200FF
    */
   public PeopleAttributeNow getPeopleAttributeNow() 
   {
		return this.pa;    
   }
}
