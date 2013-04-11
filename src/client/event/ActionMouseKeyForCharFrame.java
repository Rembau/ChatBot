//Source file: D:\\PROGRAM FILES\\WORKSPACE\\NETWORKBOT\\client\\event\\ActionMouseKeyForCharFrame.java

package client.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import client.gui.ChatFrame;
import client.gui.Login;
import client.people.PeopleAttributeNow;

public class ActionMouseKeyForCharFrame implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener, ActionListener 
{
   private ChatFrame cf;
   private PeopleAttributeNow pa;
   
   /**
    * @param cf
    * @roseuid 50187B510053
    */
   public ActionMouseKeyForCharFrame(ChatFrame cf) 
   {
		this.cf=cf;
		pa=cf.getPeople().getPeopleAttributeNow();    
   }
   
   /**
    * @param e
    * @roseuid 50187B510055
    */
   public void keyPressed(KeyEvent e) 
   {
		if(e.getKeyCode()==KeyEvent.VK_ENTER && e.getModifiers()==2){
			pa.handle();
			cf.getCommunication().sendCharMessage();
			pa.init();
		}
		//System.out.println("keyPressed="+e.getKeyChar()+"=="+e.getKeyCode());    
   }
   
   /**
    * @param e
    * @roseuid 50187B51005C
    */
   public void keyReleased(KeyEvent e) 
   {
		//System.out.println("keyReleased="+e.getKeyChar()+"=="+e.getKeyCode());    
   }
   
   /**
    * @param e
    * @roseuid 50187B51005E
    */
   public void keyTyped(KeyEvent e) 
   {
		if((int)e.getKeyChar()==8){
			pa.addBackKeyUseNum();
		} else{
			//System.out.println(pa.getWordsNum());
			if((int)e.getKeyChar()!=10){
				if(pa.getWordsNum()==0){
					pa.setStartTime();
				}
				pa.addWordsNum();
				if(pa.getWordsNum()>=10){
					pa.handle();
				}
			}
		}
		//System.out.println("keyTyped="+e.getKeyChar()+"=="+e.getKeyCode()+"=="+(int)e.getKeyChar());    
   }
   
   /**
    * @param e
    * @roseuid 50187B510060
    */
   public void mouseClicked(MouseEvent e) 
   {
		cf.getPeople().getPeopleAttributeNow().addMouseUseNum();    
   }
   
   /**
    * @param e
    * @roseuid 50187B510067
    */
   public void mouseEntered(MouseEvent e) 
   {
    
   }
   
   /**
    * @param e
    * @roseuid 50187B51006D
    */
   public void mouseExited(MouseEvent e) 
   {
    
   }
   
   /**
    * @param e
    * @roseuid 50187B51006F
    */
   public void mousePressed(MouseEvent e) 
   {
    
   }
   
   /**
    * @param e
    * @roseuid 50187B510071
    */
   public void mouseReleased(MouseEvent e) 
   {
    
   }
   
   /**
    * @param e
    * @roseuid 50187B51007D
    */
   public void mouseDragged(MouseEvent e) 
   {
    
   }
   
   /**
    * @param e
    * @roseuid 50187B51007F
    */
   public void mouseMoved(MouseEvent e) 
   {
    
   }
   
   /**
    * @param e
    * @roseuid 50187B510081
    */
   public void mouseWheelMoved(MouseWheelEvent e) 
   {
    
   }
   
   /**
    * @param e
    * @roseuid 50187B510092
    */
   public void actionPerformed(ActionEvent e) 
   {
		if(e.getActionCommand().trim().equals("发送")){
			pa.handle();
			cf.getCommunication().sendCharMessage();
			pa.init();
		} else if(e.getActionCommand().trim().equals("开始训练")){
			int select = JOptionPane.showConfirmDialog(cf, "在训练之前可以进行一些小测试，测试吗？");
			if(select==JOptionPane.YES_OPTION){
				
			} else if(select== JOptionPane.NO_OPTION){
				cf.getCommunication().sendCharMessage("teacher");
				((JButton)(e.getSource())).setText("结束训练");
			}
		} else if(e.getActionCommand().trim().equals("结束训练")){
			int select = JOptionPane.showConfirmDialog(cf, "你确定退出训练吗？");
			if(select==JOptionPane.YES_OPTION){
				cf.getCommunication().sendCharMessage("end");
				((JButton)(e.getSource())).setText("开始训练");
			}
		} else if(e.getActionCommand().trim().equals("登录")){
			new Login(cf);
		}//end if    
   }
}
