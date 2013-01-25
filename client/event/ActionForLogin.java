//Source file: D:\\PROGRAM FILES\\WORKSPACE\\NETWORKBOT\\client\\event\\ActionForLogin.java

package client.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import client.gui.Login;
import client.gui.ChatFrame;

public class ActionForLogin implements ActionListener 
{
   private Login login;
   
   /**
    * @param login
    * @roseuid 50189B71014D
    */
   public ActionForLogin(Login login) 
   {
		this.login=login;    
   }
   
   /**
    * @param e
    * @roseuid 50189B710177
    */
   public void actionPerformed(ActionEvent e) 
   {
		if(e.getActionCommand().equals("登录")){
			String user_num=login.getUserNum();
			if(user_num.equals("")){
				login.prompt("用户名不能为空！");
				return ;
			};
			String password=login.getPassword();
			if(password.equals("")){
				login.prompt("密码不能为空！");
				return ;
			};
			((ChatFrame)(login.getCF())).getCommunication().sendUserInfomation(user_num, password);
			login.remove();
		} else if(e.getActionCommand().equals("取消")){
			login.remove();
		}    
   }
}
