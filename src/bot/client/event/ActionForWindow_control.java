package bot.client.event;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import bot.comm.Context;

public class ActionForWindow_control implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(Context.window_control_min)){
			Container top = ((JButton)e.getSource()).getTopLevelAncestor();
			((JFrame)top).setExtendedState(JFrame.ICONIFIED);
		} else if(e.getActionCommand().equals(Context.window_control_max)){
			Container top = ((JButton)e.getSource()).getTopLevelAncestor();
			if(((JFrame)top).getExtendedState()!=JFrame.MAXIMIZED_BOTH){
				((JFrame)top).setExtendedState(JFrame.MAXIMIZED_BOTH);
			} else{
				((JFrame)top).setExtendedState(JFrame.NORMAL);
			}
		} else{
			System.exit(0);
		}
	}

}
