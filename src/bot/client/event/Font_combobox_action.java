package bot.client.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import bot.client.gui.ChatFrame;
import bot.comm.Context;

public class Font_combobox_action implements ActionListener {
	private String type;
	public Font_combobox_action(String str) {
		this.type=str;
	}
	public void actionPerformed(ActionEvent e) {
		JComboBox jcb = (JComboBox)e.getSource();
		ChatFrame cf = ChatFrame.instance();
		if(type.equals(Context.font_name)){
			cf.getAttrset().setFontFamily(jcb.getSelectedItem().toString());
		} else {
			cf.getAttrset().setSize(Integer.valueOf(jcb.getSelectedItem().toString()));
		}
		cf.repaint_input_panel();
		cf.getTextfield().setCharacterAttributes(cf.getAttrset(), true);
	}

}
