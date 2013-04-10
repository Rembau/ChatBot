//Source file: D:\\PROGRAM FILES\\WORKSPACE\\NETWORKBOT\\client\\people\\PeopleAttributeNow.java

package client.people;

import java.util.Calendar;

import org.apache.log4j.Logger;


/**
 * ��¼��ǰ����ʱ����������
 * @author Administrator
 */
public class PeopleAttributeNow 
{
	private static final Logger logger = Logger.getLogger(PeopleAttributeNow.class);
   /**
    * ���ʹ�ô�������
    */
   private static int limitMouseUseNum = 2;
   
   /**
    * �Ҵ��ַ��ٶ�����
    */
   private static float limitProficient1 = (float ) 0.6;
   
   /**
    * �������ٶ�����
    */
   private static float limitProficient2 = (float ) 3;
   
   /**
    * ��ר���ٶ�����
    */
   private static float limitProficient3 = (float ) 10;
   
   /**
    * ��ƽ�ͻس�ʹ�ô�������
    */
   private static int limitIrascible = 2;
   private long startTime = 0;
   
   /**
    * ��ʼ����ʱ��
    */
   private long second = 0;
   
   /**
    * ����ʱ��
    */
   private int mouseUseNum = 0;
   
   /**
    * ʹ��������
    */
   private int backKeyUseNum = 0;
   
   /**
    * ʹ��ɾ�����Ĵ���
    */
   private float wordsNum = 0;
   
   /**
    * �����������ַ���
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
		logger.info("��ʱ��ʼ��");
		Calendar calendar=Calendar.getInstance();
		this.startTime=calendar.getTimeInMillis()/1000;    
   }
   
   /**
    * @roseuid 50187B5201AD
    */
   public void setSecond() 
   {
		logger.info("��ʱ������");
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
       * 0,ƽ�� ��1��ƽ��
       */
      int proficient;
      
      /**
       * -1���Ҵ���С�ظ� 0,���� ��1������ ��2��ר��
       */
      int normal;
      
      /**
       * 0,���� ��1������
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
			logger.info("�ٶ�"+second/wordsNum+"���ʹ�ô���"+mouseUseNum+"�س�ʹ�ô���"+backKeyUseNum);
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
			logger.info("���ԣ�ƽ��"+this.irascible+"����"+this.proficient);       
      }
   }
}
