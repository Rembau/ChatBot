package bot.client.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.sun.awt.AWTUtilities;

import bot.comm.Context;

public class ActionForWindow_control implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		JFrame window = (JFrame) ((JButton)e.getSource()).getTopLevelAncestor();
		if(e.getActionCommand().equals(Context.window_control_min)){
			window.setExtendedState(JFrame.ICONIFIED);
		} else if(e.getActionCommand().equals(Context.window_control_max)){
			if(window.getExtendedState()!=JFrame.MAXIMIZED_BOTH){
				window.setExtendedState(JFrame.MAXIMIZED_BOTH);
			} else{
				window.setExtendedState(JFrame.NORMAL);
			}
			RoundRectangle2D.Double rr = new RoundRectangle2D.Double(0,0,
					window.getWidth(),window.getHeight(),
					10,10);
			AWTUtilities.setWindowShape(window,rr);
		} else{
			System.exit(0);
		}
	}
}
