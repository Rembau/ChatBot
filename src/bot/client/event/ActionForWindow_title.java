package bot.client.event;

import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
public class ActionForWindow_title implements MouseMotionListener,MouseListener{
	int x,y;
	public void mouseDragged(MouseEvent e) {
		Container top = ((JPanel)e.getSource()).getTopLevelAncestor();
		int driftx = e.getXOnScreen()-x;
		int drifty = e.getYOnScreen()-y;
		top.setLocation(top.getX()+driftx,top.getY()+drifty);
		
		x = e.getXOnScreen();
		y = e.getYOnScreen();
	}

	public void mouseMoved(MouseEvent e) {
		
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		x=e.getXOnScreen();
		y=e.getYOnScreen();
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}
