//Source file: D:\\PROGRAM FILES\\WORKSPACE\\NETWORKBOT\\client\\gui\\Login.java

package bot.client.gui;

import java.awt.BorderLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import bot.client.event.ActionForLogin;

public class Login extends JDialog 
{
   private static final long serialVersionUID = 1L;
   private JTextField user_field = new JTextField (10);
   private JPasswordField password_field = new JPasswordField (10);
   private JApplet frame;
   private ActionForLogin actionForLogin = new ActionForLogin (this);
   
   /**
    * @param frame
    * @roseuid 50187B4D0147
    */
   public Login(JApplet frame) 
   {
		super();
		this.frame=frame;
		this.setLayout(new BorderLayout());
		JPanel panel_1=new JPanel();
		panel_1.add(user_field);
		panel_1.add(password_field);
		this.add(panel_1,BorderLayout.CENTER);
		
		JPanel panel_2=new JPanel();
		JButton button_login= new JButton("µÇÂ¼");
		button_login.addActionListener(actionForLogin);
		JButton button_cancel= new JButton("È¡Ïû");
		button_cancel.addActionListener(actionForLogin);
		panel_2.add(button_login);
		panel_2.add(button_cancel);
		this.add(panel_2,BorderLayout.SOUTH);
		
		this.setBounds(350, 200, 200, 200);
		this.setVisible(true);    
   }
   
   /**
    * public void send(String user_num,String password){
    * ((ChatFrame)frame).sendUserInfor(user_num,password);
    * }
    * @return javax.swing.JApplet
    * @roseuid 50187B4D0149
    */
   public JApplet getCF() 
   {
		return this.frame;    
   }
   
   /**
    * @return java.lang.String
    * @roseuid 50187B4D014A
    */
   public String getUserNum() 
   {
		return user_field.getText();    
   }
   
   /**
    * @return java.lang.String
    * @roseuid 50187B4D014B
    */
   public String getPassword() 
   {
		return new String(password_field.getPassword());    
   }
   
   /**
    * @param message
    * @roseuid 50187B4D014C
    */
   public void prompt(String message) 
   {
		JOptionPane.showMessageDialog(this, message);    
   }
   
   /**
    * @roseuid 50187B4D0157
    */
   public void remove() 
   {
		this.setVisible(false);    
   }
}
