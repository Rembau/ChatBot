//Source file: D:\\PROGRAM FILES\\WORKSPACE\\NETWORKBOT\\client\\event\\ActionMouseKeyForCharFrame.java

package bot.client.event;

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

import bot.client.gui.ChatFrame;
import bot.client.gui.Login;
import bot.client.people.PeopleAttributeNow;
import bot.comm.Context;

public class ActionMouseKeyForCharFrame implements MouseListener,
		MouseMotionListener, MouseWheelListener, KeyListener, ActionListener {
	private ChatFrame cf;
	private PeopleAttributeNow pa;

	public ActionMouseKeyForCharFrame(ChatFrame cf) {
		this.cf = cf;
		pa = cf.getPeople().getPeopleAttributeNow();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER && e.getModifiers() == 2) {
			pa.handle();
			cf.getCommunication().sendCharMessage();
			pa.init();
		}
		// System.out.println("keyPressed="+e.getKeyChar()+"=="+e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {
		// System.out.println("keyReleased="+e.getKeyChar()+"=="+e.getKeyCode());
	}

	public void keyTyped(KeyEvent e) {
		if ((int) e.getKeyChar() == 8) {
			pa.addBackKeyUseNum();
		} else {
			// System.out.println(pa.getWordsNum());
			if ((int) e.getKeyChar() != 10) {
				if (pa.getWordsNum() == 0) {
					pa.setStartTime();
				}
				pa.addWordsNum();
				if (pa.getWordsNum() >= 10) {
					pa.handle();
				}
			}
		}
		// System.out.println("keyTyped="+e.getKeyChar()+"=="+e.getKeyCode()+"=="+(int)e.getKeyChar());
	}

	public void mouseClicked(MouseEvent e) {
		cf.getPeople().getPeopleAttributeNow().addMouseUseNum();
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseDragged(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {

	}

	public void mouseWheelMoved(MouseWheelEvent e) {

	}
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().trim().equals(Context.cmd_send_message)) {
			pa.handle();
			cf.getCommunication().sendCharMessage();
			pa.init();
		} else if (e.getActionCommand().trim().equals(Context.cmd_train_start)) {
			int select = JOptionPane.showConfirmDialog(cf,
					"��ѵ��֮ǰ���Խ���һЩС���ԣ�������");
			if (select == JOptionPane.YES_OPTION) {

			} else if (select == JOptionPane.NO_OPTION) {
				cf.getCommunication().sendCharMessage(Context.cmd_train_start_message);
				((JButton) (e.getSource())).setText(Context.cmd_train_end);
			}
		} else if (e.getActionCommand().trim().equals(Context.cmd_train_end)) {
			int select = JOptionPane.showConfirmDialog(cf, "��ȷ���˳�ѵ����");
			if (select == JOptionPane.YES_OPTION) {
				cf.getCommunication().sendCharMessage(Context.cmd_train_end_message);
				((JButton) (e.getSource())).setText(Context.cmd_train_start);
			}
		} else if (e.getActionCommand().trim().equals(Context.cmd_login)) {
			new Login(cf,true);
		}// end if
	}
}
