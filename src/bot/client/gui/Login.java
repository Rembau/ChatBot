//Source file: D:\\PROGRAM FILES\\WORKSPACE\\NETWORKBOT\\client\\gui\\Login.java

package bot.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import bot.client.event.ActionForLogin;
import bot.comm.Context;

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
   public Login(JApplet frame,boolean m) 
   {
		super();
		this.setUndecorated(true);
		this.frame=frame;

		JPanel panel_top = new JPanel();
		panel_top.setOpaque(false);
		
		JPanel panel_login=new JPanel();
		panel_login.add(user_field);
		panel_login.add(password_field);
		panel_login.setOpaque(false);
		
		JButton button_login= new Bot_button("µÇÂ¼");
		button_login.addActionListener(actionForLogin);
		JButton button_cancel= new Bot_button("È¡Ïû");
		button_cancel.addActionListener(actionForLogin);
		
		JPanel panel_control=new JPanel();
		panel_control.add(button_login);
		panel_control.add(button_cancel);
		panel_control.setOpaque(false);
		
		JPanel login_bottom = new JPanel();
		login_bottom.setOpaque(false);
		login_bottom.setLayout(new BorderLayout());
		login_bottom.add(panel_login,BorderLayout.CENTER);
		login_bottom.add(panel_control,BorderLayout.SOUTH);
		
		JPanel login = new JPanel(){
			private static final long serialVersionUID = 1L;
			public void paintComponent(Graphics g) {
			     super.paintComponent(g);
			     Image image = Toolkit.getDefaultToolkit().createImage(Context.image_path+"login.png");
			     int x = this.getWidth();
			     int y = this.getHeight();
			     ImageIcon img = new ImageIcon(image.getScaledInstance(x, y, Image.SCALE_DEFAULT));
			     g.drawImage(img.getImage(),0,0,null);
			 }
		};
		login.setBorder(new LineBorder(Color.white,1,true));
		login.setLayout(new GridLayout(2,0));
		login.add(panel_top);
		login.add(login_bottom);
		
		this.setLayout(new BorderLayout());
		this.add(login,BorderLayout.CENTER);
		this.setBounds(350, 200, 200, 170);
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
