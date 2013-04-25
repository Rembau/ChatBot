package bot.client.event;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.border.BevelBorder;

public class Button_effect implements MouseListener{
	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		JButton b = (JButton) e.getSource();
		b.setBorderPainted(true);
		b.setBorder(new BevelBorder(BevelBorder.LOWERED));
	}

	public void mouseReleased(MouseEvent e) {
		JButton b = (JButton) e.getSource();
		b.setBorder(new BevelBorder(BevelBorder.RAISED));
	}

	public void mouseEntered(MouseEvent e) {
		JButton b = (JButton) e.getSource();
		b.setBorderPainted(true);
		b.setBorder(new BevelBorder(BevelBorder.RAISED));
	}

	public void mouseExited(MouseEvent e) {
		JButton b = (JButton) e.getSource();
		b.setBorderPainted(false);
	}

}
