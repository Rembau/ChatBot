package bot.client.event;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import bot.client.gui.ChatFrame;
import bot.comm.Context;

public class Font_button_effect implements MouseListener{
	private boolean isHaveBorder=false;
	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		JLabel b = (JLabel) e.getSource();
		b.setBorder(new BevelBorder(BevelBorder.LOWERED));
		if(!isHaveBorder){
			isHaveBorder=true;
		} else {
			isHaveBorder=false;
		}
		ChatFrame cf = ChatFrame.instance();
		if(b.getText().equals(Context.font_bold)){
			cf.getAttrset().setBold(isHaveBorder);
		} else if(b.getText().equals(Context.font_italic)){
			cf.getAttrset().setItalic(isHaveBorder);
		} else if(b.getText().equals(Context.font_underline)){
			cf.getAttrset().setUnderline(isHaveBorder);
		}
		cf.repaint_input_panel();
		cf.getTextfield().setCharacterAttributes(cf.getAttrset(), true);
	}

	public void mouseReleased(MouseEvent e) {
		JLabel b = (JLabel) e.getSource();
		if(!isHaveBorder){
			b.setBorder(null);
		} else{
			b.setBorder(new LineBorder(Color.lightGray,1,true));
		}
	}

	public void mouseEntered(MouseEvent e) {
		JLabel b = (JLabel) e.getSource();
		if(b.getBorder()==null){
			b.setBorder(new LineBorder(Color.lightGray,1,true));
		}
	}

	public void mouseExited(MouseEvent e) {
		JLabel b = (JLabel) e.getSource();
		if(!isHaveBorder){
			b.setBorder(null);
		} else{
			b.setBorder(new LineBorder(Color.lightGray,1,true));
		}
	}

}
