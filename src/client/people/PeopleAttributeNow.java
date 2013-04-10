//Source file: D:\\PROGRAM FILES\\WORKSPACE\\NETWORKBOT\\client\\people\\PeopleAttributeNow.java

package client.people;

import java.util.Calendar;

import org.apache.log4j.Logger;


/**
 * 记录当前输入时的输入属性
 * @author Administrator
 */
public class PeopleAttributeNow 
{
	private static final Logger logger = Logger.getLogger(PeopleAttributeNow.class);
   /**
    * 鼠标使用次数限制
    */
   private static int limitMouseUseNum = 2;
   
   /**
    * 乱打字符速度限制
    */
   private static float limitProficient1 = (float ) 0.6;
   
   /**
    * 不熟练速度限制
    */
   private static float limitProficient2 = (float ) 3;
   
   /**
    * 不专心速度限制
    */
   private static float limitProficient3 = (float ) 10;
   
   /**
    * 不平和回车使用次数限制
    */
   private static int limitIrascible = 2;
   private long startTime = 0;
   
   /**
    * 开始输入时间
    */
   private long second = 0;
   
   /**
    * 输入时间
    */
   private int mouseUseNum = 0;
   
   /**
    * 使用鼠标次数
    */
   private int backKeyUseNum = 0;
   
   /**
    * 使用删除键的次数
    */
   private float wordsNum = 0;
   
   /**
    * 输入框的输入字符数
    */
   private boolean isHandle = false;
   private State state = new State ();
   
   /**
    * @roseuid 50187B5201AA
    */
   public void init() 
   {
		this.second=0;
		this.mouseUseNum=0;
		this.backKeyUseNum=0;
		this.wordsNum=0;
		this.isHandle=false;
		this.state.init();    
   }
   
   /**
    * @return long
    * @roseuid 50187B5201AB
    */
   public long getSecond() 
   {
		return this.second;    
   }
   
   /**
    * @roseuid 50187B5201AC
    */
   public void setStartTime() 
   {
		logger.info("计时开始。");
		Calendar calendar=Calendar.getInstance();
		this.startTime=calendar.getTimeInMillis()/1000;    
   }
   
   /**
    * @roseuid 50187B5201AD
    */
   public void setSecond() 
   {
		logger.info("计时结束。");
		Calendar calendar=Calendar.getInstance();
		this.second=calendar.getTimeInMillis()/1000-this.startTime;    
   }
   
   /**
    * @return int
    * @roseuid 50187B5201B5
    */
   public int getMouseUseNum() 
   {
		return this.mouseUseNum;    
   }
   
   /**
    * @roseuid 50187B5201B6
    */
   public void addMouseUseNum() 
   {
		this.mouseUseNum++;    
   }
   
   /**
    * @return int
    * @roseuid 50187B5201B7
    */
   public int getBackKeyUseNum() 
   {
		return this.backKeyUseNum;    
   }
   
   /**
    * @roseuid 50187B5201B8
    */
   public void addBackKeyUseNum() 
   {
		this.backKeyUseNum++;    
   }
   
   /**
    * @return float
    * @roseuid 50187B5201B9
    */
   public float getWordsNum() 
   {
		return this.wordsNum;    
   }
   
   /**
    * @roseuid 50187B5201BA
    */
   public void addWordsNum() 
   {
		this.wordsNum++;    
   }
   
   /**
    * @roseuid 50187B5201C5
    */
   public void handle() 
   {
		if(this.wordsNum==0){
			return;
		}
		if(!this.isHandle){
			this.setSecond();
			this.state.handle();
			this.isHandle=true;
		}    
   }
   
   /**
    * @return int
    * @roseuid 50187B5201C6
    */
   public int getIrascible() 
   {
		return state.irascible;    
   }
   
   /**
    * @return int
    * @roseuid 50187B5201C7
    */
   public int getProficient() 
   {
		return state.proficient;    
   }
   
   /**
    * @return int
    * @roseuid 50187B5201C8
    */
   public int getNormal() 
   {
		return state.normal;    
   }
   
   class State 
   {
      int irascible;
      
      /**
       * 0,平和 ，1不平和
       */
      int proficient;
      
      /**
       * -1，乱打或短小回复 0,熟练 ，1不熟练 ，2不专心
       */
      int normal;
      
      /**
       * 0,正常 ，1火星人
       * @roseuid 50187B5201D6
       */
      public void init() 
      {
			this.irascible=0;
			this.proficient=0;
			this.normal=0;       
      }
      
      /**
       * @roseuid 50187B5201D7
       */
      public void handle() 
      {
			if(backKeyUseNum>PeopleAttributeNow.limitIrascible){
				this.irascible=1;
			}
			logger.info("速度"+second/wordsNum+"鼠标使用次数"+mouseUseNum+"回车使用次数"+backKeyUseNum);
			if(wordsNum!=0){
				float r = second/wordsNum;
				if(r<PeopleAttributeNow.limitProficient1 || second<1){
					this.proficient=-1;
				} else if(r>PeopleAttributeNow.limitProficient2 || mouseUseNum>=PeopleAttributeNow.limitMouseUseNum){
					this.proficient=1;
				} 
				if(second/wordsNum>PeopleAttributeNow.limitProficient3){
					this.proficient=2;
				}
			}
			logger.info("属性：平和"+this.irascible+"熟练"+this.proficient);       
      }
   }
}
